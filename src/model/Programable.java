package model;

import java.time.LocalDate;
import java.util.List;

public interface Programable extends Comparable<Programable> {
    void programar(LocalDate fecha, int hora, String sede);
    LocalDate getFecha();
    Integer getHora();
    String getSede();
    String getDescripcion();
    List<Equipo> getEquipos();

    @Override
    default int compareTo(Programable otro) {
        if (getFecha() == null || otro.getFecha() == null) {
            throw new IllegalStateException("Solo se comparan eventos con programación.");
        }
        int comparacion = getFecha().compareTo(otro.getFecha());
        if (comparacion == 0) {
            comparacion = Integer.compare(getHora(), otro.getHora());
        }
        if (comparacion == 0) {
            comparacion = getSede().compareToIgnoreCase(otro.getSede());
        }
        if (comparacion == 0) {
            comparacion = getSede().compareTo(otro.getSede());
        }
        return comparacion;
    }
}