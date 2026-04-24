public class Restaurante {
    private int codigo;
    private String nome;
    private String especialidade;

    public Restaurante(int codigo, String nome, String especialidade) {
        setCodigo(codigo);
        setNome(nome);
        setEspecialidade(especialidade);
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) {
        if (codigo > 0) this.codigo = codigo;
        else System.out.println("Erro: Código deve ser maior que zero.");
    }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome != null && !nome.trim().isEmpty()) this.nome = nome;
        else System.out.println("Erro: Nome inválido.");
    }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return "Restaurante [Código: " + codigo + " | Nome: " + nome + " | Especialidade: " + especialidade + "]";
    }
}