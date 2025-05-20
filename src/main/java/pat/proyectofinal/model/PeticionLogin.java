package pat.proyectofinal.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PeticionLogin(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) {}