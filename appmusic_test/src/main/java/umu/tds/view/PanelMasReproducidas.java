package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import umu.tds.controller.AppMusic;
import umu.tds.model.Cancion;

public class PanelMasReproducidas extends JPanel {

	private JTable tableMasReproducidas;
	private DefaultTableModel modeloTabla;
	private JComboBox<Integer> comboBoxNumeroCanciones;

	private AppMusic appMusic;

	public PanelMasReproducidas() {
		appMusic = AppMusic.getInstance(); // Obtener instancia del controlador

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

		// Configuración de la JComboBox
		comboBoxNumeroCanciones = new JComboBox<>(new Integer[] { 5, 10, 20 });
		comboBoxNumeroCanciones.setSelectedIndex(0);
		comboBoxNumeroCanciones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cargarCancionesMasReproducidas();
			}
		});

		JPanel panelOpciones = new JPanel();
		panelOpciones.add(comboBoxNumeroCanciones);
		panelMasReproducidas.add(panelOpciones, BorderLayout.NORTH);

		add(panelMasReproducidas, BorderLayout.CENTER);

		cargarCancionesMasReproducidas();
	}

	private void cargarCancionesMasReproducidas() {
		int numCanciones = (Integer) comboBoxNumeroCanciones.getSelectedItem();
		List<Cancion> canciones = appMusic.getTopCanciones(numCanciones);

		modeloTabla.setRowCount(0);
		for (Cancion cancion : canciones) {
			String titulo = cancion.getTitulo();
			String interprete = cancion.getInterpretes().isEmpty() ? "" : cancion.getInterpretes().get(0).getNombre();
			String estilo = cancion.getEstilo().getNombre();
			long reproducciones = cancion.getNumReproducciones();
			modeloTabla.addRow(new Object[] { titulo, interprete, estilo, reproducciones });
		}
	}
}
