CREATE TABLE project_status_info (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    timestamp TIMESTAMP,
    project_id BIGINT,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
); 