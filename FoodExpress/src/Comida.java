public class Comida extends Produto {
    private double pesoGramas;

    public Comida(int codigo, String nome, double preco, double pesoGramas) {
        super(codigo, nome, preco); // Chama o construtor da superclasse
        this.pesoGramas = pesoGramas;
    }

    public double getPesoGramas() { return pesoGramas; }
    public void setPesoGramas(double pesoGramas) { this.pesoGramas = pesoGramas; }

    @Override
    public String obterDetalhes() {
        return "Prato Quente/Lanche";
    }

    @Override
    public String toString() {
        // Usa o super para aproveitar o texto da classe base e adiciona o específico
        return "[Comida] " + super.toString() + " | Peso: " + pesoGramas + "g";
    }
}