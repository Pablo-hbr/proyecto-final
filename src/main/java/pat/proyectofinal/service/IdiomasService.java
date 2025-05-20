package pat.proyectofinal.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pat.proyectofinal.entity.Clase;
import pat.proyectofinal.model.Idioma;
import pat.proyectofinal.entity.Usuario;
import pat.proyectofinal.model.AlumnoTabla;
import pat.proyectofinal.model.Perfil;
import pat.proyectofinal.model.PeticionRegistro;
import pat.proyectofinal.model.Role;
import pat.proyectofinal.repositories.ClaseRepository;
import pat.proyectofinal.repositories.TokenRepository;
import pat.proyectofinal.repositories.UsuarioRepository;
import pat.proyectofinal.entity.Token;

import java.util.List;
import java.util.Optional;

@Service
public class IdiomasService {
    @Autowired
    public ClaseRepository claseRepository;
    @Autowired
    public UsuarioRepository usuarioRepository;
    @Autowired
    public TokenRepository tokenRepository;

    public Token login(String email, String password) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isEmpty()) return null;
        Usuario usuario = usuarioOptional.get();

        if (!password.equals(usuario.getPassword())) return null;

        Optional<Token> tokenO = tokenRepository.findByUsuario(usuario);
        if (tokenO.isPresent()) return tokenO.get();

        Token token = new Token();
        token.usuario = usuario;

        return tokenRepository.save(token);
    }

    public Usuario authentication(String tokenId)
    {
        Optional<Token> tokeno = tokenRepository.findById(tokenId);
        if(tokeno.isEmpty()){
            return null;
        }
        return tokeno.get().usuario;
    }

    public Perfil profile(Usuario usuario) {
        if (usuario.getClase() == null) return new Perfil(usuario.getNombre(),usuario.getEmail(),usuario.getRole(),
                null, null);
        return new Perfil(usuario.getNombre(),usuario.getEmail(),usuario.getRole(),
                usuario.getClase().getNombre(), usuario.getClase().getIdioma().toString());
    }


    public Perfil registrar(PeticionRegistro register) {
        if (usuarioRepository.findByEmail(register.email()).isPresent()) {
            return null;
        }
        if (claseRepository.findById(register.idClase()).isEmpty()) {
            return null;
        }

        Clase clase = claseRepository.findById(register.idClase()).get();

        if(clase.getAforo()<1){
            return null;
        }
        clase.setAforo(clase.getAforo()-1);
        claseRepository.save(clase);
        Usuario newUser = new Usuario(register.name(), Role.ALUMNO, register.email(), register.password(), clase);

        usuarioRepository.save(newUser);

        return profile(newUser);
    }

    public void logout(String tokenId) {
        if (!StringUtils.hasText(tokenId)) return;

        tokenRepository.deleteById(tokenId);
    }

    public void delete(Usuario usuario) {
        if (usuario == null) return;
        Clase clase = usuario.getClase();
        clase.setAforo(clase.getAforo()+1);
        claseRepository.save(clase);
        usuarioRepository.delete(usuario);
    }

    public List<AlumnoTabla> datosAdmin() {
        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findByRole(Role.ALUMNO); // devuelve lista
        return usuarios.stream()
                .map(usuario -> new AlumnoTabla(
                        usuario.getNombre(),
                        usuario.getEmail(),
                        usuario.getClase() != null ? usuario.getClase().getNombre() : "Sin clase"
                ))
                .toList();
    }

    @PostConstruct
    public void crearAdminSiNoExiste() {
        if (usuarioRepository.findByEmail("admin@admin.com").isEmpty()) {
            Usuario admin = new Usuario(
                    "Administrador",
                    Role.ADMINISTRADOR,
                    "admin@admin.com",
                    "admin",
                    null
            );
            usuarioRepository.save(admin);
        }
        if (claseRepository.findByNombre("Francés").isEmpty()) {
            Clase frances = new Clase();
            frances.setNombre("Francés");
            frances.setAforo(30); // Puedes ajustar el aforo según lo que necesites
            frances.setIdioma(Idioma.FRANCES);
            claseRepository.save(frances);
        }
    }

    public List<Clase> listarClases() {
        return (List<Clase>) claseRepository.findAll();
    }

    public Clase crearClase(Clase clase) {
        return claseRepository.save(clase);
    }

}
