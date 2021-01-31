# Central Errors
<p>
    <img alt="GitHub top language" src="https://img.shields.io/github/languages/top/aceleradev-java/central-errors">
    <a href="https://github.com/my-study-area">
        <img alt="Made by" src="https://img.shields.io/badge/made%20by-adriano%20avelino-gree">
    </a>
    <img alt="Repository size" src="https://img.shields.io/github/repo-size/aceleradev-java/central-errors">
    <a href="https://github.com/EliasGcf/readme-template/commits/master">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/aceleradev-java/central-errors">
    </a>
</p>

O Aceleradev é um treinamento da Codenation com duração de 10 semanas de imersão em programação, no meu caso Java online. Esse é o desafio final do Aceleradev.

Sobre o projeto:
- [URL principal da Api](https://aceleradev-java-central-errors.herokuapp.com/v1/protected/events)
- [Documentação](https://aceleradev-java-central-errors.herokuapp.com/swagger-ui.html)

<image src="src/main/resources/static/img/app.png" alt="Imagem da documentação do projeto criada com Swagger">

## Objetivo

## Desafio
Em projetos modernos é cada vez mais comum o uso de arquiteturas baseadas em serviços ou microsserviços. Nestes ambientes complexos, erros podem surgir em diferentes camadas da aplicação (backend, frontend, mobile, desktop) e mesmo em serviços distintos. Desta forma, é muito importante que os desenvolvedores possam centralizar todos os registros de erros em um local, de onde podem monitorar e tomar decisões mais acertadas. Neste projeto vamos implementar uma API Rest para centralizar registros de erros de aplicações.

Abaixo estão os requisitos desta API, o time terá total liberdade para tomar as decisões técnicas e de arquitetura da API, desde que atendam os requisitos abaixo.

### API
#### Tecnologia
Utilizar a tecnologia base da aceleração para o desenvolvimento (Exemplo: Java, Node.js)

#### Premissas
A API deve ser pensada para atender diretamente um front-end
Deve ser capaz de gravar os logs de erro em um banco de dados relacional
O acesso a ela deve ser permitido apenas por requisições que utilizem um token de acesso válido

#### Funcionalidades
- Deve permitir a autenticação do sistema que deseja utilizar a API gerando o Token de Acesso
- Pode ser acessado por multiplos sistemas
- Deve permitir gravar registros de eventos de log salvando informações de Level(error, warning, info), Descrição do Evento, LOG do Evento, ORIGEM(Sistema ou Serviço que originou o evento), DATA(Data do evento), - QUANTIDADE(Quantidade de Eventos de mesmo tipo)
- Deve permitir a listagem dos eventos juntamente com a filtragem de eventos por qualquer parâmetro - especificado acima
- Deve suportar Paginação
- Deve suportar Ordenação por diferentes tipos de atributos
- A consulta de listagem não deve retornar os LOGs dos Eventos
- Deve permitir a busca de um evento por um ID, dessa maneira exibindo o LOG desse evento em específico

## Começando
Para configurar o ambiente de desenvolvimento execute os seguintes comandos:
```bash
#clone o projeto
git clone https://github.com/aceleradev-java/central-errors.git

#entre no diretório
cd central-errors

#inicie a aplicação
./gradlew bootRun
```

### Exemplo de uso
Com a aplicação inicia será necessário um token válido para realizar uma requisição de teste, conforme o exemplo abaixo:

```bash
#gere o token para acesso
curl --location --request POST 'http://localhost:8080/login' \
--header 'Authorization: Basic dXN1YXJpbzoxMjM=' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "user", 
    "password": "123"
}'
```
Usuários e senhas para testes:
| Usuário |  Senha |
| ------- | ------ |
| admin   | 123    |
| user    | 123    |

Exemplo de resposta da requisição válida:
```bash
#token gerado na requisição
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjEyMTk4MTk0fQ.PbMOH1CkMva0QSSnjbnpIhhG4jqupR4Utw1pf_9tUy7htL-UOCRhWR-sZBHWulFxPvtveecfqq8EdayQMjFghw
```

Com o token em mãos, para realizar uma requisição, execute o requisição abaixo:

```bash
curl --location --request GET 'http://localhost:8080/v1/protected/events' \
--header 'Authorization: Bearer seuToken' \
--data-raw ''
```
`seuToken`: é o token gerado na requisição POST na rota http://localhost:8080/login

Exemplo de requisição `GET` utilizando o token gerado acima:
```bash
curl --location --request GET 'http://localhost:8080/v1/protected/events' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjEyMTk3OTQwfQ.pbRp9u1hbyctYPOP7z1EjN4Ne0e7Tj2MTtbV07IGgXHPAuYpc56ZWfa12STnmISgKPPMZLa0M4su8CB2-5_YLQ' \
--data-raw ''
```

Exemplo de resposta:
```json
{
    "content": [
        {
            "id": 1,
            "level": "ERROR",
            "description": "erro 1",
            "source": "sistema",
            "date": "2020-01-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 2,
            "level": "WARNING",
            "description": "erro 2",
            "source": "api",
            "date": "2020-03-18T21:02:44.206",
            "quantity": 2
        },
        {
            "id": 3,
            "level": "WARNING",
            "description": "erro 3",
            "source": "teste",
            "date": "2020-02-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 4,
            "level": "WARNING",
            "description": "erro 4",
            "source": "sistema",
            "date": "2020-01-18T21:02:44.206",
            "quantity": 9
        },
        {
            "id": 5,
            "level": "ERROR",
            "description": "erro 5",
            "source": "sistema",
            "date": "2018-02-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 6,
            "level": "ERROR",
            "description": "erro 6",
            "source": "sistema",
            "date": "2020-02-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 7,
            "level": "INFO",
            "description": "erro 7",
            "source": "sistema",
            "date": "2017-05-18T21:02:44.206",
            "quantity": 2
        },
        {
            "id": 8,
            "level": "INFO",
            "description": "erro 8",
            "source": "sistema",
            "date": "2020-09-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 9,
            "level": "INFO",
            "description": "erro 9",
            "source": "sistema",
            "date": "2015-07-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 10,
            "level": "ERROR",
            "description": "erro 10",
            "source": "sistema",
            "date": "2010-05-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 11,
            "level": "ERROR",
            "description": "erro 11",
            "source": "sistema",
            "date": "2020-01-18T21:02:44.206",
            "quantity": 4
        },
        {
            "id": 12,
            "level": "ERROR",
            "description": "erro 11",
            "source": "sistema",
            "date": "2010-01-18T21:02:44.206",
            "quantity": 7
        },
        {
            "id": 13,
            "level": "WARNING",
            "description": "erro 12",
            "source": "sistema",
            "date": "2000-08-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 14,
            "level": "INFO",
            "description": "erro 13 ",
            "source": "api",
            "date": "2020-08-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 15,
            "level": "INFO",
            "description": "erro 14 ",
            "source": "sistema",
            "date": "2005-08-18T21:02:44.206",
            "quantity": 2
        },
        {
            "id": 16,
            "level": "INFO",
            "description": "erro 15",
            "source": "sistema",
            "date": "2020-02-18T21:02:44.206",
            "quantity": 6
        },{
    "content": [
        {
            "id": 1,
            "level": "ERROR",
            "description": "erro 1",
            "source": "sistema",
            "date": "2020-01-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 2,
            "level": "WARNING",
            "description": "erro 2",
            "source": "api",
            "date": "2020-03-18T21:02:44.206",
            "quantity": 2
        },
        {
            "id": 3,
            "level": "WARNING",
            "description": "erro 3",
            "source": "teste",
            "date": "2020-02-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 4,
            "level": "WARNING",
            "description": "erro 4",
            "source": "sistema",
            "date": "2020-01-18T21:02:44.206",
            "quantity": 9
        },
        {
            "id": 5,
            "level": "ERROR",
            "description": "erro 5",
            "source": "sistema",
            "date": "2018-02-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 6,
            "level": "ERROR",
            "description": "erro 6",
            "source": "sistema",
            "date": "2020-02-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 7,
            "level": "INFO",
            "description": "erro 7",
            "source": "sistema",
            "date": "2017-05-18T21:02:44.206",
            "quantity": 2
        },
        {
            "id": 8,
            "level": "INFO",
            "description": "erro 8",
            "source": "sistema",
            "date": "2020-09-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 9,
            "level": "INFO",
            "description": "erro 9",
            "source": "sistema",
            "date": "2015-07-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 10,
            "level": "ERROR",
            "description": "erro 10",
            "source": "sistema",
            "date": "2010-05-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 11,
            "level": "ERROR",
            "description": "erro 11",
            "source": "sistema",
            "date": "2020-01-18T21:02:44.206",
            "quantity": 4
        },
        {
            "id": 12,
            "level": "ERROR",
            "description": "erro 11",
            "source": "sistema",
            "date": "2010-01-18T21:02:44.206",
            "quantity": 7
        },
        {
            "id": 13,
            "level": "WARNING",
            "description": "erro 12",
            "source": "sistema",
            "date": "2000-08-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 14,
            "level": "INFO",
            "description": "erro 13 ",
            "source": "api",
            "date": "2020-08-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 15,
            "level": "INFO",
            "description": "erro 14 ",
            "source": "sistema",
            "date": "2005-08-18T21:02:44.206",
            "quantity": 2
        },
        {
            "id": 16,
            "level": "INFO",
            "description": "erro 15",
            "source": "sistema",
            "date": "2020-02-18T21:02:44.206",
            "quantity": 6
        },
        {
            "id": 17,
            "level": "WARNING",
            "description": "erro 16",
            "source": "sistema",
            "date": "2020-08-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 18,
            "level": "ERROR",
            "description": "erro 17",
            "source": "teste",
            "date": "2020-08-18T21:02:44.206",
            "quantity": 2
        },
        {
            "id": 19,
            "level": "WARNING",
            "description": "erro 18",
            "source": "teste",
            "date": "2020-08-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 20,
            "level": "INFO",
            "description": "erro 19",
            "source": "sistema",
            "date": "2020-08-18T21:02:44.206",
            "quantity": 5
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "pageNumber": 0,
        "pageSize": 20,
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalElements": 31,
    "last": false,
    "totalPages": 2,
    "first": true,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "numberOfElements": 20,
    "size": 20,
    "number": 0,
    "empty": false
}
        {
            "id": 17,
            "level": "WARNING",
            "description": "erro 16",
            "source": "sistema",
            "date": "2020-08-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 18,
            "level": "ERROR",
            "description": "erro 17",
            "source": "teste",
            "date": "2020-08-18T21:02:44.206",
            "quantity": 2
        },
        {
            "id": 19,
            "level": "WARNING",
            "description": "erro 18",
            "source": "teste",
            "date": "2020-08-18T21:02:44.206",
            "quantity": 1
        },
        {
            "id": 20,
            "level": "INFO",
            "description": "erro 19",
            "source": "sistema",
            "date": "2020-08-18T21:02:44.206",
            "quantity": 5
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "pageNumber": 0,
        "pageSize": 20,
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalElements": 31,
    "last": false,
    "totalPages": 2,
    "first": true,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "numberOfElements": 20,
    "size": 20,
    "number": 0,
    "empty": false
}
```

## Testes
Testes de Service para User:
```bash
./gradlew test -i --tests br.com.aceleradev.centralerrors.service.UserServiceTest
```

Testes de Controller para User:
```bash
./gradlew test -i --tests br.com.aceleradev.centralerrors.endpoint.UserControllerTest
```

Testes de Repository para User:
```bash
./gradlew test -i --tests br.com.aceleradev.centralerrors.repository.UserRepositoryTest
```
