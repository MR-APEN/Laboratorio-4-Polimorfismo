package model;

import java.time.LocalDate;
import java.util.List;

public abstract class Encuentro implements Programable, Puntuable {
    private final Equipo equipoA;
    private final Equipo equipoB;
    private Integer marcadorA;
    private Integer marcadorB;
    private EstadoEncuentro estado;
    private LocalDate fecha;
    private Integer hora;
    private String sede;

    protected Encuentro(Equipo equipoA, Equipo equipoB) {
        if (equipoA == null || equipoB == null) {
            throw new IllegalArgumentException("Debe seleccionar dos equipos.");
        }
        if (equipoA.getCodigo().equalsIgnoreCase(equipoB.getCodigo())) {
            throw new IllegalArgumentException("Un equipo no puede jugar contra sí mismo.");
        }
        this.equipoA = equipoA;
        this.equipoB = equipoB;
        marcadorA = null;
        marcadorB = null;
        estado = EstadoEncuentro.SIN_PROGRAMAR;
        fecha = null;
        hora = null;
        sede = null;
    }

    @Override
    public final void programar(LocalDate fecha, int hora, String sede) {
        if (estado == EstadoEncuentro.FINALIZADO) {
            throw new IllegalStateException("Un encuentro finalizado no puede reprogramarse.");
        }
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
        estado = EstadoEncuentro.PROGRAMADO;
    }

    @Override
    public final void registrarResultado(int marcadorA, int marcadorB) {
        if (estado == EstadoEncuentro.SIN_PROGRAMAR) {
            throw new IllegalStateException("Debe programar el encuentro antes de registrar su resultado.");
        }
        if (estado == EstadoEncuentro.FINALIZADO) {
            throw new IllegalStateException("Este encuentro ya tiene un resultado registrado.");
        }
        if (marcadorA < 0 || marcadorB < 0) {
            throw new IllegalArgumentException("El marcador debe contener enteros no negativos.");
        }
        if (!esMarcadorValido(marcadorA, marcadorB)) {
            throw new IllegalArgumentException("El marcador " + marcadorA + " - " + marcadorB
                    + " no es válido para " + nombreDeporte() + ".");
        }

        int puntosA = calcularPuntos(marcadorA, marcadorB);
        int puntosB = calcularPuntos(marcadorB, marcadorA);
        if (puntosA < 0 || puntosB < 0) {
            throw new IllegalArgumentException("Los puntos de tabla no pueden ser negativos.");
        }

        this.marcadorA = marcadorA;
        this.marcadorB = marcadorB;
        equipoA.sumarPuntos(puntosA);
        equipoB.sumarPuntos(puntosB);
        estado = EstadoEncuentro.FINALIZADO;
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
        return List.of(equipoA, equipoB);
    }

    @Override
    public String getDescripcion() {
        String descripcion = nombreDeporte() + " | " + equipoA.getNombre()
                + " vs " + equipoB.getNombre() + " | " + estado;
        if (estado == EstadoEncuentro.FINALIZADO) {
            descripcion += " | Marcador: " + marcadorA + " - " + marcadorB;
        }
        return descripcion;
    }

    protected abstract String nombreDeporte();
    protected abstract boolean esMarcadorValido(int a, int b);
    protected abstract int calcularPuntos(int propios, int contrarios);
}
