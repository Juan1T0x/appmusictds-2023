package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import umu.tds.model.EstiloEnum;

public class VentanaPrincipal extends JFrame {

	private static final int DIM = 40;

	private JPanel contentPane;
	private JPanel panel_Reproductor;
	private JTable tableResultadoBusqueda;
	private JTextField textBuscarInterprete;
	private JTextField textBuscarTitulo;
	private JTable tableListas;

	// Main para hacer pruebas

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaPrincipal() {
		setSize(new Dimension(860, 686));
		setResizable(false);
		setTitle("AppMusic");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Para centrar la ventana
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel_principal = new JPanel();
		contentPane.add(panel_principal, BorderLayout.CENTER);
		panel_principal.setLayout(new BorderLayout(0, 0));

		JPanel panel_Bienvenida = new JPanel();
		panel_principal.add(panel_Bienvenida, BorderLayout.NORTH);

		JLabel lbBienvenida = new JLabel("Bienvenido, usuario");
		panel_Bienvenida.add(lbBienvenida);

		JButton btnPremium = new JButton("Premium");
		panel_Bienvenida.add(btnPremium);

		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_Bienvenida.add(btnLogout);

		JPanel panel_contenido = new JPanel();
		panel_principal.add(panel_contenido, BorderLayout.CENTER);
		panel_contenido.setLayout(new BoxLayout(panel_contenido, BoxLayout.Y_AXIS));

		JPanel panel_Buscar = new JPanel();
		panel_Buscar.setBorder(new TitledBorder(null, "Buscar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_contenido.add(panel_Buscar);
		panel_Buscar.setLayout(new GridLayout(3, 2, 5, 5));

		textBuscarInterprete = new JTextField();
		textBuscarInterprete.setForeground(new Color(192, 192, 192));
		textBuscarInterprete.setText("interprete");
		panel_Buscar.add(textBuscarInterprete);
		textBuscarInterprete.setColumns(10);

		textBuscarTitulo = new JTextField();
		textBuscarTitulo.setForeground(new Color(192, 192, 192));
		textBuscarTitulo.setText("titulo");
		panel_Buscar.add(textBuscarTitulo);
		textBuscarTitulo.setColumns(10);

		JCheckBox chckbxNewCheckBox = new JCheckBox("favoritas");
		panel_Buscar.add(chckbxNewCheckBox);

		JComboBox estilos_dropdown = new JComboBox();
		estilos_dropdown.setModel(new DefaultComboBoxModel(EstiloEnum.values()));
		estilos_dropdown.setToolTipText("estilo");
		panel_Buscar.add(estilos_dropdown);

		JPanel panel_vacio_1 = new JPanel();
		panel_Buscar.add(panel_vacio_1);

		JButton btnHacerBusqueda = new JButton("Buscar");
		btnHacerBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableResultadoBusqueda.setVisible(true);
				panel_Reproductor.setVisible(true);

			}
		});
		panel_Buscar.add(btnHacerBusqueda);

		JPanel panel_ResultadoBusqueda = new JPanel();
		panel_ResultadoBusqueda.setLayout(new BorderLayout(0, 0));

		// Tabla con la última columna que contiene JCheckBox
		tableResultadoBusqueda = new JTable();
		DefaultTableModel modeloTabla = new DefaultTableModel(new Object[][] { { "temp", "temp", "temp", false },
				{ "temp", "temp", "temp", false }, { "temp", "temp", "temp", false }, { "temp", "temp", "temp", false },
				{ "temp", "temp", "temp", false },

		}, new Object[] { "Titulo", "Interprete", "Estilo", "" }) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 3 ? Boolean.class : Object.class;
			}
		};
		tableResultadoBusqueda.setModel(modeloTabla);
		tableResultadoBusqueda.setBorder(new LineBorder(new Color(0, 0, 0), 1, false));
		panel_ResultadoBusqueda.add(new JScrollPane(tableResultadoBusqueda), BorderLayout.CENTER);

		panel_contenido.add(panel_ResultadoBusqueda);

		panel_Reproductor = new JPanel();
		panel_Reproductor.setLayout(new GridLayout(0, 7, 4, 0));

		panel_contenido.add(panel_Reproductor);

		// Botón Atras
		ImageIcon iconoAtras = new ImageIcon(VentanaPrincipal.class.getResource("/umu/tds/icon/atras.png"));
		Image imagenAtras = iconoAtras.getImage().getScaledInstance(DIM, DIM, Image.SCALE_SMOOTH);
		JButton btnAtras = new JButton("", new ImageIcon(imagenAtras));
		panel_Reproductor.add(btnAtras);

		// Botón Parar
		ImageIcon iconoParar = new ImageIcon(VentanaPrincipal.class.getResource("/umu/tds/icon/detener.png"));
		Image imagenParar = iconoParar.getImage().getScaledInstance(DIM, DIM, Image.SCALE_SMOOTH);
		JButton btnParar = new JButton("", new ImageIcon(imagenParar));
		panel_Reproductor.add(btnParar);

		// Botón Pausa
		ImageIcon iconoPausa = new ImageIcon(VentanaPrincipal.class.getResource("/umu/tds/icon/boton-de-pausa.png"));
		Image imagenPausa = iconoPausa.getImage().getScaledInstance(DIM, DIM, Image.SCALE_SMOOTH);
		JButton btnPausa = new JButton("", new ImageIcon(imagenPausa));
		panel_Reproductor.add(btnPausa);

		// Botón Play
		ImageIcon iconoPlay = new ImageIcon(VentanaPrincipal.class.getResource("/umu/tds/icon/jugar.png"));
		Image imagenPlay = iconoPlay.getImage().getScaledInstance(DIM, DIM, Image.SCALE_SMOOTH);
		JButton btnPlay = new JButton("", new ImageIcon(imagenPlay));
		panel_Reproductor.add(btnPlay);

		// Botón Siguiente
		ImageIcon iconoSiguiente = new ImageIcon(VentanaPrincipal.class.getResource("/umu/tds/icon/siguiente.png"));
		Image imagenSiguiente = iconoSiguiente.getImage().getScaledInstance(DIM, DIM, Image.SCALE_SMOOTH);
		JButton btnSiguiente = new JButton("", new ImageIcon(imagenSiguiente));
		panel_Reproductor.add(btnSiguiente);

		JPanel panel_vacio = new JPanel();
		panel_Reproductor.add(panel_vacio);

		JButton btnAddPlaylist = new JButton("Añadir Lista");
		btnAddPlaylist.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddPlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_Reproductor.add(btnAddPlaylist);

		JPanel panel_lateral = new JPanel();
		panel_lateral.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(panel_lateral, BorderLayout.WEST);

		panel_lateral.setLayout(new BorderLayout(0, 0));

		JPanel panel_botonesLateral = new JPanel();
		panel_lateral.add(panel_botonesLateral, BorderLayout.NORTH);
		panel_botonesLateral.setLayout(new GridLayout(6, 1, 0, 5));

		tableListas = new JTable();
		tableListas.setModel(new DefaultTableModel(new Object[][] { { null }, { "Lista 1" }, { null }, },
				new String[] { "Listas" }));
		panel_lateral.add(tableListas, BorderLayout.CENTER);
		tableListas.setBorder(new TitledBorder(null, "Listas", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		// Botón Buscar
		ImageIcon iconoBuscar = new ImageIcon(VentanaPrincipal.class.getResource("/umu/tds/icon/lupa.png"));
		Image imagenBuscar = iconoBuscar.getImage().getScaledInstance(DIM, DIM, Image.SCALE_SMOOTH);
		JButton btnBuscar = new JButton("Buscar", new ImageIcon(imagenBuscar));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_contenido.setVisible(true);
				panel_Buscar.setVisible(true);
				panel_Reproductor.setVisible(false);
				tableResultadoBusqueda.setVisible(false);
				tableListas.setVisible(false);
			}
		});

		// Botón Gestión Playlists
		ImageIcon iconoGestionPlaylist = new ImageIcon(VentanaPrincipal.class.getResource("/umu/tds/icon/mas.png"));
		Image imagenGestionPlaylist = iconoGestionPlaylist.getImage().getScaledInstance(DIM, DIM, Image.SCALE_SMOOTH);
		JButton btnGestionPlaylist = new JButton("Gestión Playlists", new ImageIcon(imagenGestionPlaylist));

		// Botón Recientes
		ImageIcon iconoRecientes = new ImageIcon(VentanaPrincipal.class.getResource("/umu/tds/icon/despertador.png"));
		Image imagenRecientes = iconoRecientes.getImage().getScaledInstance(DIM, DIM, Image.SCALE_SMOOTH);
		JButton btnRecientes = new JButton("Recientes", new ImageIcon(imagenRecientes));
		btnRecientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_contenido.setVisible(true);
				panel_Buscar.setVisible(false);
				panel_Reproductor.setVisible(true);
				tableResultadoBusqueda.setVisible(true);
				tableListas.setVisible(false);
			}
		});

		// Botón Mis Playlists
		ImageIcon iconoMisPlaylist = new ImageIcon(
				VentanaPrincipal.class.getResource("/umu/tds/icon/herramienta-de-audio-con-altavoz.png"));
		Image imagenMisPlaylist = iconoMisPlaylist.getImage().getScaledInstance(DIM, DIM, Image.SCALE_SMOOTH);
		JButton btnMisPlaylist = new JButton("Mis Playlists", new ImageIcon(imagenMisPlaylist));
		btnMisPlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_contenido.setVisible(true);
				panel_Buscar.setVisible(false);
				panel_Reproductor.setVisible(true);
				tableResultadoBusqueda.setVisible(true);
				tableListas.setVisible(true);
			}
		});

		panel_botonesLateral.add(btnBuscar);

		panel_botonesLateral.add(btnGestionPlaylist);

		panel_botonesLateral.add(btnRecientes);

		panel_botonesLateral.add(btnMisPlaylist);

		// Alinear elementos en el centro de cada celda de tableListas
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < tableListas.getColumnModel().getColumnCount(); i++) {
			tableListas.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		tableListas.setVisible(false);
		tableResultadoBusqueda.setVisible(false);
		panel_contenido.setVisible(false);
		panel_Reproductor.setVisible(false);
	}

}
