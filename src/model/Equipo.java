package model;

public class Equipo implements Comparable<Equipo> {
    private final String codigo;
    private final String nombre;
    private final String empresa;
    private int puntosTabla;

    public Equipo(String codigo, String nombre, String empresa) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del equipo no puede estar vacío.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del equipo no puede estar vacío.");
        }
        if (empresa == null || empresa.trim().isEmpty()) {
            throw new IllegalArgumentException("La empresa no puede estar vacía.");
        }
        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.empresa = empresa.trim();
        puntosTabla = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public int getPuntosTabla() {
        return puntosTabla;
    }

    public void sumarPuntos(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("Los puntos de tabla no pueden ser negativos.");
        }
        puntosTabla += cantidad;
    }

    @Override
    public int compareTo(Equipo otro) {
        int comparacion = Integer.compare(otro.puntosTabla, puntosTabla);
        if (comparacion == 0) {
            comparacion = nombre.compareToIgnoreCase(otro.nombre);
        }
        if (comparacion == 0) {
            comparacion = nombre.compareTo(otro.nombre);
        }
        return comparacion;
    }
}
