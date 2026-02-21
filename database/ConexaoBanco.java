package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBanco {

    private static final String URL = "jdbc:sqlite:estoque.db";

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void criarTabela() {
        String sql = """
                CREATE TABLE IF NOT EXISTS produtos (
                    id    INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome  TEXT    NOT NULL,
                    quantidade INTEGER NOT NULL,
                    preco REAL    NOT NULL
                )
                """;

        try (Connection conn = getConexao();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
