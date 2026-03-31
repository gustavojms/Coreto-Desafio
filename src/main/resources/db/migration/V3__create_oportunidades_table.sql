CREATE TABLE oportunidades (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organizador_id UUID NOT NULL REFERENCES organizadores(id),
    titulo VARCHAR(150) NOT NULL,
    tipo_oportunidade VARCHAR(30) NOT NULL,
    resumo VARCHAR(600) NOT NULL,
    descricao_detalhada TEXT,
    link_externo VARCHAR(500),
    imagem_url VARCHAR(500),
    anexos JSONB DEFAULT '[]',
    data_abertura DATE,
    data_limite_inscricao DATE NOT NULL,
    data_inicio_prevista DATE,
    tipo_organizador VARCHAR(50) NOT NULL,
    possui_parceria BOOLEAN NOT NULL DEFAULT FALSE,
    parceiros JSONB DEFAULT '[]',
    estagio_elegivel VARCHAR(20),
    trl_elegivel VARCHAR(10),
    status VARCHAR(20) NOT NULL DEFAULT 'RASCUNHO',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ,
    created_by VARCHAR(255),
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMPTZ,
    CONSTRAINT chk_data_inscricao CHECK (data_limite_inscricao >= data_abertura OR data_abertura IS NULL)
);

CREATE TABLE oportunidade_areas_tematicas (
    oportunidade_id UUID NOT NULL REFERENCES oportunidades(id) ON DELETE CASCADE,
    area_tematica VARCHAR(50) NOT NULL,
    PRIMARY KEY (oportunidade_id, area_tematica)
);

CREATE TABLE oportunidade_apoio_oferecido (
    oportunidade_id UUID NOT NULL REFERENCES oportunidades(id) ON DELETE CASCADE,
    apoio_oferecido VARCHAR(50) NOT NULL,
    PRIMARY KEY (oportunidade_id, apoio_oferecido)
);

CREATE TABLE oportunidade_tipo_iniciativa_elegivel (
    oportunidade_id UUID NOT NULL REFERENCES oportunidades(id) ON DELETE CASCADE,
    tipo_iniciativa VARCHAR(50) NOT NULL,
    PRIMARY KEY (oportunidade_id, tipo_iniciativa)
);

CREATE TABLE oportunidade_tags (
    oportunidade_id UUID NOT NULL REFERENCES oportunidades(id) ON DELETE CASCADE,
    tag VARCHAR(100) NOT NULL,
    PRIMARY KEY (oportunidade_id, tag)
);

CREATE INDEX idx_oportunidades_deleted ON oportunidades(deleted);
CREATE INDEX idx_oportunidades_tipo ON oportunidades(tipo_oportunidade);
CREATE INDEX idx_oportunidades_status ON oportunidades(status);
CREATE INDEX idx_oportunidades_organizador ON oportunidades(organizador_id);
CREATE INDEX idx_oportunidades_data_limite ON oportunidades(data_limite_inscricao);
