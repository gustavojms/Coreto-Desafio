-- =============================================
-- SEED: Demo users (all passwords: "123456")
-- BCrypt hash: $2a$12$L2CbOflPyrNmDuoMweBI6ODSX.ZAdI6Ozy6TdbbcmOcPPYfUt/CSW
-- =============================================

INSERT INTO usuarios (id, nome, email, senha_hash, role) VALUES
('b0000000-0000-0000-0000-000000000001', 'Maria Organizadora', 'maria@emprel.com', '$2a$12$L2CbOflPyrNmDuoMweBI6ODSX.ZAdI6Ozy6TdbbcmOcPPYfUt/CSW', 'ORGANIZADOR'),
('c0000000-0000-0000-0000-000000000001', 'Carlos Startup', 'carlos@startup.io', '$2a$12$L2CbOflPyrNmDuoMweBI6ODSX.ZAdI6Ozy6TdbbcmOcPPYfUt/CSW', 'RESOLVEDOR'),
('d0000000-0000-0000-0000-000000000001', 'Ana Desenvolvedora', 'ana@dev.com', '$2a$12$L2CbOflPyrNmDuoMweBI6ODSX.ZAdI6Ozy6TdbbcmOcPPYfUt/CSW', 'TALENTO'),
('b0000000-0000-0000-0000-000000000002', 'Joao Porto Digital', 'joao@portodigital.org', '$2a$12$L2CbOflPyrNmDuoMweBI6ODSX.ZAdI6Ozy6TdbbcmOcPPYfUt/CSW', 'ORGANIZADOR'),
('c0000000-0000-0000-0000-000000000002', 'Fernanda Lab UFPE', 'fernanda@ufpe.br', '$2a$12$L2CbOflPyrNmDuoMweBI6ODSX.ZAdI6Ozy6TdbbcmOcPPYfUt/CSW', 'RESOLVEDOR'),
('d0000000-0000-0000-0000-000000000002', 'Pedro Designer', 'pedro@design.com', '$2a$12$L2CbOflPyrNmDuoMweBI6ODSX.ZAdI6Ozy6TdbbcmOcPPYfUt/CSW', 'TALENTO'),
('d0000000-0000-0000-0000-000000000003', 'Julia Data Scientist', 'julia@data.com', '$2a$12$L2CbOflPyrNmDuoMweBI6ODSX.ZAdI6Ozy6TdbbcmOcPPYfUt/CSW', 'TALENTO');

-- =============================================
-- SEED: Organizadores
-- =============================================

INSERT INTO organizadores (id, usuario_id, tipo_organizacao, nome, descricao, descricao_curta, cnpj, logo_url, banner_url, problema_atuacao, produtos_servicos, territorio_pais, territorio_estado, territorio_municipio, resultados_relevantes, estagio_inovacao, parceiro_desejado, created_at, created_by, deleted) VALUES
('e0000000-0000-0000-0000-000000000001', 'b0000000-0000-0000-0000-000000000001', 'GOVERNO', 'Prefeitura do Recife - SECTI', 'Secretaria de Ciencia, Tecnologia e Inovacao da Prefeitura do Recife, responsavel por fomentar o ecossistema de inovacao da cidade atraves de editais, programas de aceleracao e conexao entre atores do ecossistema.', 'SECTI Recife - Inovacao publica', '11222333000181', 'https://recife.pe.gov.br/logo.png', 'https://recife.pe.gov.br/banner.png', 'Conectar talentos, startups e organizacoes do ecossistema de inovacao do Recife para resolver desafios urbanos', 'Editais de inovacao, programas de aceleracao, hub de inovacao, hackathons', 'Brasil', 'PE', 'Recife', 'Mais de 200 startups aceleradas desde 2020, 15 editais lancados, 50M em investimentos captados', 'ESTAGIO_4', 'UNIVERSIDADE', now(), 'a0000000-0000-0000-0000-000000000001', false),

('e0000000-0000-0000-0000-000000000002', 'b0000000-0000-0000-0000-000000000002', 'ACELERADORA', 'Porto Digital', 'Parque tecnologico referencia em inovacao no Nordeste do Brasil, concentrando mais de 350 empresas de tecnologia e economia criativa no bairro do Recife Antigo.', 'Hub de inovacao do Recife', '11444777000161', 'https://portodigital.org/logo.png', 'https://portodigital.org/banner.png', 'Acelerar startups e fomentar o ecossistema de tecnologia no Nordeste', 'Aceleracao, coworking, mentoria, conexao com investidores', 'Brasil', 'PE', 'Recife', '350+ empresas residentes, 12000+ empregos diretos, referencia nacional em parques tecnologicos', 'ESTAGIO_5', 'INVESTIDOR', now(), 'a0000000-0000-0000-0000-000000000001', false);

-- Organizador 1: areas tematicas, publico alvo, o que busca, tags
INSERT INTO organizador_areas_tematicas (organizador_id, area_tematica) VALUES
('e0000000-0000-0000-0000-000000000001', 'TECNOLOGIA'),
('e0000000-0000-0000-0000-000000000001', 'EDUCACAO'),
('e0000000-0000-0000-0000-000000000001', 'GOVERNO_DIGITAL');

INSERT INTO organizador_publico_alvo (organizador_id, publico_alvo) VALUES
('e0000000-0000-0000-0000-000000000001', 'STARTUPS'),
('e0000000-0000-0000-0000-000000000001', 'PESQUISADORES'),
('e0000000-0000-0000-0000-000000000001', 'ESTUDANTES');

INSERT INTO organizador_o_que_busca (organizador_id, apoio_buscado) VALUES
('e0000000-0000-0000-0000-000000000001', 'TECNOLOGIA'),
('e0000000-0000-0000-0000-000000000001', 'MENTORIA');

INSERT INTO organizador_links_oficiais (organizador_id, link) VALUES
('e0000000-0000-0000-0000-000000000001', 'https://recife.pe.gov.br'),
('e0000000-0000-0000-0000-000000000001', 'https://recife.pe.gov.br/secti');

INSERT INTO organizador_usuarios_responsaveis (organizador_id, usuario_id) VALUES
('e0000000-0000-0000-0000-000000000001', 'b0000000-0000-0000-0000-000000000001');

INSERT INTO organizador_tags (organizador_id, tag) VALUES
('e0000000-0000-0000-0000-000000000001', 'governo'),
('e0000000-0000-0000-0000-000000000001', 'tecnologia'),
('e0000000-0000-0000-0000-000000000001', 'educacao'),
('e0000000-0000-0000-0000-000000000001', 'governo_digital'),
('e0000000-0000-0000-0000-000000000001', 'estagio_4'),
('e0000000-0000-0000-0000-000000000001', 'mentoria');

-- Organizador 2: areas tematicas, publico alvo, o que busca, tags
INSERT INTO organizador_areas_tematicas (organizador_id, area_tematica) VALUES
('e0000000-0000-0000-0000-000000000002', 'TECNOLOGIA'),
('e0000000-0000-0000-0000-000000000002', 'FINANCAS');

INSERT INTO organizador_publico_alvo (organizador_id, publico_alvo) VALUES
('e0000000-0000-0000-0000-000000000002', 'STARTUPS'),
('e0000000-0000-0000-0000-000000000002', 'EMPRESAS');

INSERT INTO organizador_o_que_busca (organizador_id, apoio_buscado) VALUES
('e0000000-0000-0000-0000-000000000002', 'FINANCEIRO'),
('e0000000-0000-0000-0000-000000000002', 'REDE_CONTATOS');

INSERT INTO organizador_links_oficiais (organizador_id, link) VALUES
('e0000000-0000-0000-0000-000000000002', 'https://portodigital.org');

INSERT INTO organizador_usuarios_responsaveis (organizador_id, usuario_id) VALUES
('e0000000-0000-0000-0000-000000000002', 'b0000000-0000-0000-0000-000000000002');

INSERT INTO organizador_tags (organizador_id, tag) VALUES
('e0000000-0000-0000-0000-000000000002', 'aceleradora'),
('e0000000-0000-0000-0000-000000000002', 'tecnologia'),
('e0000000-0000-0000-0000-000000000002', 'financas'),
('e0000000-0000-0000-0000-000000000002', 'estagio_5'),
('e0000000-0000-0000-0000-000000000002', 'financeiro'),
('e0000000-0000-0000-0000-000000000002', 'rede_contatos');

-- =============================================
-- SEED: Oportunidades
-- =============================================

INSERT INTO oportunidades (id, organizador_id, titulo, tipo_oportunidade, resumo, descricao_detalhada, data_abertura, data_limite_inscricao, data_inicio_prevista, tipo_organizador, possui_parceria, parceiros, status, created_at, created_by, deleted) VALUES
('f0000000-0000-0000-0000-000000000001', 'e0000000-0000-0000-0000-000000000001', 'Edital de Inovacao Aberta Recife 2026', 'EDITAL', 'Chamada publica para selecao de projetos inovadores que resolvam desafios urbanos da cidade do Recife nas areas de mobilidade, saude e governanca digital.', 'O edital visa selecionar ate 20 projetos inovadores que proponham solucoes tecnologicas para desafios urbanos. Cada projeto selecionado recebera ate R$100.000 em apoio financeiro, mentoria especializada e acesso a infraestrutura do hub de inovacao.', '2026-04-01', '2026-05-31', '2026-07-01', 'GOVERNO', true, '[{"nome":"UFPE","tipo":"Universidade"},{"nome":"CESAR","tipo":"Instituto"}]', 'ABERTA', now(), 'a0000000-0000-0000-0000-000000000001', false),

('f0000000-0000-0000-0000-000000000002', 'e0000000-0000-0000-0000-000000000001', 'Desafio Backend CORETO 2.0', 'DESAFIO', 'Desenvolver o CRUD completo das entidades centrais do ecossistema CORETO utilizando Java Spring Boot e PostgreSQL.', 'Desafio tecnico para desenvolvedores backend implementarem uma API REST robusta com autenticacao JWT, validacoes completas, filtros com paginacao e testes automatizados.', null, '2026-04-05', null, 'GOVERNO', false, '[]', 'ABERTA', now(), 'a0000000-0000-0000-0000-000000000001', false),

('f0000000-0000-0000-0000-000000000003', 'e0000000-0000-0000-0000-000000000002', 'Programa de Aceleracao PortoStart 2026', 'PROGRAMA', 'Programa de 6 meses para aceleracao de startups early-stage focadas em fintech e healthtech com mentoria, investimento semente e conexao com mercado.', 'O PortoStart seleciona 10 startups por ciclo para um programa intensivo de aceleracao incluindo R$50.000 de investimento semente, mentoria de CEOs de empresas do Porto Digital e conexao direta com fundos de venture capital.', '2026-04-15', '2026-06-15', '2026-08-01', 'ACELERADORA', true, '[{"nome":"Softex","tipo":"Associacao"},{"nome":"BNDES","tipo":"Banco"}]', 'RASCUNHO', now(), 'a0000000-0000-0000-0000-000000000001', false);

-- Oportunidade 1: areas tematicas, apoio oferecido, tipo iniciativa elegivel, tags
INSERT INTO oportunidade_areas_tematicas (oportunidade_id, area_tematica) VALUES
('f0000000-0000-0000-0000-000000000001', 'TECNOLOGIA'),
('f0000000-0000-0000-0000-000000000001', 'MOBILIDADE'),
('f0000000-0000-0000-0000-000000000001', 'SAUDE');

INSERT INTO oportunidade_apoio_oferecido (oportunidade_id, apoio_oferecido) VALUES
('f0000000-0000-0000-0000-000000000001', 'FINANCEIRO'),
('f0000000-0000-0000-0000-000000000001', 'MENTORIA'),
('f0000000-0000-0000-0000-000000000001', 'INFRAESTRUTURA');

INSERT INTO oportunidade_tipo_iniciativa_elegivel (oportunidade_id, tipo_iniciativa) VALUES
('f0000000-0000-0000-0000-000000000001', 'STARTUP'),
('f0000000-0000-0000-0000-000000000001', 'LABORATORIO');

INSERT INTO oportunidade_tags (oportunidade_id, tag) VALUES
('f0000000-0000-0000-0000-000000000001', 'edital'),
('f0000000-0000-0000-0000-000000000001', 'tecnologia'),
('f0000000-0000-0000-0000-000000000001', 'mobilidade'),
('f0000000-0000-0000-0000-000000000001', 'saude'),
('f0000000-0000-0000-0000-000000000001', 'financeiro'),
('f0000000-0000-0000-0000-000000000001', 'mentoria'),
('f0000000-0000-0000-0000-000000000001', 'infraestrutura');

-- Oportunidade 2: areas tematicas, apoio oferecido, tags
INSERT INTO oportunidade_areas_tematicas (oportunidade_id, area_tematica) VALUES
('f0000000-0000-0000-0000-000000000002', 'TECNOLOGIA'),
('f0000000-0000-0000-0000-000000000002', 'GOVERNO_DIGITAL');

INSERT INTO oportunidade_apoio_oferecido (oportunidade_id, apoio_oferecido) VALUES
('f0000000-0000-0000-0000-000000000002', 'CAPACITACAO'),
('f0000000-0000-0000-0000-000000000002', 'REDE_CONTATOS');

INSERT INTO oportunidade_tags (oportunidade_id, tag) VALUES
('f0000000-0000-0000-0000-000000000002', 'desafio'),
('f0000000-0000-0000-0000-000000000002', 'tecnologia'),
('f0000000-0000-0000-0000-000000000002', 'governo_digital'),
('f0000000-0000-0000-0000-000000000002', 'capacitacao'),
('f0000000-0000-0000-0000-000000000002', 'rede_contatos');

-- Oportunidade 3: areas tematicas, apoio oferecido, tipo iniciativa elegivel, tags
INSERT INTO oportunidade_areas_tematicas (oportunidade_id, area_tematica) VALUES
('f0000000-0000-0000-0000-000000000003', 'FINANCAS'),
('f0000000-0000-0000-0000-000000000003', 'SAUDE');

INSERT INTO oportunidade_apoio_oferecido (oportunidade_id, apoio_oferecido) VALUES
('f0000000-0000-0000-0000-000000000003', 'FINANCEIRO'),
('f0000000-0000-0000-0000-000000000003', 'MENTORIA'),
('f0000000-0000-0000-0000-000000000003', 'MERCADO');

INSERT INTO oportunidade_tipo_iniciativa_elegivel (oportunidade_id, tipo_iniciativa) VALUES
('f0000000-0000-0000-0000-000000000003', 'STARTUP');

INSERT INTO oportunidade_tags (oportunidade_id, tag) VALUES
('f0000000-0000-0000-0000-000000000003', 'programa'),
('f0000000-0000-0000-0000-000000000003', 'financas'),
('f0000000-0000-0000-0000-000000000003', 'saude'),
('f0000000-0000-0000-0000-000000000003', 'financeiro'),
('f0000000-0000-0000-0000-000000000003', 'mentoria'),
('f0000000-0000-0000-0000-000000000003', 'mercado');

-- =============================================
-- SEED: Resolvedores
-- =============================================

INSERT INTO resolvedores (id, usuario_id, tipo_iniciativa, nome, possui_vinculo, vinculo_detalhes, nome_responsavel, papel, email, telefone_whatsapp, dedicacao, descricao_curta, problema, solucao, trl_grupo, estagio_negocio, parceiro_desejado, created_at, created_by, deleted) VALUES
('a1000000-0000-0000-0000-000000000001', 'c0000000-0000-0000-0000-000000000001', 'STARTUP', 'HealthTech Recife', true, 'Incubada no Porto Digital desde 2024', 'Carlos Silva', 'CEO e Fundador', 'carlos@healthtech.io', '81999001122', 'INTEGRAL', 'Plataforma de telemedicina para comunidades carentes do Recife', 'Comunidades perifericas do Recife tem acesso limitado a consultas medicas especializadas, com filas de ate 6 meses no SUS para especialidades como cardiologia e dermatologia.', 'Aplicativo de telemedicina com triagem por IA que conecta pacientes a medicos voluntarios e do SUS, reduzindo o tempo de espera em 70% e ampliando o acesso a saude nas periferias.', 'TRL_5', 'VALIDACAO', 'GOVERNO', now(), 'a0000000-0000-0000-0000-000000000001', false),

('a1000000-0000-0000-0000-000000000002', 'c0000000-0000-0000-0000-000000000002', 'LABORATORIO', 'Lab Mobilidade UFPE', true, 'Universidade Federal de Pernambuco - Centro de Informatica', 'Dra. Fernanda Alves', 'Coordenadora de Pesquisa', 'fernanda@ufpe.br', '81988776655', 'PARCIAL', 'Laboratorio de pesquisa em mobilidade urbana inteligente', 'O transito e a mobilidade urbana precaria nas cidades do Nordeste causam perdas economicas estimadas em R$2 bilhoes anuais e afetam a qualidade de vida de milhoes de pessoas.', 'Modelos preditivos de trafego baseados em machine learning e dados de sensores IoT para otimizacao de rotas de transporte publico e semaforos inteligentes.', 'TRL_3', 'IDEACAO', 'GOVERNO', now(), 'a0000000-0000-0000-0000-000000000001', false);

-- Resolvedor 1: areas tematicas, publico alvo, apoio buscado, links, tags
INSERT INTO resolvedor_areas_tematicas (resolvedor_id, area_tematica) VALUES
('a1000000-0000-0000-0000-000000000001', 'SAUDE'),
('a1000000-0000-0000-0000-000000000001', 'TECNOLOGIA');

INSERT INTO resolvedor_publico_alvo (resolvedor_id, publico_alvo) VALUES
('a1000000-0000-0000-0000-000000000001', 'GOVERNO'),
('a1000000-0000-0000-0000-000000000001', 'STARTUPS');

INSERT INTO resolvedor_apoio_buscado (resolvedor_id, apoio_buscado) VALUES
('a1000000-0000-0000-0000-000000000001', 'FINANCEIRO'),
('a1000000-0000-0000-0000-000000000001', 'MENTORIA'),
('a1000000-0000-0000-0000-000000000001', 'MERCADO');

INSERT INTO resolvedor_links_oficiais (resolvedor_id, link) VALUES
('a1000000-0000-0000-0000-000000000001', 'https://healthtechrecife.com.br');

INSERT INTO resolvedor_tags (resolvedor_id, tag) VALUES
('a1000000-0000-0000-0000-000000000001', 'startup'),
('a1000000-0000-0000-0000-000000000001', 'saude'),
('a1000000-0000-0000-0000-000000000001', 'tecnologia'),
('a1000000-0000-0000-0000-000000000001', 'trl_5'),
('a1000000-0000-0000-0000-000000000001', 'validacao'),
('a1000000-0000-0000-0000-000000000001', 'financeiro'),
('a1000000-0000-0000-0000-000000000001', 'mentoria'),
('a1000000-0000-0000-0000-000000000001', 'mercado');

-- Resolvedor 2: areas tematicas, publico alvo, apoio buscado, tags
INSERT INTO resolvedor_areas_tematicas (resolvedor_id, area_tematica) VALUES
('a1000000-0000-0000-0000-000000000002', 'MOBILIDADE'),
('a1000000-0000-0000-0000-000000000002', 'TECNOLOGIA'),
('a1000000-0000-0000-0000-000000000002', 'MEIO_AMBIENTE');

INSERT INTO resolvedor_publico_alvo (resolvedor_id, publico_alvo) VALUES
('a1000000-0000-0000-0000-000000000002', 'GOVERNO'),
('a1000000-0000-0000-0000-000000000002', 'EMPRESAS');

INSERT INTO resolvedor_apoio_buscado (resolvedor_id, apoio_buscado) VALUES
('a1000000-0000-0000-0000-000000000002', 'INFRAESTRUTURA'),
('a1000000-0000-0000-0000-000000000002', 'FINANCEIRO');

INSERT INTO resolvedor_tags (resolvedor_id, tag) VALUES
('a1000000-0000-0000-0000-000000000002', 'laboratorio'),
('a1000000-0000-0000-0000-000000000002', 'mobilidade'),
('a1000000-0000-0000-0000-000000000002', 'tecnologia'),
('a1000000-0000-0000-0000-000000000002', 'meio_ambiente'),
('a1000000-0000-0000-0000-000000000002', 'trl_3'),
('a1000000-0000-0000-0000-000000000002', 'ideacao'),
('a1000000-0000-0000-0000-000000000002', 'infraestrutura'),
('a1000000-0000-0000-0000-000000000002', 'financeiro');

-- =============================================
-- SEED: Talentos
-- =============================================

INSERT INTO talentos (id, usuario_id, nome_completo, nome_social, email, telefone_whatsapp, mini_bio, pais, estado, cidade, linkedin, github, portfolio, senioridade, horas_semana, tipo_atuacao, formato, created_at, created_by, deleted) VALUES
('a2000000-0000-0000-0000-000000000001', 'd0000000-0000-0000-0000-000000000001', 'Ana Carolina Souza', 'Ana', 'ana.souza@dev.com', '81997654321', 'Desenvolvedora backend com 5 anos de experiencia em Java e Spring Boot. Apaixonada por arquitetura de microsservicos, APIs REST e clean code. Contribuidora open source.', 'Brasil', 'PE', 'Recife', 'https://linkedin.com/in/anacarolina', 'https://github.com/anadev', null, 'SENIOR', 20, 'PJ', 'REMOTO', now(), 'a0000000-0000-0000-0000-000000000001', false),

('a2000000-0000-0000-0000-000000000002', 'd0000000-0000-0000-0000-000000000002', 'Pedro Henrique Lima', null, 'pedro.lima@design.com', '81991234567', 'UX/UI Designer especializado em design systems e acessibilidade digital. 4 anos de experiencia com Figma, React e pesquisa com usuarios. Defensor do design inclusivo.', 'Brasil', 'PE', 'Olinda', 'https://linkedin.com/in/pedrolima', null, 'https://pedrolima.design', 'PLENO', 40, 'CLT', 'HIBRIDO', now(), 'a0000000-0000-0000-0000-000000000001', false),

('a2000000-0000-0000-0000-000000000003', 'd0000000-0000-0000-0000-000000000003', 'Julia Santos Martins', null, 'julia.martins@data.com', '11998887766', 'Cientista de dados com foco em NLP e modelos preditivos para o setor publico. Mestrado em IA pela USP. Experiencia com projetos de governo digital e saude publica.', 'Brasil', 'SP', 'Sao Paulo', null, 'https://github.com/juliam', null, 'ESPECIALISTA', 10, 'FREELANCER', 'REMOTO', now(), 'a0000000-0000-0000-0000-000000000001', false);

-- Talento 1: skills, areas tematicas, tags
INSERT INTO talento_skills (talento_id, skill) VALUES
('a2000000-0000-0000-0000-000000000001', 'Java'),
('a2000000-0000-0000-0000-000000000001', 'Spring Boot'),
('a2000000-0000-0000-0000-000000000001', 'PostgreSQL'),
('a2000000-0000-0000-0000-000000000001', 'Docker'),
('a2000000-0000-0000-0000-000000000001', 'Kubernetes'),
('a2000000-0000-0000-0000-000000000001', 'REST APIs');

INSERT INTO talento_areas_tematicas (talento_id, area_tematica) VALUES
('a2000000-0000-0000-0000-000000000001', 'TECNOLOGIA'),
('a2000000-0000-0000-0000-000000000001', 'GOVERNO_DIGITAL');

INSERT INTO talento_tags (talento_id, tag) VALUES
('a2000000-0000-0000-0000-000000000001', 'java'),
('a2000000-0000-0000-0000-000000000001', 'spring_boot'),
('a2000000-0000-0000-0000-000000000001', 'postgresql'),
('a2000000-0000-0000-0000-000000000001', 'docker'),
('a2000000-0000-0000-0000-000000000001', 'kubernetes'),
('a2000000-0000-0000-0000-000000000001', 'rest_apis'),
('a2000000-0000-0000-0000-000000000001', 'senior'),
('a2000000-0000-0000-0000-000000000001', 'tecnologia'),
('a2000000-0000-0000-0000-000000000001', 'governo_digital'),
('a2000000-0000-0000-0000-000000000001', 'pj');

-- Talento 2: skills, areas tematicas, tags
INSERT INTO talento_skills (talento_id, skill) VALUES
('a2000000-0000-0000-0000-000000000002', 'Figma'),
('a2000000-0000-0000-0000-000000000002', 'UX Research'),
('a2000000-0000-0000-0000-000000000002', 'Design System'),
('a2000000-0000-0000-0000-000000000002', 'React'),
('a2000000-0000-0000-0000-000000000002', 'Acessibilidade');

INSERT INTO talento_areas_tematicas (talento_id, area_tematica) VALUES
('a2000000-0000-0000-0000-000000000002', 'TECNOLOGIA'),
('a2000000-0000-0000-0000-000000000002', 'EDUCACAO');

INSERT INTO talento_tags (talento_id, tag) VALUES
('a2000000-0000-0000-0000-000000000002', 'figma'),
('a2000000-0000-0000-0000-000000000002', 'ux_research'),
('a2000000-0000-0000-0000-000000000002', 'design_system'),
('a2000000-0000-0000-0000-000000000002', 'react'),
('a2000000-0000-0000-0000-000000000002', 'acessibilidade'),
('a2000000-0000-0000-0000-000000000002', 'pleno'),
('a2000000-0000-0000-0000-000000000002', 'tecnologia'),
('a2000000-0000-0000-0000-000000000002', 'educacao'),
('a2000000-0000-0000-0000-000000000002', 'clt');

-- Talento 3: skills, areas tematicas, tags
INSERT INTO talento_skills (talento_id, skill) VALUES
('a2000000-0000-0000-0000-000000000003', 'Python'),
('a2000000-0000-0000-0000-000000000003', 'Machine Learning'),
('a2000000-0000-0000-0000-000000000003', 'NLP'),
('a2000000-0000-0000-0000-000000000003', 'SQL'),
('a2000000-0000-0000-0000-000000000003', 'Pandas'),
('a2000000-0000-0000-0000-000000000003', 'TensorFlow');

INSERT INTO talento_areas_tematicas (talento_id, area_tematica) VALUES
('a2000000-0000-0000-0000-000000000003', 'SAUDE'),
('a2000000-0000-0000-0000-000000000003', 'GOVERNO_DIGITAL'),
('a2000000-0000-0000-0000-000000000003', 'SEGURANCA');

INSERT INTO talento_tags (talento_id, tag) VALUES
('a2000000-0000-0000-0000-000000000003', 'python'),
('a2000000-0000-0000-0000-000000000003', 'machine_learning'),
('a2000000-0000-0000-0000-000000000003', 'nlp'),
('a2000000-0000-0000-0000-000000000003', 'sql'),
('a2000000-0000-0000-0000-000000000003', 'pandas'),
('a2000000-0000-0000-0000-000000000003', 'tensorflow'),
('a2000000-0000-0000-0000-000000000003', 'especialista'),
('a2000000-0000-0000-0000-000000000003', 'saude'),
('a2000000-0000-0000-0000-000000000003', 'governo_digital'),
('a2000000-0000-0000-0000-000000000003', 'seguranca'),
('a2000000-0000-0000-0000-000000000003', 'freelancer');
