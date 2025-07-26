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