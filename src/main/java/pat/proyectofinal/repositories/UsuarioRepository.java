package pat.proyectofinal.repositories;

import org.springframework.data.repository.CrudRepository;
import pat.proyectofinal.entity.Usuario;
import pat.proyectofinal.model.Role;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    public Optional<Usuario> findByEmail(String email);
    public Iterable<Usuario> findByRole(Role role);
}
