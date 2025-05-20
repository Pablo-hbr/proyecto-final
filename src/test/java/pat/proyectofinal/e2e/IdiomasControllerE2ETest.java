package pat.proyectofinal.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pat.proyectofinal.controller.IdiomasController;
import pat.proyectofinal.entity.Token;
import pat.proyectofinal.entity.Usuario;
import pat.proyectofinal.model.*;
import pat.proyectofinal.service.IdiomasService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IdiomasController.class)
public class IdiomasControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IdiomasService idiomasService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegistroLoginYPerfil() throws Exception {
        // Paso 1: Registro
        PeticionRegistro registro = new PeticionRegistro("Ana", "ana@mail.com", "Clave123A", 1L);
        Perfil perfil = new Perfil("Ana", "ana@mail.com", Role.ALUMNO, "Clase A", "INGLES");

        when(idiomasService.registrar(any())).thenReturn(perfil);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registro)))
                .andExpect(status().isCreated());

        // Paso 2: Login
        PeticionLogin login = new PeticionLogin("ana@mail.com", "Clave123A");
        Token token = new Token();
        token.id = "token123";

        when(idiomasService.login("ana@mail.com", "Clave123A")).thenReturn(token);

        mockMvc.perform(post("/api/users/me/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Set-Cookie"));

        // Paso 3: Perfil y cookie
        Usuario usuario = new Usuario("Ana", Role.ALUMNO, "ana@mail.com", "Clave123A", null);
        when(idiomasService.authentication("token123")).thenReturn(usuario);
        when(idiomasService.profile(usuario)).thenReturn(perfil);

        mockMvc.perform(get("/api/users/me")
                        .cookie(new Cookie("session", "token123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ana"))
                .andExpect(jsonPath("$.email").value("ana@mail.com"))
                .andExpect(jsonPath("$.role").value("ALUMNO"));
    }

    @Test
    public void testLoginYLogout() throws Exception {
        // Paso 1: Login
        PeticionLogin login = new PeticionLogin("juan@mail.com", "ClaveSegura1");
        Token token = new Token();
        token.id = "token456";

        when(idiomasService.login("juan@mail.com", "ClaveSegura1")).thenReturn(token);

        mockMvc.perform(post("/api/users/me/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Set-Cookie"));

        // Paso 2: Logout
        Usuario usuario = new Usuario("Juan", Role.ALUMNO, "juan@mail.com", "ClaveSegura1", null);
        when(idiomasService.authentication("token456")).thenReturn(usuario);
        doNothing().when(idiomasService).logout("token456");

        mockMvc.perform(delete("/api/users/me/session")
                        .cookie(new Cookie("session", "token456")))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Set-Cookie")); // Se espera que se borre la cookie
    }

    @Test
    public void testRegistroFallidoPorValidacion() throws Exception {
        // Email inválido y contraseña débil (menos de 8 caracteres, sin mayúsculas)
        PeticionRegistro registroInvalido = new PeticionRegistro("Ana", "correoSinArroba", "123", 1L);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registroInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLoginFallidoPorCamposInvalidos() throws Exception {
        // Email sin @ y contraseña vacía (violando @Email y @NotBlank)
        PeticionLogin loginInvalido = new PeticionLogin("usuarioSinCorreo", " ");

        mockMvc.perform(post("/api/users/me/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginInvalido)))
                .andExpect(status().isBadRequest());
    }

}

