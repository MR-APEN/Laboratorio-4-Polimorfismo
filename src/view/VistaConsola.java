package view;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import model.Equipo;
import model.Programable;
import model.Puntuable;

public class VistaConsola {
    private final Scanner entrada;

    public VistaConsola() {
        entrada = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("\n1. Registrar equipo");
        System.out.println("2. Crear encuentro");
        System.out.println("3. Crear exhibición");
        System.out.println("4. Programar o reprogramar evento");
        System.out.println("5. Registrar resultado");
        System.out.println("6. Mostrar tabla de posiciones");
        System.out.println("7. Mostrar calendario");
        System.out.println("0. Salir");
    }

    public void mostrarDeportes() {
        System.out.println("1. Fútbol sala");
        System.out.println("2. Baloncesto");
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine();
    }

    public int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje).trim());
            } catch (NumberFormatException e) {
                mostrarMensaje("Entrada inválida. Ingrese un número entero válido.");
            }
        }
    }

    public LocalDate leerFecha(String mensaje) {
        while (true) {
            try {
                return LocalDate.parse(leerTexto(mensaje).trim());
            } catch (DateTimeParseException e) {
                mostrarMensaje("Fecha inválida. Use AAAA-MM-DD con una fecha existente.");
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarEquipos(List<Equipo> equipos) {
        System.out.println("Equipos registrados:");
        for (Equipo equipo : equipos) {
            System.out.println(equipo.getCodigo() + " - " + equipo.getNombre());
        }
    }

    public void mostrarEventos(List<Programable> eventos) {
        for (int i = 0; i < eventos.size(); i++) {
            Programable evento = eventos.get(i);
            System.out.println((i + 1) + ". " + evento.getDescripcion());
            if (evento.getFecha() != null) {
                System.out.printf("   %s | %02d:00 | %s%n",
                        evento.getFecha(), evento.getHora(), evento.getSede());
            }
        }
    }

    public void mostrarEncuentros(List<Puntuable> encuentros) {
        for (int i = 0; i < encuentros.size(); i++) {
            System.out.println((i + 1) + ". " + encuentros.get(i).getDescripcion());
        }
    }

    public void mostrarTabla(List<Equipo> equipos) {
        System.out.println("\nTABLA DE POSICIONES");
        System.out.printf("%-8s %-18s %-24s %s%n", "Código", "Equipo", "Empresa", "Puntos");
        for (Equipo equipo : equipos) {
            System.out.printf("%-8s %-18s %-24s %d%n", equipo.getCodigo(),
                    equipo.getNombre(), equipo.getEmpresa(), equipo.getPuntosTabla());
        }
    }

    public void mostrarCalendario(Iterable<Programable> calendario) {
        System.out.println("\nCALENDARIO");
        boolean hayEventos = false;
        for (Programable evento : calendario) {
            hayEventos = true;
            System.out.printf("%s | %02d:00 | %s | %s%n", evento.getFecha(),
                    evento.getHora(), evento.getSede(), evento.getDescripcion());
        }
        if (!hayEventos) {
            System.out.println("No hay eventos programados.");
        }
    }

    public void cerrar() {
        entrada.close();
    }
}
