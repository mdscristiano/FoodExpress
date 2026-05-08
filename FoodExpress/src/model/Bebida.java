package model;

public class Bebida extends Produto {
    private int volumeMl;

    public Bebida(int codigo, String nome, double preco, int volumeMl) {
        super(codigo, nome, preco); // Chama o construtor da superclasse
        this.volumeMl = volumeMl;
    }

    public int getVolumeMl() { return volumeMl; }
    public void setVolumeMl(int volumeMl) { this.volumeMl = volumeMl; }

    @Override
    public String obterDetalhes() {
        return "Bebida Gelada";
    }

    @Override
    public String toString() {
        return "[Bebida] " + super.toString() + " | Volume: " + volumeMl + "ml";
    }
}