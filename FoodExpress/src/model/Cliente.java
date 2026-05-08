package model;

public class Cliente {
    private String cpf;
    private String nome;
    private String endereco;

    public Cliente(String cpf, String nome, String endereco) {
        setCpf(cpf);
        setNome(nome);
        setEndereco(endereco);
    }

    public String getCpf() { return cpf; }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            System.out.println("Erro: CPF inválido (deve conter 11 dígitos).");
        } else {
            this.cpf = cpf;
        }
    }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Erro: Nome do cliente não pode ser vazio.");
        } else {
            this.nome = nome;
        }
    }

    public String getEndereco() { return endereco; }

    public void setEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            System.out.println("Erro: Endereço não pode ser vazio.");
        } else {
            this.endereco = endereco;
        }
    }

    @Override
    public String toString() {
        return "Cliente [CPF: " + cpf + " | Nome: " + nome + " | Endereço: " + endereco + "]";
    }
}