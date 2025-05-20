package pat.proyectofinal.repositories;

import org.springframework.data.repository.CrudRepository;
import pat.proyectofinal.entity.Clase;

import java.util.Optional;

public interface ClaseRepository extends CrudRepository<Clase, Long> {
    Optional<Clase> findByNombre(String nombre);
}
