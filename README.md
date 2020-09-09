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

## Objetivo
Em projetos modernos é cada vez mais comum o uso de arquiteturas baseadas em serviços ou microsserviços. Nestes ambientes complexos, erros podem surgir em diferentes camadas da aplicação (backend, frontend, mobile, desktop) e mesmo em serviços distintos. Desta forma, é muito importante que os desenvolvedores possam centralizar todos os registros de erros em um local, de onde podem monitorar e tomar decisões mais acertadas. Neste projeto vamos implementar uma API Rest para centralizar registros de erros de aplicações.

Abaixo estão os requisitos desta API, o time terá total liberdade para tomar as decisões técnicas e de arquitetura da API, desde que atendam os requisitos abaixo.

## API
### Tecnologia
Utilizar a tecnologia base da aceleração para o desenvolvimento (Exemplo: Java, Node.js)

### Premissas
A API deve ser pensada para atender diretamente um front-end
Deve ser capaz de gravar os logs de erro em um banco de dados relacional
O acesso a ela deve ser permitido apenas por requisições que utilizem um token de acesso válido

### Funcionalidades
- Deve permitir a autenticação do sistema que deseja utilizar a API gerando o Token de Acesso
- Pode ser acessado por multiplos sistemas
- Deve permitir gravar registros de eventos de log salvando informações de Level(error, warning, info), Descrição do Evento, LOG do Evento, ORIGEM(Sistema ou Serviço que originou o evento), DATA(Data do evento), - QUANTIDADE(Quantidade de Eventos de mesmo tipo)
- Deve permitir a listagem dos eventos juntamente com a filtragem de eventos por qualquer parâmetro - especificado acima
- Deve suportar Paginação
- Deve suportar Ordenação por diferentes tipos de atributos
- A consulta de listagem não deve retornar os LOGs dos Eventos
- Deve permitir a busca de um evento por um ID, dessa maneira exibindo o LOG desse evento em específico
