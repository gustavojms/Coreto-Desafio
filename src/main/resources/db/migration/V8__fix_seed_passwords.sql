-- Fix password hashes (BCrypt strength 12)
-- admin@coreto.com.br -> admin123
UPDATE usuarios SET senha_hash = '$2a$12$TY13kR61ynQWpgJI2hKeoeOkwN1EEIwcX0lbpgBvoV51o21i0TdTu'
WHERE email = 'admin@coreto.com.br';

-- All demo users -> 123456
UPDATE usuarios SET senha_hash = '$2a$12$L2CbOflPyrNmDuoMweBI6ODSX.ZAdI6Ozy6TdbbcmOcPPYfUt/CSW'
WHERE email IN ('maria@emprel.com', 'carlos@startup.io', 'ana@dev.com', 'joao@portodigital.org', 'fernanda@ufpe.br', 'pedro@design.com', 'julia@data.com');
