package pat.proyectofinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pat.proyectofinal.model.Perfil;
import pat.proyectofinal.model.PeticionLogin;
import pat.proyectofinal.entity.Token;
import pat.proyectofinal.entity.Usuario;
import pat.proyectofinal.model.PeticionRegistro;
import pat.proyectofinal.model.Role;
import pat.proyectofinal.service.IdiomasService;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import jakarta.servlet.http.Cookie;




@WebMvcTest(IdiomasController.class)

    public class IdiomasControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private IdiomasService idiomasService;

        @Autowired
        private ObjectMapper objectMapper;

        //Empezamos con el test de login (/users/me/session)

        @Test
        public void testLoginSuccess() throws Exception {
            PeticionLogin login = new PeticionLogin("user@example.com", "password123");
            Token token = new Token();
            token.id = "abc123";

            when(idiomasService.login("user@example.com", "password123")).thenReturn(token);

            mockMvc.perform(post("/api/users/me/session")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(login)))
                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Set-Cookie"));
        }

        @Test
        public void testLoginUnauthorized() throws Exception {
            PeticionLogin login = new PeticionLogin("user@example.com", "wrongpass");

            when(idiomasService.login("user@example.com", "wrongpass")).thenReturn(null);

            mockMvc.perform(post("/api/users/me/session")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(login)))
                    .andExpect(status().isUnauthorized());
        }

        //Test para registrar usuario (POST /api/users)
        @Test
        public void testRegistrarUsuario_Correcto() throws Exception {
            Perfil perfil = new Perfil("Ana", "ana@mail.com", Role.ALUMNO, "Clase A", "INGLES");
            PeticionRegistro registro = new PeticionRegistro("Ana", "ana@mail.com", "Clave123", 1L);

            when(idiomasService.registrar(any())).thenReturn(perfil);

            mockMvc.perform(post("/api/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registro)))
                    .andExpect(status().isCreated());
        }

        @Test
        public void testRegistrarUsuario_Conflict() throws Exception {
            PeticionRegistro registro = new PeticionRegistro("Ana", "ana@mail.com", "Clave123", 1L);

            when(idiomasService.registrar(any())).thenThrow(new org.springframework.dao.DataIntegrityViolationException("duplicado"));

            mockMvc.perform(post("/api/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registro)))
                    .andExpect(status().isConflict());
        }

        //Test para ver perfil (GET /api/users/me)
        @Test
        public void testPerfilAutenticado() throws Exception {
            Usuario usuario = new Usuario("Luis", Role.ALUMNO, "luis@mail.com", "pass", null);
            Perfil perfil = new Perfil("Luis", "luis@mail.com", Role.ALUMNO, null, null);

            when(idiomasService.authentication("token123")).thenReturn(usuario);
            when(idiomasService.profile(usuario)).thenReturn(perfil);

            mockMvc.perform(get("/api/users/me").cookie(new Cookie("session", "token123")))
                    .andExpect(status().isOk());
        }

        @Test
        public void testPerfilNoAutenticado() throws Exception {
            when(idiomasService.authentication("token123")).thenReturn(null);

            mockMvc.perform(get("/api/users/me").cookie(new Cookie("session", "token123")))
                    .andExpect(status().isUnauthorized());
        }


    //Test para logout (DELETE /api/users/me/session)
        @Test
        public void testLogoutCorrecto() throws Exception {
            Usuario usuario = new Usuario("Maria", Role.ADMINISTRADOR, "maria@mail.com", "clave", null);

            when(idiomasService.authentication("token456")).thenReturn(usuario);
            doNothing().when(idiomasService).logout("token456");

            mockMvc.perform(delete("/api/users/me/session").cookie(new Cookie("session", "token456")))
                    .andExpect(status().isNoContent())
                    .andExpect(header().exists("Set-Cookie"));
        }

        @Test
        public void testLogoutSinAutenticacion() throws Exception {
            when(idiomasService.authentication("token456")).thenReturn(null);

            mockMvc.perform(delete("/api/users/me/session").cookie(new Cookie("session", "token456")))
                    .andExpect(status().isUnauthorized());
        }

    }


