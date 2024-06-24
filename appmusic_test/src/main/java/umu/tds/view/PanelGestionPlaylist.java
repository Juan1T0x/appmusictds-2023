package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class PanelGestionPlaylist extends JPanel {

	private JTextField textTituloPlaylist;
	private JTable tableCanciones;
	private DefaultTableModel modeloTabla;

	public PanelGestionPlaylist() {
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

		// Lógica para crear la playlist
		JOptionPane.showMessageDialog(this, "Playlist '" + titulo + "' creada correctamente", "Éxito",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void handleEliminarPlaylist() {
		int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar esta playlist?",
				"Confirmación", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			// Lógica para eliminar la playlist
			JOptionPane.showMessageDialog(this, "Playlist eliminada correctamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
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
}
