CREATE TABLE refresh_tokens(

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    token VARCHAR(500) NOT NULL,

    user_id BIGINT NOT NULL,

    expiry_date TIMESTAMP NOT NULL,

    revoked BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_refresh_user
        FOREIGN KEY(user_id)
        REFERENCES users(id)
);