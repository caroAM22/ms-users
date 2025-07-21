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

DELIMITER //

-- Create trigger to validate user age (must be 18 or older)
CREATE TRIGGER IF NOT EXISTS validate_user_age
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
    IF DATEDIFF(CURRENT_DATE, NEW.birth_date) < 18*365 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'User must be at least 18 years old';
    END IF;
END//

-- Restaura el delimitador
DELIMITER ;

-- Insert roles only if the table is empty
INSERT INTO roles (id, name, description)
SELECT 
    UUID() as id,
    role_name,
    role_description
FROM (
    SELECT 'ADMIN' as role_name, 'Administrator with full access' as role_description
    UNION ALL
    SELECT 'OWNER', 'Restaurant owner'
    UNION ALL
    SELECT 'EMPLOYEE', 'Restaurant employee'
    UNION ALL
    SELECT 'CUSTOMER', 'Regular customer'
) AS roles_to_insert
WHERE NOT EXISTS (SELECT 1 FROM roles);