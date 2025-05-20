package pat.proyectofinal.unit.model;

import pat.proyectofinal.model.Perfil;
import org.junit.jupiter.api.Test;
import pat.proyectofinal.model.Role;

import static org.junit.jupiter.api.Assertions.*;

public class PerfilUnitTest {

    @Test
    public void testConstructorAndGetters() {
        Perfil perfil = new Perfil("Lucía", "lucia@example.com", Role.ALUMNO, "Clase 1", "Inglés");

        assertEquals("Lucía", perfil.nombre());
        assertEquals("lucia@example.com", perfil.email());
        assertEquals(Role.ALUMNO, perfil.role());
        assertEquals("Clase 1", perfil.clase());
        assertEquals("Inglés", perfil.idioma());
    }

    @Test
    public void testEqualsAndHashCode() {
        Perfil p1 = new Perfil("Lucía", "lucia@example.com", Role.ALUMNO, "Clase 1", "Inglés");
        Perfil p2 = new Perfil("Lucía", "lucia@example.com", Role.ALUMNO, "Clase 1", "Inglés");

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testToStringIncludesFields() {
        Perfil perfil = new Perfil("Carlos", "carlos@mail.com", Role.ADMINISTRADOR, "Clase 2", "Francés");
        String str = perfil.toString();

        assertTrue(str.contains("Carlos"));
        assertTrue(str.contains("carlos@mail.com"));
        assertTrue(str.contains("ADMIN"));
        assertTrue(str.contains("Clase 2"));
        assertTrue(str.contains("Francés"));
    }
}

