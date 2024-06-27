package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import umu.tds.controller.AppMusic;
import umu.tds.model.Cancion;
import umu.tds.model.EstiloMusical;

public class PanelBuscar extends JPanel {

	private JTextField textBuscarInterprete;
	private JTextField textBuscarTitulo;
	private JTable tableResultadoBusqueda;
	private JComboBox<String> estilosDropdown;

	private AppMusic appMusic;
	private List<Cancion> cancionesMostradas; // Lista para almacenar las canciones mostradas actualmente

	public PanelBuscar() {

		appMusic = AppMusic.getInstance();

		setLayout(new BorderLayout());

		JPanel panelBuscar = new JPanel();
		panelBuscar.setBorder(new TitledBorder(null, "Buscar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelBuscar.setLayout(new GridLayout(3, 2, 5, 5));
		add(panelBuscar, BorderLayout.NORTH);

		textBuscarInterprete = new JTextField();
		textBuscarInterprete.setForeground(new Color(192, 192, 192));
		textBuscarInterprete.setText("intérprete");
		panelBuscar.add(textBuscarInterprete);
		textBuscarInterprete.setColumns(10);
		textBuscarInterprete.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textBuscarInterprete.getText().equals("intérprete")) {
					textBuscarInterprete.setText("");
				}
			}
		});

		textBuscarTitulo = new JTextField();
		textBuscarTitulo.setForeground(new Color(192, 192, 192));
		textBuscarTitulo.setText("título");
		panelBuscar.add(textBuscarTitulo);
		textBuscarTitulo.setColumns(10);
		textBuscarTitulo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textBuscarTitulo.getText().equals("título")) {
					textBuscarTitulo.setText("");
				}
			}
		});

		JCheckBox chckbxFavoritas = new JCheckBox("favoritas");
		panelBuscar.add(chckbxFavoritas);

		estilosDropdown = new JComboBox<>();
		cargarEstilos();
		estilosDropdown.setToolTipText("estilo");
		panelBuscar.add(estilosDropdown);

		JButton btnHacerBusqueda = new JButton("Buscar");
		btnHacerBusqueda.addActionListener(e -> buscar());
		panelBuscar.add(btnHacerBusqueda);

		JButton btnLimpiarBusqueda = new JButton("Limpiar búsqueda");
		btnLimpiarBusqueda.addActionListener(e -> mostrarTodasLasCanciones());
		panelBuscar.add(btnLimpiarBusqueda);

		JPanel panelResultadoBusqueda = new JPanel(new BorderLayout());
		tableResultadoBusqueda = new JTable();
		tableResultadoBusqueda.setModel(
				new DefaultTableModel(new Object[][] {}, new Object[] { "Título", "Intérprete", "Estilo", "" }) {
					@Override
					public Class<?> getColumnClass(int columnIndex) {
						return columnIndex == 3 ? Boolean.class : Object.class;
					}
				});

		// Añadir RowSorter a la tabla para habilitar la ordenación
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableResultadoBusqueda.getModel());
		tableResultadoBusqueda.setRowSorter(sorter);

		panelResultadoBusqueda.add(new JScrollPane(tableResultadoBusqueda), BorderLayout.CENTER);
		add(panelResultadoBusqueda, BorderLayout.CENTER);

		// Mostrar todas las canciones por defecto
		mostrarTodasLasCanciones();
	}

	private void cargarEstilos() {
		List<EstiloMusical> estilos = appMusic.getAllEstilosMusicales();
		estilosDropdown.addItem("");
		for (EstiloMusical estilo : estilos) {
			estilosDropdown.addItem(estilo.getNombre());
		}
	}
	
	private void buscar() {
		// TODO: Ahora mismo solo busca 1 interprete, pueden haber varios
		String tituloBusqueda = textBuscarTitulo.getText().trim().toLowerCase();
		String interpreteBusqueda = textBuscarInterprete.getText().trim().toLowerCase();
		String estiloSeleccionado = (String) estilosDropdown.getSelectedItem();
		
		cancionesMostradas = appMusic.queryListaCanciones(tituloBusqueda, interpreteBusqueda, estiloSeleccionado);
		
		actualizarTabla(cancionesMostradas);
	}

	private void mostrarTodasLasCanciones() {
		cancionesMostradas = appMusic.getAllCanciones();
		actualizarTabla(cancionesMostradas);
	}

	private void actualizarTabla(List<Cancion> canciones) {
		DefaultTableModel model = (DefaultTableModel) tableResultadoBusqueda.getModel();
		model.setRowCount(0); // Clear existing rows

		for (Cancion cancion : canciones) {
			String titulo = cancion.getTitulo();
			String interprete = cancion.getInterpretes().isEmpty() ? "" : cancion.getInterpretes().get(0).getNombre();
			String estilo = cancion.getEstilo().getNombre();
			model.addRow(new Object[] { titulo, interprete, estilo, false });
		}
	}

	public List<Cancion> getCancionesMostradas() {
		return cancionesMostradas;
	}

	public List<Cancion> getCancionesSeleccionadas() {
		DefaultTableModel model = (DefaultTableModel) tableResultadoBusqueda.getModel();
		return cancionesMostradas.stream().filter(c -> {
			for (int i = 0; i < model.getRowCount(); i++) {
				if ((Boolean) model.getValueAt(i, 3) && c.getTitulo().equals(model.getValueAt(i, 0))) {
					return true;
				}
			}
			return false;
		}).collect(Collectors.toList());
	}
}
