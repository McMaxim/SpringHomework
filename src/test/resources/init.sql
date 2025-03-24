CREATE TABLE IF NOT EXISTS employees (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    position VARCHAR(255),
    department VARCHAR(255),
    experience_years INTEGER
);

CREATE TABLE IF NOT EXISTS projects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    status VARCHAR(50)
);

INSERT INTO employees (full_name, email, position, department, experience_years)
VALUES 
    ('Test Employee 1', 'test1@example.com', 'Developer', 'Engineering', 3),
    ('Test Employee 2', 'test2@example.com', 'Manager', 'Management', 5),
    ('Test Employee 3', 'test3@example.com', 'QA', 'Quality Assurance', 2);

INSERT INTO projects (name, description, start_date, end_date, status)
VALUES 
    ('Test Project 1', 'Description of test project 1', '2023-01-01', '2023-12-31', 'In Progress'),
    ('Test Project 2', 'Description of test project 2', '2023-02-15', '2023-10-15', 'Planning'); 