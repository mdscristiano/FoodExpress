import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        // Variáveis temporárias para demonstração
        Produto produtoExemplo = null;
        Cliente clienteExemplo = null;

        System.out.println("=== Bem-vindo ao Sistema FoodExpress Delivery ===");

        while (opcao != 0) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Cadastrar Novo Produto");
            System.out.println("2. Cadastrar Novo Cliente");
            System.out.println("3. Visualizar Dados Cadastrados");
            System.out.println("4. Simular um Pedido Completo");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            // Validação simples de entrada
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer
            } else {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.next(); // Descarta a entrada ruim
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Digite o código do produto: ");
                    int cod = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer
                    
                    System.out.print("Digite o nome do produto: ");
                    String nomeProd = scanner.nextLine();
                    
                    System.out.print("Digite o preço do produto: ");
                    double preco = scanner.nextDouble();
                    
                    produtoExemplo = new Produto(cod, nomeProd, preco);
                    System.out.println("Produto criado com sucesso!");
                    break;
                    
                case 2:
                    System.out.print("Digite o CPF (11 números): ");
                    String cpf = scanner.nextLine();
                    
                    System.out.print("Digite o nome do cliente: ");
                    String nomeCli = scanner.nextLine();
                    
                    System.out.print("Digite o endereço completo: ");
                    String end = scanner.nextLine();
                    
                    clienteExemplo = new Cliente(cpf, nomeCli, end);
                    System.out.println("Cliente criado com sucesso!");
                    break;
                    
                case 3:
                    System.out.println("\n--- DADOS ATUAIS ---");
                    if (produtoExemplo != null) {
                        System.out.println(produtoExemplo.toString());
                    } else {
                        System.out.println("Nenhum produto cadastrado.");
                    }
                    
                    if (clienteExemplo != null) {
                        System.out.println(clienteExemplo.toString());
                    } else {
                        System.out.println("Nenhum cliente cadastrado.");
                    }
                    break;

                case 4:
                    if (clienteExemplo == null || produtoExemplo == null) {
                        System.out.println("Erro: Cadastre pelo menos 1 cliente e 1 produto primeiro (Opções 1 e 2).");
                    } else {
                        System.out.println("\n--- SIMULANDO PEDIDO ---");
                        // 1. Cria o Pedido vinculado ao cliente
                        Pedido novoPedido = new Pedido(1001, clienteExemplo);
                        
                        // 2. Pergunta quantos itens o cliente quer daquele produto
                        System.out.print("Quantos(as) '" + produtoExemplo.getNome() + "' você quer comprar? ");
                        int qtd = scanner.nextInt();
                        scanner.nextLine(); // limpar buffer
                        
                        // 3. Adiciona ao carrinho
                        ItemPedido item = new ItemPedido(produtoExemplo, qtd);
                        novoPedido.adicionarItem(item);
                        
                        // 4. Adiciona um entregador (opcional)
                        Entregador entregador = new Entregador("Carlos", "ABC-1234");
                        novoPedido.setEntregador(entregador);
                        novoPedido.setStatus("Saiu para Entrega");
                        
                        // 5. Imprime o cupom (Aqui a mágica do frete acontece!)
                        System.out.println("\n" + novoPedido.toString());
                    }
                    break;

                    
                case 0:
                    System.out.println("Encerrando o sistema... Até logo!");
                    break;
                    
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
        scanner.close();
    }
}