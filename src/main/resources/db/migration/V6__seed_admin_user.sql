-- Default admin user (password: admin123)
-- BCrypt hash generated with strength 12
INSERT INTO usuarios (id, nome, email, senha_hash, role)
VALUES (
    'a0000000-0000-0000-0000-000000000001',
    'Administrador',
    'admin@coreto.com.br',
    '$2a$12$TY13kR61ynQWpgJI2hKeoeOkwN1EEIwcX0lbpgBvoV51o21i0TdTu',
    'ADMIN'
);
