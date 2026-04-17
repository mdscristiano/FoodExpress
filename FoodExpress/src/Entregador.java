public class Entregador {
    private String nome;
    private String placaVeiculo;
    private boolean disponivel; // true = livre para entregas, false = em rota

    public Entregador(String nome, String placaVeiculo) {
        setNome(nome);
        setPlacaVeiculo(placaVeiculo);
        this.disponivel = true; // Por padrão, começa disponível
    }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Erro: O nome do entregador não pode ser vazio.");
        } else {
            this.nome = nome;
        }
    }

    public String getPlacaVeiculo() { return placaVeiculo; }

    public void setPlacaVeiculo(String placaVeiculo) {
        if (placaVeiculo == null || placaVeiculo.trim().isEmpty()) {
            System.out.println("Erro: A placa do veículo não pode ser vazia.");
        } else {
            this.placaVeiculo = placaVeiculo;
        }
    }

    public boolean isDisponivel() { return disponivel; }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        String status = disponivel ? "Disponível" : "Em Rota";
        return "Entregador [Nome: " + nome + " | Veículo: " + placaVeiculo + " | Status: " + status + "]";
    }
}