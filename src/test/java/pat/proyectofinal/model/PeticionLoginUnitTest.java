package pat.proyectofinal.model;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PeticionLoginUnitTest {

    @Test
    public void testConstructorAndGetters() {
        PeticionLogin login = new PeticionLogin("user@example.com", "securePass");

        assertEquals("user@example.com", login.email());
        assertEquals("securePass", login.password());
    }

    @Test
    public void testEqualsAndHashCode() {
        PeticionLogin p1 = new PeticionLogin("test@example.com", "pass123");
        PeticionLogin p2 = new PeticionLogin("test@example.com", "pass123");

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testToStringContainsValues() {
        PeticionLogin login = new PeticionLogin("a@b.com", "mypassword");
        String result = login.toString();

        assertTrue(result.contains("a@b.com"));
        assertTrue(result.contains("mypassword"));
    }

}

