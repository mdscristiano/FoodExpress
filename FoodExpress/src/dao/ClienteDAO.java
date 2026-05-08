package dao;

import model.Cliente;
import util.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // 1. CREATE
    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO clientes (cpf, nome, endereco) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEndereco());
            stmt.executeUpdate();
            System.out.println("✅ Cliente inserido na base de dados com sucesso!");
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir cliente: " + e.getMessage());
        }
    }

    // 2. READ
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getString("endereco")
                );
                clientes.add(c);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    // 3. UPDATE
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, endereco = ? WHERE cpf = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getCpf());
            
            int linhasAfetadas = stmt.executeUpdate();
            if(linhasAfetadas > 0) {
                System.out.println("✅ Cliente atualizado na base de dados!");
            } else {
                System.out.println("❌ Cliente não encontrado na base de dados.");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    // 4. DELETE
    public void excluir(String cpf) {
        String sql = "DELETE FROM clientes WHERE cpf = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            int linhasAfetadas = stmt.executeUpdate();
            if(linhasAfetadas > 0) {
                System.out.println("✅ Cliente excluído da base de dados!");
            } else {
                System.out.println("❌ Cliente não encontrado na base de dados.");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao excluir cliente: " + e.getMessage());
        }
    }
}