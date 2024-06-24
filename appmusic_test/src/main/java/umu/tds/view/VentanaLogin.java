package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import umu.tds.controller.AppMusic;

public class VentanaLogin {

	private JFrame frmLogin;
	private JTextField textUsuario;
	private JPasswordField textPassword;
	private JPanel panelLogin;
	private AppMusic appMusic;

	public VentanaLogin() {
		appMusic = AppMusic.getInstance();
		initialize();
	}

	public void mostrarVentana() {
		frmLogin.setLocationRelativeTo(null);
		frmLogin.setVisible(true);
	}

	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login AppMusic");
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.setLocationRelativeTo(null);
		frmLogin.getContentPane().setLayout(new BorderLayout());
		crearPanelTitulo();
		crearPanelLogin();
		frmLogin.setResizable(false);
		frmLogin.pack();
	}

	private void crearPanelTitulo() {
		JPanel panel_Norte = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 15));
		frmLogin.getContentPane().add(panel_Norte, BorderLayout.NORTH);

		JLabel lblTitulo = new JLabel("AppMusic");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitulo.setForeground(Color.DARK_GRAY);
		panel_Norte.add(lblTitulo);
	}

	private void crearPanelLogin() {
		panelLogin = new JPanel(new BorderLayout(0, 0));
		panelLogin.setBorder(new EmptyBorder(10, 10, 10, 10));
		frmLogin.getContentPane().add(panelLogin, BorderLayout.SOUTH);

		panelLogin.add(crearPanelCampos(), BorderLayout.NORTH);
		panelLogin.add(crearPanelBotones(), BorderLayout.SOUTH);
	}

	private JPanel crearPanelCampos() {
		JPanel panelCampos = new JPanel();
		panelCampos.setBorder(new TitledBorder(null, "Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));

		panelCampos.add(crearPanelCampo("Usuario: ", textUsuario = new JTextField(15)));
		panelCampos.add(crearPanelCampo("Contrase\u00F1a: ", textPassword = new JPasswordField(15)));

		return panelCampos;
	}

	private JPanel crearPanelCampo(String labelText, JTextField textField) {
		JPanel panelCampo = new JPanel(new BorderLayout(0, 0));

		JLabel label = new JLabel(labelText);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelCampo.add(label, BorderLayout.WEST);

		panelCampo.add(textField, BorderLayout.EAST);

		return panelCampo;
	}

	private JPanel crearPanelBotones() {
		JPanel panelBotones = new JPanel(new BorderLayout(0, 0));
		panelBotones.setBorder(new EmptyBorder(5, 0, 5, 0));

		JPanel panelBotonesLoginRegistro = new JPanel();
		panelBotones.add(panelBotonesLoginRegistro, BorderLayout.WEST);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleLogin();
			}
		});
		panelBotonesLoginRegistro.add(btnLogin);

		JButton btnRegistro = new JButton("Registro");
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleRegistro();
			}
		});
		panelBotonesLoginRegistro.add(btnRegistro);

		JButton btnGitHub = new JButton("GitHub");
		btnGitHub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleGitHubLogin();
			}
		});
		panelBotonesLoginRegistro.add(btnGitHub);

		JPanel panelBotonSalir = new JPanel();
		panelBotones.add(panelBotonSalir, BorderLayout.EAST);

		return panelBotones;
	}

	private void handleLogin() {
		String usuario = textUsuario.getText();
		String password = new String(textPassword.getPassword());

		if (usuario.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(frmLogin, "Por favor, completa todos los campos", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		boolean autenticado = appMusic.login(usuario, password);

		if (autenticado) {
			frmLogin.dispose();
			VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
			ventanaPrincipal.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(frmLogin, "Nombre de usuario o contraseña no válido", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void handleRegistro() {
		frmLogin.dispose();
		VentanaRegistro ventanaRegistro = new VentanaRegistro();
		ventanaRegistro.mostrarVentana();
	}

	private void handleGitHubLogin() {
		String usuario = textUsuario.getText();
		String password = new String(textPassword.getPassword());

		boolean autenticado = appMusic.loginConGitHub(usuario, password);

		if (autenticado) {
			JOptionPane.showMessageDialog(frmLogin, "Inicio de sesión exitoso con GitHub", "Login",
					JOptionPane.INFORMATION_MESSAGE);
			frmLogin.dispose();
			VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
			ventanaPrincipal.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(frmLogin, "Nombre de usuario o contraseña no válido", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
