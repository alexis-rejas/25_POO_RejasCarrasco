package pe.edu.vallegrande.misisitema.controller;

import pe.edu.vallegrande.misisitema.model.Usuario;
import pe.edu.vallegrande.misisitema.model.Conexion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    
    public boolean crearContacto(Usuario contacto) {
        String sql = "INSERT INTO contactos (nombre, email, telefono, empresa, asunto, mensaje, estado, fecha_creacion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            Connection conn = Conexion.getConexion();
            if (conn == null || conn.isClosed()) {
                System.err.println("✗ Conexión cerrada");
                return false;
            }
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, contacto.getNombre());
            stmt.setString(2, contacto.getEmail());
            stmt.setString(3, contacto.getTelefono());
            stmt.setString(4, contacto.getEmpresa());
            stmt.setString(5, contacto.getAsunto());
            stmt.setString(6, contacto.getMensaje());
            stmt.setString(7, contacto.getEstado());
            stmt.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            
            int filasAfectadas = stmt.executeUpdate();
            stmt.close();
            System.out.println("✓ Contacto creado: " + contacto.getNombre());
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al crear contacto: " + e.getMessage());
            return false;
        }
    }
    
    public List<Usuario> obtenerTodos() {
        List<Usuario> contactos = new ArrayList<>();
        String sql = "SELECT * FROM contactos ORDER BY fecha_creacion DESC";
        
        try {
            Connection conn = Conexion.getConexion();
            if (conn == null || conn.isClosed()) {
                System.err.println("✗ Conexión cerrada");
                return contactos;
            }
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Usuario contacto = new Usuario();
                contacto.setId(rs.getInt("id"));
                contacto.setNombre(rs.getString("nombre"));
                contacto.setEmail(rs.getString("email"));
                contacto.setTelefono(rs.getString("telefono"));
                contacto.setEmpresa(rs.getString("empresa"));
                contacto.setAsunto(rs.getString("asunto"));
                contacto.setMensaje(rs.getString("mensaje"));
                contacto.setEstado(rs.getString("estado"));
                contacto.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
                
                if (rs.getTimestamp("fecha_respuesta") != null) {
                    contacto.setFechaRespuesta(rs.getTimestamp("fecha_respuesta").toLocalDateTime());
                }
                
                contactos.add(contacto);
            }
            
            rs.close();
            stmt.close();
            System.out.println("✓ Se cargaron " + contactos.size() + " contactos");
            return contactos;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al obtener contactos: " + e.getMessage());
            return contactos;
        }
    }
    
    public Usuario obtenerPorId(int id) {
        String sql = "SELECT * FROM contactos WHERE id = ?";
        
        try {
            Connection conn = Conexion.getConexion();
            if (conn == null || conn.isClosed()) {
                System.err.println("✗ Conexión cerrada");
                return null;
            }
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario contacto = new Usuario();
                contacto.setId(rs.getInt("id"));
                contacto.setNombre(rs.getString("nombre"));
                contacto.setEmail(rs.getString("email"));
                contacto.setTelefono(rs.getString("telefono"));
                contacto.setEmpresa(rs.getString("empresa"));
                contacto.setAsunto(rs.getString("asunto"));
                contacto.setMensaje(rs.getString("mensaje"));
                contacto.setEstado(rs.getString("estado"));
                contacto.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
                
                if (rs.getTimestamp("fecha_respuesta") != null) {
                    contacto.setFechaRespuesta(rs.getTimestamp("fecha_respuesta").toLocalDateTime());
                }
                
                rs.close();
                stmt.close();
                return contacto;
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            System.err.println("✗ Error al obtener contacto: " + e.getMessage());
        }
        
        return null;
    }
    
    public boolean actualizarContacto(Usuario contacto) {
        String sql = "UPDATE contactos SET nombre=?, email=?, telefono=?, empresa=?, asunto=?, mensaje=?, estado=? WHERE id=?";
        
        try {
            Connection conn = Conexion.getConexion();
            if (conn == null || conn.isClosed()) {
                System.err.println("✗ Conexión cerrada");
                return false;
            }
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, contacto.getNombre());
            stmt.setString(2, contacto.getEmail());
            stmt.setString(3, contacto.getTelefono());
            stmt.setString(4, contacto.getEmpresa());
            stmt.setString(5, contacto.getAsunto());
            stmt.setString(6, contacto.getMensaje());
            stmt.setString(7, contacto.getEstado());
            stmt.setInt(8, contacto.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            stmt.close();
            System.out.println("✓ Contacto actualizado: " + contacto.getNombre());
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar contacto: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminarContacto(int id) {
        String sql = "DELETE FROM contactos WHERE id = ?";
        
        try {
            Connection conn = Conexion.getConexion();
            if (conn == null || conn.isClosed()) {
                System.err.println("✗ Conexión cerrada");
                return false;
            }
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();
            stmt.close();
            System.out.println("✓ Contacto eliminado");
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al eliminar contacto: " + e.getMessage());
            return false;
        }
    }
    
    public List<Usuario> obtenerPorEstado(String estado) {
        List<Usuario> contactos = new ArrayList<>();
        String sql = "SELECT * FROM contactos WHERE estado = ? ORDER BY fecha_creacion DESC";
        
        try {
            Connection conn = Conexion.getConexion();
            if (conn == null || conn.isClosed()) {
                System.err.println("✗ Conexión cerrada");
                return contactos;
            }
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Usuario contacto = new Usuario();
                contacto.setId(rs.getInt("id"));
                contacto.setNombre(rs.getString("nombre"));
                contacto.setEmail(rs.getString("email"));
                contacto.setTelefono(rs.getString("telefono"));
                contacto.setEmpresa(rs.getString("empresa"));
                contacto.setAsunto(rs.getString("asunto"));
                contacto.setMensaje(rs.getString("mensaje"));
                contacto.setEstado(rs.getString("estado"));
                contacto.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
                contactos.add(contacto);
            }
            
            rs.close();
            stmt.close();
            return contactos;
            
        } catch (SQLException e) {
            System.err.println("✗ Error al filtrar por estado: " + e.getMessage());
            return contactos;
        }
    }
}
