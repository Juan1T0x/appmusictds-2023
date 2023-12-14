package umu.tds;

import java.awt.EventQueue;

import javax.swing.UIManager;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;  // Para los temas de JTattoo, tema oscuro

import umu.tds.view.VentanaLogin;

public class Lanzador {
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new HiFiLookAndFeel());
                    VentanaLogin ventana = new VentanaLogin();
                    ventana.mostrarVentana();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
