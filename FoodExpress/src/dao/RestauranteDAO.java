package dao;

import model.Restaurante;
import util.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestauranteDAO {

    // 1. CREATE
    public void inserir(Restaurante restaurante) {
        String sql = "INSERT INTO restaurantes (codigo, nome, especialidade) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, restaurante.getCodigo());
            stmt.setString(2, restaurante.getNome());
            stmt.setString(3, restaurante.getEspecialidade());
            stmt.executeUpdate();
            System.out.println("✅ Restaurante inserido na base de dados com sucesso!");
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir restaurante: " + e.getMessage());
        }
    }

    // 2. READ
    public List<Restaurante> listarTodos() {
        List<Restaurante> restaurantes = new ArrayList<>();
        String sql = "SELECT * FROM restaurantes";
        
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Restaurante r = new Restaurante(
                    rs.getInt("codigo"),
                    rs.getString("nome"),
                    rs.getString("especialidade")
                );
                restaurantes.add(r);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar restaurantes: " + e.getMessage());
        }
        return restaurantes;
    }

    // 3. UPDATE
    public void atualizar(Restaurante restaurante) {
        String sql = "UPDATE restaurantes SET nome = ?, especialidade = ? WHERE codigo = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, restaurante.getNome());
            stmt.setString(2, restaurante.getEspecialidade());
            stmt.setInt(3, restaurante.getCodigo()); // O código vai no WHERE
            
            int linhasAfetadas = stmt.executeUpdate();
            if(linhasAfetadas > 0) {
                System.out.println("✅ Restaurante atualizado na base de dados!");
            } else {
                System.out.println("❌ Restaurante não encontrado na base de dados.");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar restaurante: " + e.getMessage());
        }
    }

    // 4. DELETE
    public void excluir(int codigo) {
        String sql = "DELETE FROM restaurantes WHERE codigo = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codigo);
            int linhasAfetadas = stmt.executeUpdate();
            if(linhasAfetadas > 0) {
                System.out.println("✅ Restaurante excluído da base de dados!");
            } else {
                System.out.println("❌ Restaurante não encontrado na base de dados.");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao excluir restaurante: " + e.getMessage());
        }
    }
}