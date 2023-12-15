package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VentanaGestionPlayList extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public VentanaGestionPlayList() {
		setSize(new Dimension(800, 550));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 801, 540);
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
		
		JPanel panel_lateral = new JPanel();
		contentPane.add(panel_lateral, BorderLayout.WEST);
		panel_lateral.setLayout(new GridLayout(5, 0, 0, 0));
		
		JButton btnBuscar = new JButton("Buscar");
		panel_lateral.add(btnBuscar);
		
		JButton btnGestionPlaylist = new JButton("Gestión Playlists");
		btnGestionPlaylist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_lateral.add(btnGestionPlaylist);
		
		JButton btnRecientes = new JButton("Recientes");
		panel_lateral.add(btnRecientes);
		
		JButton btnMisPlaylist = new JButton("Mis Playlists");
		panel_lateral.add(btnMisPlaylist);
	}

}
