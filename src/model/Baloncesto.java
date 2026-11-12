package model;

public class Baloncesto extends Encuentro {
    public Baloncesto(Equipo a, Equipo b) {
        super(a, b);
    }

    @Override
    protected String nombreDeporte() {
        return "Baloncesto";
    }

    @Override
    protected boolean esMarcadorValido(int a, int b) {
        return a != b;
    }

    @Override
    protected int calcularPuntos(int propios, int contrarios) {
        if (propios > contrarios) {
            return 2;
        }
        return 1;
    }
}
