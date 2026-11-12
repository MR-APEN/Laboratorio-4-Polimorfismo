package main;

import controller.ControladorCopa;
import model.Copa;
import view.VistaConsola;

public class Main {
    public static void main(String[] args) {
        Copa copa = new Copa();
        VistaConsola vista = new VistaConsola();
        ControladorCopa controlador = new ControladorCopa(copa, vista);
        controlador.ejecutar();
    }
}
