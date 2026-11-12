package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL =
            "jdbc:mysql://localhost:3306/laboratorio_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    // Lê da variável de ambiente DB_SENHA. Se o seu MySQL local tiver senha e não usar variável de ambiente,
    // preencha temporariamente entre as aspas de "" para testar (lembre-se de apagar antes de gerar o ZIP).
    private static final String SENHA = System.getenv("DB_SENHA") == null ? "" : System.getenv("DB_SENHA");

    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL Connector/J não encontrado. Adicione o .jar da pasta lib ao projeto.", e);
        }
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}