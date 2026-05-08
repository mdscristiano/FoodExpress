import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Importar as classes do pacote model
import model.Cliente;
import model.Entregador;
import model.ItemPedido;
import model.Pedido;
import model.Produto;
import model.Restaurante;
import model.Comida;
import model.Bebida;

// Importar os DAOs
import dao.ClienteDAO;
import dao.RestauranteDAO;

public class Main {
    // Usando Listas para Produtos, Entregadores e Pedidos (em memória por enquanto)
    static List<Produto> listaProdutos = new ArrayList<>();
    static List<Entregador> listaEntregadores = new ArrayList<>();
    static List<Pedido> listaPedidos = new ArrayList<>();
    
    // As listas de Clientes e Restaurantes foram removidas daqui porque agora usamos o Banco de Dados!
    
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== SISTEMA DELIVERY (FoodExpress) =====");
            System.out.println("1. Gerenciar Restaurantes (Banco de Dados)");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("3. Gerenciar Clientes (Banco de Dados)");
            System.out.println("4. Gerenciar Entregadores");
            System.out.println("5. Gerenciar Pedidos");
            System.out.println("6. Relatórios");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer
            } else {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.next(); 
                continue;
            }

            switch (opcao) {
                case 1:
                    menuRestaurantes();
                    break;
                case 2:
                    menuProdutos();
                    break;
                case 3:
                    menuClientes();
                    break;
                case 4:
                    menuEntregadores();
                    break;
                case 5:
                    menuPedidos();
                    break;
                case 6:
                    gerarRelatorios();
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

    // ==========================================
    // 1. GERENCIAR RESTAURANTES (Integrado com Banco de Dados via DAO)
    // ==========================================
    private static void menuRestaurantes() {
        System.out.println("\n--- GERENCIAR RESTAURANTES (DB) ---");
        System.out.println("1. Cadastrar Restaurante");
        System.out.println("2. Listar Restaurantes");
        System.out.println("3. Editar Restaurante");
        System.out.println("4. Excluir Restaurante");
        System.out.print("Escolha: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        // Instancia o DAO
        RestauranteDAO restauranteDAO = new RestauranteDAO();

        if (op == 1) { // CREATE
            System.out.print("Digite o código do restaurante: ");
            int cod = scanner.nextInt(); scanner.nextLine();
            System.out.print("Nome do restaurante: ");
            String nome = scanner.nextLine();
            System.out.print("Especialidade (ex: Japonesa, Pizzaria): ");
            String esp = scanner.nextLine();
            
            Restaurante novoRestaurante = new Restaurante(cod, nome, esp);
            restauranteDAO.inserir(novoRestaurante); // Grava no banco
        } 
        else if (op == 2) { // READ
            System.out.println("\n--- LISTA DE RESTAURANTES ---");
            List<Restaurante> restaurantesBanco = restauranteDAO.listarTodos(); // Busca do banco
            
            if (restaurantesBanco.isEmpty()) {
                System.out.println("Nenhum restaurante cadastrado no banco.");
            } else {
                for (Restaurante r : restaurantesBanco) {
                    System.out.println(r);
                }
            }
        } 
        else if (op == 3) { // UPDATE
            System.out.print("Código do restaurante a editar: ");
            int cod = scanner.nextInt(); scanner.nextLine();
            System.out.print("Novo nome: "); 
            String nome = scanner.nextLine();
            System.out.print("Nova especialidade: "); 
            String esp = scanner.nextLine();
            
            Restaurante restauranteAtualizado = new Restaurante(cod, nome, esp);
            restauranteDAO.atualizar(restauranteAtualizado); // Atualiza no banco
        } 
        else if (op == 4) { // DELETE
            System.out.print("Código do restaurante a excluir: ");
            int cod = scanner.nextInt(); scanner.nextLine();
            restauranteDAO.excluir(cod); // Apaga do banco
        }
    }

    // ==========================================
    // 2. GERENCIAR PRODUTOS (Classe Abstrata)
    // ==========================================
    private static void menuProdutos() {
        System.out.println("\n--- GERENCIAR PRODUTOS ---");
        System.out.println("1. Cadastrar Produto");
        System.out.println("2. Listar Produtos");
        System.out.println("3. Editar Produto");
        System.out.println("4. Excluir Produto");
        System.out.print("Escolha: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 1) {
            System.out.print("Digite o código do produto: ");
            int cod = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Digite o nome do produto: ");
            String nome = scanner.nextLine();
            System.out.print("Digite o preço do produto: ");
            double preco = scanner.nextDouble();
            scanner.nextLine();
            
            // Decidir se é Comida ou Bebida (pois Produto é abstrato)
            System.out.println("Tipo de Produto: [1] Comida  [2] Bebida");
            int tipo = scanner.nextInt();
            scanner.nextLine();
            
            if (tipo == 1) {
                System.out.print("Digite o peso em gramas: ");
                double peso = scanner.nextDouble(); scanner.nextLine();
                listaProdutos.add(new Comida(cod, nome, preco, peso));
                System.out.println("✅ Comida cadastrada com sucesso!");
            } else if (tipo == 2) {
                System.out.print("Digite o volume em ml: ");
                int volume = scanner.nextInt(); scanner.nextLine();
                listaProdutos.add(new Bebida(cod, nome, preco, volume));
                System.out.println("✅ Bebida cadastrada com sucesso!");
            } else {
                System.out.println("❌ Tipo inválido. Registo cancelado.");
            }
            
        } else if (op == 2) {
            System.out.println("\n--- LISTA DE PRODUTOS ---");
            if (listaProdutos.isEmpty()) System.out.println("Nenhum produto cadastrado.");
            for (Produto p : listaProdutos) {
                System.out.println(p.toString() + " | Detalhe: " + p.obterDetalhes());
            }
        } else if (op == 3) {
            System.out.print("Código do produto a editar: ");
            int cod = scanner.nextInt(); scanner.nextLine();
            for (Produto p : listaProdutos) {
                if (p.getCodigo() == cod) {
                    System.out.print("Novo nome: "); p.setNome(scanner.nextLine());
                    System.out.print("Novo preço: "); p.setPreco(scanner.nextDouble());
                    System.out.println("✅ Produto atualizado!"); return;
                }
            }
            System.out.println("❌ Produto não encontrado.");
        } else if (op == 4) {
            System.out.print("Código do produto a excluir: ");
            int cod = scanner.nextInt(); scanner.nextLine();
            if (listaProdutos.removeIf(p -> p.getCodigo() == cod)) System.out.println("✅ Produto excluído!");
            else System.out.println("❌ Produto não encontrado.");
        }
    }

    // ==========================================
    // 3. GERENCIAR CLIENTES (Integrado com Banco de Dados via DAO)
    // ==========================================
    private static void menuClientes() {
        System.out.println("\n--- GERENCIAR CLIENTES (DB) ---");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Listar Clientes");
        System.out.println("3. Editar Cliente");
        System.out.println("4. Excluir Cliente");
        System.out.print("Escolha: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        // Instancia o DAO
        ClienteDAO clienteDAO = new ClienteDAO();

        if (op == 1) { // CREATE
            System.out.print("Digite o CPF (11 números): ");
            String cpf = scanner.nextLine();
            System.out.print("Digite o nome do cliente: ");
            String nome = scanner.nextLine();
            System.out.print("Digite o endereço completo: ");
            String end = scanner.nextLine();
            
            Cliente novoCliente = new Cliente(cpf, nome, end);
            clienteDAO.inserir(novoCliente); // Chama o banco

        } else if (op == 2) { // READ
            System.out.println("\n--- LISTA DE CLIENTES ---");
            List<Cliente> clientesDoBanco = clienteDAO.listarTodos(); // Busca do banco
            
            if (clientesDoBanco.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado no banco.");
            } else {
                for (Cliente c : clientesDoBanco) {
                    System.out.println(c);
                }
            }
        } else if (op == 3) { // UPDATE
            System.out.print("CPF do cliente a editar: ");
            String cpf = scanner.nextLine();
            System.out.print("Novo nome: "); 
            String nome = scanner.nextLine();
            System.out.print("Novo endereço: "); 
            String end = scanner.nextLine();
            
            Cliente clienteAtualizado = new Cliente(cpf, nome, end);
            clienteDAO.atualizar(clienteAtualizado); // Atualiza no banco

        } else if (op == 4) { // DELETE
            System.out.print("CPF do cliente a excluir: ");
            String cpf = scanner.nextLine();
            clienteDAO.excluir(cpf); // Exclui do banco
        }
    }

    // ==========================================
    // 4. GERENCIAR ENTREGADORES
    // ==========================================
    private static void menuEntregadores() {
        System.out.println("\n--- GERENCIAR ENTREGADORES ---");
        System.out.println("1. Cadastrar Entregador");
        System.out.println("2. Listar Entregadores");
        System.out.println("3. Editar Entregador");
        System.out.println("4. Excluir Entregador");
        System.out.print("Escolha: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        if (op == 1) {
            System.out.print("Digite o nome do entregador: ");
            String nome = scanner.nextLine();
            System.out.print("Digite a placa do veículo: ");
            String placa = scanner.nextLine();
            
            listaEntregadores.add(new Entregador(nome, placa));
            System.out.println("✅ Entregador cadastrado com sucesso!");
        } else if (op == 2) {
            System.out.println("\n--- LISTA DE ENTREGADORES ---");
            if (listaEntregadores.isEmpty()) System.out.println("Nenhum entregador cadastrado.");
            for (Entregador e : listaEntregadores) System.out.println(e);
        } else if (op == 3) {
            System.out.print("Placa do entregador a editar: ");
            String placa = scanner.nextLine();
            for (Entregador e : listaEntregadores) {
                if (e.getPlacaVeiculo().equalsIgnoreCase(placa)) {
                    System.out.print("Novo nome: "); e.setNome(scanner.nextLine());
                    System.out.println("✅ Entregador atualizado!"); return;
                }
            }
            System.out.println("❌ Entregador não encontrado.");
        } else if (op == 4) {
            System.out.print("Placa do entregador a excluir: ");
            String placa = scanner.nextLine();
            if (listaEntregadores.removeIf(e -> e.getPlacaVeiculo().equalsIgnoreCase(placa))) System.out.println("✅ Entregador excluído!");
            else System.out.println("❌ Entregador não encontrado.");
        }
    }

    // ==========================================
    // 5. GERENCIAR PEDIDOS
    // ==========================================
    private static void menuPedidos() {
        System.out.println("\n--- GERENCIAR PEDIDOS ---");
        System.out.println("1. Criar Novo Pedido");
        System.out.println("2. Listar Pedidos");
        System.out.println("3. Atualizar Status do Pedido");
        System.out.println("4. Excluir Pedido");
        System.out.print("Escolha: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        ClienteDAO clienteDAO = new ClienteDAO();

        if (op == 1) {
            List<Cliente> clientesDoBanco = clienteDAO.listarTodos();
            
            if (clientesDoBanco.isEmpty() || listaProdutos.isEmpty()) {
                System.out.println("❌ Erro: Cadastre pelo menos 1 cliente no banco e 1 produto antes de criar um pedido.");
                return;
            }

            // Exibir clientes para escolha
            System.out.println("\n--- Escolha um Cliente ---");
            for (Cliente c : clientesDoBanco) System.out.println("CPF: " + c.getCpf() + " | Nome: " + c.getNome());
            System.out.print("Digite o CPF do cliente escolhido: ");
            String cpfBusca = scanner.nextLine();
            
            Cliente clienteSelecionado = null;
            for(Cliente c : clientesDoBanco) {
                if(c.getCpf().equals(cpfBusca)) clienteSelecionado = c;
            }
            if(clienteSelecionado == null) {
                System.out.println("❌ Cliente não encontrado!"); return;
            }

            int numPedido = listaPedidos.size() + 1001;
            Pedido novoPedido = new Pedido(numPedido, clienteSelecionado);
            
            // Exibir produtos para escolha (adicionar vários itens)
            boolean adicionando = true;
            while(adicionando) {
                System.out.println("\n--- Escolha um Produto ---");
                for (Produto p : listaProdutos) System.out.println("Código: " + p.getCodigo() + " | Nome: " + p.getNome() + " | Preço: R$" + p.getPreco());
                System.out.print("Digite o CÓDIGO do produto (ou 0 para finalizar os itens): ");
                int codBusca = scanner.nextInt(); scanner.nextLine();
                
                if (codBusca == 0) {
                    break;
                }

                Produto produtoSelecionado = null;
                for(Produto p : listaProdutos) {
                    if(p.getCodigo() == codBusca) produtoSelecionado = p;
                }

                if(produtoSelecionado != null) {
                    System.out.print("Quantidade de '" + produtoSelecionado.getNome() + "': ");
                    int qtd = scanner.nextInt(); scanner.nextLine();
                    novoPedido.adicionarItem(new ItemPedido(produtoSelecionado, qtd));
                    System.out.println("✅ Item adicionado ao carrinho!");
                } else {
                    System.out.println("❌ Produto não encontrado.");
                }
            }
            
            // Se tiver entregador cadastrado, vincula o primeiro
            if (!listaEntregadores.isEmpty()) {
                novoPedido.setEntregador(listaEntregadores.get(0));
                novoPedido.setStatus("Saiu para Entrega");
            }

            listaPedidos.add(novoPedido);
            System.out.println("\n✅ Pedido finalizado com sucesso!");
            System.out.println(novoPedido.toString());

        } else if (op == 2) {
            System.out.println("\n--- LISTA DE PEDIDOS ---");
            if (listaPedidos.isEmpty()) System.out.println("Nenhum pedido registrado.");
            for (Pedido p : listaPedidos) {
                System.out.println("Pedido #" + p.getNumeroPedido() + " | Cliente: " + p.getCliente().getNome() + " | Status: " + p.getStatus() + " | Total: R$" + String.format("%.2f", p.calcularTotal()));
            }
        } else if (op == 3) {
            System.out.print("Número do pedido a atualizar status: ");
            int num = scanner.nextInt(); scanner.nextLine();
            for(Pedido p : listaPedidos) {
                if(p.getNumeroPedido() == num) {
                    System.out.print("Novo Status (Ex: Em preparo, Entregue): ");
                    p.setStatus(scanner.nextLine());
                    System.out.println("✅ Status atualizado!"); return;
                }
            }
            System.out.println("❌ Pedido não encontrado.");
        } else if (op == 4) {
            System.out.print("Número do pedido a excluir: ");
            int num = scanner.nextInt(); scanner.nextLine();
            if(listaPedidos.removeIf(p -> p.getNumeroPedido() == num)) System.out.println("✅ Pedido excluído!");
            else System.out.println("❌ Pedido não encontrado.");
        }
    }

    // ==========================================
    // 6. RELATÓRIOS
    // ==========================================
    private static void gerarRelatorios() {
        System.out.println("\n===== RELATÓRIO GERAL =====");

        ClienteDAO cliDao = new ClienteDAO();
        List<Cliente> clientesNoBanco = cliDao.listarTodos();

        RestauranteDAO restDao = new RestauranteDAO();
        List<Restaurante> restaurantesNoBanco = restDao.listarTodos();

        System.out.println("Total de Restaurantes (no BD): " + restaurantesNoBanco.size());
        System.out.println("Total de Clientes (no BD): " + clientesNoBanco.size());
        System.out.println("Total de Produtos: " + listaProdutos.size());
        System.out.println("Total de Entregadores: " + listaEntregadores.size());
        System.out.println("Total de Pedidos Realizados: " + listaPedidos.size());
    }
}