CREATE TABLE users (
                       user_id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50)
);


CREATE TABLE tasks (
                       task_id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(255),
                       description TEXT,
                       status VARCHAR(50),
                       user_id BIGINT REFERENCES users(user_id),
                       created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE attachments (
                             attachment_id BIGSERIAL PRIMARY KEY,
                             file_name VARCHAR(255),
                             s3_key VARCHAR(255),
                             content_type VARCHAR(255),
                             task_id BIGINT REFERENCES tasks(task_id) ON DELETE CASCADE
);