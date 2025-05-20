package pat.proyectofinal.repositories;

import org.springframework.data.repository.CrudRepository;
import pat.proyectofinal.entity.Token;
import pat.proyectofinal.entity.Usuario;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, String> {
    public Optional<Token> findByUsuario(Usuario usuario);
}
