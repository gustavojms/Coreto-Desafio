# CORETO 2.0 API

Backend do ecossistema de inovacao **CORETO** da Prefeitura do Recife. A plataforma conecta quatro atores do ecossistema de inovacao: **Organizadores** (instituicoes), **Oportunidades** (editais, desafios, programas), **Resolvedores** (startups e laboratorios) e **Talentos** (profissionais).

---

## Sumario

- [Stack Tecnologica](#stack-tecnologica)
- [Pre-requisitos](#pre-requisitos)
- [Como Executar](#como-executar)
  - [Opcao 1: Tudo via Docker Compose (Recomendado)](#opcao-1-tudo-via-docker-compose-recomendado)
  - [Opcao 2: Banco via Docker + Aplicacao Local](#opcao-2-banco-via-docker--aplicacao-local)
- [Verificando se Funcionou](#verificando-se-funcionou)
- [Usuarios de Demonstracao](#usuarios-de-demonstracao)
- [Autenticacao e Seguranca](#autenticacao-e-seguranca)
- [Endpoints da API](#endpoints-da-api)
- [Filtros e Paginacao](#filtros-e-paginacao)
- [Perfis e Permissoes](#perfis-e-permissoes)
- [Variaveis de Ambiente](#variaveis-de-ambiente)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Banco de Dados](#banco-de-dados)
- [Testes](#testes)
- [Postman](#postman)
- [Decisoes Arquiteturais](#decisoes-arquiteturais)

---

## Stack Tecnologica

| Camada | Tecnologia |
|--------|------------|
| Linguagem | Java 21 (LTS) |
| Framework | Spring Boot 3.4.4 |
| Banco de Dados | PostgreSQL 17 |
| Migracoes | Flyway |
| Seguranca | Spring Security + OAuth2 Resource Server + JWT (RSA) |
| Mapeamento | MapStruct 1.6 + Lombok |
| Documentacao | SpringDoc OpenAPI 2.8 (Swagger UI) |
| Containerizacao | Docker + Docker Compose |
| Testes | JUnit 5 + Testcontainers + H2 |

---

## Pre-requisitos

Voce so precisa de **uma** coisa instalada:

- **Docker** e **Docker Compose** (v2+)

> Se preferir rodar a aplicacao fora do Docker, tambem vai precisar do **Java 21+** e do **Maven** (o wrapper `./mvnw` ja vem incluso).

---

## Como Executar

### Opcao 1: Tudo via Docker Compose (Recomendado)

Esta opcao sobe o banco de dados **e** a aplicacao automaticamente. Nao precisa de Java instalado.

#### Modo Desenvolvimento (com hot reload)

```bash
docker compose --profile dev up --build
```

Neste modo, o codigo-fonte e montado como volume dentro do container. Alteracoes nos arquivos em `src/` sao detectadas automaticamente pelo Spring DevTools, e a aplicacao reinicia sozinha.

#### Modo Producao

```bash
docker compose --profile prod up --build
```

Neste modo, o build e feito em multi-stage: o Maven compila o JAR, e o container final usa apenas o JRE Alpine (imagem leve, sem codigo-fonte).

#### O que acontece por tras

1. O Docker Compose sobe um container **PostgreSQL 17** com o banco `coreto` criado automaticamente
2. Um health check verifica se o PostgreSQL esta pronto (a cada 5 segundos)
3. Assim que o banco esta saudavel, o container da **API** inicia
4. O **Flyway** executa todas as migracoes automaticamente (cria tabelas, indices, dados de demonstracao)
5. A aplicacao fica disponivel em `http://localhost:8080`

#### Parar tudo

```bash
docker compose --profile dev down     # para modo dev
docker compose --profile prod down    # para modo prod
```

#### Resetar o banco de dados (apagar todos os dados)

```bash
docker compose down -v
```

O `-v` remove o volume `coreto_data`, apagando todos os dados do PostgreSQL. Na proxima vez que subir, o Flyway recria tudo do zero.

---

### Opcao 2: Banco via Docker + Aplicacao Local

Util se voce quer usar sua IDE com debug, breakpoints, etc.

#### 1. Subir apenas o PostgreSQL

```bash
docker compose up -d postgres
```

#### 2. Executar a aplicacao

```bash
./mvnw spring-boot:run
```

No Windows (sem bash):
```bash
mvnw.cmd spring-boot:run
```

A aplicacao conecta automaticamente no PostgreSQL em `localhost:5432` com usuario `coreto` e senha `coreto123`.

---

## Verificando se Funcionou

Apos subir a aplicacao (por qualquer uma das opcoes), verifique:

#### 1. Health Check

```bash
curl http://localhost:8080/actuator/health
```

Resposta esperada:
```json
{"status":"UP"}
```

#### 2. Swagger UI

Abra no navegador: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Voce vera a documentacao interativa de todos os endpoints, com a possibilidade de testar direto pelo navegador.

#### 3. Testar Login

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@coreto.com.br","senha":"admin123"}'
```

Resposta esperada:
```json
{"token":"eyJhbGciOiJS...","role":"ADMIN"}
```

---

## Usuarios de Demonstracao

O Flyway cria automaticamente os seguintes usuarios para teste:

| Email | Senha | Perfil | Descricao |
|-------|-------|--------|-----------|
| `admin@coreto.com.br` | `admin123` | ADMIN | Administrador do sistema |
| `maria@emprel.com` | `123456` | ORGANIZADOR | SECTI Recife |
| `joao@portodigital.org` | `123456` | ORGANIZADOR | Porto Digital |
| `carlos@startup.io` | `123456` | RESOLVEDOR | HealthTech Startup |
| `fernanda@ufpe.br` | `123456` | RESOLVEDOR | Laboratorio UFPE |
| `ana@dev.com` | `123456` | TALENTO | Desenvolvedora Backend |
| `pedro@design.com` | `123456` | TALENTO | Designer UX/UI |
| `julia@data.com` | `123456` | TALENTO | Cientista de Dados |

> Estes usuarios e seus dados de exemplo (organizadores, oportunidades, resolvedores e talentos) sao criados pelas migracoes V6, V7 e V8.

---

## Autenticacao e Seguranca

A API usa **JWT (JSON Web Token)** assinado com chaves **RSA** (par publico/privado). Nao depende de nenhum servidor OAuth externo.

### Fluxo Completo

#### 1. Registro de Novo Usuario

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Meu Nome",
    "email": "meu@email.com",
    "senha": "minhaSenha123",
    "role": "ORGANIZADOR"
  }'
```

- O campo `role` aceita: `ORGANIZADOR`, `RESOLVEDOR` ou `TALENTO`
- Nao e possivel registrar `ADMIN` pela API (somente via seed/banco)
- A senha e armazenada com hash BCrypt (fator 12)
- A resposta ja retorna o token JWT

#### 2. Login

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@coreto.com.br",
    "senha": "admin123"
  }'
```

Resposta:
```json
{
  "token": "eyJhbGciOiJSUzI1NiJ9...",
  "role": "ADMIN"
}
```

> **Rate Limiting:** apos 5 tentativas de login falhas no mesmo email em 5 minutos, o login e bloqueado temporariamente.

#### 3. Usando o Token

Inclua o token no header `Authorization` de todas as requisicoes protegidas:

```bash
curl -H "Authorization: Bearer eyJhbGciOiJSUzI1NiJ9..." \
  http://localhost:8080/api/v1/organizadores
```

#### Detalhes do Token JWT

| Campo | Descricao |
|-------|-----------|
| `sub` | UUID do usuario |
| `email` | Email do usuario |
| `role` | Perfil (ADMIN, ORGANIZADOR, RESOLVEDOR, TALENTO) |
| `iss` | Emissor: `coreto-api` |
| `exp` | Expira em **24 horas** |

### Chaves RSA

As chaves ficam em `src/main/resources/certs/`:
- `public.pem` - Chave publica (validacao do token)
- `private.pem` - Chave privada (geracao do token)

No Docker, as chaves sao copiadas para `/app/certs/` durante o build.

---

## Endpoints da API

Base URL: `http://localhost:8080/api/v1`

### Autenticacao (Publico)

| Metodo | Endpoint | Descricao |
|--------|----------|-----------|
| `POST` | `/auth/register` | Registrar novo usuario |
| `POST` | `/auth/login` | Fazer login e obter token |

### Organizadores

| Metodo | Endpoint | Permissao | Descricao |
|--------|----------|-----------|-----------|
| `POST` | `/organizadores` | ADMIN, ORGANIZADOR | Criar organizador |
| `GET` | `/organizadores` | Autenticado | Listar com filtros e paginacao |
| `GET` | `/organizadores/{id}` | Autenticado | Buscar por ID |
| `PUT` | `/organizadores/{id}` | ADMIN ou Dono | Atualizar organizador |
| `DELETE` | `/organizadores/{id}` | ADMIN ou Dono | Deletar (soft delete) |

### Oportunidades

| Metodo | Endpoint | Permissao | Descricao |
|--------|----------|-----------|-----------|
| `POST` | `/oportunidades` | ADMIN, ORGANIZADOR | Criar oportunidade |
| `GET` | `/oportunidades` | Autenticado | Listar com filtros e paginacao |
| `GET` | `/oportunidades/{id}` | Autenticado | Buscar por ID |
| `PUT` | `/oportunidades/{id}` | ADMIN ou Dono do Organizador | Atualizar oportunidade |
| `DELETE` | `/oportunidades/{id}` | ADMIN ou Dono do Organizador | Deletar (soft delete) |

### Resolvedores

| Metodo | Endpoint | Permissao | Descricao |
|--------|----------|-----------|-----------|
| `POST` | `/resolvedores` | ADMIN, RESOLVEDOR | Criar resolvedor |
| `GET` | `/resolvedores` | Autenticado | Listar com filtros e paginacao |
| `GET` | `/resolvedores/{id}` | Autenticado | Buscar por ID |
| `PUT` | `/resolvedores/{id}` | ADMIN ou Dono | Atualizar resolvedor |
| `DELETE` | `/resolvedores/{id}` | ADMIN ou Dono | Deletar (soft delete) |

### Talentos

| Metodo | Endpoint | Permissao | Descricao |
|--------|----------|-----------|-----------|
| `POST` | `/talentos` | ADMIN, TALENTO | Criar talento |
| `GET` | `/talentos` | Autenticado | Listar com filtros e paginacao |
| `GET` | `/talentos/{id}` | Autenticado | Buscar por ID |
| `PUT` | `/talentos/{id}` | ADMIN ou Dono | Atualizar talento |
| `DELETE` | `/talentos/{id}` | ADMIN ou Dono | Deletar (soft delete) |

### Documentacao e Monitoramento

| Endpoint | Descricao |
|----------|-----------|
| `/swagger-ui.html` | Interface Swagger interativa |
| `/v3/api-docs` | Especificacao OpenAPI 3.0 (JSON) |
| `/actuator/health` | Health check da aplicacao |

---

## Filtros e Paginacao

Todos os endpoints `GET` de listagem suportam os seguintes parametros:

| Parametro | Tipo | Default | Descricao |
|-----------|------|---------|-----------|
| `search` | String | - | Busca por texto nos campos da entidade |
| `tag` | String | - | Filtra por TAG |
| `page` | Integer | `0` | Numero da pagina (comeca em 0) |
| `size` | Integer | `20` | Quantidade de itens por pagina |
| `sort` | String | - | Ordenacao (ex: `createdAt,desc`) |

Exemplo:
```bash
curl -H "Authorization: Bearer {token}" \
  "http://localhost:8080/api/v1/oportunidades?search=edital&tag=SAUDE&page=0&size=10&sort=createdAt,desc"
```

> Cada entidade possui filtros especificos adicionais. Consulte o Swagger para ver todos os parametros disponiveis.

---

## Perfis e Permissoes

| Perfil | O que pode fazer |
|--------|-----------------|
| **ADMIN** | Acesso total a todos os recursos do sistema |
| **ORGANIZADOR** | CRUD dos seus organizadores + oportunidades vinculadas |
| **RESOLVEDOR** | CRUD do seu proprio perfil de resolvedor |
| **TALENTO** | CRUD do seu proprio perfil de talento |

- Operacoes de escrita (PUT, DELETE) em recursos proprios verificam ownership: somente o dono do registro ou um ADMIN pode alterar/deletar
- Todos os usuarios autenticados podem listar e visualizar qualquer registro publico

---

## Variaveis de Ambiente

A aplicacao funciona com valores padrao para desenvolvimento local. Em producao, configure as seguintes variaveis:

### Banco de Dados

| Variavel | Padrao | Descricao |
|----------|--------|-----------|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/coreto` | URL de conexao JDBC |
| `PGUSER` | `coreto` | Usuario do banco |
| `PGPASSWORD` | `coreto123` | Senha do banco |

### Seguranca

| Variavel | Padrao | Descricao |
|----------|--------|-----------|
| `RSA_PUBLIC_KEY` | `classpath:certs/public.pem` | Caminho da chave publica RSA |
| `RSA_PRIVATE_KEY` | `classpath:certs/private.pem` | Caminho da chave privada RSA |

### Aplicacao

| Variavel | Padrao | Descricao |
|----------|--------|-----------|
| `PORT` | `8080` | Porta do servidor |
| `SPRING_PROFILES_ACTIVE` | (vazio) | Perfil ativo: `dev`, `test`, ou `prod` |
| `CORS_ALLOWED_ORIGINS` | `http://localhost:3000,http://localhost:5173` | Origens CORS permitidas |

> No Docker Compose, essas variaveis ja estao configuradas automaticamente. Voce so precisa se preocupar com elas em deploys customizados.

---

## Estrutura do Projeto

O projeto segue **Clean Architecture**, separando responsabilidades em quatro camadas:

```
src/main/java/br/com/coreto/
├── domain/                          # Nucleo do negocio
│   ├── entity/                      # Entidades JPA
│   │   ├── BaseEntity.java          # Classe base (soft delete, auditoria)
│   │   ├── Usuario.java             # Usuario do sistema
│   │   ├── Organizador.java         # Instituicao/organizacao
│   │   ├── Oportunidade.java        # Edital, desafio, programa
│   │   ├── Resolvedor.java          # Startup, laboratorio
│   │   └── Talento.java             # Profissional individual
│   └── enums/                       # Enumeracoes do dominio
│       ├── Role                     # ADMIN, ORGANIZADOR, RESOLVEDOR, TALENTO
│       ├── AreaTematica             # SAUDE, EDUCACAO, TECNOLOGIA, etc.
│       ├── ApoioBuscado             # FINANCEIRO, MENTORIA, INFRAESTRUTURA, etc.
│       ├── ApoioOferecido           # Tipos de apoio oferecidos
│       ├── EstagioInovacao          # Estagios 1 a 5
│       ├── EstagioNegocio           # IDEACAO, VALIDACAO, TRACAO, ESCALA, etc.
│       ├── TRL                      # Technology Readiness Level (1 a 9)
│       └── ...                      # + 10 outros enums
│
├── application/                     # Casos de uso
│   ├── dto/
│   │   ├── request/                 # DTOs de entrada (RegisterRequest, etc.)
│   │   ├── response/                # DTOs de saida (TokenResponse, etc.)
│   │   └── filter/                  # Filtros de consulta
│   ├── mapper/                      # Mapeadores MapStruct (Entity <-> DTO)
│   ├── service/                     # Logica de negocio
│   │   ├── AuthService              # Registro e login
│   │   ├── OrganizadorService       # CRUD + filtros de organizadores
│   │   ├── OportunidadeService      # CRUD + filtros de oportunidades
│   │   ├── ResolvedorService        # CRUD + filtros de resolvedores
│   │   ├── TalentoService           # CRUD + filtros de talentos
│   │   └── TagService               # Geracao automatica de tags
│   └── validation/                  # Validadores customizados
│       ├── CNPJ / CNPJValidator     # Validacao de CNPJ brasileiro
│       ├── DateOrderValidator       # data_limite >= data_abertura
│       └── MaxCollectionSize        # Limite de tamanho de colecoes
│
├── infrastructure/                  # Detalhes tecnicos
│   ├── security/
│   │   ├── SecurityConfig           # Configuracao do Spring Security
│   │   ├── JwtConfig                # Encoder/Decoder JWT com RSA
│   │   ├── JwtTokenService          # Geracao de tokens (expira em 24h)
│   │   ├── OwnershipChecker         # Verificacao de propriedade de recursos
│   │   └── RateLimiter              # Limite de tentativas de login
│   ├── persistence/
│   │   ├── repository/              # Repositorios Spring Data JPA
│   │   └── specification/           # JPA Specifications (filtros dinamicos)
│   └── config/
│       └── LoggingFilter            # Log de requisicoes/respostas
│
└── web/                             # Camada HTTP
    ├── controller/                  # Controllers REST
    │   ├── AuthController           # /api/v1/auth
    │   ├── OrganizadorController    # /api/v1/organizadores
    │   ├── OportunidadeController   # /api/v1/oportunidades
    │   ├── ResolvedorController     # /api/v1/resolvedores
    │   └── TalentoController        # /api/v1/talentos
    └── exception/
        ├── GlobalExceptionHandler   # Tratamento global de erros
        ├── BusinessException        # Excecao de regra de negocio
        └── ResourceNotFoundException # Recurso nao encontrado (404)
```

### Outros Arquivos Importantes

```
src/main/resources/
├── application.yml                  # Configuracao principal
├── application-dev.yml              # Configuracao de desenvolvimento (hot reload)
├── application-test.yml             # Configuracao de testes (H2 in-memory)
├── certs/
│   ├── public.pem                   # Chave publica RSA
│   └── private.pem                  # Chave privada RSA
└── db/migration/                    # Migracoes Flyway (V1 a V8)
```

---

## Banco de Dados

### Migracoes Flyway

O Flyway executa automaticamente as migracoes ao iniciar a aplicacao. Nao e necessario rodar nenhum script manualmente.

| Migracao | Descricao |
|----------|-----------|
| `V1` | Cria tabela `usuarios` (id, nome, email, senha_hash, role) |
| `V2` | Cria tabela `organizadores` + tabelas de juncao (publico_alvo, areas_tematicas, links, tags) |
| `V3` | Cria tabela `oportunidades` + tabelas de juncao + campos JSONB (anexos, parceiros) |
| `V4` | Cria tabela `resolvedores` + tabelas de juncao |
| `V5` | Cria tabela `talentos` + tabelas de juncao (skills, areas_tematicas) |
| `V6` | Insere usuario admin (`admin@coreto.com.br`) |
| `V7` | Insere dados de demonstracao (7 usuarios, organizadores, oportunidades, resolvedores, talentos) |
| `V8` | Corrige hashes BCrypt dos usuarios seed |

### Padroes do Banco

- **Soft Delete:** Todos os registros tem campos `deleted` e `deleted_at`. Nada e removido fisicamente.
- **Auditoria:** Campos `created_at`, `updated_at` e `created_by` em todas as entidades.
- **JSONB:** Campos flexiveis como `parceiros` e `anexos` usam o tipo nativo JSONB do PostgreSQL.
- **ElementCollection:** Arrays de enums armazenados em tabelas de juncao para queries eficientes com JPA Specifications.
- **Indices:** Otimizados para filtros comuns (deleted, type, status, email, CNPJ).

---

## Testes

### Executar todos os testes

```bash
./mvnw test
```

### Ambiente de testes

Os testes usam:
- **H2** como banco in-memory para testes rapidos
- **Testcontainers** com PostgreSQL para testes de integracao
- **Spring Security Test** para simular autenticacao

### Build completo (compilar + testar)

```bash
./mvnw clean package
```

### Build sem testes

```bash
./mvnw clean package -DskipTests
```

---

## Postman

O projeto inclui colecoes Postman prontas para testar a API:

| Arquivo | Descricao |
|---------|-----------|
| `CORETO-API.postman_collection.json` | Colecao completa com todos os endpoints |
| `CORETO-API.postman_environment.json` | Ambiente de producao |
| `CORETO-API-LOCAL.postman_environment.json` | Ambiente local (localhost:8080) |

### Como importar

1. Abra o Postman
2. Clique em **Import**
3. Arraste os arquivos `.json` da raiz do projeto
4. Selecione o ambiente desejado (Local ou Producao)
5. Faca login com um dos usuarios de demonstracao - o token e salvo automaticamente nas variaveis do ambiente

---

## Decisoes Arquiteturais

| Decisao | Justificativa |
|---------|---------------|
| **JWT com RSA** | Par de chaves publica/privada local. Sem dependencia de servidor OAuth externo. Permite validacao stateless. |
| **Soft Delete** | Registros nunca sao removidos fisicamente do banco. Permite auditoria e recuperacao. |
| **TAGs derivadas** | Geradas automaticamente pelo `TagService` a partir dos campos classificatorios das entidades. |
| **JPA Specifications** | Filtros compostos e dinamicos sem necessidade de QueryDSL ou queries SQL manuais. |
| **ElementCollection** | Enums armazenados em tabelas de juncao para queries eficientes com JPQL/Criteria API. |
| **JSONB** | Campos flexiveis (parceiros, anexos) usando tipo nativo PostgreSQL. Permite queries JSON sem desnormalizar. |
| **Clean Architecture** | Separacao em camadas (domain, application, infrastructure, web) para facilitar testes e manutencao. |
| **Flyway** | Migracoes versionadas e deterministicas. O banco e sempre reproduzivel a partir dos scripts. |
| **Rate Limiting** | Protecao contra brute force no login (5 tentativas por email a cada 5 minutos). |
| **CORS configuravel** | Origens permitidas via variavel de ambiente para flexibilidade entre ambientes. |
