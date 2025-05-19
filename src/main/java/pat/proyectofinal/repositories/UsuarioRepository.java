package pat.proyectofinal.repositories;

import org.springframework.data.repository.CrudRepository;
import pat.proyectofinal.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
