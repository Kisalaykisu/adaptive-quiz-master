-- Existing questions from basic version + more
INSERT INTO question (text, topic, difficulty, model_answer_hint) VALUES
-- ... your existing 10+ ...
('What is Spring Boot?', 'Programming', 'medium', 'Framework for Java microservices with auto-config.'),
('Explain machine learning.', 'AI', 'hard', 'Algorithms that learn from data to make predictions.');

-- Users (password: 'password' - use BCrypt tool to generate if changing)
INSERT INTO "user" (username, password, role) VALUES
                                                  ('student1', '$2a$10$K.RVCIWRy6L07h7Np9cVtuOEW6ZV0v2Q4yM1LBSwTrJiaIf0rU6JG', 'STUDENT'),
                                                  ('teacher1', '$2a$10$K.RVCIWRy6L07h7Np9cVtuOEW6ZV0v2Q4yM1LBSwTrJiaIf0rU6JG', 'TEACHER');