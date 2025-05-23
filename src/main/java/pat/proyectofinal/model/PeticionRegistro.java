package pat.proyectofinal.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PeticionRegistro(
    @NotBlank
    String name,
    @NotBlank @Email
    String email,
    // Patrón: al menos una mayúscula, una minúscula, y un número, y de longitud más de 7
    @NotBlank @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,}$")
    String password,
    @NotNull
    long idClase
) {}
