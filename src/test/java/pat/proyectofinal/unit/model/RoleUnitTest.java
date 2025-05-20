package pat.proyectofinal.unit.model;

import org.junit.jupiter.api.Test;
import pat.proyectofinal.model.Role;

import static org.junit.jupiter.api.Assertions.*;

public class RoleUnitTest {

    @Test
    public void testEnumValuesExist() {
        assertEquals(Role.ALUMNO, Role.valueOf("ALUMNO"));
        assertEquals(Role.ADMINISTRADOR, Role.valueOf("ADMINISTRADOR"));
    }

    @Test
    public void testEnumValuesList() {
        Role[] roles = Role.values();
        assertTrue(roles.length >= 2);
        assertTrue(java.util.Arrays.asList(roles).contains(Role.ALUMNO));
        assertTrue(java.util.Arrays.asList(roles).contains(Role.ADMINISTRADOR));
    }

    @Test
    public void testToStringOutput() {
        assertEquals("ALUMNO", Role.ALUMNO.toString());
        assertEquals("ADMINISTRADOR", Role.ADMINISTRADOR.toString());
    }
}

