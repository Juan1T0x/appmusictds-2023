package umu.tds.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PanelMasReproducidas extends JPanel {

	private JTable tableMasReproducidas;
	private DefaultTableModel modeloTabla;

	public PanelMasReproducidas() {
		setLayout(new BorderLayout(0, 0)); // No gaps between components

		// Configuración de la tabla de canciones más reproducidas
		modeloTabla = new DefaultTableModel(new Object[][] {},
				new String[] { "Título", "Intérprete", "Estilo", "Veces Reproducida" }) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 3 ? Integer.class : String.class;
			}
		};

		tableMasReproducidas = new JTable(modeloTabla);
		JPanel panelMasReproducidas = new JPanel(new BorderLayout(0, 0)); // No gaps between components
		panelMasReproducidas.setBorder(new TitledBorder(null, "Canciones Más Reproducidas", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelMasReproducidas.add(new JScrollPane(tableMasReproducidas), BorderLayout.CENTER);

		add(panelMasReproducidas, BorderLayout.CENTER);

		cargarCancionesMasReproducidas();
	}

	private void cargarCancionesMasReproducidas() {
		// Simular carga de canciones más reproducidas
		modeloTabla.setRowCount(0);
		modeloTabla.addRow(new Object[] { "Título 1", "Intérprete 1", "Estilo 1", 10 });
		modeloTabla.addRow(new Object[] { "Título 2", "Intérprete 2", "Estilo 2", 8 });
	}
}
