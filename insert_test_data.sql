-- Insert test project
INSERT INTO projects ("key", name, created_at) VALUES ('DEMO', 'Demo Project', CURRENT_TIMESTAMP);

-- Insert test users  
INSERT INTO users (email, name) VALUES ('john@example.com', 'John Doe');
INSERT INTO users (email, name) VALUES ('jane@example.com', 'Jane Smith');
