# netdeal-test

Projeto de desafio teste

## Requisitos
* Possuir o Java e o maven instalados
* Possuir um banco MySql executando, podendo ser instalação direta ou um container docker
  * O mesmo deve conter um database chamado ``teste``, as demais configurações de acesso do banco podem ser consultadas no ``applications.properties``
* Importar o projeto na sua IDE de preferência

## Como executar
* ### Backend
  * Rodar o comando ``mvn clean install``
  * Acessar a classe ```DemoApplication.java``` e executar a mesma

* ### Frontend
  * Acessar a pasta ``src/main/resources/static/`` e rodar o comando ``npm start``, o mesmo irá iniciar o frontend
  * Acessar o endereço `` http://localhost:4646`` onde estará rodando o frontend
    


