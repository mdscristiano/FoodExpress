package dao;

import model.*;
import util.ConexaoBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void inserir(Produto produto) {
        String sqlProduto = "INSERT INTO produtos (codigo, nome, preco, codigo_restaurante) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexaoBD.conectar()) {
            conn.setAutoCommit(false); // Inicia transação para garantir que insira em ambas as tabelas

            try (PreparedStatement stmtProd = conn.prepareStatement(sqlProduto)) {
                stmtProd.setInt(1, produto.getCodigo());
                stmtProd.setString(2, produto.getNome());
                stmtProd.setDouble(3, produto.getPreco());
                stmtProd.setInt(4, produto.getRestaurante().getCodigo());
                stmtProd.executeUpdate();

                // Insere na tabela específica baseada no tipo (Herança)
                if (produto instanceof Comida) {
                    String sqlComida = "INSERT INTO comidas (codigo_produto, peso_gramas) VALUES (?, ?)";
                    try (PreparedStatement stmtCom = conn.prepareStatement(sqlComida)) {
                        stmtCom.setInt(1, produto.getCodigo());
                        stmtCom.setDouble(2, ((Comida) produto).getPesoGramas());
                        stmtCom.executeUpdate();
                    }
                } else if (produto instanceof Bebida) {
                    String sqlBebida = "INSERT INTO bebidas (codigo_produto, volume_ml) VALUES (?, ?)";
                    try (PreparedStatement stmtBeb = conn.prepareStatement(sqlBebida)) {
                        stmtBeb.setInt(1, produto.getCodigo());
                        stmtBeb.setInt(2, ((Bebida) produto).getVolumeMl());
                        stmtBeb.executeUpdate();
                    }
                }
                
                conn.commit();
                System.out.println("✅ Produto salvo no banco com sucesso!");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir produto: " + e.getMessage());
        }
    }

    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        // Query que junta Produto com Restaurante e verifica se é Comida ou Bebida
        String sql = "SELECT p.*, r.nome as res_nome, r.especialidade, c.peso_gramas, b.volume_ml " +
                     "FROM produtos p " +
                     "JOIN restaurantes r ON p.codigo_restaurante = r.codigo " +
                     "LEFT JOIN comidas c ON p.codigo = c.codigo_produto " +
                     "LEFT JOIN bebidas b ON p.codigo = b.codigo_produto";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Restaurante rest = new Restaurante(rs.getInt("codigo_restaurante"), rs.getString("res_nome"), rs.getString("especialidade"));
                
                if (rs.getDouble("peso_gramas") > 0) {
                    produtos.add(new Comida(rs.getInt("codigo"), rs.getString("nome"), rs.getDouble("preco"), rest, rs.getDouble("peso_gramas")));
                } else {
                    produtos.add(new Bebida(rs.getInt("codigo"), rs.getString("nome"), rs.getDouble("preco"), rest, rs.getInt("volume_ml")));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

    public void excluir(int codigo) {
        String sql = "DELETE FROM produtos WHERE codigo = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
            System.out.println("✅ Produto removido do banco!");
        } catch (SQLException e) {
            System.out.println("❌ Erro ao excluir produto: " + e.getMessage());
        }
    }

    // 3. UPDATE
    public void atualizar(int codigo, String novoNome, double novoPreco) {
        String sql = "UPDATE produtos SET nome = ?, preco = ? WHERE codigo = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, novoNome);
            stmt.setDouble(2, novoPreco);
            stmt.setInt(3, codigo);
            
            int linhasAfetadas = stmt.executeUpdate();
            if(linhasAfetadas > 0) {
                System.out.println("✅ Produto atualizado na base de dados!");
            } else {
                System.out.println("❌ Produto não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar produto: " + e.getMessage());
        }
    }
}