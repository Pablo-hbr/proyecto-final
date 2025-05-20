package pat.proyectofinal.model;


import jakarta.validation.constraints.NotBlank;

public record PeticionLogin(
        @NotBlank
        String email,
        @NotBlank
        String password
) {}