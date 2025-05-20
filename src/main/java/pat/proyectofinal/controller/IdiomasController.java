package pat.proyectofinal.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pat.proyectofinal.entity.Token;
import pat.proyectofinal.entity.Usuario;
import pat.proyectofinal.entity.Clase;
import pat.proyectofinal.model.*;
import pat.proyectofinal.service.IdiomasService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IdiomasController {
    @Autowired
    private IdiomasService idiomasService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Perfil registrar(@Valid @RequestBody PeticionRegistro registro) {
        try {
            return idiomasService.registrar(registro);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PostMapping("/users/me/session")
    public ResponseEntity<Void> login(@Valid @RequestBody PeticionLogin credentials) {
        Token token = idiomasService.login(credentials.email(), credentials.password());
        if (token == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        ResponseCookie session = ResponseCookie
                .from("session", token.id)
                .httpOnly(true)
                .path("/")
                .sameSite("Strict")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.SET_COOKIE, session.toString()).build();
    }

    @DeleteMapping("/users/me/session")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> logout(@CookieValue(value = "session", required = true) String session) {
        Usuario appUser = idiomasService.authentication(session);
        if (appUser == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        idiomasService.logout(session);
        ResponseCookie expireSession = ResponseCookie
                .from("session")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).header(HttpHeaders.SET_COOKIE, expireSession.toString()).build();
    }

    @GetMapping("/users/me")
    @ResponseStatus(HttpStatus.OK)
    public Perfil profile(@CookieValue(value = "session", required = true) String session) {
        Usuario usuario = idiomasService.authentication(session);
        if (usuario == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return idiomasService.profile(usuario);
    }

    @DeleteMapping("/users/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@CookieValue(value = "session", required = true) String session) {
        Usuario usuario = idiomasService.authentication(session);
        if (usuario == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        idiomasService.delete(usuario);
    }


    @GetMapping("/admin/datos")
    @ResponseStatus(HttpStatus.OK)
    public List<AlumnoTabla> listaClases(@CookieValue(value = "session", required = true) String session) {
        Usuario usuario = idiomasService.authentication(session);
        if (usuario == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (!usuario.getRole().equals(Role.ADMINISTRADOR)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return idiomasService.datosAdmin();
    }

    @GetMapping("/clases")
    @ResponseStatus(HttpStatus.OK)
    public List<Clase> listarClases() {
        return idiomasService.listarClases();
    }

    @PostMapping("/clases")
    @ResponseStatus(HttpStatus.CREATED)
    public Clase crearClase(
            @CookieValue(value = "session", required = true) String session,
            @Valid @RequestBody Clase nuevaClase
    ) {
        Usuario usuario = idiomasService.authentication(session);
        if (usuario == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (!usuario.getRole().equals(Role.ADMINISTRADOR)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return idiomasService.crearClase(nuevaClase);
    }



}
