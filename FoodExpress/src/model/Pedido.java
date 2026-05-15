package model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import util.Auditavel;
import util.Calculavel;

public class Pedido implements Auditavel, Calculavel {
    private int numeroPedido;
    private Cliente cliente;
    private Restaurante restaurante;
    private Entregador entregador;
    private List<ItemPedido> itens;
    private String status;
    private List<String> historico; // Para a interface Auditavel

    public Pedido(int numeroPedido, Cliente cliente, Restaurante restaurante) {
        setNumeroPedido(numeroPedido);
        setCliente(cliente);
        setRestaurante(restaurante);
        this.itens = new ArrayList<>();
        this.historico = new ArrayList<>();
        this.status = "Aguardando Pagamento";
        registrarLog("Pedido criado no sistema.");
    }

    // --- Implementação da Interface Auditavel ---
    @Override
    public void registrarLog(String acao) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.historico.add(dtf.format(LocalDateTime.now()) + " - " + acao);
    }

    @Override
    public String obterHistorico() {
        return String.join("\n", historico);
    }

    // --- Implementação da Interface Calculavel ---
    @Override
    public double calcularDesconto() {
        double subtotal = calcularSubtotal();
        if (subtotal > 300.00) return subtotal * 0.15;
        if (subtotal > 200.00) return subtotal * 0.10;
        if (subtotal > 100.00) return subtotal * 0.05;
        return 0.0;
    }

    @Override
    public double calcularTotal() {
        double subtotal = calcularSubtotal();
        double desconto = calcularDesconto();
        double taxaEntrega = 8.00;
        if (subtotal == 0) return 0.0;
        return (subtotal - desconto) + taxaEntrega;
    }

    // --- Getters e Setters com validações ---
    public int getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(int numeroPedido) {
        if (numeroPedido <= 0) System.out.println("Erro: Número do pedido inválido.");
        else this.numeroPedido = numeroPedido;
    }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) {
        if (cliente == null) System.out.println("Erro: Cliente inválido.");
        else this.cliente = cliente;
    }

    public Restaurante getRestaurante() { return restaurante; }
    public void setRestaurante(Restaurante restaurante) {
        if (restaurante == null) System.out.println("Erro: Restaurante inválido.");
        else this.restaurante = restaurante;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            System.out.println("Erro: Status não pode ser vazio.");
        } else {
            this.status = status;
            registrarLog("Status alterado para: " + status);
        }
    }

    public Entregador getEntregador() { return entregador; }
    public void setEntregador(Entregador entregador) {
        this.entregador = entregador;
        if (entregador != null) registrarLog("Entregador atribuído: " + entregador.getNome());
    }

    // --- Métodos de itens (Sobrecarga / Overload) ---
    public void adicionarItem(ItemPedido item) {
        if (item != null) {
            itens.add(item);
            registrarLog("Item adicionado: " + item.getProduto().getNome());
        }
    }

    public void adicionarItem(Produto produto, int quantidade) {
        if (produto != null && quantidade > 0) {
            adicionarItem(new ItemPedido(produto, quantidade));
        }
    }

    public double calcularSubtotal() {
        double subtotal = 0.0;
        for (ItemPedido item : itens) subtotal += item.calcularSubtotal();
        return subtotal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- PEDIDO #").append(numeroPedido).append(" ---\n");
        sb.append("Cliente: ").append(cliente != null ? cliente.getNome() : "Não informado").append("\n");
        sb.append("Restaurante: ").append(restaurante != null ? restaurante.getNome() : "Não informado").append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Entregador: ").append(entregador != null ? entregador.getNome() : "Aguardando").append("\n");
        sb.append("Itens:\n");
        
        for (ItemPedido item : itens) sb.append("  - ").append(item.toString()).append("\n");
        
        if (!itens.isEmpty()) {
            sb.append("\n=== RESUMO FINANCEIRO ===\n");
            sb.append(String.format("Subtotal: R$ %.2f\n", calcularSubtotal()));
            sb.append(String.format("Desconto: - R$ %.2f\n", calcularDesconto()));
            sb.append("Taxa de Entrega: R$ 8,00\n");
            sb.append("------------------------\n");
            sb.append(String.format("VALOR FINAL: R$ %.2f\n", calcularTotal()));
        }
        return sb.toString();
    }

   
   public java.util.List<ItemPedido> getItens() {
    return this.itens;

 }
}