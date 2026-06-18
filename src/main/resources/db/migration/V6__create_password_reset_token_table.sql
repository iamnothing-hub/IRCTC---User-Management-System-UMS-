CREATE TABLE password_reset_tokens(

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    token VARCHAR(255) NOT NULL,

    user_id BIGINT NOT NULL,

    expiry_date TIMESTAMP NOT NULL,

    used BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_reset_user
        FOREIGN KEY(user_id)
        REFERENCES users(id)
);