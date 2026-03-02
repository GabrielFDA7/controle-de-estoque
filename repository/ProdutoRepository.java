package repository;

import database.ConexaoBanco;
import model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {

    public Produto salvar(Produto produto) {
        String sql = "INSERT INTO produtos (nome, quantidade, preco) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBanco.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidade());
            stmt.setDouble(3, produto.getPreco());
            stmt.executeUpdate();

            try (Statement stmtId = conn.createStatement();
                    ResultSet rs = stmtId.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    produto.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar produto: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return produto;
    }

    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (Connection conn = ConexaoBanco.getConexao();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                produtos.add(mapearProduto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return produtos;
    }

    public Produto buscarPorId(Long id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";

        try (Connection conn = ConexaoBanco.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearProduto(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Produto> buscarPorNome(String nome) {
        List<Produto> resultado = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE LOWER(nome) LIKE ?";

        try (Connection conn = ConexaoBanco.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resultado.add(mapearProduto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar por nome: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return resultado;
    }

    private Produto mapearProduto(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setId(rs.getLong("id"));
        p.setNome(rs.getString("nome"));
        p.setQuantidade(rs.getInt("quantidade"));
        p.setPreco(rs.getDouble("preco"));
        return p;
    }

    public void adicionarEstoque(Long id, int quantidade) throws Exception {
        Produto p = buscarPorId(id);
        if (p == null) {
            throw new Exception("Produto não encontrado.");
        }
        String sql = "UPDATE produtos SET quantidade = quantidade + ? WHERE id = ?";
        try (Connection conn = ConexaoBanco.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantidade);
            stmt.setLong(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar estoque: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void removerEstoque(Long id, int quantidade) throws Exception {
        Produto p = buscarPorId(id);
        if (p == null) {
            throw new Exception("Produto não encontrado com o ID fornecido.");
        }
        if (p.getQuantidade() < quantidade) {
            throw new Exception("Saldo insuficiente! Quantidade em estoque: " + p.getQuantidade());
        }

        String sql = "UPDATE produtos SET quantidade = quantidade - ? WHERE id = ?";
        try (Connection conn = ConexaoBanco.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantidade);
            stmt.setLong(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao remover estoque: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}