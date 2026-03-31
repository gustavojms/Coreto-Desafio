# Relatorio Tecnico - CORETO 2.0 API

## 1. Problema Abordado

A plataforma CORETO 2.0 necessita de um backend robusto para gerenciar o ecossistema de inovacao da cidade do Recife, conectando quatro atores principais: Talentos (individuos), Resolvedores (startups/labs), Organizadores (instituicoes) e Oportunidades (editais/desafios/programas). O desafio e implementar um CRUD completo, seguro e validado dessas entidades, com normalizacao de dados via TAGs automaticas e APIs prontas para integracao com o modulo de Matching Maker.

## 2. Solucao Proposta

### 2.1 Stack Tecnologica

| Componente | Tecnologia | Justificativa |
|------------|-----------|---------------|
| Linguagem | Java 21 (LTS) | Estabilidade, performance, ecossistema maduro |
| Framework | Spring Boot 3.4.x | Convencao sobre configuracao, produtividade |
| ORM | Spring Data JPA + Hibernate 6 | Mapeamento objeto-relacional robusto |
| Banco de Dados | PostgreSQL 17 | JSONB nativo, arrays, performance comprovada |
| Migracoes | Flyway | Versionamento de schema, rollback seguro |
| Seguranca | Spring Security + JWT (RSA) | Autenticacao stateless, padrao OAuth2 Resource Server |
| Documentacao | SpringDoc OpenAPI 3 | Swagger UI integrado com autenticacao JWT |
| Mapeamento | MapStruct | Geracao de codigo em compile-time, zero overhead |
| Testes | JUnit 5 + Mockito + Spring Test | Cobertura unitaria e de integracao |
| Container | Docker + Docker Compose | Deploy padronizado e reprodutivel |

### 2.2 Arquitetura (Clean Architecture)

```
domain/           Entidades JPA e Enums (nucleo do negocio)
application/      DTOs, Mappers, Services, Validations (casos de uso)
infrastructure/   Config, Persistence, Security (frameworks)
web/              Controllers, Exception Handlers (apresentacao)
```

**Justificativa:** A separacao em camadas garante que o dominio do negocio nao depende de frameworks. DTOs sao separados de Entities para controlar exposicao de dados na API. MapStruct gera os mappers em tempo de compilacao sem reflexao.

## 3. Decisoes Arquiteturais

### 3.1 JWT com RSA (Self-Signed)

Utilizamos `spring-boot-starter-oauth2-resource-server` com chave RSA propria ao inves de um servidor OAuth externo. A API atua como emissor e validador de tokens. Tokens incluem `sub` (userId), `role` e `email` como claims.

**Motivacao:** Simplicidade para MVP. Nao requer infraestrutura adicional (Keycloak, Auth0). A migracao para OAuth2 externo e possivel no futuro sem alterar os controllers (apenas configuracao do Resource Server).

### 3.2 Soft Delete

Todos os registros possuem campos `deleted` (boolean) e `deleted_at` (timestamp). O DELETE logico preserva dados para auditoria e compliance (LGPD). Todas as queries filtram `deleted = false` via JPA Specifications.

**Motivacao:** Reversibilidade de operacoes, rastreabilidade para auditoria, conformidade com boas praticas de retencao de dados.

### 3.3 TAGs Derivadas

As TAGs sao campos desnormalizados gerados automaticamente a partir dos campos classificatorios de cada entidade (tipo, area tematica, estagio, etc.). Sao recalculadas em cada create/update, nunca editadas manualmente.

**Motivacao:** Facilita o Matching Maker futuro. Permite filtrar entidades por qualquer combinacao de classificacoes sem queries complexas. A normalizacao (lowercase, sem acentos) garante consistencia na busca.

| Entidade | Campos fonte das TAGs |
|----------|----------------------|
| Oportunidade | tipo_oportunidade, areas_tematicas, apoio_oferecido |
| Organizador | tipo_organizacao, area_tematica, estagio_inovacao, o_que_busca |
| Resolvedor | tipo_iniciativa, area_tematica, trl_grupo, estagio_negocio, apoio_buscado |
| Talento | skills, senioridade, areas_tematicas, tipo_atuacao |

### 3.4 JPA Specifications para Filtros

Utilizamos `JpaSpecificationExecutor` ao inves de QueryDSL para composicao de filtros. Cada filtro nulo e ignorado (retorna `null`, que o Spring Data descarta), permitindo combinacao livre.

**Motivacao:** Nativo do Spring Data (sem dependencia adicional), composavel, testavel, sem geracao de codigo.

### 3.5 ElementCollection para Arrays de Enums

Arrays como `areas_tematicas`, `publico_alvo` e `apoio_buscado` sao armazenados em tabelas de juncao via `@ElementCollection`. Isso permite queries com `cb.isMember()` para filtragem eficiente por TAG.

**Motivacao:** Alternativa ao JSONB que permite queries tipadas. Maximo de 3 itens por campo garante tabelas compactas. `FetchType.EAGER` e aceitavel dado o tamanho pequeno das colecoes.

### 3.6 JSONB para Dados Flexiveis

Campos como `parceiros` e `anexos` usam PostgreSQL JSONB via Hypersistence Utils. Esses campos nao sao filtrados e possuem estrutura variavel.

**Motivacao:** Flexibilidade para dados semi-estruturados sem necessidade de tabelas adicionais. Compativel com PostgreSQL nativo.

## 4. Seguranca

### 4.1 Perfis e Permissoes

| Perfil | Permissao |
|--------|-----------|
| ADMIN | Acesso total a todos os recursos |
| ORGANIZADOR | CRUD dos seus Organizadores e Oportunidades |
| RESOLVEDOR | CRUD do seu perfil de Resolvedor |
| TALENTO | CRUD do seu perfil de Talento |

### 4.2 Implementacao

- `@PreAuthorize` em todos os endpoints de escrita (POST/PUT/DELETE)
- `OwnershipChecker` bean que valida se o usuario autenticado e dono do recurso
- Endpoints de leitura (GET) sao publicos para usuarios autenticados
- Endpoints de autenticacao (`/api/v1/auth/**`) sao publicos

### 4.3 Conformidade LGPD

- Soft delete preserva dados sem exposicao publica
- Auditoria (created_by, created_at, updated_at) em todas as entidades
- Senhas armazenadas com BCrypt (fator 12)
- Tokens JWT com expiracao de 24 horas

## 5. Validacoes

- Campos obrigatorios: `@NotBlank`, `@NotNull`, `@NotEmpty`
- Tamanho: `@Size(max=150)` para titulo, `@Size(max=600)` para resumo, `@Size(max=500)` para mini_bio
- CNPJ: Validador customizado com algoritmo modulo-11
- Colecoes: `@MaxCollectionSize(3)` para areas tematicas, publico alvo, apoio buscado
- Datas: `@ValidDateOrder` garante data_limite_inscricao >= data_abertura
- Condicionais: `@ValidParceria` (parceiros obrigatorios quando possui_parceria=true), `@ValidVinculo` (detalhes obrigatorios quando possui_vinculo=true)
- Email: `@Email` em todos os campos de email

## 6. Premissas e Limitacoes

### Premissas
- Um usuario possui apenas um perfil (Role unica)
- Cada Organizador/Resolvedor/Talento esta vinculado a um Usuario
- TAGs sao somente leitura (derivadas, nao editaveis pela API)
- O admin seed possui senha `admin123` (deve ser alterada em producao)

### Limitacoes
- Upload de arquivos (imagens, anexos) nao implementado - apenas URLs
- Busca full-text e basica (LIKE) - pode ser otimizada com pg_trgm ou Elasticsearch
- Sem rate limiting ou throttling
- Sem cache (pode adicionar Spring Cache + Redis)
- Matching Maker nao implementado (apenas TAGs preparatorias)

## 7. Proximos Passos

1. **Matching Maker** - Algoritmo de correspondencia baseado nas TAGs e classificacoes
2. **Upload de arquivos** - Integracao com S3/MinIO para imagens e anexos
3. **Notificacoes** - Sistema de alertas para novas oportunidades compatíveis
4. **Cache** - Redis para queries frequentes e sessoes
5. **Busca avancada** - PostgreSQL full-text search ou Elasticsearch
6. **CI/CD** - Pipeline GitHub Actions com testes automatizados
7. **Monitoramento** - Spring Actuator + Prometheus + Grafana

## 8. Como Executar

```bash
# Com Docker
docker-compose up --build

# Sem Docker (requer PostgreSQL local)
# Criar banco: CREATE DATABASE coreto; CREATE USER coreto WITH PASSWORD 'coreto123';
./mvnw spring-boot:run

# Testes
./mvnw test
```

- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html
- Endpoints de auth sao publicos, demais requerem JWT Bearer token
