-- Inserir clientes
INSERT INTO customer (id, email, password, phone, company_name, creation_date)
VALUES
    (1, 'cliente1@email.com', 'senha123', 123456789, 'Empresa Alpha', NOW()),
    (2, 'cliente2@email.com', 'senha456', 987654321, 'Empresa Beta', NOW())
ON CONFLICT (id) DO NOTHING;

-- Inserir requisições (sem a coluna documents)
INSERT INTO requests (
    id, customer_id, sector, project_name, response, evaluation,
    general_description, objectives, problems, type_of_services,
    expected_features, preferred_technologies, restrictions_or_requests,
    budget, deadline, additional_comments
)
VALUES
    (1, 1, 'Tecnologia', 'Projeto X', 'Em análise', 0,
     'Descrição geral do projeto', 'Objetivo principal', 'Problemas enfrentados', 'Consultoria',
     'Funcionalidades esperadas', 'Java, Spring Boot', 'Sem requisitos especiais',
     5000.00, '2025-12-31', 'Nenhum comentário'),

    (2, 2, 'Educação', 'Projeto Y', 'Aprovado', 4,
     'Sistema para escolas', 'Melhorar gestão escolar', 'Falta de digitalização', 'Desenvolvimento',
     'Interface amigável', 'React, Node.js', 'Suporte multilíngue',
     8000.00, '2025-10-15', 'Entrega urgente');
