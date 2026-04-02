# Relatorio Tecnico - CORETO 2.0 API

## 1. Problema Abordado

O ecossistema de inovacao do Recife envolve atores diversos — instituicoes, startups, laboratorios e profissionais — que operam de forma fragmentada. Oportunidades (editais, desafios, programas) sao divulgadas em canais dispersos, dificultando o acesso. Organizadores nao tem visibilidade sobre quais resolvedores ou talentos possuem perfil adequado para suas demandas, e a correspondencia e feita manualmente. Alem disso, as informacoes nao estao padronizadas entre instituicoes, impedindo cruzamento de dados e analises do ecossistema.

A plataforma CORETO 2.0 resolve esses problemas centralizando o cadastro e classificacao de quatro atores: **Organizadores** (instituicoes), **Oportunidades** (editais/desafios/programas), **Resolvedores** (startups/laboratorios) e **Talentos** (profissionais). A API oferece CRUD completo com seguranca, validacao e TAGs normalizadas que preparam os dados para um futuro modulo de Matching Maker.

---

## 2. Solucao Proposta

### 2.1 Stack e Arquitetura

| Componente | Tecnologia |
|------------|-----------|
| Linguagem / Framework | Java 21 + Spring Boot 3.4.4 |
| Banco de Dados / Migracoes | PostgreSQL 17 + Flyway |
| Seguranca | Spring Security + JWT (RSA) + OAuth2 Resource Server |
| Mapeamento / Docs | MapStruct 1.6 + SpringDoc OpenAPI 3 |
| Testes | JUnit 5 + Mockito + Testcontainers |
| Container | Docker + Docker Compose (profiles dev/prod) |

O projeto segue **Clean Architecture** em quatro camadas: `domain` (entidades e enums), `application` (DTOs, services, validacoes), `infrastructure` (seguranca, persistencia, config) e `web` (controllers, tratamento de erros). DTOs sao separados de entidades para controlar exposicao de dados. MapStruct gera mapeadores em compile-time sem reflexao.

### 2.2 Modelo de Dados

As quatro entidades compartilham UUID como PK, soft delete (`deleted`, `deleted_at`), auditoria (`created_at`, `updated_at`, `created_by`) e TAGs derivadas automaticamente:

| Entidade | Descricao | Campos Chave |
|----------|-----------|-------------|
| Organizador | Instituicao (governo, empresa, universidade) | CNPJ, tipo organizacao, areas tematicas, publico alvo |
| Oportunidade | Edital, desafio ou programa (vinculado a Organizador) | Tipo, datas, apoio oferecido, parceiros (JSONB) |
| Resolvedor | Startup ou laboratorio | Tipo iniciativa, TRL, estagio negocio, apoio buscado |
| Talento | Profissional individual | Skills, senioridade, tipo atuacao, dedicacao |

### 2.3 Decisoes Tecnicas Relevantes

- **JWT com RSA (self-signed):** A API emite e valida tokens com chaves RSA proprias. Simplicidade para MVP sem dependencia externa (Keycloak/Auth0), com migracao futura possivel alterando apenas configuracao.
- **Soft Delete:** Registros nunca sao removidos fisicamente — preserva dados para auditoria e conformidade LGPD.
- **TAGs derivadas:** Geradas automaticamente pelo `TagService` a partir de campos classificatorios (tipo, area tematica, estagio, etc.), normalizadas em lowercase sem acentos. Preparacao para o Matching Maker.
- **JPA Specifications:** Filtros dinamicos compostos nativos do Spring Data, sem QueryDSL.
- **ElementCollection:** Enums em tabelas de juncao para queries tipadas com `cb.isMember()`. JSONB reservado para dados flexiveis nao filtraveis (parceiros, anexos).

### 2.4 Seguranca

Quatro perfis de acesso: **ADMIN** (acesso total), **ORGANIZADOR** (CRUD seus registros + oportunidades), **RESOLVEDOR** e **TALENTO** (CRUD do proprio perfil). Endpoints de escrita usam `@PreAuthorize` e verificacao de ownership. Medidas implementadas apos testes de penetracao:

- Rate limiting no login (5 tentativas/5 min por email)
- Bloqueio de registro com role ADMIN pela API
- Sanitizacao XSS em todas as strings de entrada (Jackson deserializer)
- Limite de payload de 1MB
- Tratamento seguro de erros (sem stack traces, tipo info ou campos do schema em respostas de erro)
- Senhas com BCrypt fator 12; tokens JWT com expiracao de 24h
- `userId` omitido da resposta de login (prevencao IDOR)

### 2.5 Validacoes

Bean Validation (`@NotBlank`, `@Size`, `@Email`) em todos os campos criticos, alem de validadores customizados: CNPJ (modulo-11), `@MaxCollectionSize(3)` para colecoes de enums, `@ValidDateOrder` (data limite >= abertura), `@ValidParceria` e `@ValidVinculo` (campos condicionais).

---

## 3. Premissas e Limitacoes

### Premissas

- Cada usuario possui uma unica role. Cada entidade (Organizador/Resolvedor/Talento) esta vinculada a um usuario.
- TAGs sao somente leitura, derivadas automaticamente. O backend e API-first (sem views).
- Credenciais de seed (`admin123`, `123456`) e chaves RSA do repositorio sao para desenvolvimento — devem ser substituidas em producao.

### Limitacoes

| Limitacao | Mitigacao Planejada |
|-----------|---------------------|
| Upload de arquivos nao implementado (apenas URLs) | Integracao com S3/MinIO |
| Busca textual basica (LIKE) | PostgreSQL `pg_trgm` ou Elasticsearch |
| Matching Maker nao implementado (apenas TAGs preparatorias) | Proximo passo prioritario |
| Sem cache (todas as queries vao ao banco) | Spring Cache + Redis |
| Rate limiting em memoria (nao compartilhado entre instancias) | Migrar para Redis |
| Sem refresh token (requer novo login a cada 24h) | Fluxo de refresh token com rotacao |

---

## 4. Proximos Passos

| Prioridade | Item | Descricao |
|------------|------|-----------|
| 1 | **Matching Maker** | Algoritmo de correspondencia baseado nas TAGs e classificacoes para sugerir oportunidades a resolvedores/talentos e vice-versa |
| 2 | **Upload de arquivos** | S3/MinIO para imagens de perfil, logos e anexos de oportunidades |
| 3 | **Notificacoes** | Alertas por email/push para novas oportunidades compativeis com o perfil |
| 4 | **Busca avancada** | Full-text search com relevancia, sinonimos e tolerancia a erros |
| 5 | **Cache + Redis** | Reduzir carga no banco para listagens frequentes |
| 6 | **CI/CD** | GitHub Actions com testes, analise de codigo e deploy automatico |
| 7 | **Monitoramento** | Actuator + Prometheus + Grafana para metricas e alertas |
