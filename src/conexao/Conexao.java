package conexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL_PADRAO =
            "jdbc:mysql://localhost:3306/laboratorio_db"
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String USUARIO_PADRAO = "root";
    private static final String SENHA_PADRAO = "";

    private static String ler(String nome, String padrao) {
        String v = System.getenv(nome);
        return (v == null || v.isEmpty()) ? padrao : v;
    }
    public static Connection getConexao() throws SQLException {
        String url = ler("DB_URL", URL_PADRAO);
        String usuario = ler("DB_USUARIO", USUARIO_PADRAO);
        String senha = ler("DB_SENHA", SENHA_PADRAO);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL Connector/J não encontrado no classpath (pom.xml ou pasta lib).", e);
        }
        return DriverManager.getConnection(url, usuario, senha);
    }
}
