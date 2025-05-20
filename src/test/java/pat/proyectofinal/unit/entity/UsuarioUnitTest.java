package pat.proyectofinal.unit.entity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pat.proyectofinal.entity.Clase;
import pat.proyectofinal.entity.Usuario;
import pat.proyectofinal.model.Role;

public class UsuarioUnitTest {

        @Test
        public void testConstructorYGetters() {
            Clase clase = new Clase(); // puedes simular esta clase como objeto vacío si no hay lógica
            Usuario usuario = new Usuario("Juan", Role.ALUMNO, "juan@example.com", "1234", clase);

            assertEquals("Juan", usuario.getNombre());
            assertEquals(Role.ALUMNO, usuario.getRole());
            assertEquals("juan@example.com", usuario.getEmail());
            assertEquals("1234", usuario.getPassword());
            assertEquals(clase, usuario.getClase());
        }

        @Test
        public void testSetters() {
            Usuario usuario = new Usuario();
            Clase clase = new Clase();

            usuario.setNombre("Ana");
            usuario.setRole(Role.ADMINISTRADOR);
            usuario.setEmail("ana@example.com");
            usuario.setPassword("adminpass");
            usuario.setClase(clase);

            assertEquals("Ana", usuario.getNombre());
            assertEquals(Role.ADMINISTRADOR, usuario.getRole());
            assertEquals("ana@example.com", usuario.getEmail());
            assertEquals("adminpass", usuario.getPassword());
            assertEquals(clase, usuario.getClase());
        }

        @Test
        public void testValoresNulos() {
            Usuario usuario = new Usuario();

            usuario.setNombre(null);
            usuario.setEmail(null);
            usuario.setPassword(null);
            usuario.setClase(null);
            usuario.setRole(null);

            assertNull(usuario.getNombre());
            assertNull(usuario.getEmail());
            assertNull(usuario.getPassword());
            assertNull(usuario.getClase());
            assertNull(usuario.getRole());
        }
    }

