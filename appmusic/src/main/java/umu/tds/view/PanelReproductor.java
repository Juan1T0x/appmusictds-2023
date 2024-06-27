package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

import javafx.util.Duration;
import umu.tds.controller.AppMusic;

public class PanelReproductor extends JPanel {

	private static final int DIM = 40;
	private VentanaPrincipal ventanaPrincipal; // Referencia a la VentanaPrincipal
	private JSlider sliderProgreso;

	private AppMusic appMusic;

	public PanelReproductor(VentanaPrincipal ventanaPrincipal) {

		appMusic = AppMusic.getInstance();

		this.ventanaPrincipal = ventanaPrincipal; // Inicializar la referencia
		setLayout(new BorderLayout());
		sliderProgreso = new JSlider();
		sliderProgreso.setValue(0);
		sliderProgreso.setMaximum(100);
		sliderProgreso.setMinimum(0);
		sliderProgreso.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int value = sliderProgreso.getValue();
				Duration totalDuration = appMusic.getDuracion();
				Duration seekTime = totalDuration.multiply(value / 100.0);
				appMusic.seek(seekTime);
			}
		});
		add(sliderProgreso, BorderLayout.NORTH);
		JPanel panelBotones = new JPanel(new GridLayout(1, 6, 4, 0));
		add(panelBotones, BorderLayout.CENTER);
		agregarBotonesReproductor(panelBotones);

		// Iniciar el timer para actualizar el slider de progreso cada segundo
		Timer timer = new Timer(1000, e -> actualizarProgreso());
		timer.start();

		ventanaPrincipal.actualizarCancionActual("Ninguna canción en reproducción");
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

	public void handlePlay() {
		// Lógica para reproducir canción
		appMusic.play();
		ventanaPrincipal.actualizarCancionActual("Reproduciendo: " + appMusic.getCancionActual().getTitulo());
	}

	private void handlePause() {
		// Lógica para pausar canción
		appMusic.pause();
		ventanaPrincipal.actualizarCancionActual("Pausada: " + appMusic.getCancionActual().getTitulo());
	}

	private void handleStop() {
		// Lógica para detener canción
		appMusic.stop();
		ventanaPrincipal.actualizarCancionActual("Ninguna canción en reproducción");
	}

	private void handlePrevious() {
		// Lógica para canción anterior
		appMusic.previous();
		ventanaPrincipal.actualizarCancionActual("Reproduciendo: " + appMusic.getCancionActual().getTitulo());
	}

	private void handleNext() {
		// Lógica para siguiente canción
		appMusic.next();
		ventanaPrincipal.actualizarCancionActual("Reproduciendo: " + appMusic.getCancionActual().getTitulo());
	}

	private void actualizarProgreso() {
		Duration currentTime = appMusic.getTiempoActual();
		Duration totalDuration = appMusic.getDuracion();

		if (totalDuration != Duration.UNKNOWN && totalDuration != null) {
			int progress = (int) ((currentTime.toSeconds() / totalDuration.toSeconds()) * 100);
			sliderProgreso.setValue(progress);
		}
	}
}
