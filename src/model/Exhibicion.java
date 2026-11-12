package model;

import java.time.LocalDate;
import java.util.List;

public class Exhibicion implements Programable {
    private final String titulo;
    private EstadoExhibicion estado;
    private LocalDate fecha;
    private Integer hora;
    private String sede;

    public Exhibicion(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la exhibición no puede estar vacío.");
        }
        this.titulo = titulo.trim();
        estado = EstadoExhibicion.SIN_PROGRAMAR;
        fecha = null;
        hora = null;
        sede = null;
    }

    @Override
    public void programar(LocalDate fecha, int hora, String sede) {
        if (fecha == null) {
            throw new IllegalArgumentException("Debe indicar una fecha válida.");
        }
        if (hora < 0 || hora > 23) {
            throw new IllegalArgumentException("La hora debe ser un entero de 0 a 23.");
        }
        if (sede == null || sede.trim().isEmpty()) {
            throw new IllegalArgumentException("La sede no puede estar vacía.");
        }
        this.fecha = fecha;
        this.hora = hora;
        this.sede = sede.trim();
        estado = EstadoExhibicion.PROGRAMADA;
    }

    @Override
    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public Integer getHora() {
        return hora;
    }

    @Override
    public String getSede() {
        return sede;
    }

    @Override
    public List<Equipo> getEquipos() {
        return List.of();
    }

    @Override
    public String getDescripcion() {
        return "Exhibición | " + titulo + " | " + estado;
    }
}
