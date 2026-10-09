# Sistema de productos JavaFX y MySQL

Aplicación CRUD de escritorio hecha con JavaFX, Maven y MySQL.

## Requisitos

- JDK 17
- MySQL

La aplicación espera una base de datos `sistema_productos` con una tabla `productos` que tenga las columnas `id` (autoincremental), `nombre`, `categoria`, `cantidad` y `precio`. La conexión predeterminada usa `localhost:3307`.

## Configuración y ejecución

Configura la contraseña de MySQL en `DB_PASSWORD` antes de iniciar. En PowerShell:

```powershell
$env:DB_PASSWORD = "tu_contraseña"
$env:DB_USER = "root" # Opcional; predeterminado: root
$env:DB_URL = "jdbc:mysql://localhost:3307/sistema_productos" # Opcional
.\mvnw.cmd javafx:run
```

En macOS o Linux, exporta `DB_PASSWORD` y ejecuta `./mvnw javafx:run`.
