-- Create projects table
CREATE TABLE projects (
  id BIGSERIAL PRIMARY KEY,             -- unique ID auto-increment
  key VARCHAR(16) NOT NULL UNIQUE,      -- short code (like "CORE")
  name VARCHAR(100) NOT NULL,           -- project name
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW() -- timestamp, default now
);

-- Create users table
CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,   -- unique email per user
  name VARCHAR(100) NOT NULL
);

-- Define allowed statuses for issues
CREATE TYPE issue_status AS ENUM ('OPEN','IN_PROGRESS','DONE');

-- Create issues table
CREATE TABLE issues (
  id BIGSERIAL PRIMARY KEY,
  project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
  assignee_id BIGINT REFERENCES users(id), -- optional
  title VARCHAR(200) NOT NULL,
  description TEXT,
  status issue_status NOT NULL DEFAULT 'OPEN',
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Helpful indexes for searching/filtering
CREATE INDEX idx_issues_project ON issues(project_id);
CREATE INDEX idx_issues_status ON issues(status);
CREATE INDEX idx_issues_assignee ON issues(assignee_id);
