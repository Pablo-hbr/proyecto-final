package pat.proyectofinal.unit.model;



import org.junit.jupiter.api.Test;
import pat.proyectofinal.model.Idioma;

import static org.junit.jupiter.api.Assertions.*;

public class IdiomaUnitTest {

    @Test
    public void testEnumValues() {
        assertEquals("INGLES", Idioma.INGLES.name());
        assertEquals("FRANCES", Idioma.FRANCES.name());
        assertEquals("ALEMAN", Idioma.ALEMAN.name());
        assertEquals("ITALIANO", Idioma.ITALIANO.name());
    }

    @Test
    public void testValueOf() {
        assertEquals(Idioma.INGLES, Idioma.valueOf("INGLES"));
        assertEquals(Idioma.FRANCES, Idioma.valueOf("FRANCES"));
    }

    @Test
    public void testValuesContainsAll() {
        Idioma[] valores = Idioma.values();
        assertTrue(java.util.Arrays.asList(valores).contains(Idioma.INGLES));
        assertTrue(java.util.Arrays.asList(valores).contains(Idioma.FRANCES));
        assertTrue(java.util.Arrays.asList(valores).contains(Idioma.ALEMAN));
        assertTrue(java.util.Arrays.asList(valores).contains(Idioma.ITALIANO));
    }
}
