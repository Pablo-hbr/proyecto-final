package pat.proyectofinal.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlumnoTablaUnitTest {

    @Test
    public void testConstructorAndGetters() {
        AlumnoTabla alumno = new AlumnoTabla("Laura", "laura@example.com", "Clase A");

        assertEquals("Laura", alumno.nombre());
        assertEquals("laura@example.com", alumno.email());
        assertEquals("Clase A", alumno.clase());
    }

    @Test
    public void testEqualsAndHashCode() {
        AlumnoTabla a1 = new AlumnoTabla("Carlos", "carlos@example.com", "Clase B");
        AlumnoTabla a2 = new AlumnoTabla("Carlos", "carlos@example.com", "Clase B");

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    public void testToString() {
        AlumnoTabla alumno = new AlumnoTabla("Eva", "eva@example.com", "Clase C");
        String str = alumno.toString();

        assertTrue(str.contains("Eva"));
        assertTrue(str.contains("eva@example.com"));
        assertTrue(str.contains("Clase C"));
    }
}

