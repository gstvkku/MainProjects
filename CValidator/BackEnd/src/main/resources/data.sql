-- =========================
-- USERS
-- =========================
INSERT INTO app_user (id, name, email, password_hash, created_at)
VALUES
('11111111-1111-1111-1111-111111111111', 'João Silva', 'joao@email.com', '123', NOW()),
('22222222-2222-2222-2222-222222222222', 'Maria Santos', 'maria@email.com', '123', NOW()),
('33333333-3333-3333-3333-333333333333', 'Pedro Costa', 'pedro@email.com', '123', NOW());


-- =========================
-- CVS
-- =========================
INSERT INTO cv (id, file_name, file_path, upload_date, status, user_id)
VALUES
('44444444-4444-4444-4444-444444444444', 'joao_cv.pdf', '/uploads/joao.pdf', NOW(), 'DONE',
 '11111111-1111-1111-1111-111111111111'),

('55555555-5555-5555-5555-555555555555', 'maria_cv.pdf', '/uploads/maria.pdf', NOW(), 'PROCESSING',
 '22222222-2222-2222-2222-222222222222'),

('66666666-6666-6666-6666-666666666666', 'pedro_cv.pdf', '/uploads/pedro.pdf', NOW(), 'UPLOADED',
 '33333333-3333-3333-3333-333333333333');


-- =========================
-- CV ANALYSIS
-- =========================
INSERT INTO cv_analysis (id, score, strengths, weaknesses, suggestions, analyzed_at, cv_id)
VALUES
('77777777-7777-7777-7777-777777777777', 9,
 'Excelente backend em Java e Spring Boot',
 'Pouca experiência em cloud',
 'Aprender AWS e Docker',
 NOW(),
 '44444444-4444-4444-4444-444444444444');


-- =========================
-- SKILLS (cv_skills)
-- =========================
INSERT INTO cv_skills (cv_id, skill)
VALUES

-- João
('44444444-4444-4444-4444-444444444444', 'Java'),
('44444444-4444-4444-4444-444444444444', 'Spring Boot'),
('44444444-4444-4444-4444-444444444444', 'SQL'),
('44444444-4444-4444-4444-444444444444', 'Docker'),

-- Maria
('55555555-5555-5555-5555-555555555555', 'Python'),
('55555555-5555-5555-5555-555555555555', 'Data Analysis'),
('55555555-5555-5555-5555-555555555555', 'Pandas'),

-- Pedro
('66666666-6666-6666-6666-666666666666', 'HTML'),
('66666666-6666-6666-6666-666666666666', 'CSS'),
('66666666-6666-6666-6666-666666666666', 'JavaScript');

