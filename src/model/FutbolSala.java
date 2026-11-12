package model;

public class FutbolSala extends Encuentro {
    public FutbolSala(Equipo a, Equipo b) {
        super(a, b);
    }

    @Override
    protected String nombreDeporte() {
        return "Fútbol sala";
    }

    @Override
    protected boolean esMarcadorValido(int a, int b) {
        return true;
    }

    @Override
    protected int calcularPuntos(int propios, int contrarios) {
        if (propios > contrarios) {
            return 3;
        }
        if (propios == contrarios) {
            return 1;
        }
        return 0;
    }
}
