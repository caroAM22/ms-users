-- Script de inicialización de datos para Plaza Comidas - Microservicio de Usuarios

-- Insert roles
INSERT IGNORE INTO roles (name, description) VALUES
('ADMIN', 'Administrator with full access'),
('OWNER', 'Restaurant owner'),
('EMPLOYEE', 'Restaurant employee'),
('CUSTOMER', 'Regular customer');

-- Insert admin user
INSERT IGNORE INTO users (
    name, lastname, document_number, phone,
    birth_date, email, password, role_id
) VALUES (
    'Admin', 'User', '12345678', '+573001234567',
    '1990-01-01', 'admin@plazacomidas.com', 
    '$2a$10$7L.nJ6xXChR616G35L4l2OpJM9TlHtpSwAwfBpVWmSmDDhiF9k9d2',
    (SELECT id FROM roles WHERE name = 'ADMIN')
);