CREATE TABLE talentos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID REFERENCES usuarios(id),
    nome_completo VARCHAR(255) NOT NULL,
    nome_social VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone_whatsapp VARCHAR(20) NOT NULL,
    mini_bio VARCHAR(500) NOT NULL,
    pais VARCHAR(100) NOT NULL,
    estado VARCHAR(100) NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    linkedin VARCHAR(500),
    github VARCHAR(500),
    portfolio VARCHAR(500),
    senioridade VARCHAR(20) NOT NULL,
    horas_semana INTEGER NOT NULL,
    tipo_atuacao VARCHAR(20) NOT NULL,
    formato VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ,
    created_by VARCHAR(255),
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMPTZ
);

CREATE TABLE talento_skills (
    talento_id UUID NOT NULL REFERENCES talentos(id) ON DELETE CASCADE,
    skill VARCHAR(100) NOT NULL,
    PRIMARY KEY (talento_id, skill)
);

CREATE TABLE talento_areas_tematicas (
    talento_id UUID NOT NULL REFERENCES talentos(id) ON DELETE CASCADE,
    area_tematica VARCHAR(50) NOT NULL,
    PRIMARY KEY (talento_id, area_tematica)
);

CREATE TABLE talento_tags (
    talento_id UUID NOT NULL REFERENCES talentos(id) ON DELETE CASCADE,
    tag VARCHAR(100) NOT NULL,
    PRIMARY KEY (talento_id, tag)
);

CREATE INDEX idx_talentos_deleted ON talentos(deleted);
CREATE INDEX idx_talentos_senioridade ON talentos(senioridade);
CREATE INDEX idx_talentos_tipo_atuacao ON talentos(tipo_atuacao);
CREATE INDEX idx_talentos_estado ON talentos(estado);
CREATE INDEX idx_talentos_email ON talentos(email);
