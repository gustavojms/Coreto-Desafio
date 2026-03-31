CREATE TABLE organizadores (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID REFERENCES usuarios(id),
    tipo_organizacao VARCHAR(50) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    descricao_curta VARCHAR(500),
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    logo_url VARCHAR(500) NOT NULL,
    banner_url VARCHAR(500) NOT NULL,
    problema_atuacao TEXT,
    produtos_servicos TEXT,
    territorio_pais VARCHAR(100),
    territorio_estado VARCHAR(100),
    territorio_municipio VARCHAR(255),
    resultados_relevantes TEXT,
    estagio_inovacao VARCHAR(20) NOT NULL,
    parceiro_desejado VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ,
    created_by VARCHAR(255),
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMPTZ
);

CREATE TABLE organizador_usuarios_responsaveis (
    organizador_id UUID NOT NULL REFERENCES organizadores(id) ON DELETE CASCADE,
    usuario_id UUID NOT NULL,
    PRIMARY KEY (organizador_id, usuario_id)
);

CREATE TABLE organizador_publico_alvo (
    organizador_id UUID NOT NULL REFERENCES organizadores(id) ON DELETE CASCADE,
    publico_alvo VARCHAR(50) NOT NULL,
    PRIMARY KEY (organizador_id, publico_alvo)
);

CREATE TABLE organizador_links_oficiais (
    organizador_id UUID NOT NULL REFERENCES organizadores(id) ON DELETE CASCADE,
    link VARCHAR(500) NOT NULL
);

CREATE TABLE organizador_areas_tematicas (
    organizador_id UUID NOT NULL REFERENCES organizadores(id) ON DELETE CASCADE,
    area_tematica VARCHAR(50) NOT NULL,
    PRIMARY KEY (organizador_id, area_tematica)
);

CREATE TABLE organizador_o_que_busca (
    organizador_id UUID NOT NULL REFERENCES organizadores(id) ON DELETE CASCADE,
    apoio_buscado VARCHAR(50) NOT NULL,
    PRIMARY KEY (organizador_id, apoio_buscado)
);

CREATE TABLE organizador_tags (
    organizador_id UUID NOT NULL REFERENCES organizadores(id) ON DELETE CASCADE,
    tag VARCHAR(100) NOT NULL,
    PRIMARY KEY (organizador_id, tag)
);

CREATE INDEX idx_organizadores_deleted ON organizadores(deleted);
CREATE INDEX idx_organizadores_tipo ON organizadores(tipo_organizacao);
CREATE INDEX idx_organizadores_estado ON organizadores(territorio_estado);
CREATE INDEX idx_organizadores_cnpj ON organizadores(cnpj);
