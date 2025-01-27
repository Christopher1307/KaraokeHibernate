package aed.db;

import jakarta.persistence.*;

@Entity
@Table(name = "karaokelog")
public class KaraokeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLog;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idCancion", nullable = false)
    private Cancion cancion;

    @Column(name = "fechaRepro")
    private String fechaRepro;

    // Getters y Setters
    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    public String getFechaRepro() {
        return fechaRepro;
    }

    public void setFechaRepro(String fechaRepro) {
        this.fechaRepro = fechaRepro;
    }
}