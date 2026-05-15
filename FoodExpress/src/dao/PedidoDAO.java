package dao;

import model.Pedido;
import model.ItemPedido;
import util.ConexaoBD;
import java.sql.*;

public class PedidoDAO {

    public void inserir(Pedido pedido) {
        // Correção: A query agora reflete exatamente as colunas da tabela pedidos no schema.sql
        String sqlPedido = "INSERT INTO pedidos (numero_pedido, cpf_cliente, placa_entregador, status) VALUES (?, ?, ?, ?)";
        String sqlItem = "INSERT INTO itens_pedido (numero_pedido, codigo_produto, quantidade) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar()) {
            conn.setAutoCommit(false); // Inicia transação

            try (PreparedStatement stmtP = conn.prepareStatement(sqlPedido)) {
                stmtP.setInt(1, pedido.getNumeroPedido());
                stmtP.setString(2, pedido.getCliente().getCpf());
                
                // Tratamento para entregador que pode ser nulo
                stmtP.setString(3, pedido.getEntregador() != null ? pedido.getEntregador().getPlacaVeiculo() : null);
                
                // Adicionando o status, que é um campo NOT NULL na tabela
                stmtP.setString(4, pedido.getStatus()); 
                
                stmtP.executeUpdate();

                try (PreparedStatement stmtI = conn.prepareStatement(sqlItem)) {
                    // ATENÇÃO: Para este loop funcionar, o método getItens() em Pedido.java precisa estar corrigido
                    for (ItemPedido item : pedido.getItens()) {
                        stmtI.setInt(1, pedido.getNumeroPedido());
                        stmtI.setInt(2, item.getProduto().getCodigo());
                        stmtI.setInt(3, item.getQuantidade());
                        stmtI.addBatch();
                    }
                    stmtI.executeBatch();
                }

                conn.commit(); // Finaliza transação com sucesso
                System.out.println("✅ Pedido #" + pedido.getNumeroPedido() + " salvo no Banco de Dados!");
            } catch (SQLException e) {
                conn.rollback(); // Cancela as alterações do banco se der erro
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao salvar pedido: " + e.getMessage());
        }
    }
}