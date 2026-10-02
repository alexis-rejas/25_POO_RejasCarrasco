package pe.edu.vallegrande.misisitema.model;

import java.time.LocalDateTime;

public class Usuario {
    
    private Integer id;
    private String nombre;
    private String email;
    private String telefono;
    private String empresa;
    private String asunto;
    private String mensaje;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaRespuesta;
    
    public Usuario() {}
    
    public Usuario(String nombre, String email, String telefono, String empresa, 
                    String asunto, String mensaje) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.empresa = empresa;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.estado = "pendiente";
        this.fechaCreacion = LocalDateTime.now();
    }
    
    public Usuario(Integer id, String nombre, String email, String telefono, String empresa, 
                    String asunto, String mensaje, String estado, LocalDateTime fechaCreacion, LocalDateTime fechaRespuesta) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.empresa = empresa;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaRespuesta = fechaRespuesta;
    }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }
    
    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }
    
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public LocalDateTime getFechaRespuesta() { return fechaRespuesta; }
    public void setFechaRespuesta(LocalDateTime fechaRespuesta) { this.fechaRespuesta = fechaRespuesta; }
    
    @Override
    public String toString() {
        return String.format("Usuario{id=%d, nombre='%s', email='%s', estado='%s'}", 
            id, nombre, email, estado);
    }
}
