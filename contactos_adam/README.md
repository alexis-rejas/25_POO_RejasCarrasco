# ADAM Contactos (JavaFX)

Desktop contact manager following MVC and using the same MySQL database and
`contactos` table as the ADAM website.

## Requirements

- JDK 17
- Maven 3.9 or later
- Access to the MySQL database used by the website

The Maven build targets Java 17. JavaFX 21 runs on JDK 17 or later.
The `Main` class is a plain Java launcher so IntelliJ can run it with JavaFX
dependencies on the classpath; the JavaFX window is created by
`ContactosApplication`.

## Database connection

The application connects to the existing local Workbench/Docker MySQL instance
and reads the same `.env` settings as the website (environment variables and
JVM system properties take precedence):

| Setting | Default |
| --- | --- |
| `DB_HOST` | `127.0.0.1` |
| `DB_PORT` | `3308` |
| `DB_NAME` | `adam_db` |
| `DB_USER` | `adam_user` |
| `DB_PASSWORD` | Required; no default |
| `DB_SSL_MODE` | `DISABLED` |

Copy `.env.example` to `.env` in `contactos_adam` and set `DB_PASSWORD` to the
password of the existing local MySQL container. The Java application loads
this file from the working directory or from the project containing the loaded
`Conexion.class`, so IntelliJ can use the project `.env` even if its working
directory is set to the repository root. The `.env` file is ignored by Git;
never commit database passwords or add them to Java source code.

The web form and this application share the `contactos` table and these fields:
`id`, `nombre`, `email`, `telefono`, `empresa`, `asunto`, `mensaje`, `estado`,
`fecha_creacion`, and `fecha_respuesta`. Both applications use `email` as the
database column for the form's email address.

## Run

From this directory, create `.env` as described above and run:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-17'
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
mvn clean javafx:run
```

To compile without opening the desktop window:

```powershell
mvn clean verify
```

In IntelliJ, run `pe.edu.vallegrande.misisitema.Main` as a normal Application
with the Maven dependencies enabled. Do not run `ContactosApplication`
directly as an IntelliJ Application; use the `Main` launcher or `mvn
javafx:run`.

## MVC structure

- `model/Usuario.java`: contact data.
- `model/ContactoDAO.java`: SQL operations on the shared table.
- `model/Conexion.java`: JDBC configuration and connection creation.
- `controller/MainController.java`: validation and application operations.
- `view/MainView.java`: JavaFX form, table, and actions.
- `Main.java`: JavaFX application entry point.
