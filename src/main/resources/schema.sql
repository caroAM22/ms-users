-- Create roles table
CREATE TABLE IF NOT EXISTS roles (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    document_number BIGINT NOT NULL UNIQUE,
    phone VARCHAR(13) NOT NULL,
    birth_date DATE NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id CHAR(36) NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);