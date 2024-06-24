package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import umu.tds.controller.AppMusic;
import umu.tds.model.Cancion;
import umu.tds.model.Playlist;

public class PanelMisPlaylist extends JPanel {

	private JTable tableCanciones;
	private DefaultTableModel modeloTabla;
	private Playlist currentPlaylist;

	private AppMusic appMusic;

	public PanelMisPlaylist() {
		appMusic = AppMusic.getInstance(); // Aseguramos que appMusic se inicializa correctamente

		setLayout(new BorderLayout(0, 0)); // No gaps between components

		// Configuración de la tabla de canciones
		modeloTabla = new DefaultTableModel(new Object[][] {},
				new String[] { "Título", "Intérprete", "Estilo", "Seleccionar" }) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 3 ? Boolean.class : String.class;
			}
		};

		tableCanciones = new JTable(modeloTabla);
		JPanel panelCanciones = new JPanel(new BorderLayout(0, 0)); // No gaps between components
		panelCanciones.setBorder(
				new TitledBorder(null, "Canciones en la Playlist", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCanciones.add(new JScrollPane(tableCanciones), BorderLayout.CENTER);

		add(panelCanciones, BorderLayout.CENTER);

		// Añadir botón para eliminar canciones seleccionadas
		JButton btnEliminarCancion = new JButton("Eliminar Canción");
		btnEliminarCancion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleEliminarCancion();
			}
		});
		add(btnEliminarCancion, BorderLayout.SOUTH);
	}

	public void setPlaylist(Playlist playlist) {
		this.currentPlaylist = playlist;
		cargarCancionesDePlaylist(playlist);
	}

	private void cargarCancionesDePlaylist(Playlist playlist) {
		modeloTabla.setRowCount(0); // Clear the table before loading new data
		if (playlist != null) {
			List<Cancion> canciones = appMusic.getAllCancionesFromPlaylist(appMusic.getUsuarioActual().getId(),
					playlist.getNombre());
			if (canciones != null) {
				for (Cancion cancion : canciones) {
					String titulo = cancion.getTitulo();
					String interprete = cancion.getInterpretes().isEmpty() ? ""
							: cancion.getInterpretes().get(0).getNombre();
					String estilo = cancion.getEstilo().getNombre();
					modeloTabla.addRow(new Object[] { titulo, interprete, estilo, false });
				}
			}
		}
	}

	private void handleEliminarCancion() {
		List<Integer> selectedRows = getSelectedRows();
		if (selectedRows.isEmpty()) {
			return; // No hay canciones seleccionadas
		}

		for (int rowIndex : selectedRows) {
			String tituloCancion = (String) modeloTabla.getValueAt(rowIndex, 0);
			Cancion cancion = getCancionFromTable(rowIndex);
			appMusic.removeCancionFromPlaylist(appMusic.getUsuarioActual().getId(), currentPlaylist.getId(),
					cancion.getId());
		}
		// Recargar la playlist actualizada
		cargarCancionesDePlaylist(currentPlaylist);
	}

	private List<Integer> getSelectedRows() {
		return IntStream.range(0, modeloTabla.getRowCount()).filter(row -> (Boolean) modeloTabla.getValueAt(row, 3))
				.boxed().collect(Collectors.toList());
	}

	private Cancion getCancionFromTable(int rowIndex) {
		String tituloCancion = (String) modeloTabla.getValueAt(rowIndex, 0);
		List<Cancion> canciones = appMusic.getAllCancionesFromPlaylist(appMusic.getUsuarioActual().getId(),
				currentPlaylist.getNombre());
		return canciones.stream().filter(c -> c.getTitulo().equals(tituloCancion)).findFirst().orElse(null);
	}
}
