CREATE TABLE users(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name varchar(255),
    email varchar(255) UNIQUE,
    password varchar(255),
    language_preference varchar(255)
)