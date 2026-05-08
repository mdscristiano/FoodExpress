# Sistema de Gerenciamento de Delivery (FoodExpress)

**Tema Escolhido:** Delivery  
**Integrantes do Grupo:** - Apollo Moura de Sousa - RGM: 46162232
- Cristiano Souza Santos - RGM: 45689385
- Felipe da Fonseca Silva - RGM: 44982887
- Jonathan de Souza - RGM: 45268908
- Igor Alves Ferreira - RGM: 44779542
- Lucas Costa de Lima - RGM: 45850259

## Objetivo do Sistema
O sistema FoodExpress gerencia as operações diárias de um aplicativo de delivery. Ele facilita a interação entre clientes, restaurantes, produtos e logística de entrega, centralizando a gestão que antes era manual.

## Funcionalidades Principais
O sistema conta com operações completas de CRUD para as seguintes áreas:
1. **Gerenciamento de Restaurantes:** Agora integrado com banco de dados PostgreSQL.
2. **Gerenciamento de Produtos:** Controle de catálogo com suporte a diferentes tipos de itens.
3. **Gerenciamento de Clientes:** Cadastro e gestão persistida em banco de dados relacional.
4. **Gerenciamento de Entregadores:** Controle de frota e status de disponibilidade.
5. **Gestão de Pedidos:** Criação interativa de pedidos com múltiplos itens e cálculo automático.
6. **Relatórios:** Visão geral automatizada baseada nos dados do sistema e do banco.

## Estrutura de Classes (8 Classes implementadas)
1. `Restaurante`: Representa os estabelecimentos parceiros.
2. `Cliente`: Usuário que realiza os pedidos.
3. `Produto`: **Classe Abstrata** base para o cardápio, utilizando o método abstrato `obterDetalhes()`.
   - `Comida`: Subclasse (atributo peso em gramas).
   - `Bebida`: Subclasse (atributo volume em ml).
4. `Pedido`: Vincula Cliente, Entregador e itens.
5. `ItemPedido`: Gerencia a quantidade de um produto num pedido.
6. `Entregador`: Profissional responsável pela logística.

## Regra de Negócio: Descontos Progressivos
O sistema calcula a fatura automaticamente seguindo estas faixas:
- **15% de desconto:** Pedidos acima de R$ 300,00.
- **10% de desconto:** Pedidos acima de R$ 200,00.
- **5% de desconto:** Pedidos acima de R$ 100,00.
- **Taxa de Entrega:** R$ 8,00 fixa.

---
## Regra de Negócio Complexa

**Cálculo de Valor Total com Descontos Progressivos:** O sistema realiza o cálculo da fatura do pedido em 4 etapas detalhadas:

1. **Subtotal:** Soma o preço de todos os produtos multiplicados por suas respectivas quantidades.

2. **Descontos Progressivos:** Avalia o subtotal e aplica reduções automáticas:

   - 15% de desconto para pedidos acima de R$ 300,00

   - 10% de desconto para pedidos acima de R$ 200,00

   - 5% de desconto para pedidos acima de R$ 100,00

3. **Logística:** Adiciona uma taxa de entrega fixa de R$ 8,00.

4. **Cupom Fiscal:** Exibe no console um resumo detalhado informando o subtotal, o valor do desconto aplicado (e sua porcentagem), a taxa fixa e o valor final a ser pago pelo cliente.



---



# Sobre o Projeto (FoodExpress)



O FoodExpress é um sistema desenvolvido inteiramente em Java, projetado para aplicar conceitos de Orientação a Objetos (Encapsulamento, Relacionamentos, Listas e Validações) automatizando a rotina de um estabelecimento de entrega. O sistema atua no terminal rodando "em memória" utilizando `ArrayList` para persistência em tempo de execução.



## Como executar



Pelo terminal, navegue até a pasta raiz do projeto e execute os comandos:



```bash

javac src/*.java

java -cp src Main



# Tecnologias e Padrões (Atualização CP3)

O projeto evoluiu para uma arquitetura mais profissional e persistente:
* **Persistência de Dados:** Integração com **PostgreSQL** via JDBC para Clientes e Restaurantes.
* **Padrão DAO (Data Access Object):** Separação total da lógica de acesso a dados.
* **Organização em Pacotes:** Código dividido em `model`, `dao` e `util` para melhor manutenção.
* **Programação Orientada a Objetos:** Uso de Abstração, Herança (Polimorfismo), Encapsulamento e Sobrecarga.

## Estrutura de Pastas
```text
FoodExpress/
├── lib/               # Driver JDBC do PostgreSQL (.jar)
├── src/
│   ├── dao/           # Classes de acesso ao banco (CRUD)
│   ├── model/         # Entidades e regras de negócio
│   ├── util/          # Conexão com o banco de dados
│   └── Main.java      # Classe principal com os menus
├── schema.sql         # Script de criação das tabelas
└── README.md