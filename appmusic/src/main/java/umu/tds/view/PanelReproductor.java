package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class PanelReproductor extends JPanel {

	private static final int DIM = 40;
	private VentanaPrincipal ventanaPrincipal; // Referencia a la VentanaPrincipal
	private JSlider sliderProgreso;

	public PanelReproductor(VentanaPrincipal ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal; // Inicializar la referencia
		setLayout(new BorderLayout());
		sliderProgreso = new JSlider();
		add(sliderProgreso, BorderLayout.NORTH);
		JPanel panelBotones = new JPanel(new GridLayout(1, 6, 4, 0));
		add(panelBotones, BorderLayout.CENTER);
		agregarBotonesReproductor(panelBotones);
	}

	private void agregarBotonesReproductor(JPanel panelBotones) {
		panelBotones.add(crearBotonReproductor("/umu/tds/icon/atras.png", e -> handlePrevious()));
		panelBotones.add(crearBotonReproductor("/umu/tds/icon/detener.png", e -> handleStop()));
		panelBotones.add(crearBotonReproductor("/umu/tds/icon/boton-de-pausa.png", e -> handlePause()));
		panelBotones.add(crearBotonReproductor("/umu/tds/icon/jugar.png", e -> handlePlay()));
		panelBotones.add(crearBotonReproductor("/umu/tds/icon/siguiente.png", e -> handleNext()));
		panelBotones.add(new JPanel()); // Panel vacío para espacio adicional si necesario
	}

	private JButton crearBotonReproductor(String iconPath, ActionListener actionListener) {
		ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
		Image image = icon.getImage().getScaledInstance(DIM, DIM, Image.SCALE_SMOOTH);
		JButton boton = new JButton(new ImageIcon(image));
		boton.addActionListener(actionListener);
		return boton;
	}

	private void handlePlay() {
		// Lógica para reproducir canción
		ventanaPrincipal.actualizarCancionActual("Reproduciendo: Canción 1");
	}

	private void handlePause() {
		// Lógica para pausar canción
		ventanaPrincipal.actualizarCancionActual("Pausada: Canción 1");
	}

	private void handleStop() {
		// Lógica para detener canción
		ventanaPrincipal.actualizarCancionActual("Ninguna canción en reproducción");
	}

	private void handlePrevious() {
		// Lógica para canción anterior
		ventanaPrincipal.actualizarCancionActual("Reproduciendo: Canción Anterior");
	}

	private void handleNext() {
		// Lógica para siguiente canción
		ventanaPrincipal.actualizarCancionActual("Reproduciendo: Siguiente Canción");
	}
}
