package pat.proyectofinal.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false) //Se usa para indicar que clave de la BD actual se usa para unir con la relación de antes a la otra BD
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Usuario usuario;


}

