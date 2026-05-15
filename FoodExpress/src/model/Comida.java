package model;

public class Comida extends Produto {
    private double pesoGramas;

    public Comida(int codigo, String nome, double preco, Restaurante restaurante, double pesoGramas) {
        super(codigo, nome, preco, restaurante); // Atualizado
        this.pesoGramas = pesoGramas;
    }

    public double getPesoGramas() { return pesoGramas; }
    public void setPesoGramas(double pesoGramas) { this.pesoGramas = pesoGramas; }

    @Override
    public String obterDetalhes() { return "Prato Quente/Lanche"; }

    @Override
    public String toString() {
        return "[Comida] " + super.toString() + " | Peso: " + pesoGramas + "g";
    }
}