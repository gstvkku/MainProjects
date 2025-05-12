INSERT INTO users (name, email, password) VALUES
('Ana Souza', 'ana.souza@example.com', 'ana123'),
('Carlos Mendes', 'carlos.mendes@example.com', 'carlos123'),
('Juliana Lima', 'juliana.lima@example.com', 'juliana123'),
('Rafael Costa', 'rafael.costa@example.com', 'rafael123'),
('Fernanda Alves', 'fernanda.alves@example.com', 'fernanda123');

INSERT INTO books (title, author, isbn, available_copies) VALUES
('Clean Code', 'Robert C. Martin', '9780132350884', 2),
('Effective Java', 'Joshua Bloch', '9780134685991', 3),
('Spring in Action', 'Craig Walls', '9781617294945', 1),
('Domain-Driven Design', 'Eric Evans', '9780321125217', 0),
('Refactoring', 'Martin Fowler', '9780201485677', 4);

INSERT INTO reservations (user_id, book_id, created_at, status) VALUES
(1, 1, NOW() - INTERVAL '2 days', 'ACTIVE'),  -- Ana reservou "Clean Code"
(1, 2, NOW() - INTERVAL '1 days', 'ACTIVE'),  -- Ana reservou "Effective Java"
(2, 1, NOW() - INTERVAL '5 days', 'CANCELED'),-- Carlos cancelou "Clean Code"
(2, 3, NOW() - INTERVAL '3 days', 'ACTIVE'),  -- Carlos reservou "Spring in Action"
(1, 4, NOW() - INTERVAL '4 days', 'CANCELED'),-- Ana reservou e cancelou "DDD"2
(3, 2, NOW() - INTERVAL '1 hours', 'ACTIVE'); -- Juliana reservou "Effective Java"

