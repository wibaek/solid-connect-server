package com.example.solidconnection.post.service;

import com.example.solidconnection.board.domain.Board;
import com.example.solidconnection.board.dto.PostFindBoardResponse;
import com.example.solidconnection.board.repository.BoardRepository;
import com.example.solidconnection.comment.dto.PostFindCommentResponse;
import com.example.solidconnection.comment.service.CommentService;
import com.example.solidconnection.custom.exception.CustomException;
import com.example.solidconnection.entity.PostImage;
import com.example.solidconnection.post.domain.Post;
import com.example.solidconnection.post.domain.PostLike;
import com.example.solidconnection.post.dto.PostCreateRequest;
import com.example.solidconnection.post.dto.PostCreateResponse;
import com.example.solidconnection.post.dto.PostDeleteResponse;
import com.example.solidconnection.post.dto.PostDislikeResponse;
import com.example.solidconnection.post.dto.PostFindPostImageResponse;
import com.example.solidconnection.post.dto.PostFindResponse;
import com.example.solidconnection.post.dto.PostLikeResponse;
import com.example.solidconnection.post.dto.PostUpdateRequest;
import com.example.solidconnection.post.dto.PostUpdateResponse;
import com.example.solidconnection.post.repository.PostLikeRepository;
import com.example.solidconnection.post.repository.PostRepository;
import com.example.solidconnection.s3.S3Service;
import com.example.solidconnection.s3.UploadedFileUrlResponse;
import com.example.solidconnection.service.RedisService;
import com.example.solidconnection.siteuser.domain.SiteUser;
import com.example.solidconnection.siteuser.dto.PostFindSiteUserResponse;
import com.example.solidconnection.siteuser.repository.SiteUserRepository;
import com.example.solidconnection.type.BoardCode;
import com.example.solidconnection.type.ImgType;
import com.example.solidconnection.type.PostCategory;
import com.example.solidconnection.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.solidconnection.custom.exception.ErrorCode.CAN_NOT_DELETE_OR_UPDATE_QUESTION;
import static com.example.solidconnection.custom.exception.ErrorCode.CAN_NOT_UPLOAD_MORE_THAN_FIVE_IMAGES;
import static com.example.solidconnection.custom.exception.ErrorCode.DUPLICATE_POST_LIKE;
import static com.example.solidconnection.custom.exception.ErrorCode.INVALID_BOARD_CODE;
import static com.example.solidconnection.custom.exception.ErrorCode.INVALID_POST_ACCESS;
import static com.example.solidconnection.custom.exception.ErrorCode.INVALID_POST_CATEGORY;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SiteUserRepository siteUserRepository;
    private final BoardRepository boardRepository;
    private final S3Service s3Service;
    private final CommentService commentService;
    private final RedisService redisService;
    private final RedisUtils redisUtils;
    private final PostLikeRepository postLikeRepository;

    private String validateCode(String code) {
        try {
            return String.valueOf(BoardCode.valueOf(code));
        } catch (IllegalArgumentException ex) {
            throw new CustomException(INVALID_BOARD_CODE);
        }
    }

    private void validateOwnership(Post post, String email) {
        if (!post.getSiteUser().getEmail().equals(email)) {
            throw new CustomException(INVALID_POST_ACCESS);
        }
    }

    private void validateFileSize(List<MultipartFile> imageFile) {
        if (imageFile.isEmpty()) {
            return;
        }
        if (imageFile.size() > 5) {
            throw new CustomException(CAN_NOT_UPLOAD_MORE_THAN_FIVE_IMAGES);
        }
    }

    private void validateQuestion(Post post) {
        if (post.getIsQuestion()) {
            throw new CustomException(CAN_NOT_DELETE_OR_UPDATE_QUESTION);
        }
    }

    private void validatePostCategory(String category) {
        if (!EnumUtils.isValidEnum(PostCategory.class, category) || category.equals(PostCategory.전체.toString())) {
            throw new CustomException(INVALID_POST_CATEGORY);
        }
    }

    private Boolean getIsOwner(Post post, String email) {
        return post.getSiteUser().getEmail().equals(email);
    }

    private Boolean getIsLiked(Post post, SiteUser siteUser) {
        return postLikeRepository.findPostLikeByPostAndSiteUser(post, siteUser)
                .isPresent();
    }

    @Transactional
    public PostCreateResponse createPost(String email, String code, PostCreateRequest postCreateRequest,
                                         List<MultipartFile> imageFile) {

        // 유효성 검증
        String boardCode = validateCode(code);
        validatePostCategory(postCreateRequest.postCategory());
        validateFileSize(imageFile);

        // 객체 생성
        SiteUser siteUser = siteUserRepository.getByEmail(email);
        Board board = boardRepository.getByCode(boardCode);
        Post post = postCreateRequest.toEntity(siteUser, board);
        // 이미지 처리
        savePostImages(imageFile, post);
        Post createdPost = postRepository.save(post);

        return PostCreateResponse.from(createdPost);
    }

    @Transactional
    public PostUpdateResponse updatePost(String email, String code, Long postId, PostUpdateRequest postUpdateRequest,
                                         List<MultipartFile> imageFile) {

        // 유효성 검증
        String boardCode = validateCode(code);
        Post post = postRepository.getById(postId);
        validateOwnership(post, email);
        validateQuestion(post);
        validateFileSize(imageFile);

        // 기존 사진 모두 삭제
        removePostImages(post);
        // 새로운 이미지 등록
        savePostImages(imageFile, post);
        // 게시글 내용 수정
        post.update(postUpdateRequest);

        return PostUpdateResponse.from(post);
    }

    private void savePostImages(List<MultipartFile> imageFile, Post post) {
        if (imageFile.isEmpty()) {
            return;
        }
        List<UploadedFileUrlResponse> uploadedFileUrlResponseList = s3Service.uploadFiles(imageFile, ImgType.COMMUNITY);
        for (UploadedFileUrlResponse uploadedFileUrlResponse : uploadedFileUrlResponseList) {
            PostImage postImage = new PostImage(uploadedFileUrlResponse.fileUrl());
            postImage.setPost(post);
        }
    }

    private void removePostImages(Post post) {
        for (PostImage postImage : post.getPostImageList()) {
            s3Service.deletePostImage(postImage.getUrl());
        }
        post.getPostImageList().clear();
    }

    @Transactional(readOnly = true)
    public PostFindResponse findPostById(String email, String code, Long postId) {

        String boardCode = validateCode(code);

        Post post = postRepository.getByIdUsingEntityGraph(postId);
        SiteUser siteUser = siteUserRepository.getByEmail(email);
        Boolean isOwner = getIsOwner(post, email);
        Boolean isLiked = getIsLiked(post, siteUser);

        PostFindBoardResponse boardPostFindResultDTO = PostFindBoardResponse.from(post.getBoard());
        PostFindSiteUserResponse siteUserPostFindResultDTO = PostFindSiteUserResponse.from(post.getSiteUser());
        List<PostFindPostImageResponse> postImageFindResultDTOList = PostFindPostImageResponse.from(post.getPostImageList());
        List<PostFindCommentResponse> commentFindResultDTOList = commentService.findCommentsByPostId(email, postId);

        // caching && 어뷰징 방지
        if (redisService.isPresent(redisUtils.getValidatePostViewCountRedisKey(email, postId))) {
            redisService.increaseViewCount(redisUtils.getPostViewCountRedisKey(postId));
        }

        return PostFindResponse.from(
                post, isOwner, isLiked, boardPostFindResultDTO, siteUserPostFindResultDTO, commentFindResultDTOList, postImageFindResultDTOList);
    }

    @Transactional
    public PostDeleteResponse deletePostById(String email, String code, Long postId) {

        String boardCode = validateCode(code);
        Post post = postRepository.getById(postId);
        validateOwnership(post, email);
        validateQuestion(post);

        removePostImages(post);
        post.resetBoardAndSiteUser();
        // cache out
        redisService.deleteKey(redisUtils.getPostViewCountRedisKey(postId));
        postRepository.deleteById(post.getId());

        return new PostDeleteResponse(postId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public PostLikeResponse likePost(String email, String code, Long postId) {

        String boardCode = validateCode(code);
        Post post = postRepository.getById(postId);
        SiteUser siteUser = siteUserRepository.getByEmail(email);
        validateDuplicatePostLike(post, siteUser);

        PostLike postLike = new PostLike();
        postLike.setPostAndSiteUser(post, siteUser);
        postLikeRepository.save(postLike);
        postRepository.increaseLikeCount(post.getId());

        return PostLikeResponse.from(postRepository.getById(postId)); // 실시간성을 위한 재조회
    }

    private void validateDuplicatePostLike(Post post, SiteUser siteUser) {
        if (postLikeRepository.findPostLikeByPostAndSiteUser(post, siteUser).isPresent()) {
            throw new CustomException(DUPLICATE_POST_LIKE);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public PostDislikeResponse dislikePost(String email, String code, Long postId) {

        String boardCode = validateCode(code);
        Post post = postRepository.getById(postId);
        SiteUser siteUser = siteUserRepository.getByEmail(email);

        PostLike postLike = postLikeRepository.getByPostAndSiteUser(post, siteUser);
        postLike.resetPostAndSiteUser();
        postLikeRepository.deleteById(postLike.getId());
        postRepository.decreaseLikeCount(post.getId());

        return PostDislikeResponse.from(postRepository.getById(postId)); // 실시간성을 위한 재조회
    }
}
