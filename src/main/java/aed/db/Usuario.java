package aed.db;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    public Usuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(name = "nombreUsuario")
    private String nombreUsuario;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KaraokeLog> logs;

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public List<KaraokeLog> getLogs() {
        return logs;
    }

    public void setLogs(List<KaraokeLog> logs) {
        this.logs = logs;
    }
}
