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

public class PanelRecientes extends JPanel {

	private JTable tableRecientes;
	private DefaultTableModel modeloTabla;

	private AppMusic appMusic;

	public PanelRecientes() {
		appMusic = AppMusic.getInstance(); // Inicializar appMusic

		setLayout(new BorderLayout(0, 0)); // No gaps between components

		// Configuración de la tabla de canciones recientes
		modeloTabla = new DefaultTableModel(new Object[][] {},
				new String[] { "Título", "Intérprete", "Estilo", "Seleccionar" }) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 3 ? Boolean.class : String.class;
			}
		};

		tableRecientes = new JTable(modeloTabla);
		JPanel panelRecientes = new JPanel(new BorderLayout(0, 0)); // No gaps between components
		panelRecientes.setBorder(
				new TitledBorder(null, "Canciones Recientes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelRecientes.add(new JScrollPane(tableRecientes), BorderLayout.CENTER);

		add(panelRecientes, BorderLayout.CENTER);

		cargarCancionesRecientes();
	}

	private void cargarCancionesRecientes() {
		modeloTabla.setRowCount(0);
		List<Cancion> cancionesRecientes = appMusic.getCancionesRecientes(appMusic.getUsuarioActual().getId());
		for (Cancion cancion : cancionesRecientes) {
			String titulo = cancion.getTitulo();
			String interprete = cancion.getInterpretes().isEmpty() ? "" : cancion.getInterpretes().get(0).getNombre();
			String estilo = cancion.getEstilo().getNombre();
			modeloTabla.addRow(new Object[] { titulo, interprete, estilo, false });
		}
	}
}
