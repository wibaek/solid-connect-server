package com.example.solidconnection.scheduler;

import com.example.solidconnection.siteuser.service.SiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRemovalScheduler {
    private final SiteUserService siteUserService;

    @Scheduled(cron = "0 0 0 * * ?")     // 매일 자정에 실행
    public void scheduledUserRemoval() {
        siteUserService.deleteUsersNeverVisitedAfterQuited();
    }
}