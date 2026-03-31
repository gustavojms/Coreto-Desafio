# CORETO 2.0 API

Backend do ecossistema de inovacao CORETO - Prefeitura do Recife.

## Stack

- Java 21 + Spring Boot 3.4.x
- PostgreSQL 17 + Flyway
- Spring Security + JWT (RSA)
- MapStruct + Lombok
- SpringDoc OpenAPI (Swagger)

## Pre-requisitos

- Java 21+
- Docker e Docker Compose (para PostgreSQL)

## Setup

### 1. Iniciar banco de dados

```bash
docker-compose up -d
```

### 2. Executar a aplicacao

```bash
./mvnw spring-boot:run
```

A aplicacao iniciara na porta 8080.

### 3. Acessar Swagger UI

Abra no navegador: http://localhost:8080/swagger-ui.html

## Autenticacao

### Registrar usuario

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"nome":"Teste","email":"teste@teste.com","senha":"123456","role":"ORGANIZADOR"}'
```

### Login

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@coreto.com.br","senha":"admin123"}'
```

### Usar token

```bash
curl -H "Authorization: Bearer {token}" http://localhost:8080/api/v1/organizadores
```

## Endpoints

| Metodo | Endpoint | Descricao |
|--------|----------|-----------|
| POST | /api/v1/auth/register | Registrar usuario |
| POST | /api/v1/auth/login | Login |
| POST | /api/v1/organizadores | Criar organizador |
| GET | /api/v1/organizadores/{id} | Buscar organizador |
| PUT | /api/v1/organizadores/{id} | Atualizar organizador |
| DELETE | /api/v1/organizadores/{id} | Deletar organizador (soft) |
| GET | /api/v1/organizadores | Listar com filtros |
| POST | /api/v1/oportunidades | Criar oportunidade |
| GET | /api/v1/oportunidades/{id} | Buscar oportunidade |
| PUT | /api/v1/oportunidades/{id} | Atualizar oportunidade |
| DELETE | /api/v1/oportunidades/{id} | Deletar oportunidade (soft) |
| GET | /api/v1/oportunidades | Listar com filtros |
| POST | /api/v1/resolvedores | Criar resolvedor |
| GET | /api/v1/resolvedores/{id} | Buscar resolvedor |
| PUT | /api/v1/resolvedores/{id} | Atualizar resolvedor |
| DELETE | /api/v1/resolvedores/{id} | Deletar resolvedor (soft) |
| GET | /api/v1/resolvedores | Listar com filtros |
| POST | /api/v1/talentos | Criar talento |
| GET | /api/v1/talentos/{id} | Buscar talento |
| PUT | /api/v1/talentos/{id} | Atualizar talento |
| DELETE | /api/v1/talentos/{id} | Deletar talento (soft) |
| GET | /api/v1/talentos | Listar com filtros |

## Filtros disponiveis

Todos os endpoints GET de listagem suportam:
- `search` - busca por texto
- `tag` - filtrar por TAG
- `page` - pagina (default: 0)
- `size` - itens por pagina (default: 20)
- `sort` - ordenacao (ex: `createdAt,desc`)

Filtros especificos por entidade sao documentados no Swagger.

## Perfis de seguranca

| Perfil | Permissao |
|--------|-----------|
| ADMIN | Acesso total |
| ORGANIZADOR | CRUD dos seus registros + oportunidades |
| RESOLVEDOR | CRUD do seu perfil |
| TALENTO | CRUD do seu perfil |

## Testes

```bash
./mvnw test
```

## Estrutura do Projeto (Clean Architecture)

```
src/main/java/br/com/coreto/
  domain/          -> Entidades JPA e Enums
  application/     -> DTOs, Mappers, Services, Validations
  infrastructure/  -> Config, Persistence, Security
  web/             -> Controllers, Exception Handlers
```

## Decisoes Arquiteturais

1. **JWT com RSA** - Chave publica/privada local. Sem servidor OAuth externo.
2. **Soft Delete** - Registros nunca sao removidos fisicamente.
3. **TAGs derivadas** - Geradas automaticamente a partir dos campos das entidades.
4. **JPA Specifications** - Filtros compostos sem QueryDSL.
5. **ElementCollection** - Arrays de enums em tabelas de juncao para queries eficientes.
6. **JSONB** - Campos flexiveis (parceiros, anexos) em PostgreSQL nativo.
