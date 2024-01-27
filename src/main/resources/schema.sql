DROP TABLE IF EXISTS `application`, `country`, `gpa_requirement`, `interested_country`, `interested_region`, `language_requirement`, `region`, `site_user`, `university`, `wish_university`;

CREATE TABLE `application` (
                               `gpa` float NOT NULL,
                               `first_choice_univ_id` bigint DEFAULT NULL,
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `second_choice_univ_id` bigint DEFAULT NULL,
                               `site_user_id` bigint DEFAULT NULL,
                               `language_test_type` varchar(255) NOT NULL,
                               `verify_status` varchar(50) NOT NULL,
                               `gpa_report_url` varchar(500) NOT NULL,
                               `language_test_report_url` varchar(500) NOT NULL,
                               `language_test_score` varchar(255) NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FK4xffa66ucb9651me7uc8ek71c` (`first_choice_univ_id`),
                               KEY `FK401wya8j2e7jfrx7hu6gcc4fx` (`second_choice_univ_id`),
                               KEY `FKs4s3hebtn7vwd0b4xt8msxsis` (`site_user_id`),
                               CONSTRAINT `FK401wya8j2e7jfrx7hu6gcc4fx` FOREIGN KEY (`second_choice_univ_id`) REFERENCES `university` (`id`),
                               CONSTRAINT `FK4xffa66ucb9651me7uc8ek71c` FOREIGN KEY (`first_choice_univ_id`) REFERENCES `university` (`id`),
                               CONSTRAINT `FKs4s3hebtn7vwd0b4xt8msxsis` FOREIGN KEY (`site_user_id`) REFERENCES `site_user` (`id`)
);

CREATE TABLE `country` (
                           `country_code` varchar(255) NOT NULL,
                           `region_code` varchar(10) DEFAULT NULL,
                           PRIMARY KEY (`country_code`)
);

CREATE TABLE `gpa_requirement` (
                                   `min_gpa` float NOT NULL,
                                   `scale` varchar(5) NOT NULL,
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `university_id` bigint DEFAULT NULL,
                                   PRIMARY KEY (`id`),
                                   KEY `FK74nj7od0mj9e63ervpl0wmy4q` (`university_id`),
                                   CONSTRAINT `FK74nj7od0mj9e63ervpl0wmy4q` FOREIGN KEY (`university_id`) REFERENCES `university` (`id`)
);

CREATE TABLE `interested_country` (
                                      `country_code` varchar(2) DEFAULT NULL,
                                      `id` bigint NOT NULL AUTO_INCREMENT,
                                      `site_user_id` bigint DEFAULT NULL,
                                      PRIMARY KEY (`id`),
                                      KEY `FK26u5am55jefclcd7r5smk8ai7` (`site_user_id`),
                                      CONSTRAINT `FK26u5am55jefclcd7r5smk8ai7` FOREIGN KEY (`site_user_id`) REFERENCES `site_user` (`id`)
);

CREATE TABLE `interested_region` (
                                     `id` bigint NOT NULL AUTO_INCREMENT,
                                     `site_user_id` bigint DEFAULT NULL,
                                     `region_code` varchar(10) DEFAULT NULL,
                                     PRIMARY KEY (`id`),
                                     KEY `FKia6h0pbisqhgm3lkeya6vqo4w` (`site_user_id`),
                                     CONSTRAINT `FKia6h0pbisqhgm3lkeya6vqo4w` FOREIGN KEY (`site_user_id`) REFERENCES `site_user` (`id`)
);

CREATE TABLE `language_requirement` (
                                        `id` bigint NOT NULL AUTO_INCREMENT,
                                        `university_id` bigint DEFAULT NULL,
                                        `language_test_type` varchar(255) NOT NULL,
                                        `min_score` varchar(255) NOT NULL,
                                        PRIMARY KEY (`id`),
                                        KEY `FKp723kfidkuu8kus5svxnqq5hw` (`university_id`),
                                        CONSTRAINT `FKp723kfidkuu8kus5svxnqq5hw` FOREIGN KEY (`university_id`) REFERENCES `university` (`id`)
);

CREATE TABLE `region` (
                          `region_code` varchar(255) NOT NULL,
                          PRIMARY KEY (`region_code`)
);

CREATE TABLE `site_user` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `nickname_modified_at` datetime(6) DEFAULT NULL,
                             `quited_at` datetime(6) DEFAULT NULL,
                             `birth` varchar(20) NOT NULL,
                             `gender` varchar(255) NOT NULL,
                             `preparation_stage` varchar(255) NOT NULL,
                             `role` varchar(255) NOT NULL,
                             `email` varchar(100) NOT NULL,
                             `nickname` varchar(100) NOT NULL,
                             `profile_image_url` varchar(500) DEFAULT NULL,
                             PRIMARY KEY (`id`)
);

CREATE TABLE `university` (
                              `country_code` varchar(2) DEFAULT NULL,
                              `recruit_number` int NOT NULL,
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `region_code` varchar(10) DEFAULT NULL,
                              `exchange_semester` varchar(255) NOT NULL,
                              `tuition_fee_payment_type` varchar(255) NOT NULL,
                              `english_name` varchar(100) NOT NULL,
                              `internal_name` varchar(100) NOT NULL,
                              `korean_name` varchar(100) NOT NULL,
                              `accommodation_url` varchar(500) DEFAULT NULL,
                              `background_image_url` varchar(500) NOT NULL,
                              `details` varchar(500) DEFAULT NULL,
                              `english_course_url` varchar(500) DEFAULT NULL,
                              `homepage_url` varchar(500) DEFAULT NULL,
                              `logo_image_url` varchar(500) NOT NULL,
                              `details_for_accommodation` varchar(1000) DEFAULT NULL,
                              `details_for_apply` varchar(1000) DEFAULT NULL,
                              `details_for_language` varchar(1000) DEFAULT NULL,
                              `details_for_major` varchar(1000) DEFAULT NULL,
                              PRIMARY KEY (`id`)
);

CREATE TABLE `wish_university` (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `site_user_id` bigint DEFAULT NULL,
                                   `university_id` bigint DEFAULT NULL,
                                   PRIMARY KEY (`id`),
                                   KEY `FKrrhud921brslcukx6fyuh0th3` (`site_user_id`),
                                   KEY `FKhj3gn3mqmfeiiw9jt83g7t3rk` (`university_id`),
                                   CONSTRAINT `FKhj3gn3mqmfeiiw9jt83g7t3rk` FOREIGN KEY (`university_id`) REFERENCES `university` (`id`),
                                   CONSTRAINT `FKrrhud921brslcukx6fyuh0th3` FOREIGN KEY (`site_user_id`) REFERENCES `site_user` (`id`)
);
