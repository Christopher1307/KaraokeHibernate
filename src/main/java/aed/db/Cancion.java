package aed.db;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "canciones")
public class Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCancion;

    @Column(name = "nombreCancion")
    private String nombreCancion;

    @Column(name = "nombreAutor")
    private String nombreAutor;

    @Column(name = "vecesCantada")
    private Integer vecesCantada;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "tiempo") // Nueva columna para tiempo en segundos
    private Integer tiempo;

    @OneToMany(mappedBy = "cancion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KaraokeLog> logs;

    // Getters y Setters
    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Integer getVecesCantada() {
        return vecesCantada;
    }

    public void setVecesCantada(Integer vecesCantada) {
        this.vecesCantada = vecesCantada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    public List<KaraokeLog> getLogs() {
        return logs;
    }

    public void setLogs(List<KaraokeLog> logs) {
        this.logs = logs;
    }

    @Override
    public String toString() {
        return nombreCancion + " - " + nombreAutor;
    }
}
