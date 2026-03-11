# 🏠 Sistema de Aluguel — API Backend

API REST para gerenciamento de imóveis, contratos de aluguel, inquilinos, contas e pagamentos. Desenvolvida com **Java 17 + Spring Boot 3 + MySQL**.

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 17 | Linguagem principal |
| Spring Boot | 3.x | Framework base |
| Spring Security | 6.x | Autenticação e autorização |
| Spring Data JPA | 3.x | Persistência de dados |
| JWT (JJWT) | 0.11+ | Tokens de autenticação |
| MySQL | 8.x | Banco de dados |
| Lombok | latest | Redução de boilerplate |
| Maven | 3.x | Gerenciamento de dependências |

---

## 📋 Pré-requisitos

- Java 17+
- Maven 3.8+
- MySQL 8.x rodando localmente


---

### Unidades — `/unidades` 🔒

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/unidades` | Cria uma unidade |
| `GET` | `/unidades` | Lista unidades do usuário |
| `DELETE` | `/unidades/{id}` | Remove uma unidade |

```json
POST /unidades
{ "nome": "Residencial Aurora" }
```

---

### Casas — `/casas` 🔒

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/casas` | Cria uma casa |
| `GET` | `/casas` | Lista todas as casas |
| `GET` | `/casas/unidade/{id}` | Lista casas de uma unidade |
| `GET` | `/casas/status/{status}` | Filtra por status (`VAGA`, `ALUGADA`, `MANUTENCAO`) |
| `DELETE` | `/casas/{id}` | Remove uma casa (não pode ter contrato ativo) |

```json
POST /casas
{
  "unidadeId": 1,
  "numero": "101",
  "status": "VAGA"
}
```

---

### Inquilinos — `/inquilinos` 🔒

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/inquilinos` | Cadastra inquilino |
| `GET` | `/inquilinos` | Lista todos |
| `GET` | `/inquilinos/{id}` | Busca por ID |
| `PUT` | `/inquilinos/{id}` | Atualiza dados |
| `DELETE` | `/inquilinos/{id}` | Remove (não pode ter contrato ativo) |

```json
POST /inquilinos
{
  "nome": "João Silva",
  "cpf": "123.456.789-00",
  "telefone": "(81) 99999-0000",
  "email": "joao@email.com"
}
```

---

### Contratos — `/contratos` 🔒

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/contratos` | Cria contrato (casa muda para ALUGADA) |
| `GET` | `/contratos` | Lista todos os contratos |
| `GET` | `/contratos/{id}` | Busca por ID |
| `PUT` | `/contratos/{id}/encerrar` | Encerra contrato (casa volta para VAGA) |
| `GET` | `/contratos/{id}/total-mensal` | Retorna aluguel + contas do mês |

```json
POST /contratos
{
  "casaId": 1,
  "inquilinoId": 2,
  "valorAluguel": 1200.00,
  "dataInicio": "2025-01-01",
  "dataFim": "2025-12-31"
}
```

> **Regras:** uma casa só pode ter um contrato ativo por vez. Um inquilino também não pode ter dois contratos ativos simultaneamente.

---

### Contas — `/contas` 🔒

Contas de consumo vinculadas a um contrato ativo (água, luz, internet, etc).

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/contas` | Cria uma conta |
| `GET` | `/contas/contrato/{id}` | Lista contas de um contrato |
| `GET` | `/contas/{id}` | Busca por ID |
| `DELETE` | `/contas/{id}` | Remove uma conta |

```json
POST /contas
{
  "contratoId": 1,
  "tipo": "AGUA",
  "valor": 85.50,
  "vencimento": "2025-03-10"
}
```

**Tipos disponíveis:** `AGUA` · `LUZ` · `INTERNET` · `CONDOMINIO` · `IPTU` · `OUTROS`

---

### Pagamentos — `/pagamentos` 🔒

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/pagamentos` | Registra pagamento |
| `GET` | `/pagamentos` | Lista todos |
| `GET` | `/pagamentos/contrato/{id}` | Lista por contrato |
| `GET` | `/pagamentos/{id}` | Busca por ID |
| `DELETE` | `/pagamentos/{id}` | Remove um pagamento |

```json
POST /pagamentos
{
  "contratoId": 1,
  "dataPagamento": "2025-03-05",
  "mesReferencia": "2025-03-01"
}
```

> **Regras:** o valor é calculado automaticamente (aluguel + contas do mês). O status é definido pela data: pagamento até o dia 10 → `PAGO`, após o dia 10 → `ATRASADO`. Não é permitido duplicar pagamento no mesmo mês para o mesmo contrato.

**Status possíveis:** `PAGO` · `PENDENTE` · `ATRASADO` · `CANCELADO`

---

## 🗂️ Estrutura do Projeto

```
src/main/java/com/pamella/sistema_aluguel_api/
├── controller/        # Endpoints REST
├── service/           # Regras de negócio
├── repository/        # Acesso ao banco (Spring Data JPA)
├── model/             # Entidades JPA
├── dto/               # Request e Response records
├── security/          # JWT, filtros e configuração de segurança
└── exception/         # GlobalExceptionHandler
```

---


## 📄 Licença

Projeto de uso pessoal. Desenvolvido por **Pamella**.
