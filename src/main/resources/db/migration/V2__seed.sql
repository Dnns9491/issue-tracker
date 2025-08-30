-- Insert test projects
INSERT INTO projects("key", name, created_at) VALUES
    ('CORE', 'Core Platform', CURRENT_TIMESTAMP),
    ('WEB', 'Web Application', CURRENT_TIMESTAMP),
    ('MOBILE', 'Mobile App', CURRENT_TIMESTAMP),
    ('API', 'REST API Service', CURRENT_TIMESTAMP);

-- Insert test users
INSERT INTO users(email, name) VALUES
    ('john.doe@example.com', 'John Doe'),
    ('jane.smith@example.com', 'Jane Smith'),
    ('mike.wilson@example.com', 'Mike Wilson'),
    ('sarah.jones@example.com', 'Sarah Jones'),
    ('dev@example.com', 'Dev One'),
    ('admin@example.com', 'Admin User');

-- Insert test issues
INSERT INTO issues(project_id, assignee_id, title, description, status, created_at, updated_at) VALUES
    (1, 1, 'Fix login authentication bug', 'Users cannot login with special characters in password', 'OPEN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 2, 'Improve database performance', 'Query optimization needed for user search', 'IN_PROGRESS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 3, 'Add dark mode support', 'Implement dark theme for better user experience', 'OPEN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 1, 'Fix responsive design issues', 'Mobile layout breaks on small screens', 'RESOLVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 4, 'Implement push notifications', 'Add real-time notifications for mobile users', 'IN_PROGRESS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 2, 'Fix crash on startup', 'App crashes when opening on iOS 15', 'OPEN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 5, 'Add rate limiting', 'Implement API rate limiting to prevent abuse', 'OPEN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 3, 'Update API documentation', 'Swagger docs are outdated and missing endpoints', 'RESOLVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, NULL, 'Security audit findings', 'Address security vulnerabilities found in audit', 'OPEN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 4, 'Performance optimization', 'Page load times are too slow on dashboard', 'IN_PROGRESS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
