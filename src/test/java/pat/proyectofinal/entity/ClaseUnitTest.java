package pat.proyectofinal.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pat.proyectofinal.model.Idioma;


public class ClaseUnitTest {

    @Test
    public void testConstructorYGetters() {
        Clase clase = new Clase(30, "Inglés Básico", Idioma.INGLES);

        assertEquals(30, clase.getAforo());
        assertEquals("Inglés Básico", clase.getNombre());
        assertEquals(Idioma.INGLES, clase.getIdioma());
    }

    @Test
    public void testSetters() {
        Clase clase = new Clase();

        clase.setAforo(20);
        clase.setNombre("Francés Intermedio");
        clase.setIdioma(Idioma.FRANCES);

        assertEquals(20, clase.getAforo());
        assertEquals("Francés Intermedio", clase.getNombre());
        assertEquals(Idioma.FRANCES, clase.getIdioma());
    }

    @Test
    public void testValoresNulos() {
        Clase clase = new Clase();

        clase.setNombre(null);
        clase.setIdioma(null);

        assertNull(clase.getNombre());
        assertNull(clase.getIdioma());
    }
}


