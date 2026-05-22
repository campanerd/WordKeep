# WordKeep

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-blue?logo=openjdk" alt="Java 17"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen?logo=springboot" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/SQLite-embedded-lightgrey?logo=sqlite" alt="SQLite"/>
  <img src="https://img.shields.io/badge/status-em%20desenvolvimento-yellow" alt="Status"/>
</p>

> Aplicação web para estudo de vocabulário em inglês. Cadastre palavras, organize-as em decks temáticos e revise quando quiser — com tradução automática integrada via [MyMemory API](https://mymemory.translated.net/).

---

## Sumário

- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Como executar](#como-executar)
- [Endpoints da API](#endpoints-da-api)
- [Autores](#autores)

---

## Funcionalidades

| Status | Funcionalidade |
|--------|---------------|
| ✅ | Cadastro de palavras com tradução automática (en → pt-BR via MyMemory API) |
| ✅ | Criação e gerenciamento de decks temáticos |
| ✅ | Associação de palavras a decks |
| ✅ | Visualização de decks em carrossel interativo |
| ✅ | Edição e remoção de palavras e decks |
| ✅ | Prevenção de duplicatas no mesmo deck |
| ✅ | Tratamento global de erros na API |
| 🔜 | Sistema de revisão com flashcards |

---

## Tecnologias

### Backend
| Tecnologia | Versão | Uso |
|------------|--------|-----|
| Java | 17 | Linguagem principal |
| Spring Boot | 3.4.5 | Framework web |
| Spring Data JPA | — | Persistência |
| Hibernate Community Dialects | — | Suporte ao SQLite |
| SQLite | — | Banco de dados embarcado |
| Flyway | — | Migrations do banco |
| Bean Validation | — | Validação de dados |
| Lombok | — | Redução de boilerplate |
| Spring WebFlux (WebClient) | — | Integração com MyMemory API |

### Frontend
| Tecnologia | Uso |
|------------|-----|
| HTML5 | Estrutura das páginas |
| CSS3 | Estilização e animações |
| JavaScript (ES Modules) | Lógica de interface |

---

## Estrutura do projeto

```
WordKeep/
├── backend/
│   ├── src/main/java/wordkeep/apiEnglish/
│   │   ├── controller/         # WordController, DeckController, TratadorDeErros
│   │   ├── word/               # Entidade, DTOs e serviço de palavras
│   │   ├── deck/               # Entidade, DTOs e serviço de decks
│   │   ├── translation/        # Integração com MyMemory API
│   │   └── config/             # CORS, WebClient, conversores
│   ├── src/main/resources/
│   │   └── db/migration/       # Scripts Flyway
│   ├── bruno/                  # Coleção de requisições (Bruno API client)
│   └── pom.xml
└── frontend/
    ├── index.html              # Página principal (carrossel de decks)
    ├── pages/
    │   ├── criarPalavra.html   # Cadastro de palavras
    │   ├── criarDecks.html     # Criação de decks
    │   ├── editar.html         # Edição de palavras
    │   └── decks.html          # Listagem de decks
    └── src/
        ├── exibirDeck.js       # Lógica do carrossel
        ├── script.js           # Cadastro de palavras
        ├── decks.js            # Listagem/gerenciamento de decks
        ├── criar.js            # Criação de decks
        ├── edit.js             # Edição de palavras
        └── config.js           # URL da API (não versionado — veja abaixo)
```

---

## Como executar

### Pré-requisitos

- Java 17+
- Maven 3.8+
- Servidor local para o frontend (ex: [Live Server](https://marketplace.visualstudio.com/items?itemName=ritwickdey.LiveServer) no VS Code)

### 1. Backend

```bash
cd backend
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

> **Porta ocupada?** Rode `netstat -ano | findstr :8080` para encontrar o PID e encerre com `taskkill /PID <pid> /F`.

### 2. Frontend

Crie o arquivo `frontend/src/config.js` com a URL do backend:

```js
export const API_URL = "http://localhost:8080";
```

> ⚠️ Este arquivo **não está versionado** (está no `.gitignore`) pois contém o endereço local de cada máquina. Deve ser criado manualmente por cada desenvolvedor.

Em seguida, abra `frontend/index.html` com um servidor local.

---

## Endpoints da API

> **Base URL:** `http://localhost:8080`
>
> ⚠️ A tradução automática depende da [MyMemory API](https://mymemory.translated.net/). Sem conexão com a internet, o campo `translation` não será preenchido.

---

### Palavras

#### `POST /words` — Cadastrar palavra
A tradução é gerada automaticamente. Não é possível cadastrar a mesma palavra duas vezes no mesmo deck.

**Request body:**
```json
{
  "word": "serendipity",
  "deckId": 1
}
```

**Response `201 Created`:**
```json
{
  "id": 1,
  "word": "serendipity",
  "translation": "serendipidade",
  "sourceLanguage": "en",
  "targetLanguage": "pt-BR",
  "createdAt": "2025-01-01T10:00:00",
  "decks": [
    { "id": 1, "name": "Vocabulário Geral" }
  ]
}
```

---

#### `GET /words` — Listar todas as palavras

**Response `200 OK`:**
```json
[
  {
    "id": 1,
    "word": "serendipity",
    "translation": "serendipidade",
    "decks": [{ "id": 1, "name": "Vocabulário Geral" }]
  }
]
```

---

#### `PUT /words` — Atualizar palavra
A tradução é regerada automaticamente ao atualizar a palavra.

**Request body:**
```json
{
  "id": 1,
  "word": "ephemeral"
}
```

**Response `200 OK`:** retorna o objeto completo atualizado (mesmo formato do `POST`).

---

#### `DELETE /words/{id}` — Excluir palavra

**Response `204 No Content`**

---

#### `GET /words/translate` — Traduzir palavra

**Query params:** `word`, `sourceLanguage`, `targetLanguage`

**Exemplo:** `GET /words/translate?word=hello&sourceLanguage=en&targetLanguage=pt-BR`

**Response `200 OK`:**
```json
{
  "translation": "olá"
}
```

---

### Decks

#### `POST /decks` — Criar deck

**Request body:**
```json
{
  "name": "Vocabulário Geral"
}
```

**Response `201 Created`:**
```json
{
  "id": 1,
  "name": "Vocabulário Geral",
  "createdAt": "2025-01-01T10:00:00"
}
```

---

#### `GET /decks` — Listar todos os decks

**Response `200 OK`:**
```json
[
  { "id": 1, "name": "Vocabulário Geral" }
]
```

---

#### `PUT /decks` — Atualizar deck

**Request body:**
```json
{
  "id": 1,
  "name": "Novo Nome"
}
```

**Response `200 OK`:** retorna o objeto completo atualizado (mesmo formato do `POST`).

---

#### `DELETE /decks/{id}` — Excluir deck

**Response `204 No Content`**

---

#### `GET /decks/{id}/words` — Listar palavras de um deck

**Response `200 OK`:**
```json
[
  {
    "id": 1,
    "word": "serendipity",
    "translation": "serendipidade"
  }
]
```

---

### Respostas de erro

| Status | Situação |
|--------|----------|
| `400 Bad Request` | Campos obrigatórios ausentes ou inválidos |
| `404 Not Found` | Deck ou palavra não encontrado(a) |
| `409 Conflict` | Palavra já cadastrada neste deck |

---

## Autores

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/campanerd">
        <b>Davi Campaner</b>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/regazzio">
        <b>Vinicius Regazio</b>
      </a>
    </td>
  </tr>
</table>
