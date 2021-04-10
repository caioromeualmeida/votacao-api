# API de votação.

# Dependências
* Lombok
Para a configuração de getters e setters foi usado o Lombok. É necessário realizar a instalação para a IDE a ser usada por quem irá testar.
https://projectlombok.org/setup/overview

* Docker
A versão utulizada é a 20.10.5. A base de dados do MariaDB e o RabbitMq estão configurados na pasta docker, no raiz do projeto. Há um dockekfile, mas o arquivo é usado apenas para adicionar um plugin que permite o agendamento de execução no RabbitMq. Para subir ambos basta executar: docker-compose up e para finalizar executar docker-compose down, ambos os comandos na pasta docker.

* MapStruct
Foi usado para facilitar a geração dos mappers. Já está configurado no maven.

# Observações
* Foram realizados os testes básicos de rotas que visam testar os verbos http. Como melhoria é necessário adicionar o hateos, paginação etc. A nível de testes, faltam ser realizados os testes de serviço, separando as camadas. Foi feito dessa forma por falta de tempo hábil. Como versionamento a nível de projeto, o ideal seria utilizar o Gitflow.

* Os dtos foram gerados para evitar a injeção de código malicioso via parâmetro, isolando o modelo dos jsons que sçao trafegados.

* Como o projeto foi subido de uma única vez, não há histórico de commits.

* O RabbitMq irá mostrar um log da mensagem após o prazo de votação expirar. O console está mapeado para: http://localhost:8090/

* Como melhoria na qualidade do código poderia ser adicionado o SonarQube.

# Documentação 
Collection e documentação via Postman para consumo da API.
<br>
https://www.getpostman.com/collections/c7cb196c5a80c2e511db
<br>
https://documenter.getpostman.com/view/4512599/TzCV2Pqv

# Versionamento
* O projeto foi feito para que, em futuras versões seja versionado pela url. O ideal seria criar as pastas de versão abaixo da pasta api. ex: [v1, v2, v3], versionar os controllers, models e objetos que seriam específicos e separar as chamadas via url. Com isso seria possível manter várias versões da api no ar, não "quebrando" a compatibilidade com quem já consome ela.

Bom teste!
