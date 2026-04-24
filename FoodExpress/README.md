# Sistema de Gerenciamento de Delivery (FoodExpress)

**Tema Escolhido:** Delivery  
**Integrantes do Grupo:** - Apollo Moura de Sousa - RGM: 46162232
- Cristiano Souza Santos - RGM: 45689385
- Felipe da Fonseca Silva - RGM: 44982887
- Jonathan de Souza - RGM: 45268908
- Igor Alves Ferreira - RGM: 44779542
- Lucas Costa de Lima - RGM: 45850259

## Objetivo do Sistema
O sistema FoodExpress tem como objetivo principal gerenciar as operações diárias de um aplicativo de delivery de alimentos. Ele visa facilitar a interação entre clientes, restaurantes parceiros, produtos disponíveis e a logística de entrega. 

Através do sistema, é possível manter um controle rigoroso (CRUD) sobre o catálogo de itens, registrar clientes, acompanhar a frota de entregadores e gerenciar todo o ciclo de vida de um pedido (desde o carrinho de compras até a entrega final). Isso resolve o problema de desorganização no recebimento de pedidos manuais e centraliza a gestão.

## Funcionalidades Principais
O sistema conta com operações completas de CRUD (Create, Read, Update, Delete) para as seguintes áreas:
1. **Gerenciamento de Restaurantes:** Cadastro, listagem, edição e exclusão de restaurantes.
2. **Gerenciamento de Produtos:** Controle de catálogo de vendas e preços.
3. **Gerenciamento de Clientes:** Controle de usuários cadastrados para realizar pedidos.
4. **Gerenciamento de Entregadores:** Controle de frota, placas e status de entregadores.
5. **Gestão de Pedidos:** Criação de pedidos com adição interativa de múltiplos itens ao carrinho, atribuição de entregadores e atualização de status (Ex: Em preparo, Entregue).
6. **Relatórios:** Visão geral automatizada das entidades do sistema.

## Estrutura de Classes (6 Classes implementadas)
## Estrutura de Classes (8 Classes implementadas)
1. `Restaurante`: Representa os estabelecimentos parceiros.
2. `Cliente`: Representa o usuário que faz o pedido.
3. `Produto`: Superclasse base para itens do cardápio.
   - `Comida`: Subclasse de Produto (com atributo peso em gramas).
   - `Bebida`: Subclasse de Produto (com atributo volume em ml).
4. `Pedido`: Classe central que vincula Cliente, Entregador e a lista de Produtos.
5. `ItemPedido`: Classe associativa que gerencia a quantidade de um Produto específico em um Pedido.
6. `Entregador`: Representa o profissional e seu veículo para a logística.

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

FoodExpress/
├── README.md
├── src/
│   ├── Bebida.java
│   ├── Cliente.java
│   ├── Comida.java
│   ├── Entregador.java
│   ├── ItemPedido.java
│   ├── Main.java
│   ├── Pedido.java
│   ├── Produto.java
│   └── Restaurante.java
└── .gitignore