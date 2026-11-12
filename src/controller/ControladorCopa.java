package controller;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import model.Baloncesto;
import model.Copa;
import model.Encuentro;
import model.Equipo;
import model.Exhibicion;
import model.FutbolSala;
import model.Programable;
import model.Puntuable;
import view.VistaConsola;

public class ControladorCopa {
    private final Copa copa;
    private final VistaConsola vista;

    public ControladorCopa(Copa copa, VistaConsola vista) {
        this.copa = copa;
        this.vista = vista;
    }

    public void ejecutar() {
        cargarDatosIniciales();
        vista.mostrarMensaje("ArenaGT Lite - Copa entre empresas");
        vista.mostrarMensaje("Datos iniciales cargados: 4 equipos, 4 encuentros y 1 exhibición.");
        boolean continuar = true;
        while (continuar) {
            vista.mostrarMenu();
            try {
                int opcion = vista.leerEntero("Seleccione una opción: ");
                if (opcion == 0) {
                    continuar = false;
                    vista.mostrarMensaje("Programa finalizado.");
                } else {
                    atenderOpcion(opcion);
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                vista.mostrarMensaje("No se pudo realizar la operación: " + e.getMessage());
            } catch (NoSuchElementException e) {
                continuar = false;
                vista.mostrarMensaje("\nEntrada finalizada.");
            }
        }
        vista.cerrar();
    }

    private void cargarDatosIniciales() {
        Equipo faro = copa.registrarEquipo("F01", "Faro", "TecnoGT");
        Equipo sol = copa.registrarEquipo("S01", "Sol", "Servicios del Valle");
        Equipo luna = copa.registrarEquipo("L01", "Luna", "TecnoGT");
        Equipo norte = copa.registrarEquipo("N01", "Norte", "Servicios del Valle");

        Encuentro primero = new FutbolSala(faro, sol);
        Encuentro segundo = new Baloncesto(sol, faro);
        Encuentro tercero = new FutbolSala(luna, norte);
        Encuentro cuarto = new Baloncesto(luna, sol);
        copa.agregarEncuentro(primero);
        copa.agregarEncuentro(segundo);
        copa.agregarEncuentro(tercero);
        copa.agregarEncuentro(cuarto);

        copa.programarEvento(primero, LocalDate.of(2026, 11, 12), 9, "Cancha A");
        copa.programarEvento(segundo, LocalDate.of(2026, 11, 12), 14, "Gimnasio");
        copa.programarEvento(tercero, LocalDate.of(2026, 11, 13), 9, "Cancha A");
        copa.registrarResultado(primero, 3, 1);
        copa.registrarResultado(segundo, 78, 74);
        copa.registrarResultado(tercero, 2, 2);

        Exhibicion exhibicion = new Exhibicion("Clínica de baloncesto");
        copa.agregarEvento(exhibicion);
        copa.programarEvento(exhibicion, LocalDate.of(2026, 11, 12), 9, "Salón B");
    }

    private void atenderOpcion(int opcion) {
        switch (opcion) {
            case 1: {
                String codigo = vista.leerTexto("Código: ");
                String nombre = vista.leerTexto("Nombre del equipo: ");
                String empresa = vista.leerTexto("Empresa: ");
                copa.registrarEquipo(codigo, nombre, empresa);
                vista.mostrarMensaje("Equipo registrado.");
                break;
            }
            case 2: {
                vista.mostrarEquipos(copa.getTablaPosiciones());
                vista.mostrarDeportes();
                int deporte = vista.leerEntero("Deporte: ");
                if (deporte != 1 && deporte != 2) {
                    throw new IllegalArgumentException("Seleccione 1 o 2 para el deporte.");
                }
                Equipo a = copa.buscarEquipo(vista.leerTexto("Código del equipo A: "));
                Equipo b = copa.buscarEquipo(vista.leerTexto("Código del equipo B: "));
                Encuentro encuentro;
                if (deporte == 1) {
                    encuentro = new FutbolSala(a, b);
                } else {
                    encuentro = new Baloncesto(a, b);
                }
                copa.agregarEncuentro(encuentro);
                vista.mostrarMensaje("Encuentro creado sin programar.");
                break;
            }
            case 3: {
                String titulo = vista.leerTexto("Título de la exhibición: ");
                Exhibicion exhibicion = new Exhibicion(titulo);
                copa.agregarEvento(exhibicion);
                vista.mostrarMensaje("Exhibición creada sin programar.");
                break;
            }
            case 4: {
                List<Programable> eventos = copa.getEventos();
                if (eventos.isEmpty()) {
                    vista.mostrarMensaje("No hay eventos registrados.");
                    break;
                }
                vista.mostrarEventos(eventos);
                int numero = vista.leerEntero("Número del evento: ");
                if (numero < 1 || numero > eventos.size()) {
                    throw new IllegalArgumentException("El número de evento no existe.");
                }
                LocalDate fecha = vista.leerFecha("Fecha (AAAA-MM-DD): ");
                int hora = vista.leerEntero("Hora de inicio (0 a 23): ");
                String sede = vista.leerTexto("Sede: ");
                copa.programarEvento(eventos.get(numero - 1), fecha, hora, sede);
                vista.mostrarMensaje("Programación guardada.");
                break;
            }
            case 5: {
                List<Puntuable> encuentros = copa.getEncuentros();
                if (encuentros.isEmpty()) {
                    vista.mostrarMensaje("No hay encuentros registrados.");
                    break;
                }
                vista.mostrarEncuentros(encuentros);
                int numero = vista.leerEntero("Número del encuentro: ");
                if (numero < 1 || numero > encuentros.size()) {
                    throw new IllegalArgumentException("El número de encuentro no existe.");
                }
                int a = vista.leerEntero("Marcador del equipo A: ");
                int b = vista.leerEntero("Marcador del equipo B: ");
                copa.registrarResultado(encuentros.get(numero - 1), a, b);
                vista.mostrarMensaje("Resultado registrado y puntos de tabla actualizados.");
                break;
            }
            case 6:
                vista.mostrarTabla(copa.getTablaPosiciones());
                break;
            case 7:
                vista.mostrarCalendario(copa);
                break;
            default:
                throw new IllegalArgumentException("Seleccione una opción del menú entre 0 y 7.");
        }
    }
}
