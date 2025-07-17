-- Script de inicialización de datos para Plaza Comidas - Microservicio de Usuarios

-- Insertar roles del sistema
INSERT INTO roles (nombre, descripcion) VALUES 
('CLIENTE', 'Usuario que puede realizar pedidos en los restaurantes'),
('ADMIN', 'Administrador del sistema con acceso completo'),
('PROPIETARIO', 'Propietario de restaurante que puede gestionar su establecimiento'),
('EMPLEADO', 'Empleado de restaurante que puede preparar pedidos')
ON DUPLICATE KEY UPDATE descripcion = VALUES(descripcion);

-- Insertar usuario administrador por defecto (opcional)
-- Contraseña: admin123 (encriptada con BCrypt)
INSERT INTO usuarios (nombre, apellido, numero_documento, celular, fecha_nacimiento, correo, clave, id_rol) VALUES 
('Admin', 'Sistema', '12345678', '+573001234567', '1990-01-01', 'admin@plazacomidas.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 2)
ON DUPLICATE KEY UPDATE 
    nombre = VALUES(nombre),
    apellido = VALUES(apellido),
    celular = VALUES(celular),
    fecha_nacimiento = VALUES(fecha_nacimiento),
    clave = VALUES(clave); 