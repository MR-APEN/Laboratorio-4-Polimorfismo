package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Copa implements Iterable<Programable> {
    private final List<Equipo> equipos;
    private final List<Programable> eventos;
    private final List<Puntuable> encuentros;

    public Copa() {
        equipos = new ArrayList<>();
        eventos = new ArrayList<>();
        encuentros = new ArrayList<>();
    }

    public Equipo registrarEquipo(String codigo, String nombre, String empresa) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del equipo no puede estar vacío.");
        }
        for (Equipo equipo : equipos) {
            if (equipo.getCodigo().equalsIgnoreCase(codigo.trim())) {
                throw new IllegalArgumentException("Ya existe un equipo con ese código.");
            }
        }
        Equipo nuevo = new Equipo(codigo, nombre, empresa);
        equipos.add(nuevo);
        return nuevo;
    }

    public Equipo buscarEquipo(String codigo) {
        if (codigo != null) {
            for (Equipo equipo : equipos) {
                if (equipo.getCodigo().equalsIgnoreCase(codigo.trim())) {
                    return equipo;
                }
            }
        }
        throw new IllegalArgumentException("No existe un equipo con el código indicado.");
    }

    public void agregarEvento(Programable evento) {
        if (evento == null) {
            throw new IllegalArgumentException("Debe indicar un evento.");
        }
        if (eventos.contains(evento)) {
            throw new IllegalArgumentException("Este evento ya está registrado.");
        }
        for (Equipo equipo : evento.getEquipos()) {
            if (!equipos.contains(equipo)) {
                throw new IllegalArgumentException("Los equipos deben estar registrados en la copa.");
            }
        }
        if (evento.getFecha() != null) {
            validarProgramacion(evento, evento.getFecha(), evento.getHora(), evento.getSede());
        }
        eventos.add(evento);
    }

    public void agregarEncuentro(Encuentro encuentro) {
        if (encuentro == null) {
            throw new IllegalArgumentException("Debe indicar un encuentro.");
        }
        if (encuentros.contains(encuentro)) {
            throw new IllegalArgumentException("Este encuentro ya está registrado.");
        }
        agregarEvento(encuentro);
        encuentros.add(encuentro);
    }

    public void programarEvento(Programable evento, LocalDate fecha, int hora, String sede) {
        if (!eventos.contains(evento)) {
            throw new IllegalArgumentException("El evento no está registrado en la copa.");
        }
        validarProgramacion(evento, fecha, hora, sede);
        evento.programar(fecha, hora, sede);
    }

    private void validarProgramacion(Programable evento, LocalDate fecha, int hora, String sede) {
        if (fecha == null) {
            throw new IllegalArgumentException("Debe indicar una fecha válida.");
        }
        if (hora < 0 || hora > 23) {
            throw new IllegalArgumentException("La hora debe ser un entero de 0 a 23.");
        }
        if (sede == null || sede.trim().isEmpty()) {
            throw new IllegalArgumentException("La sede no puede estar vacía.");
        }
        for (Programable otro : eventos) {
            if (otro == evento || otro.getFecha() == null) {
                continue;
            }
            if (fecha.equals(otro.getFecha()) && hora == otro.getHora()) {
                if (sede.trim().equalsIgnoreCase(otro.getSede())) {
                    throw new IllegalArgumentException("La sede ya está ocupada en esa fecha y hora.");
                }
                for (Equipo equipo : evento.getEquipos()) {
                    if (otro.getEquipos().contains(equipo)) {
                        throw new IllegalArgumentException("El equipo " + equipo.getNombre()
                                + " ya tiene otro encuentro en esa fecha y hora.");
                    }
                }
            }
        }
    }

    public void registrarResultado(Puntuable encuentro, int a, int b) {
        if (!encuentros.contains(encuentro)) {
            throw new IllegalArgumentException("El encuentro no está registrado en la copa.");
        }
        encuentro.registrarResultado(a, b);
    }

    public List<Programable> getEventos() {
        return List.copyOf(eventos);
    }

    public List<Puntuable> getEncuentros() {
        return List.copyOf(encuentros);
    }

    public List<Equipo> getTablaPosiciones() {
        List<Equipo> tabla = new ArrayList<>(equipos);
        Collections.sort(tabla);
        return List.copyOf(tabla);
    }

    @Override
    public Iterator<Programable> iterator() {
        List<Programable> calendario = new ArrayList<>();
        for (Programable evento : eventos) {
            if (evento.getFecha() != null) {
                calendario.add(evento);
            }
        }
        Collections.sort(calendario);
        return List.copyOf(calendario).iterator();
    }
}
