-- Country
CREATE TABLE country (
                         country_code VARCHAR(255) PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         region_code VARCHAR(255),
                         FOREIGN KEY (region_code) REFERENCES region(region_code)
);

-- GpaRequirement
CREATE TABLE gpa_requirement (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 scale VARCHAR(255) NOT NULL,
                                 min_gpa FLOAT NOT NULL,
                                 university_id BIGINT,
                                 FOREIGN KEY (university_id) REFERENCES university(id)
);

-- InterestedCountry
CREATE TABLE interested_country (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    site_user_id BIGINT,
                                    country_code VARCHAR(255),
                                    region_code VARCHAR(255),
                                    FOREIGN KEY (site_user_id) REFERENCES site_user(id),
                                    FOREIGN KEY (country_code) REFERENCES country(country_code),
                                    FOREIGN KEY (region_code) REFERENCES region(region_code)
);

-- Region
CREATE TABLE region (
    region_code VARCHAR(255) PRIMARY KEY
);

-- University
CREATE TABLE university (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            korean_name VARCHAR(255) NOT NULL,
                            english_name VARCHAR(255) NOT NULL,
                            internal_name VARCHAR(255) NOT NULL,
                            recruit_number INT,
                            tuition_fee_payment_type VARCHAR(255), -- Enum, adjust as needed
                            exchange_semester VARCHAR(255), -- Enum, adjust as needed
                            details_for_language TEXT,
                            details_for_apply TEXT,
                            details_for_major TEXT,
                            details_for_accommodation TEXT,
                            homepage_url VARCHAR(255),
                            english_course_url VARCHAR(255),
                            accommodation_url VARCHAR(255),
                            details TEXT,
                            logo_image_url VARCHAR(255),
                            background_image_url VARCHAR(255),
                            country_code VARCHAR(255),
                            region_code VARCHAR(255),
                            FOREIGN KEY (country_code) REFERENCES country(country_code),
                            FOREIGN KEY (region_code) REFERENCES region(region_code)
);

-- WishUniversity
CREATE TABLE wish_university (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 university_id BIGINT,
                                 site_user_id BIGINT,
                                 FOREIGN KEY (university_id) REFERENCES university(id),
                                 FOREIGN KEY (site_user_id) REFERENCES site_user(id)
);

-- InterestedRegion
CREATE TABLE interested_region (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   site_user_id BIGINT,
                                   region_code VARCHAR(255),
                                   FOREIGN KEY (site_user_id) REFERENCES site_user(id),
                                   FOREIGN KEY (region_code) REFERENCES region(region_code)
);

-- Application
CREATE TABLE application (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             language_test_type VARCHAR(255), -- Enum, adjust as needed
                             language_test_score VARCHAR(255),
                             language_test_report_url VARCHAR(255),
                             gpa FLOAT NOT NULL,
                             gpa_report_url VARCHAR(255),
                             verify_status VARCHAR(255),
                             first_choice_university_id BIGINT,
                             second_choice_university_id BIGINT,
                             site_user_id BIGINT,
                             FOREIGN KEY (first_choice_university_id) REFERENCES university(id),
                             FOREIGN KEY (second_choice_university_id) REFERENCES university(id),
                             FOREIGN KEY (site_user_id) REFERENCES site_user(id)
);

-- LanguageRequirement
CREATE TABLE language_requirement (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      language_test_type VARCHAR(255), -- Enum, adjust as needed
                                      min_score VARCHAR(255),
                                      university_id BIGINT,
                                      FOREIGN KEY (university_id) REFERENCES university(id)
);

-- SiteUser
CREATE TABLE site_user (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           email VARCHAR(255) NOT NULL UNIQUE,
                           nickname VARCHAR(255) NOT NULL UNIQUE,
                           preparation_stage VARCHAR(255), -- Enum, adjust as needed
                           profile_image_url VARCHAR(255),
                           nickname_modified_at TIMESTAMP,
                           quited_at TIMESTAMP,
                           role VARCHAR(255) -- Enum, adjust as needed
);
