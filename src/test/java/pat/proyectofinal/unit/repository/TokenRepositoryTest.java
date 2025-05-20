package pat.proyectofinal.unit.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pat.proyectofinal.entity.Token;
import pat.proyectofinal.entity.Usuario;
import pat.proyectofinal.model.Role;
import pat.proyectofinal.repositories.TokenRepository;
import pat.proyectofinal.repositories.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testFindByUsuario() {
        Usuario usuario = new Usuario("Pepe", Role.ALUMNO, "pepe@mail.com", "1234", null);
        usuario = usuarioRepository.save(usuario);

        Token token = new Token();
        token.usuario = usuario;
        token = tokenRepository.save(token);

        Optional<Token> encontrado = tokenRepository.findByUsuario(usuario);
        assertTrue(encontrado.isPresent());
        assertEquals(usuario.getEmail(), encontrado.get().usuario.getEmail());
    }

    @Test
    public void testFindByUsuario_NoExiste() {
        Usuario usuario = new Usuario("Ghost", Role.ADMINISTRADOR, "ghost@mail.com", "xyz", null);
        usuario = usuarioRepository.save(usuario);

        Optional<Token> encontrado = tokenRepository.findByUsuario(usuario);
        assertFalse(encontrado.isPresent());
    }
}

