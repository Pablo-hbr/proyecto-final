package pat.proyectofinal.integration.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pat.proyectofinal.entity.Clase;
import pat.proyectofinal.entity.Token;
import pat.proyectofinal.entity.Usuario;
import pat.proyectofinal.model.Idioma;
import pat.proyectofinal.model.Perfil;
import pat.proyectofinal.model.PeticionRegistro;
import pat.proyectofinal.model.Role;
import pat.proyectofinal.repositories.ClaseRepository;
import pat.proyectofinal.repositories.TokenRepository;
import pat.proyectofinal.repositories.UsuarioRepository;
import pat.proyectofinal.service.IdiomasService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IdiomasServiceUnitTest {

    private UsuarioRepository usuarioRepository;
    private TokenRepository tokenRepository;
    private ClaseRepository claseRepository;
    private IdiomasService service;

    @BeforeEach
    public void setup() {
        usuarioRepository = mock(UsuarioRepository.class);
        tokenRepository = mock(TokenRepository.class);
        claseRepository = mock(ClaseRepository.class);

        service = new IdiomasService();
        service.usuarioRepository = usuarioRepository;
        service.tokenRepository = tokenRepository;
        service.claseRepository = claseRepository;
    }

    //TODOS LOS TEST DE LOGIN

    @Test
    public void testLoginSuccess_UsuarioYaTieneToken() {
        Usuario user = new Usuario("Ana", null, "ana@mail.com", "1234", null);
        Token token = new Token();
        token.usuario = user;

        when(usuarioRepository.findByEmail("ana@mail.com")).thenReturn(Optional.of(user));
        when(tokenRepository.findByUsuario(user)).thenReturn(Optional.of(token));

        Token result = service.login("ana@mail.com", "1234");

        assertNotNull(result);
        assertEquals(user, result.usuario);
    }

    @Test
    public void testLoginSuccess_UsuarioSinToken() {
        Usuario user = new Usuario("Ana", null, "ana@mail.com", "1234", null);
        when(usuarioRepository.findByEmail("ana@mail.com")).thenReturn(Optional.of(user));
        when(tokenRepository.findByUsuario(user)).thenReturn(Optional.empty());
        when(tokenRepository.save(any(Token.class))).thenAnswer(inv -> inv.getArgument(0));

        Token result = service.login("ana@mail.com", "1234");

        assertNotNull(result);
        assertEquals(user, result.usuario);
    }

    @Test
    public void testLoginFails_EmailNoExiste() {
        when(usuarioRepository.findByEmail("no@existe.com")).thenReturn(Optional.empty());

        Token result = service.login("no@existe.com", "1234");
        assertNull(result);
    }

    @Test
    public void testLoginFails_PasswordIncorrecta() {
        Usuario user = new Usuario("Ana", null, "ana@mail.com", "1234", null);
        when(usuarioRepository.findByEmail("ana@mail.com")).thenReturn(Optional.of(user));

        Token result = service.login("ana@mail.com", "wrongpass");
        assertNull(result);
    }


    //TODOS LOS TEST DE AUTENTICACIÓN
    @Test
    public void testAuthenticationTokenValido() {
        Usuario user = new Usuario("Carlos", null, "carlos@mail.com", "pass", null);
        Token token = new Token();
        token.usuario = user;

        when(tokenRepository.findById("token123")).thenReturn(Optional.of(token));

        Usuario result = service.authentication("token123");

        assertNotNull(result);
        assertEquals("Carlos", result.getNombre());
        assertEquals("carlos@mail.com", result.getEmail());
    }

    @Test
    public void testAuthenticationTokenInvalido() {
        when(tokenRepository.findById("tokenInvalido")).thenReturn(Optional.empty());

        Usuario result = service.authentication("tokenInvalido");

        assertNull(result);
    }

    //TODOS LOS TEST PARA PROFILE
    @Test
    public void testProfileConClase() {
        Clase clase = new Clase(20, "Clase A", Idioma.FRANCES);
        Usuario usuario = new Usuario("Laura", Role.ALUMNO, "laura@mail.com", "pass", clase);

        Perfil perfil = service.profile(usuario);

        assertNotNull(perfil);
        assertEquals("Laura", perfil.nombre());
        assertEquals("laura@mail.com", perfil.email());
        assertEquals(Role.ALUMNO, perfil.role());
        assertEquals("Clase A", perfil.clase());
        assertEquals("FRANCES", perfil.idioma());  // es un enum → se espera toString()
    }

    @Test
    public void testProfileSinClase() {
        Usuario usuario = new Usuario("Mario", Role.ADMINISTRADOR, "mario@mail.com", "admin", null);

        Perfil perfil = service.profile(usuario);

        assertNotNull(perfil);
        assertEquals("Mario", perfil.nombre());
        assertEquals("mario@mail.com", perfil.email());
        assertEquals(Role.ADMINISTRADOR, perfil.role());
        assertNull(perfil.clase());
        assertNull(perfil.idioma());
    }

    //TODOS LOS TEST QUE TIENEN QUE VER CON EL REGISTRO

    @Test
    public void testRegistrar_UsuarioYaExiste() {
        PeticionRegistro registro = new PeticionRegistro("Juan", "juan@mail.com", "Clave123", 1L);
        when(usuarioRepository.findByEmail("juan@mail.com")).thenReturn(Optional.of(new Usuario()));

        Perfil perfil = service.registrar(registro);

        assertNull(perfil);
    }

    @Test
    public void testRegistrar_ClaseNoExiste() {
        PeticionRegistro registro = new PeticionRegistro("Juan", "juan@mail.com", "Clave123", 1L);
        when(usuarioRepository.findByEmail("juan@mail.com")).thenReturn(Optional.empty());
        when(claseRepository.findById(1L)).thenReturn(Optional.empty());

        Perfil perfil = service.registrar(registro);

        assertNull(perfil);
    }

    @Test
    public void testRegistrar_ClaseSinAforo() {
        Clase clase = new Clase(0, "Clase Sin Cupo", Idioma.INGLES);
        PeticionRegistro registro = new PeticionRegistro("Juan", "juan@mail.com", "Clave123", 1L);

        when(usuarioRepository.findByEmail("juan@mail.com")).thenReturn(Optional.empty());
        when(claseRepository.findById(1L)).thenReturn(Optional.of(clase));

        Perfil perfil = service.registrar(registro);

        assertNull(perfil);
    }

    @Test
    public void testRegistrar_Correctamente() {
        Clase clase = new Clase(10, "Clase Disponible", Idioma.ITALIANO);
        PeticionRegistro registro = new PeticionRegistro("Elena", "elena@mail.com", "Clave123", 1L);

        when(usuarioRepository.findByEmail("elena@mail.com")).thenReturn(Optional.empty());
        when(claseRepository.findById(1L)).thenReturn(Optional.of(clase));
        when(claseRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(usuarioRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Perfil perfil = service.registrar(registro);

        assertNotNull(perfil);
        assertEquals("Elena", perfil.nombre());
        assertEquals("elena@mail.com", perfil.email());
        assertEquals("Clase Disponible", perfil.clase());
        assertEquals("ITALIANO", perfil.idioma());

        // Verifica que el aforo se redujo
        assertEquals(9, clase.getAforo());
    }
    @Test
    public void testLogoutConTokenValido() {
        String tokenId = "token123";

        service.logout(tokenId);

        verify(tokenRepository, times(1)).deleteById(tokenId);
    }

    @Test
    public void testLogoutConTokenVacio() {
        service.logout("");
        service.logout("   ");
        service.logout(null);

        verify(tokenRepository, never()).deleteById(anyString());
    }




}


