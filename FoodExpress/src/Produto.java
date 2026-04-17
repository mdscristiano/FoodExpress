public class Produto {
    // 1. Atributos private (Encapsulamento)
    private int codigo;
    private String nome;
    private double preco;

    // 2. Construtor adequado
    public Produto(int codigo, String nome, double preco) {
        setCodigo(codigo);
        setNome(nome);
        setPreco(preco);
    }

    // 3. Getters e Setters com Validações
    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) {
        if (codigo <= 0) {
            System.out.println("Erro: O código do produto deve ser maior que zero.");
        } else {
            this.codigo = codigo;
        }
    }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Erro: O nome do produto não pode ser vazio.");
        } else {
            this.nome = nome;
        }
    }

    public double getPreco() { return preco; }

    public void setPreco(double preco) {
        if (preco <= 0.0) {
            System.out.println("Erro: O preço deve ser maior que zero.");
        } else {
            this.preco = preco;
        }
    }

    // 4. Método toString()
    @Override
    public String toString() {
        return "Produto [Código: " + codigo + " | Nome: " + nome + " | Preço: R$" + String.format("%.2f", preco) + "]";
    }
}