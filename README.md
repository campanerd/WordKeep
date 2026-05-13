# WordKeep 📚

> 🚧 **Projeto em desenvolvimento** — funcionalidades podem mudar ao longo do tempo.

WordKeep é uma aplicação para estudo de vocabulário em inglês. O usuário cadastra palavras com suas traduções, organiza em decks temáticos e pode revisá-las quando quiser.

---

## Tecnologias

**Backend**
- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- SQLite + Hibernate Community Dialects
- Flyway (migrations)
- Bean Validation
- Lombok

**Frontend**
- HTML
- CSS
- JavaScript

---

## Estrutura do projeto

```
WordKeep/
├── backend/         # API REST em Java Spring Boot
│   ├── src/
│   ├── bruno/       # Coleção de requisições (Bruno)
│   └── pom.xml
└── frontend/        # Interface web
    └── src/
```

---

## Funcionalidades

- [x] Cadastro de palavras com tradução e idiomas
- [x] Cadastro de decks (listas temáticas)
- [x] Associação de palavras a decks
- [x] Listagem de palavras e decks
- [x] Listagem de palavras por deck
- [x] Atualização de palavras e decks
- [x] Prevenção de palavras duplicadas no mesmo deck
- [ ] Remoção de palavras e decks
- [ ] Tradução automática via API externa
- [ ] Tratamento global de erros

---

## Como executar

### Pré-requisitos
- Java 17+
- Maven

### Backend

```bash
cd backend
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

### Frontend

Abra o arquivo `frontend/src/index.html` em um servidor local (ex: Live Server no VS Code).

---

## Endpoints principais

| Método | Rota                  | Descrição                        |
|--------|-----------------------|----------------------------------|
| POST   | `/words`              | Cadastrar palavra                |
| GET    | `/words`              | Listar todas as palavras         |
| PUT    | `/words`              | Atualizar palavra                |
| POST   | `/decks`              | Cadastrar deck                   |
| GET    | `/decks`              | Listar todos os decks            |
| PUT    | `/decks`              | Atualizar deck                   |
| GET    | `/decks/{id}/words`   | Listar palavras de um deck       |

---

## Autores

- Davi Campaner
- Vinicius Regazio
