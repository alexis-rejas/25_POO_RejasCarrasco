package pe.edu.vallegrande.misisitema.model;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public final class Conexion {
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 3308;
    private static final String DEFAULT_DATABASE = "adam_db";
    private static final String DEFAULT_USER = "adam_user";
    private static final Dotenv DOTENV = loadDotenv();

    private Conexion() {
    }

    public static Connection getConexion() throws SQLException {
        String password = setting("DB_PASSWORD", "");
        if (password.isBlank()) {
            throw new SQLException(
                    "Falta DB_PASSWORD. No se encontró una contraseña en variables de entorno "
                            + "ni en el archivo .env del proyecto contactos_adam."
            );
        }

        String host = setting("DB_HOST", DEFAULT_HOST);
        String portValue = setting("DB_PORT", Integer.toString(DEFAULT_PORT));
        String database = setting("DB_NAME", DEFAULT_DATABASE);
        String user = setting("DB_USER", DEFAULT_USER);
        String sslMode = setting("DB_SSL_MODE", "DISABLED").toUpperCase();
        int port;
        try {
            port = Integer.parseInt(portValue);
        } catch (NumberFormatException error) {
            throw new SQLException("DB_PORT debe ser un número válido.", error);
        }

        if (port < 1 || port > 65535) {
            throw new SQLException("DB_PORT debe estar entre 1 y 65535.");
        }
        if (!sslMode.matches("DISABLED|PREFERRED|REQUIRED|VERIFY_CA|VERIFY_IDENTITY")) {
            throw new SQLException("DB_SSL_MODE no contiene un modo TLS válido.");
        }

        String url = String.format(
                "jdbc:mysql://%s:%d/%s?sslMode=%s&allowPublicKeyRetrieval=true"
                        + "&connectionTimeZone=UTC&characterEncoding=UTF-8",
                host,
                port,
                database,
                sslMode
        );
        return DriverManager.getConnection(url, user, password);
    }

    private static Dotenv loadDotenv() {
        Path envFile = findDotenvFile();
        if (envFile == null) {
            return Dotenv.configure().ignoreIfMissing().load();
        }
        return Dotenv.configure()
                .directory(envFile.getParent().toString())
                .filename(envFile.getFileName().toString())
                .ignoreIfMissing()
                .load();
    }

    private static Path findDotenvFile() {
        Path workingDirectory = Path.of(System.getProperty("user.dir", "."))
                .toAbsolutePath()
                .normalize();
        Path found = findDotenvFrom(workingDirectory);
        if (found != null) {
            return found;
        }

        try {
            Path classLocation = Path.of(
                    Conexion.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            ).toAbsolutePath().normalize();
            return findDotenvFrom(Files.isDirectory(classLocation)
                    ? classLocation
                    : classLocation.getParent());
        } catch (URISyntaxException error) {
            throw new IllegalStateException("No se pudo resolver la ubicación de Conexion.class.", error);
        }
    }

    private static Path findDotenvFrom(Path start) {
        Path parent = start;
        while (parent != null) {
            Path candidate = parent.resolve(".env");
            if (Files.isRegularFile(candidate)) {
                return candidate;
            }
            parent = parent.getParent();
        }
        return null;
    }

    private static String setting(String name, String fallback) {
        String value = System.getProperty(name);
        if (value == null || value.isBlank()) {
            value = System.getenv(name);
        }
        if (value == null || value.isBlank()) {
            value = DOTENV.get(name);
        }
        String sanitized = normalizeValue(value);
        return sanitized == null || sanitized.isBlank() ? fallback : sanitized;
    }

    private static String normalizeValue(String value) {
        if (value == null) {
            return null;
        }
        String sanitized = value.trim();
        if (sanitized.length() >= 2) {
            char first = sanitized.charAt(0);
            char last = sanitized.charAt(sanitized.length() - 1);
            if ((first == '"' && last == '"') || (first == '\'' && last == '\'')) {
                sanitized = sanitized.substring(1, sanitized.length() - 1);
            }
        }
        return sanitized;
    }
}
