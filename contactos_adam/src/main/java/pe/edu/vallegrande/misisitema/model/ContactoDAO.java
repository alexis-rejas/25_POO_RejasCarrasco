package pe.edu.vallegrande.misisitema.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ContactoDAO {
    private static final String SELECT_COLUMNS =
            "id, nombre, email, telefono, empresa, asunto, mensaje, estado, "
                    + "fecha_creacion, fecha_respuesta";

    public boolean crear(Usuario contacto) throws SQLException {
        String sql = "INSERT INTO contactos "
                + "(nombre, email, telefono, empresa, asunto, mensaje, estado, fecha_creacion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Conexion.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contacto.getNombre());
            statement.setString(2, contacto.getEmail());
            statement.setString(3, contacto.getTelefono());
            statement.setString(4, contacto.getEmpresa());
            statement.setString(5, contacto.getAsunto());
            statement.setString(6, contacto.getMensaje());
            statement.setString(7, contacto.getEstado());
            statement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            return statement.executeUpdate() == 1;
        }
    }

    public List<Usuario> obtenerTodos() throws SQLException {
        String sql = "SELECT " + SELECT_COLUMNS
                + " FROM contactos ORDER BY fecha_creacion DESC";
        List<Usuario> contactos = new ArrayList<>();

        try (Connection connection = Conexion.getConexion();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {
            while (result.next()) {
                contactos.add(mapear(result));
            }
        }
        return contactos;
    }

    public Usuario obtenerPorId(int id) throws SQLException {
        String sql = "SELECT " + SELECT_COLUMNS + " FROM contactos WHERE id = ?";
        try (Connection connection = Conexion.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? mapear(result) : null;
            }
        }
    }

    public boolean actualizar(Usuario contacto) throws SQLException {
        String sql = "UPDATE contactos SET nombre = ?, email = ?, telefono = ?, "
                + "empresa = ?, asunto = ?, mensaje = ?, estado = ? WHERE id = ?";
        try (Connection connection = Conexion.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contacto.getNombre());
            statement.setString(2, contacto.getEmail());
            statement.setString(3, contacto.getTelefono());
            statement.setString(4, contacto.getEmpresa());
            statement.setString(5, contacto.getAsunto());
            statement.setString(6, contacto.getMensaje());
            statement.setString(7, contacto.getEstado());
            statement.setInt(8, contacto.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM contactos WHERE id = ?";
        try (Connection connection = Conexion.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public List<Usuario> obtenerPorEstado(String estado) throws SQLException {
        String sql = "SELECT " + SELECT_COLUMNS
                + " FROM contactos WHERE estado = ? ORDER BY fecha_creacion DESC";
        List<Usuario> contactos = new ArrayList<>();
        try (Connection connection = Conexion.getConexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, estado);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    contactos.add(mapear(result));
                }
            }
        }
        return contactos;
    }

    private Usuario mapear(ResultSet result) throws SQLException {
        Timestamp fechaCreacion = result.getTimestamp("fecha_creacion");
        Timestamp fechaRespuesta = result.getTimestamp("fecha_respuesta");
        return new Usuario(
                result.getInt("id"),
                result.getString("nombre"),
                result.getString("email"),
                result.getString("telefono"),
                result.getString("empresa"),
                result.getString("asunto"),
                result.getString("mensaje"),
                result.getString("estado"),
                fechaCreacion == null ? null : fechaCreacion.toLocalDateTime(),
                fechaRespuesta == null ? null : fechaRespuesta.toLocalDateTime()
        );
    }
}
