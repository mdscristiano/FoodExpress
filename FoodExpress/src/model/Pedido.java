package model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int numeroPedido;
    private Cliente cliente;
    private Entregador entregador;
    private List<ItemPedido> itens;
    private String status;

    public Pedido(int numeroPedido, Cliente cliente) {
        setNumeroPedido(numeroPedido);
        setCliente(cliente);
        this.itens = new ArrayList<>();
        this.status = "Aguardando Pagamento";
    }

    // --- Getters e Setters (com validações) ---
    public int getNumeroPedido() { return numeroPedido; }

    public void setNumeroPedido(int numeroPedido) {
        if (numeroPedido <= 0) {
            System.out.println("Erro: Número do pedido inválido.");
        } else {
            this.numeroPedido = numeroPedido;
        }
    }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            System.out.println("Erro: O pedido deve estar vinculado a um cliente válido.");
        } else {
            this.cliente = cliente;
        }
    }

    public Entregador getEntregador() { return entregador; }

    public void setEntregador(Entregador entregador) {
        this.entregador = entregador;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            System.out.println("Erro: Status não pode ser vazio.");
        } else {
            this.status = status;
        }
    }

    // ============================================================
    // REQUISITO CP2: SOBRECARGA DE MÉTODOS (OVERLOAD)
    // ============================================================

    // Método 1: Adiciona recebendo um ItemPedido já montado
    public void adicionarItem(ItemPedido item) {
        if (item != null) {
            itens.add(item);
        } else {
            System.out.println("Erro: Não é possível adicionar um item nulo.");
        }
    }

    // Método 2 (Sobrecarga): Adiciona recebendo Produto e quantidade separados
    public void adicionarItem(Produto produto, int quantidade) {
        if (produto != null && quantidade > 0) {
            ItemPedido novoItem = new ItemPedido(produto, quantidade);
            this.itens.add(novoItem);
        } else {
            System.out.println("Erro: Produto inválido ou quantidade zerada.");
        }
    }

    // Método 3 (Sobrecarga): Adiciona recebendo apenas o Produto (assume quantidade 1)
    public void adicionarItem(Produto produto) {
        adicionarItem(produto, 1);
    }

    // ============================================================
    // REGRA DE NEGÓCIO: DESCONTOS PROGRESSIVOS
    // ============================================================

    // 1. Calcula apenas o subtotal dos produtos
    public double calcularSubtotal() {
        double subtotal = 0.0;
        for (ItemPedido item : itens) {
            subtotal += item.calcularSubtotal();
        }
        return subtotal;
    }

    // 2. Calcula o desconto baseado no subtotal
    public double calcularDesconto() {
        double subtotal = calcularSubtotal();
        
        if (subtotal > 300.00) {
            return subtotal * 0.15; // 15% de desconto
        } else if (subtotal > 200.00) {
            return subtotal * 0.10; // 10% de desconto
        } else if (subtotal > 100.00) {
            return subtotal * 0.05; // 5% de desconto
        }
        
        return 0.0; // Sem desconto se for R$ 100 ou menos
    }

    // 3. Calcula o total final
    public double calcularTotal() {
        double subtotal = calcularSubtotal();
        double desconto = calcularDesconto();
        double taxaEntrega = 8.00; // Taxa fixa

        // Evita calcular taxa de entrega para pedidos vazios
        if (subtotal == 0) {
            return 0.0;
        }

        return (subtotal - desconto) + taxaEntrega;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- PEDIDO #").append(numeroPedido).append(" ---\n");
        sb.append("Cliente: ").append(cliente != null ? cliente.getNome() : "Não informado").append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Entregador: ").append(entregador != null ? entregador.getNome() : "Ainda não atribuído").append("\n");
        sb.append("Itens:\n");
        
        if (itens.isEmpty()) {
            sb.append("  (Carrinho vazio)\n");
        } else {
            for (ItemPedido item : itens) {
                sb.append("  - ").append(item.toString()).append("\n");
            }
        }
        
        // Se houver itens, exibe o Resumo Detalhado
        if (!itens.isEmpty()) {
            double subtotal = calcularSubtotal();
            double desconto = calcularDesconto();
            
            sb.append("\n=== RESUMO DETALHADO ===\n");
            sb.append(String.format("Subtotal: R$ %.2f\n", subtotal));
            
            if (desconto > 0) {
                // Calcula a porcentagem do desconto apenas para exibição
                int percentual = (int) Math.round((desconto / subtotal) * 100);
                sb.append(String.format("Desconto (%d%%): - R$ %.2f\n", percentual, desconto));
            } else {
                sb.append("Desconto: Não aplicável (Abaixo de R$ 100,00)\n");
            }
            
            sb.append("Taxa de Entrega: R$ 8,00\n");
            sb.append("------------------------\n");
            sb.append(String.format("VALOR FINAL: R$ %.2f\n", calcularTotal()));
        }

        return sb.toString();
    }
}