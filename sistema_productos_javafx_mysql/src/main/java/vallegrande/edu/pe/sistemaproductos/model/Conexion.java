package vallegrande.edu.pe.sistemaproductos.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = System.getenv().getOrDefault(
            "DB_URL", "jdbc:mysql://localhost:3307/sistema_productos");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "root");

    public static Connection getConexion() throws SQLException {
        String password = System.getenv("DB_PASSWORD");
        if (password == null || password.isBlank()) {
            throw new SQLException("La variable de entorno DB_PASSWORD no está configurada");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC de MySQL no encontrado", e);
        }
    }
}
