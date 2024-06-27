package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import umu.tds.controller.AppMusic;
import umu.tds.descuento.Descuento;
import umu.tds.descuento.DescuentoFactory;
import umu.tds.model.Cancion;
import umu.tds.model.Interprete;
import umu.tds.model.Playlist;
import umu.tds.model.Usuario;

public class VentanaPrincipal extends JFrame {

	private static final int DIM = 40;
	private JPanel contentPane;
	private JPanel panelContenido;
	private JPanel panelLateral;
	private JList<String> listPlaylists;
	private JLabel lblCancionActual;
	private boolean isPremiumUser;
	private boolean isModoAleatorio = false; // Estado del modo de reproducción
	private JButton btnModoReproduccion; // Botón de modo de reproducción

	private AppMusic appMusic;

	private PanelMisPlaylist panelMisPlaylist; // Añadir referencia a PanelMisPlaylist
	private PanelRecientes panelRecientes; // Añadir referencia a PanelRecientes
	private PanelMasReproducidas panelMasReproducidas; // Añadir referencia a PanelMasReproducidas
	private PanelGestionPlaylist panelGestionPlaylist; // Añadir referencia a PanelGestionPlaylist
	private PanelBuscar panelBuscar; // Añadir referencia a PanelBuscar

	public VentanaPrincipal() {

		appMusic = AppMusic.getInstance();
		isPremiumUser = appMusic.getUsuarioActual().isPremium();

		setSize(new Dimension(920, 710));
		setResizable(false);
		setTitle("AppMusic");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelPrincipal = new JPanel();
		contentPane.add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(new BorderLayout(0, 0));

		JPanel panelBienvenida = new JPanel();
		panelPrincipal.add(panelBienvenida, BorderLayout.NORTH);

		JLabel lbBienvenida = new JLabel("Bienvenido, " + appMusic.getUsuarioActual().getUser());
		panelBienvenida.add(lbBienvenida);

		JButton btnPremium = new JButton("Premium");
		btnPremium.addActionListener(e -> handlePremium());
		panelBienvenida.add(btnPremium);

		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(e -> handleLogout());
		panelBienvenida.add(btnLogout);

		panelContenido = new JPanel();
		panelPrincipal.add(panelContenido, BorderLayout.CENTER);
		panelContenido.setLayout(new BorderLayout(0, 0));

		panelLateral = new JPanel();
		panelLateral.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(panelLateral, BorderLayout.WEST);
		panelLateral.setLayout(new GridBagLayout());

		listPlaylists = new JList<>();
		panelMisPlaylist = new PanelMisPlaylist(); // Crear instancia de PanelMisPlaylist
		panelRecientes = new PanelRecientes(); // Crear instancia de PanelRecientes
		panelMasReproducidas = new PanelMasReproducidas(); // Crear instancia de PanelMasReproducidas
		panelBuscar = new PanelBuscar(); // Crear instancia de PanelBuscar
		panelGestionPlaylist = new PanelGestionPlaylist(panelBuscar, this); // Crear instancia de PanelGestionPlaylist

		listPlaylists.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					String playlistName = listPlaylists.getSelectedValue();
					if (playlistName != null) {
						System.out.println("Playlist seleccionada: " + playlistName); // Imprimir el nombre de la
																						// playlist
						panelMisPlaylist.setPlaylist(appMusic.getAllPlaylists(appMusic.getUsuarioActual().getId())
								.stream().filter(pl -> pl.getNombre().equals(playlistName)).findFirst().orElse(null));
						mostrarPanel(panelMisPlaylist); // Mostrar PanelMisPlaylist
					}
				}
			}
		});

		actualizarBotonesPanelLateral();

		PanelReproductor panelReproductor = new PanelReproductor(this);
		contentPane.add(panelReproductor, BorderLayout.SOUTH);

		panelContenido.setVisible(false);
	}

	private void actualizarBotonesPanelLateral() {
		panelLateral.removeAll(); // Clear existing buttons

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		panelLateral.add(crearBotonLateral("Buscar", "/umu/tds/icon/lupa.png", panelBuscar), gbc);

		gbc.gridy++;
		panelLateral.add(crearBotonLateral("Gestión Playlists", "/umu/tds/icon/mas.png", panelGestionPlaylist), gbc);

		gbc.gridy++;
		panelLateral.add(crearBotonLateral("Recientes", "/umu/tds/icon/despertador.png", panelRecientes), gbc); // Pasar
																												// PanelRecientes
		gbc.gridy++;
		panelLateral.add(crearBotonLateral("Cargar Canciones", "/umu/tds/icon/mas.png", panelGestionPlaylist), gbc);

		gbc.gridy++;
		gbc.anchor = GridBagConstraints.NORTH;
		JButton btnMisPlaylists = crearBotonLateral("Mis Playlists",
				"/umu/tds/icon/herramienta-de-audio-con-altavoz.png", panelMisPlaylist); // Pasar PanelMisPlaylist
		panelLateral.add(btnMisPlaylists, gbc);

		if (isPremiumUser) {
			gbc.gridy++;
			panelLateral.add(crearBotonLateral("Más Reproducidas", "/umu/tds/icon/estrella.png", panelMasReproducidas),
					gbc); // Pasar PanelMasReproducidas

			gbc.gridy++;
			JButton btnGenerarPDF = crearBotonLateral("Generar PDF", "/umu/tds/icon/pdf.png", null);
			btnGenerarPDF.addActionListener(e -> handleGenerarPDF());
			panelLateral.add(btnGenerarPDF, gbc);
		}

		gbc.gridy++;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.BOTH; // Permitir que la lista ocupe todo el espacio vertical disponible
		listPlaylists.setBorder(new TitledBorder(null, "Listas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelLateral.add(new JScrollPane(listPlaylists), gbc);

		// Añadir el panel de la canción actual al final del método
		JPanel panelCancionActual = new JPanel();
		panelCancionActual
				.setBorder(new TitledBorder(null, "Reproduciendo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblCancionActual = new JLabel("Ninguna canción en reproducción");
		panelCancionActual.add(lblCancionActual);
		GridBagConstraints gbcPanelCancionActual = new GridBagConstraints();
		gbcPanelCancionActual.gridx = 0;
		gbcPanelCancionActual.gridy = GridBagConstraints.RELATIVE;
		gbcPanelCancionActual.fill = GridBagConstraints.HORIZONTAL;
		gbcPanelCancionActual.insets = new Insets(5, 5, 5, 5);
		panelLateral.add(panelCancionActual, gbcPanelCancionActual);

		// Añadir el panel para el botón de modo de reproducción
		JPanel panelModoReproduccion = new JPanel();
		btnModoReproduccion = new JButton("Modo: Secuencial");
		btnModoReproduccion.addActionListener(e -> cambiarModoReproduccion());
		panelModoReproduccion.add(btnModoReproduccion);
		GridBagConstraints gbcPanelModoReproduccion = new GridBagConstraints();
		gbcPanelModoReproduccion.gridx = 0;
		gbcPanelModoReproduccion.gridy = GridBagConstraints.RELATIVE;
		gbcPanelModoReproduccion.fill = GridBagConstraints.HORIZONTAL;
		gbcPanelModoReproduccion.insets = new Insets(5, 5, 5, 5);
		panelLateral.add(panelModoReproduccion, gbcPanelModoReproduccion);

		cargarPlaylistsUsuario();

		panelLateral.revalidate();
		panelLateral.repaint();
	}

	public void cargarPlaylistsUsuario() {
		List<Playlist> playlists = appMusic.getAllPlaylists(appMusic.getUsuarioActual().getId());
		DefaultListModel<String> listModel = new DefaultListModel<>();
		for (Playlist playlist : playlists) {
			listModel.addElement(playlist.getNombre());
		}
		listPlaylists.setModel(listModel);
	}

	private void cambiarModoReproduccion() {
		isModoAleatorio = !isModoAleatorio;
		if (isModoAleatorio) {
			btnModoReproduccion.setText("Modo: Aleatorio");
		} else {
			btnModoReproduccion.setText("Modo: Secuencial");
		}
	}

	private JButton crearBotonLateral(String texto, String iconPath, JPanel panelFuncion) {
		ImageIcon icon = new ImageIcon(VentanaPrincipal.class.getResource(iconPath));
		Image image = icon.getImage().getScaledInstance(DIM, DIM, Image.SCALE_SMOOTH);
		JButton boton = new JButton(texto, new ImageIcon(image));
		if (panelFuncion != null) {
			boton.addActionListener(e -> {
				if (panelFuncion == panelGestionPlaylist) {
					panelGestionPlaylist.cargarCancionesDeBusqueda(); // Cargar canciones del panel de búsqueda
				}
				mostrarPanel(panelFuncion);
			});
		}
		return boton;
	}

	private void mostrarPanel(JPanel panelFuncion) {
		panelContenido.removeAll();
		panelContenido.add(panelFuncion, BorderLayout.CENTER);
		panelContenido.revalidate();
		panelContenido.repaint();
		panelContenido.setVisible(true);
	}

	private void handlePremium() {
		// Crear la ventana emergente
		JDialog dialog = new JDialog(this, "Suscripción a Premium", true);
		dialog.setSize(350, 250);
		dialog.setLocationRelativeTo(this);
		dialog.setLayout(new BorderLayout());

		// Añadir un desplegable (JComboBox)
		String[] premiumFeatures = { "Por defecto", "Descuento Fijo", "Descuento Jóvenes" };
		JComboBox<String> comboBox = new JComboBox<>(premiumFeatures);
		dialog.add(comboBox, BorderLayout.NORTH);

		// Añadir un panel para la etiqueta
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		// Añadir una etiqueta
		JLabel label = new JLabel("Seleccione una opción de descuento");
		panel.add(label);

		// Añadir una etiqueta para mostrar el precio con descuento
		JLabel priceLabel = new JLabel("Precio con descuento: ");
		panel.add(priceLabel);

		// Añadir el panel al JDialog
		dialog.add(panel, BorderLayout.CENTER);

		// Añadir un panel para los botones adicionales
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		// Añadir el botón "Pagar"
		JButton pagarButton = new JButton("Pagar");
		buttonPanel.add(pagarButton);

		// Añadir el botón "Cancelar"
		JButton cancelarButton = new JButton("Cancelar");
		buttonPanel.add(cancelarButton);

		// Añadir el panel de botones al JDialog
		dialog.add(buttonPanel, BorderLayout.SOUTH);

		// Crear un contenedor para el descuento seleccionado
		final Descuento[] descuento = { DescuentoFactory.getDescuento("") };

		// Añadir ActionListener al JComboBox para actualizar el texto de la etiqueta y
		// el descuento
		comboBox.addActionListener(e -> {
			String selected = (String) comboBox.getSelectedItem();
			descuento[0] = DescuentoFactory.getDescuento(selected);
			label.setText(descuento[0].getDescripcion());

			// Calcular y mostrar el precio con descuento
			double precioConDescuento = descuento[0].aplicarDescuento();
			priceLabel.setText(String.format("Precio con descuento: %.2f€", precioConDescuento));
		});

		// Añadir ActionListener al botón de "Cancelar"
		cancelarButton.addActionListener(e -> {
			isPremiumUser = false;
			actualizarBotonesPanelLateral(); // Recargar los botones laterales
			dialog.dispose();
		});

		// Añadir ActionListener al botón de "Pagar"
		pagarButton.addActionListener(e -> {
			isPremiumUser = true;
			actualizarBotonesPanelLateral(); // Recargar los botones laterales
			dialog.dispose();
		});

		// Mostrar la ventana emergente
		dialog.setVisible(true);
	}

	private void handleLogout() {
		dispose();
		VentanaLogin ventanaLogin = new VentanaLogin();
		ventanaLogin.mostrarVentana();
	}

	private void handleGenerarPDF() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new java.io.File("."));
		fileChooser.setDialogTitle("Selecciona un directorio");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		Usuario usuarioActual = appMusic.getUsuarioActual();

		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			String filePath = fileChooser.getSelectedFile().getAbsolutePath() + File.separator + usuarioActual.getUser()
					+ ".pdf";
			createPDF(filePath, usuarioActual);
		}
	}

	private void createPDF(String filePath, Usuario usuarioActual) {
		Document documentoPDF = new Document();
		try {
			PdfWriter.getInstance(documentoPDF, new FileOutputStream(filePath));
			documentoPDF.open();
			List<Playlist> plst = appMusic.getAllPlaylists(usuarioActual.getId());

			for (Playlist p : plst) {
				documentoPDF.add(new Paragraph(p.getNombre()));
				for (Cancion c : p.getCanciones()) {
					StringBuilder linea = new StringBuilder("      " + c.getTitulo() + "      ");
					for (Interprete i : c.getInterpretes()) {
						linea.append(i.getNombre()).append("     ");
					}
					linea.append(c.getEstilo().getNombre());
					documentoPDF.add(new Paragraph(linea.toString()));
				}
			}
		} catch (FileNotFoundException | DocumentException e) {
			JOptionPane.showMessageDialog(this, "Error al generar el PDF: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			documentoPDF.close();
		}
	}

	// Método para actualizar la canción actual
	public void actualizarCancionActual(String cancion) {
		lblCancionActual.setText(cancion);
	}
}
