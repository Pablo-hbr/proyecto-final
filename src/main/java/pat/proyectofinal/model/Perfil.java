package pat.proyectofinal.model;

public record Perfil(
        String nombre,
        String email,
        Role role,
        String clase,
        String idioma
) {}
