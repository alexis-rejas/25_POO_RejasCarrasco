package pe.edu.vallegrande.misisitema.controller;

import pe.edu.vallegrande.misisitema.model.ContactoDAO;
import pe.edu.vallegrande.misisitema.model.Usuario;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class MainController {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private final ContactoDAO contactoDAO;

    public MainController() {
        this(new ContactoDAO());
    }

    MainController(ContactoDAO contactoDAO) {
        this.contactoDAO = contactoDAO;
    }

    public String validarContacto(Usuario contacto) {
        String nombre = texto(contacto.getNombre());
        String email = texto(contacto.getEmail());
        String mensaje = texto(contacto.getMensaje());

        if (nombre.isEmpty() || email.isEmpty() || mensaje.isEmpty()) {
            return "Completa nombre, correo y mensaje.";
        }
        if (nombre.length() < 3 || nombre.length() > 120) {
            return "El nombre debe tener entre 3 y 120 caracteres.";
        }
        if (email.length() > 120 || !EMAIL_PATTERN.matcher(email).matches()) {
            return "Ingresa un correo válido de hasta 120 caracteres.";
        }
        if (texto(contacto.getTelefono()).length() > 20
                || texto(contacto.getEmpresa()).length() > 150
                || texto(contacto.getAsunto()).length() > 200) {
            return "Teléfono, empresa o asunto supera la longitud permitida.";
        }
        if (mensaje.length() < 10) {
            return "El mensaje debe tener al menos 10 caracteres.";
        }
        return null;
    }

    public boolean crearContacto(Usuario contacto) throws SQLException {
        String error = validarContacto(contacto);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        return contactoDAO.crear(contacto);
    }

    public List<Usuario> obtenerTodos() throws SQLException {
        return contactoDAO.obtenerTodos();
    }

    public Usuario obtenerPorId(int id) throws SQLException {
        return contactoDAO.obtenerPorId(id);
    }

    public boolean actualizarContacto(Usuario contacto) throws SQLException {
        String error = validarContacto(contacto);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        return contactoDAO.actualizar(contacto);
    }

    public boolean eliminarContacto(int id) throws SQLException {
        return contactoDAO.eliminar(id);
    }

    public List<Usuario> obtenerPorEstado(String estado) throws SQLException {
        return contactoDAO.obtenerPorEstado(estado);
    }

    private String texto(String valor) {
        return valor == null ? "" : valor.trim();
    }
}
