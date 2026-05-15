package model;

import util.Auditavel;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cliente implements Auditavel {
    private String cpf;
    private String nome;
    private String endereco;
    
    // Lista para armazenar o histórico exigido pela interface
    private List<String> historico; 

    public Cliente(String cpf, String nome, String endereco) {
        this.historico = new ArrayList<>(); // Inicializa a lista de histórico
        setCpf(cpf);
        setNome(nome);
        setEndereco(endereco);
        registrarLog("Cliente cadastrado no sistema.");
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
            registrarLog("Nome alterado para: " + nome); // Registra log na alteração
        }
    }

    public String getEndereco() { return endereco; }

    public void setEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            System.out.println("Erro: Endereço não pode ser vazio.");
        } else {
            this.endereco = endereco;
            registrarLog("Endereço alterado para: " + endereco); // Registra log na alteração
        }
    }

    // ==========================================
    // IMPLEMENTAÇÃO DA INTERFACE AUDITAVEL
    // ==========================================
    
    @Override
    public void registrarLog(String acao) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String logFormatado = dtf.format(LocalDateTime.now()) + " - " + acao;
        this.historico.add(logFormatado);
    }

    @Override
    public String obterHistorico() {
        if (historico.isEmpty()) {
            return "Nenhum histórico registrado para este cliente.";
        }
        return String.join("\n", historico);
    }

    @Override
    public String toString() {
        return "Cliente [CPF: " + cpf + " | Nome: " + nome + " | Endereço: " + endereco + "]";
    }
}