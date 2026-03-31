CREATE TABLE resolvedores (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID REFERENCES usuarios(id),
    tipo_iniciativa VARCHAR(50) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    possui_vinculo BOOLEAN NOT NULL DEFAULT FALSE,
    vinculo_detalhes VARCHAR(500),
    nome_responsavel VARCHAR(255) NOT NULL,
    papel VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefone_whatsapp VARCHAR(20) NOT NULL,
    dedicacao VARCHAR(20) NOT NULL,
    descricao_curta VARCHAR(500) NOT NULL,
    problema TEXT NOT NULL,
    solucao TEXT NOT NULL,
    trl_grupo VARCHAR(10) NOT NULL,
    estagio_negocio VARCHAR(30) NOT NULL,
    parceiro_desejado VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ,
    created_by VARCHAR(255),
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMPTZ
);

CREATE TABLE resolvedor_publico_alvo (
    resolvedor_id UUID NOT NULL REFERENCES resolvedores(id) ON DELETE CASCADE,
    publico_alvo VARCHAR(50) NOT NULL,
    PRIMARY KEY (resolvedor_id, publico_alvo)
);

CREATE TABLE resolvedor_areas_tematicas (
    resolvedor_id UUID NOT NULL REFERENCES resolvedores(id) ON DELETE CASCADE,
    area_tematica VARCHAR(50) NOT NULL,
    PRIMARY KEY (resolvedor_id, area_tematica)
);

CREATE TABLE resolvedor_apoio_buscado (
    resolvedor_id UUID NOT NULL REFERENCES resolvedores(id) ON DELETE CASCADE,
    apoio_buscado VARCHAR(50) NOT NULL,
    PRIMARY KEY (resolvedor_id, apoio_buscado)
);

CREATE TABLE resolvedor_links_oficiais (
    resolvedor_id UUID NOT NULL REFERENCES resolvedores(id) ON DELETE CASCADE,
    link VARCHAR(500) NOT NULL
);

CREATE TABLE resolvedor_tags (
    resolvedor_id UUID NOT NULL REFERENCES resolvedores(id) ON DELETE CASCADE,
    tag VARCHAR(100) NOT NULL,
    PRIMARY KEY (resolvedor_id, tag)
);

CREATE INDEX idx_resolvedores_deleted ON resolvedores(deleted);
CREATE INDEX idx_resolvedores_tipo ON resolvedores(tipo_iniciativa);
CREATE INDEX idx_resolvedores_estagio ON resolvedores(estagio_negocio);
