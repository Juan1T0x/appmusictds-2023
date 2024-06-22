package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

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

import com.toedter.calendar.JCalendar;

import umu.tds.controller.AppMusic;

public class VentanaLogin {

	private JFrame frmLogin;
	private JTextField textUsuario;
	private JPasswordField textPassword;
	private JPanel panelLogin;
	private JTextField textUser, textEmail;
	private JPasswordField passwordField;
	private JCalendar calendar;

	/**
	 * Create the application.
	 */
	public VentanaLogin() {
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
		JPanel panel_Norte = new JPanel();
		frmLogin.getContentPane().add(panel_Norte, BorderLayout.NORTH);
		panel_Norte.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));

		JLabel lblTitulo = new JLabel("AppMusic");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitulo.setForeground(Color.DARK_GRAY);
		panel_Norte.add(lblTitulo);
	}

	private void crearPanelLogin() {
		panelLogin = new JPanel();
		panelLogin.setBorder(new EmptyBorder(10, 10, 10, 10));
		frmLogin.getContentPane().add(panelLogin, BorderLayout.SOUTH);
		panelLogin.setLayout(new BorderLayout(0, 0));

		panelLogin.add(crearPanelCampos(), BorderLayout.NORTH);
		panelLogin.add(crearPanelBotones(), BorderLayout.SOUTH);
	}

	private JPanel crearPanelCampos() {
		JPanel panelCampos = new JPanel();
		panelCampos.setBorder(new TitledBorder(null, "Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));

		// Panel Campo Login
		JPanel panelCampoUsuario = new JPanel();
		panelCampos.add(panelCampoUsuario);
		panelCampoUsuario.setLayout(new BorderLayout(0, 0));

		JLabel lblUsuario = new JLabel("Usuario: ");
		panelCampoUsuario.add(lblUsuario);
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));

		textUsuario = new JTextField();
		panelCampoUsuario.add(textUsuario, BorderLayout.EAST);
		textUsuario.setColumns(15);

		// Panel Campo Password
		JPanel panelCampoPassword = new JPanel();
		panelCampos.add(panelCampoPassword);
		panelCampoPassword.setLayout(new BorderLayout(0, 0));

		JLabel lblPassword = new JLabel("Contrase\u00F1a: ");
		panelCampoPassword.add(lblPassword);
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));

		textPassword = new JPasswordField();
		panelCampoPassword.add(textPassword, BorderLayout.EAST);
		textPassword.setColumns(15);

		return panelCampos;
	}

	private JPanel crearPanelBotones() {
		JPanel panelBotones = new JPanel();
		panelBotones.setBorder(new EmptyBorder(5, 0, 5, 0));
		panelBotones.setLayout(new BorderLayout(0, 0));

		JPanel panelBotonesLoginRegistro = new JPanel();
		panelBotones.add(panelBotonesLoginRegistro, BorderLayout.WEST);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean autenticado = true;

				if (autenticado) {
					// Cierra la ventana actual
					frmLogin.dispose();
					// Abre la nueva ventana principal
					VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
					ventanaPrincipal.setVisible(true);
				} else {
					// Muestra un mensaje de error si las credenciales no son correctas
					JOptionPane.showMessageDialog(frmLogin, "Nombre de usuario o contraseña no válido", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		panelBotonesLoginRegistro.add(btnLogin);

		JButton btnRegistro = new JButton("Registro");
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmLogin.remove(panelLogin);
				frmLogin.getContentPane().add(panelRegistro(), BorderLayout.CENTER);
				frmLogin.setSize(500, 410);
				frmLogin.revalidate();
				frmLogin.repaint();
			}
		});

		panelBotonesLoginRegistro.add(btnRegistro);

		JButton btnGitHub = new JButton("GitHub");
		btnGitHub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginConGitHub();
			}
		});
		panelBotonesLoginRegistro.add(btnGitHub);

		JPanel panelBotonSalir = new JPanel();
		panelBotones.add(panelBotonSalir, BorderLayout.EAST);

		return panelBotones;
	}

	private JPanel panelRegistro() {
		JPanel panelCentral = new JPanel();
		frmLogin.setTitle("Registro AppMusic");

		panelCentral.setLayout(new BorderLayout(0, 0));

		JPanel panelCampos = new JPanel();
		panelCentral.add(panelCampos, BorderLayout.CENTER);
		panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));

		JPanel panelUser = new JPanel();
		panelUser.setBorder(new TitledBorder(null, "User", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCampos.add(panelUser);
		panelUser.setLayout(new BorderLayout(0, 0));

		textUser = new JTextField();
		panelUser.add(textUser, BorderLayout.CENTER);
		textUser.setColumns(10);

		JPanel panelPassword = new JPanel();
		panelPassword.setBorder(new TitledBorder(null, "Password", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCampos.add(panelPassword);
		panelPassword.setLayout(new BorderLayout(0, 0));

		passwordField = new JPasswordField();
		panelPassword.add(passwordField, BorderLayout.NORTH);

		JPanel panelEmail = new JPanel();
		panelEmail.setBorder(new TitledBorder(null, "Email", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCampos.add(panelEmail);
		panelEmail.setLayout(new BorderLayout(0, 0));

		textEmail = new JTextField();
		panelEmail.add(textEmail, BorderLayout.CENTER);
		textEmail.setColumns(10);

		JPanel panelBirth = new JPanel();
		panelBirth.setBorder(new TitledBorder(null, "Birth Date", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCampos.add(panelBirth);
		panelBirth.setLayout(new BorderLayout(0, 0));

		calendar = new JCalendar();
		panelBirth.add(calendar, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel();
		panelCentral.add(panelBotones, BorderLayout.SOUTH);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Vuelve a la ventana de inicio de sesión
				frmLogin.remove(panelCentral);
				frmLogin.getContentPane().add(panelLogin, BorderLayout.CENTER);
				frmLogin.setSize(300, 250); // Ajusta el tamaño según tus necesidades
				frmLogin.revalidate();
				frmLogin.repaint();
			}
		});
		panelBotones.add(btnVolver);

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Usuario: " + textUser.getText() + ", Contraseña: " + passwordField.getText()
						+ ", email: " + textEmail.getText() + ", fechaNac: " + calendar.getDate().toString());
				String email = textEmail.getText();
				Date fechaNac = calendar.getDate();
				String user = textUser.getText();
				String password = passwordField.getText();
				boolean premium = false; // TODO

				if (email == null || fechaNac == null || user == null || password == null) {
					JOptionPane.showMessageDialog(panelBotones, "Parámetros incorrectos, intentelo de nuevo", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					AppMusic.getUnicaInstancia().registrarUsuario(email, fechaNac, user, password, premium);
					JOptionPane.showMessageDialog(panelBotones, "Usuario dado de alta", "Registrar usuario",
							JOptionPane.PLAIN_MESSAGE);
				}

				textEmail.setText("");
				textUser.setText(password);
			}
		});
		panelBotones.add(btnRegister);

		return panelCentral;
	}

	private void loginConGitHub() {
		String usuario = textUsuario.getText();
		String password = new String(textPassword.getPassword());

		boolean autenticado = AppMusic.getUnicaInstancia().loginConGitHub(usuario, password);

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

	private void addManejadorBotonSalir(JButton btnSalir) {
	}

	//
	// private void addManejadorBotonRegistro(JButton btnRegistro) {
	// btnRegistro.addActionListener(new ActionListener() {
	// public void actionPerformed(ActionEvent e) {
	// RegistroView registroView = new RegistroView();
	// registroView.mostrarVentana();
	// frmLogin.dispose();
	// }
	// });
	// }
	//
	// private void addManejadorBotonLogin(JButton btnLogin) {
	// btnLogin.addActionListener(new ActionListener() {
	// public void actionPerformed(ActionEvent e) {
	// boolean login =
	// Controlador.getUnicaInstancia().loginUsuario(textUsuario.getText(),
	// new String(textPassword.getPassword()));
	//
	// if (login) {
	// VentanaPrincipal window = new VentanaPrincipal();
	// window.mostrarVentana();
	// frmLogin.dispose();
	// } else
	// JOptionPane.showMessageDialog(frmLogin, "Nombre de usuario o contrase�a no
	// valido",
	// "Error", JOptionPane.ERROR_MESSAGE);
	// }
	// });
	// }
}
