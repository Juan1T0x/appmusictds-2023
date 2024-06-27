package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import umu.tds.controller.AppMusic;
import umu.tds.model.Cancion;

public class PanelGestionPlaylist extends JPanel {

	private JTextField textTituloPlaylist;
	private JTable tableCanciones;
	private DefaultTableModel modeloTabla;
	private PanelBuscar panelBuscar; // Referencia al PanelBuscar
	private VentanaPrincipal ventanaPrincipal; // Referencia a VentanaPrincipal

	public PanelGestionPlaylist(PanelBuscar panelBuscar, VentanaPrincipal ventanaPrincipal) {
		this.panelBuscar = panelBuscar;
		this.ventanaPrincipal = ventanaPrincipal;

		setLayout(new BorderLayout());

		JPanel panelSuperior = new JPanel();
		panelSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelSuperior.setLayout(new GridLayout(1, 3, 10, 10));

		textTituloPlaylist = new JTextField();
		panelSuperior.add(new JLabel("Título:"));
		panelSuperior.add(textTituloPlaylist);

		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(1, 2, 10, 10));

		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleCrearPlaylist();
			}
		});
		panelBotones.add(btnCrear);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEliminarPlaylist();
			}
		});
		panelBotones.add(btnEliminar);

		panelSuperior.add(panelBotones);

		add(panelSuperior, BorderLayout.NORTH);

		JPanel panelCentral = new JPanel(new BorderLayout());
		panelCentral.setBorder(
				new TitledBorder(null, "Canciones en la Playlist", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		modeloTabla = new DefaultTableModel(new Object[][] {},
				new String[] { "Título", "Intérprete", "Estilo", "Seleccionar" }) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 3 ? Boolean.class : String.class;
			}
		};

		tableCanciones = new JTable(modeloTabla);
		JScrollPane scrollPane = new JScrollPane(tableCanciones);
		panelCentral.add(scrollPane, BorderLayout.CENTER);

		add(panelCentral, BorderLayout.CENTER);

		JPanel panelInferior = new JPanel();
		JButton btnEliminarDeLaLista = new JButton("Eliminar de la Lista");
		btnEliminarDeLaLista.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEliminarDeLaLista();
			}
		});
		panelInferior.add(btnEliminarDeLaLista);

		add(panelInferior, BorderLayout.SOUTH);
	}

	private void handleCrearPlaylist() {
		String titulo = textTituloPlaylist.getText();
		if (titulo.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El título de la playlist no puede estar vacío", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		List<Integer> selectedCancionIds = getSelectedCancionIdsFromTable();
		try {
			AppMusic.getInstance().addPlaylistToUsuario(AppMusic.getInstance().getUsuarioActual().getId(), titulo,
					selectedCancionIds);
			JOptionPane.showMessageDialog(this, "Playlist '" + titulo + "' creada correctamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			ventanaPrincipal.cargarPlaylistsUsuario(); // Actualizar panel lateral
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al crear la playlist: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void handleEliminarPlaylist() {
		int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar esta playlist?",
				"Confirmación", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			String titulo = textTituloPlaylist.getText();
			try {
				AppMusic.getInstance().removePlaylist(AppMusic.getInstance().getUsuarioActual().getId(), titulo);
				JOptionPane.showMessageDialog(this, "Playlist eliminada correctamente", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				ventanaPrincipal.cargarPlaylistsUsuario(); // Actualizar panel lateral
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error al eliminar la playlist: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void handleEliminarDeLaLista() {
		for (int i = 0; i < modeloTabla.getRowCount(); i++) {
			if ((Boolean) modeloTabla.getValueAt(i, 3)) {
				modeloTabla.removeRow(i);
				i--;
			}
		}
	}

	private List<Integer> getSelectedCancionIdsFromTable() {
		return panelBuscar.getCancionesMostradas().stream().filter(c -> {
			for (int i = 0; i < modeloTabla.getRowCount(); i++) {
				if ((Boolean) modeloTabla.getValueAt(i, 3) && c.getTitulo().equals(modeloTabla.getValueAt(i, 0))) {
					return true;
				}
			}
			return false;
		}).map(Cancion::getId).collect(Collectors.toList());
	}

	public void cargarCancionesDeBusqueda() {
		List<Cancion> canciones = panelBuscar.getCancionesMostradas();
		modeloTabla.setRowCount(0); // Clear existing rows

		for (Cancion cancion : canciones) {
			String titulo = cancion.getTitulo();
			String interprete = cancion.getInterpretes().isEmpty() ? "" : cancion.getInterpretes().get(0).getNombre();
			String estilo = cancion.getEstilo().getNombre();
			modeloTabla.addRow(new Object[] { titulo, interprete, estilo, false });
		}
	}
}
