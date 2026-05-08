package model;

// 1. Adicionada a palavra "abstract" na declaração da classe
public abstract class Produto {
    // 1. Mudança de private para PROTECTED (Exigência do CP2 para Herança)
    protected int codigo;
    protected String nome;
    protected double preco;

    // 2. Construtor continua igual
    public Produto(int codigo, String nome, double preco) {
        setCodigo(codigo);
        setNome(nome);
        setPreco(preco);
    }

    // 3. Getters e Setters com Validações continuam iguais
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

    // 4. Método alterado para abstrato (sem corpo, termina com ponto e vírgula)
    public abstract String obterDetalhes();

    // 5. Método toString() modificado levemente para não ficar repetindo a palavra "Produto"
    @Override
    public String toString() {
        return "Código: " + codigo + " | Nome: " + nome + " | Preço: R$" + String.format("%.2f", preco);
    }
}