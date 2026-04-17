import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int numeroPedido;
    private Cliente cliente;
    private Entregador entregador; // Pode ser null no início
    private List<ItemPedido> itens;
    private String status; // Ex: Preparando, Saiu para Entrega, Entregue

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

    // --- Métodos de Negócio ---

    public void adicionarItem(ItemPedido item) {
        if (item != null) {
            itens.add(item);
        } else {
            System.out.println("Erro: Não é possível adicionar um item nulo.");
        }
    }

    // A REGRA DE NEGÓCIO COMPLEXA: Cálculo com frete dinâmico
    public double calcularTotal() {
        double subtotal = 0.0;
        for (ItemPedido item : itens) {
            subtotal += item.calcularSubtotal();
        }

        double taxaEntrega = 0.0;
        // Se o pedido não estiver vazio e o subtotal for menor que 30, cobra 8 reais de frete
        if (subtotal > 0 && subtotal < 30.00) {
            taxaEntrega = 8.00;
        }

        return subtotal + taxaEntrega;
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
        
        double subtotal = calcularTotal();
        // Apenas para exibir se teve frete ou não no texto final
        double taxa = (subtotal > 0 && subtotal - (subtotal % 100) < 30) ? 8.00 : 0.0; // Lógica simplificada para o print
        if(subtotal > 0 && itens.stream().mapToDouble(ItemPedido::calcularSubtotal).sum() < 30.00) {
            sb.append("Taxa de Entrega: R$8,00\n");
        } else if (subtotal > 0) {
            sb.append("Taxa de Entrega: GRÁTIS\n");
        }

        sb.append("TOTAL DO PEDIDO: R$").append(String.format("%.2f", calcularTotal()));
        return sb.toString();
    }
}