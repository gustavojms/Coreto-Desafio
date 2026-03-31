-- Default admin user (password: admin123)
-- BCrypt hash generated with strength 12
INSERT INTO usuarios (id, nome, email, senha_hash, role)
VALUES (
    'a0000000-0000-0000-0000-000000000001',
    'Administrador',
    'admin@coreto.com.br',
    '$2a$12$LJ3m4ys3uz0b75xUGEOVPeKMCnMbqhHLCJkEseJBLKRSmFa2jHKSy',
    'ADMIN'
);
