package pe.edu.vallegrande.misisitema.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase singleton para gestionar la conexión a MySQL
 * Conecta a la base de datos ADAM de Vallegrande
 */
public class Conexion {
    
    // Parámetros de conexión a MySQL Workbench
    private static final String HOST = "localhost";
    private static final int PUERTO = 3308;  // Puerto de MySQL Workbench
    private static final String BD = "adam_db";
    private static final String USUARIO = "adam_user";
    private static final String CONTRASENA = "adam123";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static Connection conexion = null;
    
    /**
     * Obtiene la conexión a la base de datos
     * Si no existe, crea una nueva
     */
    public static Connection getConexion() {
        if (conexion == null) {
            try {
                Class.forName(DRIVER);
                String url = String.format(
                    "jdbc:mysql://%s:%d/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    HOST, PUERTO, BD
                );
                conexion = DriverManager.getConnection(url, USUARIO, CONTRASENA);
                System.out.println("✓ Conexión a MySQL Workbench exitosa");
                System.out.println("  Base de datos: " + BD);
                System.out.println("  Host: " + HOST + ":" + PUERTO);
            } catch (ClassNotFoundException e) {
                System.err.println("✗ Error: Driver MySQL no encontrado");
                System.err.println("  " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("✗ Error al conectar a la base de datos");
                System.err.println("  " + e.getMessage());
                System.err.println("  Verifica que MySQL esté corriendo y los datos sean correctos");
            }
        }
        return conexion;
    }
    
    /**
     * Cierra la conexión a la base de datos
     */
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("✓ Conexión cerrada");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al cerrar la conexión: " + e.getMessage());
        }
    }
    
    /**
     * Verifica si la conexión está activa
     */
    public static boolean isConectado() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
