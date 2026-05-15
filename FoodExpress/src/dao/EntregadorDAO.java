package dao;

import model.Entregador;
import util.ConexaoBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntregadorDAO {
    public void inserir(Entregador entregador) {
        String sql = "INSERT INTO entregadores (placa_veiculo, nome, disponivel) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entregador.getPlacaVeiculo());
            stmt.setString(2, entregador.getNome());
            stmt.setBoolean(3, entregador.isDisponivel());
            stmt.executeUpdate();
            System.out.println("✅ Entregador salvo no banco!");
        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir entregador: " + e.getMessage());
        }
    }

    public List<Entregador> listarTodos() {
        List<Entregador> lista = new ArrayList<>();
        String sql = "SELECT * FROM entregadores";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Entregador e = new Entregador(rs.getString("nome"), rs.getString("placa_veiculo"));
                e.setDisponivel(rs.getBoolean("disponivel"));
                lista.add(e);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    // 3. UPDATE
    public void atualizar(Entregador entregador) {
        String sql = "UPDATE entregadores SET nome = ?, disponivel = ? WHERE placa_veiculo = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, entregador.getNome());
            stmt.setBoolean(2, entregador.isDisponivel());
            stmt.setString(3, entregador.getPlacaVeiculo());
            
            int linhasAfetadas = stmt.executeUpdate();
            if(linhasAfetadas > 0) {
                System.out.println("✅ Entregador atualizado na base de dados!");
            } else {
                System.out.println("❌ Entregador não encontrado na base de dados.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar entregador: " + e.getMessage());
        }
    }

    // 4. DELETE
    public void excluir(String placa) {
        String sql = "DELETE FROM entregadores WHERE placa_veiculo = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, placa);
            int linhasAfetadas = stmt.executeUpdate();
            if(linhasAfetadas > 0) {
                System.out.println("✅ Entregador excluído da base de dados!");
            } else {
                System.out.println("❌ Entregador não encontrado na base de dados.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao excluir entregador: " + e.getMessage());
        }
    }
}