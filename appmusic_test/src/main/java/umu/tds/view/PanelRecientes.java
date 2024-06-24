package umu.tds.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PanelRecientes extends JPanel {

	private JTable tableRecientes;
	private DefaultTableModel modeloTabla;

	public PanelRecientes() {
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
		// Simular carga de canciones recientes
		modeloTabla.setRowCount(0);
		modeloTabla.addRow(new Object[] { "Título 1", "Intérprete 1", "Estilo 1", false });
		modeloTabla.addRow(new Object[] { "Título 2", "Intérprete 2", "Estilo 2", false });
	}
}
