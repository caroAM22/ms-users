-- Create roles table
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    document_number VARCHAR(20) NOT NULL UNIQUE,
    phone VARCHAR(13) NOT NULL,
    birth_date DATE NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Create trigger to validate user age (must be 18 or older)
DELIMITER //
CREATE TRIGGER IF NOT EXISTS validate_user_age
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
    IF DATEDIFF(CURRENT_DATE, NEW.birth_date) < 18*365 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'User must be at least 18 years old';
    END IF;
END//
DELIMITER ;