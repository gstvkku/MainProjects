CREATE TABLE users(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name varchar(255),
    email varchar(255),
    password varchar(255)
)