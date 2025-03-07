-- src/main/resources/db/migration/V7__create-table-refresh_token.sql

CREATE TABLE refresh_token (
                               id BIGSERIAL PRIMARY KEY,
                               token TEXT NOT NULL,
                               user_id TEXT NOT NULL,
                               expiry_date TIMESTAMP NOT NULL,
                               CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);