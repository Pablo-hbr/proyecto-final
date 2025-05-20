#  Proyecto Final – Programación de Aplicaciones Telemáticas (PAT)

Este es el proyecto final de la asignatura **PAT** desarrollado por:

- Paola Fernández-Checa
- Lorena Lam
- Pablo Alonso
- Pablo Hebrero

## Descripción del proyecto

Esta aplicación simula la gestión de una **academia de idiomas**, permitiendo:

- El **registro de alumnos** seleccionando clases disponibles
- La **gestión de clases y alumnos por parte de un administrador**
- La visualización de alumnos y clases desde un panel web dinámico
- El uso de una arquitectura **frontend + backend** con integración y despliegue continuo

El sistema incluye funcionalidades clave como:

- Creación de nuevas clases con control de aforo
- Registro de usuarios con validaciones
- Reducción automática del aforo al inscribirse
- Gestión de sesiones e inicio/cierre de sesión
- Frontend con HTML, CSS y JavaScript
- Backend en Spring Boot (Java 17), con base de datos embebida (H2)

## Usuario y datos por defecto

Para facilitar las pruebas, la aplicación incluye por defecto:

- 👤 Usuario administrador
    - Correo: `admin@admin.com`
    - Contraseña: `PAT123`

- 📚 Clase precargada:
    - Idioma: **Francés**
    - Nombre: **Francés Lunes**
    - Aforo: **30 plazas**

