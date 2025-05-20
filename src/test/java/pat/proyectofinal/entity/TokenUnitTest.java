package pat.proyectofinal.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TokenUnitTest {

    @Test
    public void testTokenFieldsAssignment() {
        Usuario usuario = new Usuario();
        Token token = new Token();
        token.id = "abc-123-uuid";
        token.usuario = usuario;

        assertEquals("abc-123-uuid", token.id);
        assertEquals(usuario, token.usuario);
    }

    @Test
    public void testTokenDefaultValues() {
        Token token = new Token();
        assertNull(token.id);
        assertNull(token.usuario);
    }
}

