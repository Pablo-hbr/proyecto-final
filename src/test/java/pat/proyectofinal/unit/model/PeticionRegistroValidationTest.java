package pat.proyectofinal.unit.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pat.proyectofinal.model.PeticionRegistro;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PeticionRegistroValidationTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testRegistroValido() {
        PeticionRegistro registro = new PeticionRegistro("Ana", "ana@mail.com", "Clave123", 1L);
        Set<ConstraintViolation<PeticionRegistro>> violations = validator.validate(registro);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNombreVacio() {
        PeticionRegistro registro = new PeticionRegistro("  ", "ana@mail.com", "Clave123", 1L);
        Set<ConstraintViolation<PeticionRegistro>> violations = validator.validate(registro);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    public void testEmailInvalido() {
        PeticionRegistro registro = new PeticionRegistro("Ana", "correoNoValido", "Clave123", 1L);
        Set<ConstraintViolation<PeticionRegistro>> violations = validator.validate(registro);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    public void testPasswordInvalida() {
        PeticionRegistro registro = new PeticionRegistro("Ana", "ana@mail.com", "abc", 1L);
        Set<ConstraintViolation<PeticionRegistro>> violations = validator.validate(registro);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    public void testIdClaseCero() {
        PeticionRegistro registro = new PeticionRegistro("Ana", "ana@mail.com", "Clave123", 0L);
        Set<ConstraintViolation<PeticionRegistro>> violations = validator.validate(registro);
        assertTrue(violations.isEmpty()); // porque 0L no viola @NotNull (solo sería inválido si el campo fuera un Long null)
    }
}