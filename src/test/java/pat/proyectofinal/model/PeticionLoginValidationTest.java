package pat.proyectofinal.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PeticionLoginValidationTest {

    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testLoginValido() {
        PeticionLogin login = new PeticionLogin("usuario@valido.com", "clave123");
        Set<ConstraintViolation<PeticionLogin>> violations = validator.validate(login);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testEmailInvalidoYPasswordVacio() {
        PeticionLogin login = new PeticionLogin("noesunemail", " ");
        Set<ConstraintViolation<PeticionLogin>> violations = validator.validate(login);

        assertEquals(2, violations.size());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    public void testEmailVacio() {
        PeticionLogin login = new PeticionLogin(" ", "claveValida123");
        Set<ConstraintViolation<PeticionLogin>> violations = validator.validate(login);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    public void testPasswordNula() {
        PeticionLogin login = new PeticionLogin("usuario@correo.com", null);
        Set<ConstraintViolation<PeticionLogin>> violations = validator.validate(login);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

}

