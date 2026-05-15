import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Importar as classes do pacote model
import model.Cliente;
import model.Entregador;

import model.Pedido;
import model.Produto;
import model.Restaurante;
import model.Comida;
import model.Bebida;

// Importar os DAOs
import dao.ClienteDAO;
import dao.RestauranteDAO;
import dao.ProdutoDAO;
import dao.EntregadorDAO; 
import dao.PedidoDAO;

public class Main {
    static List<Pedido> listaPedidos = new ArrayList<>();
    
    static Scanner scanner = new Scanner(System.in);
    static ProdutoDAO produtoDAO = new ProdutoDAO();
    static EntregadorDAO entregadorDAO = new EntregadorDAO(); 
    static PedidoDAO pedidoDAO = new PedidoDAO(); 

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== SISTEMA DELIVERY (FoodExpress) =====");

            System.out.println("1. Gerenciar Restaurantes (Banco de Dados)");
            System.out.println("2. Gerenciar Produtos (Banco de Dados)");
            System.out.println("3. Gerenciar Clientes (Banco de Dados)");
            System.out.println("4. Gerenciar Entregadores (Banco de Dados)"); 
            System.out.println("5. Gerenciar Pedidos (Banco de Dados)");
            System.out.println("6. Relatório Financeiro (Vendas por Restaurante)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.next(); 
                continue;
            }

            switch (opcao) {
                case 1: menuRestaurantes(); break;
                case 2: menuProdutos(); break;
                case 3: menuClientes(); break;
                case 4: menuEntregadores(); break;
                case 5: menuPedidos(); break;
                case 6: gerarRelatorios(); break;
                case 0: System.out.println("Encerrando o sistema... Até logo!"); break;
                default: System.out.println("Opção inválida! Tente novamente.");
            }
        }
        scanner.close();
    }

    // ==========================================
    // 1. GERENCIAR RESTAURANTES (Persistência DB)
    // ==========================================
    private static void menuRestaurantes() {
        System.out.println("\n--- GERENCIAR RESTAURANTES (DB) ---");
        System.out.println("1. Cadastrar | 2. Listar | 3. Atualizar | 4. Excluir");
        System.out.print("Escolha: ");
        int op = scanner.nextInt(); scanner.nextLine();

        RestauranteDAO restauranteDAO = new RestauranteDAO();

        if (op == 1) { 
            System.out.print("Código: "); int cod = scanner.nextInt(); scanner.nextLine();
            System.out.print("Nome: "); String nome = scanner.nextLine();
            System.out.print("Especialidade: "); String esp = scanner.nextLine();
            restauranteDAO.inserir(new Restaurante(cod, nome, esp)); 
        } else if (op == 2) { 
            List<Restaurante> lista = restauranteDAO.listarTodos(); 
            if (lista.isEmpty()) System.out.println("Vazio.");
            else lista.forEach(System.out::println);
        } else if (op == 3) {
            System.out.print("Código do restaurante a atualizar: "); int cod = scanner.nextInt(); scanner.nextLine();
            System.out.print("Novo Nome: "); String nome = scanner.nextLine();
            System.out.print("Nova Especialidade: "); String esp = scanner.nextLine();
            restauranteDAO.atualizar(new Restaurante(cod, nome, esp));
        } else if (op == 4) {
            System.out.print("Código para excluir: ");
            restauranteDAO.excluir(scanner.nextInt());
        } else {
            System.out.println("Opção inválida.");
        }
    }

    // ==========================================
    // 2. GERENCIAR PRODUTOS (Persistência DB)
    // ==========================================
    private static void menuProdutos() {
        System.out.println("\n--- GERENCIAR PRODUTOS (DB) ---");
        System.out.println("1. Cadastrar | 2. Listar | 3. Atualizar | 4. Excluir");
        System.out.print("Escolha: ");
        int op = scanner.nextInt(); scanner.nextLine();

        if (op == 1) {
            List<Restaurante> rests = new RestauranteDAO().listarTodos();
            if (rests.isEmpty()) { System.out.println("Cadastre um restaurante primeiro."); return; }
            
            rests.forEach(r -> System.out.println(r.getCodigo() + " - " + r.getNome()));
            System.out.print("Código do restaurante: ");
            int idRest = scanner.nextInt(); scanner.nextLine();
            Restaurante res = rests.stream().filter(r -> r.getCodigo() == idRest).findFirst().orElse(null);

            if(res != null) {
                System.out.print("Código Produto: "); int cod = scanner.nextInt(); scanner.nextLine();
                System.out.print("Nome: "); String nome = scanner.nextLine();
                System.out.print("Preço: "); double preco = scanner.nextDouble();
                System.out.print("Tipo [1] Comida [2] Bebida: "); int tipo = scanner.nextInt();
                
                if (tipo == 1) {
                    System.out.print("Peso (g): ");
                    produtoDAO.inserir(new Comida(cod, nome, preco, res, scanner.nextDouble()));
                } else {
                    System.out.print("Volume (ml): ");
                    produtoDAO.inserir(new Bebida(cod, nome, preco, res, scanner.nextInt()));
                }
            } else {
                System.out.println("Restaurante não encontrado.");
            }
        } else if (op == 2) {
            produtoDAO.listarTodos().forEach(p -> System.out.println(p + " | " + p.obterDetalhes()));
        } else if (op == 3) {
            System.out.print("Código do produto a atualizar: "); int cod = scanner.nextInt(); scanner.nextLine();
            System.out.print("Novo Nome: "); String nome = scanner.nextLine();
            System.out.print("Novo Preço: "); double preco = scanner.nextDouble();
            produtoDAO.atualizar(cod, nome, preco);
        } else if (op == 4) {
            System.out.print("Código para excluir: ");
            produtoDAO.excluir(scanner.nextInt());
        } else {
            System.out.println("Opção inválida.");
        }
    }

    // ==========================================
    // 3. GERENCIAR CLIENTES (Persistência DB)
    // ==========================================
    private static void menuClientes() {
        System.out.println("\n--- GERENCIAR CLIENTES (DB) ---");
        ClienteDAO clienteDAO = new ClienteDAO();
        System.out.println("1. Cadastrar | 2. Listar | 3. Atualizar | 4. Excluir");
        System.out.print("Escolha: ");
        int op = scanner.nextInt(); scanner.nextLine();

        if (op == 1) {
            System.out.print("CPF: "); String cpf = scanner.nextLine();
            System.out.print("Nome: "); String nome = scanner.nextLine();
            System.out.print("Endereço: "); String end = scanner.nextLine();
            clienteDAO.inserir(new Cliente(cpf, nome, end));
        } else if (op == 2) {
            clienteDAO.listarTodos().forEach(System.out::println);
        } else if (op == 3) {
            System.out.print("CPF do cliente a atualizar: "); String cpf = scanner.nextLine();
            System.out.print("Novo Nome: "); String nome = scanner.nextLine();
            System.out.print("Novo Endereço: "); String end = scanner.nextLine();
            clienteDAO.atualizar(new Cliente(cpf, nome, end));
        } else if (op == 4) {
            System.out.print("CPF para excluir: "); String cpf = scanner.nextLine();
            clienteDAO.excluir(cpf);
        } else {
            System.out.println("Opção inválida.");
        }
    }

    // ==========================================
    // 4. GERENCIAR ENTREGADORES (Persistência DB)
    // ==========================================
    private static void menuEntregadores() {
        System.out.println("\n--- GERENCIAR ENTREGADORES (DB) ---");
        System.out.println("1. Cadastrar | 2. Listar | 3. Atualizar | 4. Excluir");
        System.out.print("Escolha: ");
        int op = scanner.nextInt(); scanner.nextLine();

        if (op == 1) {
            System.out.print("Nome: "); String nome = scanner.nextLine();
            System.out.print("Placa: "); String placa = scanner.nextLine();
            entregadorDAO.inserir(new Entregador(nome, placa));
        } else if (op == 2) {
            List<Entregador> lista = entregadorDAO.listarTodos();
            if (lista.isEmpty()) System.out.println("Nenhum entregador no banco.");
            else lista.forEach(System.out::println);
        } else if (op == 3) {
            System.out.print("Placa do entregador a atualizar: "); String placa = scanner.nextLine();
            System.out.print("Novo Nome: "); String nome = scanner.nextLine();
            System.out.print("Disponível? (true/false): "); boolean disp = scanner.nextBoolean(); scanner.nextLine();
            Entregador e = new Entregador(nome, placa);
            e.setDisponivel(disp);
            entregadorDAO.atualizar(e);
        } else if (op == 4) {
            System.out.print("Placa para excluir: "); String placa = scanner.nextLine();
            entregadorDAO.excluir(placa);
        } else {
            System.out.println("Opção inválida.");
        }
    }

    // ==========================================
    // 5. GERENCIAR PEDIDOS (Persistência DB)
    // ==========================================
    private static void menuPedidos() {
        System.out.println("\n--- NOVO PEDIDO ---");
        List<Cliente> clientes = new ClienteDAO().listarTodos();
        List<Produto> cardapio = produtoDAO.listarTodos();
        List<Entregador> entregadores = entregadorDAO.listarTodos();

        if (clientes.isEmpty() || cardapio.isEmpty()) {
            System.out.println("❌ Erro: Cadastre clientes e produtos no banco primeiro.");
            return;
        }

        for(Cliente c : clientes) System.out.println(c.getCpf() + " - " + c.getNome());
        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();
        Cliente selecionado = clientes.stream().filter(c -> c.getCpf().equals(cpf)).findFirst().orElse(null);

        if(selecionado != null) {
            for(Produto p : cardapio) System.out.println(p.getCodigo() + " - " + p.getNome());
            System.out.print("Código do produto: ");
            int codP = scanner.nextInt();
            Produto prod = cardapio.stream().filter(p -> p.getCodigo() == codP).findFirst().orElse(null);
            
                   //Menu para selecionar quantidade
            if(prod != null) {
                Pedido p = new Pedido(listaPedidos.size() + 101, selecionado, prod.getRestaurante());
                p.adicionarItem(prod, 3);
                
                // Atribui primeiro entregador disponível do banco
                Entregador e = entregadores.stream().filter(Entregador::isDisponivel).findFirst().orElse(null);
                if(e != null) {
                    p.setEntregador(e);
                    p.setStatus("Saiu para Entrega");
                }
                
                // NOVO: Persiste o pedido no banco de dados usando o DAO
                pedidoDAO.inserir(p);
                
                listaPedidos.add(p);
                System.out.println("✅ Pedido Criado!");
                System.out.println(p); // Calcula total via interface Calculavel automaticamente
            }
        }
    }

    private static void gerarRelatorios() {
        System.out.println("\n===== RELATÓRIO FINAL CP4 =====");
        System.out.println("Pedidos Total: " + listaPedidos.size());
        listaPedidos.forEach(p -> System.out.println("Pedido #" + p.getNumeroPedido() + " | Valor Final: R$ " + String.format("%.2f", p.calcularTotal())));
    }
}