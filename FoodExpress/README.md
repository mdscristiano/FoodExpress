# Sistema de Gerenciamento de Delivery (FoodExpress)

**Tema Escolhido:** Delivery
**Integrantes do Grupo:** 

 Apollo Moura de Sousa- RGM: 46162232
 Cristiano Souza Santos - RGM: 45689385
 Felipe da Fonseca Silva - RGM: 44982887
 Jonathan de Souza - RGM: - 45268908
 Igor Alves Ferreira - RGM: - 44779542
 Lucas Costa de Lima - RGM: - 45850259
 


## Objetivo do Sistema
O sistema FoodExpress tem como objetivo principal gerenciar as operações diárias de um aplicativo de delivery de alimentos. Ele visa facilitar a interação entre clientes, restaurantes (produtos) e a logística de entrega. 

Através do sistema, será possível manter um controle rigoroso sobre o catálogo de itens disponíveis, registrar os usuários que farão os pedidos e acompanhar o ciclo de vida de uma entrega. Isso resolve o problema de desorganização no recebimento de pedidos manuais e perda de informações sobre as entregas.

## Funcionalidades Principais (Mínimo 5)
1. Cadastro e gerenciamento de Clientes.
2. Cadastro e controle de Produtos (cardápio).
3. Registro de novos Pedidos (carrinho de compras).
4. Cadastro de Entregadores.
5. Acompanhamento do status do pedido (Preparando, Saiu para Entrega, Entregue).

## Estrutura de Classes Planejada
1. `Cliente`: Representa o usuário que faz o pedido.
2. `Produto`: Representa o item do cardápio (ex: lanche, bebida).
3. `Pedido`: Representa a transação em si, vinculando o Cliente aos Produtos.
4. `ItemPedido`: Representa a quantidade de um Produto específico dentro de um Pedido.
5. `Entregador`: Representa o profissional responsável por levar o pedido.

## Regra de Negócio Complexa
**Cálculo do Valor Total com Taxa de Entrega Dinâmica:** O valor total do pedido não será apenas a soma dos produtos. Ele deverá calcular o subtotal dos itens e aplicar uma validação: se o subtotal for menor que R$ 30,00, aplica-se uma taxa fixa de entrega de R$ 8,00. Se for maior que R$ 30,00, o frete é grátis. Além disso, pedidos não podem ser finalizados se a lista de itens estiver vazia.

# Meu Projeto Java

---O FoodExpress é um sistema de gerenciamento de delivery desenvolvido em Java, projetado para organizar e automatizar a rotina de um estabelecimento de entrega de alimentos. O sistema atua como um intermediário eficiente entre o restaurante, o cliente e a logística de entrega.
 

## Como executar

```bash
javac src/*.java
java -cp src Main
```

## Estrutura do Projeto

```
seu-projeto/
├── README.md
├── src/
│   ├── Cliente.java
│   ├── Entregador.java
│   ├── ItemPedido.java
│   ├── Main.java
│   ├── Pedido.java
│   └── Produto.java
└── .gitignore
```