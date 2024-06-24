package umu.tds.view;

import java.awt.BorderLayout;
import java.util.List;

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
	}

	public void setPlaylist(Playlist playlist) {
		cargarCancionesDePlaylist(playlist);
	}

	private void cargarCancionesDePlaylist(Playlist playlist) {
		if (playlist != null) {
			List<Cancion> canciones = appMusic.getAllCancionesFromPlaylist(appMusic.getUsuarioActual().getId(),
					playlist.getNombre());
			modeloTabla.setRowCount(0);
			for (Cancion cancion : canciones) {
				String titulo = cancion.getTitulo();
				String interprete = cancion.getInterpretes().isEmpty() ? ""
						: cancion.getInterpretes().get(0).getNombre();
				String estilo = cancion.getEstilo().getNombre();
				modeloTabla.addRow(new Object[] { titulo, interprete, estilo, false });
			}
		} else {
			modeloTabla.setRowCount(0); // Clear the table if playlist is null
		}
	}
}
