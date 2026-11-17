package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexao {
    private static final String URL_PADRAO =
            "jdbc:mysql://localhost:3306/laboratorio_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static String ler(String nome) throws SQLException {
        String v = System.getenv(nome);
        if (v == null || v.isEmpty())
            throw new SQLException("Variável de ambiente " + nome + " não definida. Veja o README.");
        return v;
    }

    public static Connection getConexao() throws SQLException {
        String url = System.getenv("DB_URL");
        if (url == null || url.isEmpty()) url = URL_PADRAO;
        String usuario = ler("DB_USUARIO");
        String senha = ler("DB_SENHA");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL Connector/J não encontrado no classpath (pom.xml ou pasta lib).", e);
        }
        return DriverManager.getConnection(url, usuario, senha);
    }
}