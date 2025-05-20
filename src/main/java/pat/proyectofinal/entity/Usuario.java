package pat.proyectofinal.entity;
import jakarta.persistence.*;
import pat.proyectofinal.model.Role;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Role role;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "clase_id")
    private Clase clase;

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    public Usuario() {
    }

    public Usuario(String nombre, Role role, String email, String password, Clase clase) {
        this.nombre = nombre;
        this.role = role;
        this.email = email;
        this.password = password;
        this.clase = clase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
