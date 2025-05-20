package pat.proyectofinal.unit.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pat.proyectofinal.entity.Usuario;
import pat.proyectofinal.model.Role;
import pat.proyectofinal.repositories.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testFindByEmail() {
        Usuario user = new Usuario("Maria", Role.ALUMNO, "maria@mail.com", "pass123", null);
        usuarioRepository.save(user);

        Optional<Usuario> encontrado = usuarioRepository.findByEmail("maria@mail.com");

        assertTrue(encontrado.isPresent());
        assertEquals("Maria", encontrado.get().getNombre());
    }

    @Test
    public void testFindByEmail_NoExiste() {
        Optional<Usuario> encontrado = usuarioRepository.findByEmail("no@existe.com");
        assertFalse(encontrado.isPresent());
    }
}
