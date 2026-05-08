package model;

public class ItemPedido {
    // 1. Atributos private
    private Produto produto;
    private int quantidade;

    // 2. Construtor
    public ItemPedido(Produto produto, int quantidade) {
        setProduto(produto);
        setQuantidade(quantidade);
    }

    // 3. Getters e Setters com validação
    public Produto getProduto() { return produto; }

    public void setProduto(Produto produto) {
        if (produto == null) {
            System.out.println("Erro: O item deve ter um produto válido.");
        } else {
            this.produto = produto;
        }
    }

    public int getQuantidade() { return quantidade; }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            System.out.println("Erro: A quantidade deve ser de pelo menos 1.");
        } else {
            this.quantidade = quantidade;
        }
    }

    // Método auxiliar para facilitar cálculos no carrinho
    public double calcularSubtotal() {
        if (produto != null) {
            return produto.getPreco() * quantidade;
        }
        return 0.0;
    }

    // 4. toString
    @Override
    public String toString() {
        return quantidade + "x " + (produto != null ? produto.getNome() : "Produto Inválido") + 
               " | Subtotal: R$" + String.format("%.2f", calcularSubtotal());
    }
}