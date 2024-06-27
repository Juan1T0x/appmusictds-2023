package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JCalendar;

import umu.tds.controller.AppMusic;
import umu.tds.validation.ValidationException;

public class VentanaRegistro {

	private JFrame frmRegistro;
	private JTextField textUser, textEmail;
	private JPasswordField passwordField;
	private JCalendar calendar;
	private AppMusic appMusic;

	public VentanaRegistro() {
		appMusic = AppMusic.getInstance();
		initialize();
	}

	public void mostrarVentana() {
		frmRegistro.setLocationRelativeTo(null);
		frmRegistro.setVisible(true);
	}

	private void initialize() {
		frmRegistro = new JFrame();
		frmRegistro.setTitle("Registro AppMusic");
		frmRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRegistro.setLocationRelativeTo(null);
		frmRegistro.getContentPane().setLayout(new BorderLayout());
		frmRegistro.add(crearPanelRegistro(), BorderLayout.CENTER);
		frmRegistro.setSize(500, 410);
		frmRegistro.setResizable(false);
	}

	private JPanel crearPanelRegistro() {
		JPanel panelCentral = new JPanel(new BorderLayout(0, 0));

		JPanel panelCampos = new JPanel();
		panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
		panelCentral.add(panelCampos, BorderLayout.CENTER);

		panelCampos.add(crearPanelCampo("User", textUser = new JTextField(10)));
		panelCampos.add(crearPanelCampo("Password", passwordField = new JPasswordField(10)));
		panelCampos.add(crearPanelCampo("Email", textEmail = new JTextField(10)));

		JPanel panelBirth = new JPanel(new BorderLayout(0, 0));
		panelBirth.setBorder(new TitledBorder(null, "Birth Date", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCampos.add(panelBirth);

		calendar = new JCalendar();
		panelBirth.add(calendar, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel();
		panelCentral.add(panelBotones, BorderLayout.SOUTH);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleVolver();
			}
		});
		panelBotones.add(btnVolver);

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleRegister(panelBotones);
			}
		});
		panelBotones.add(btnRegister);

		return panelCentral;
	}

	private JPanel crearPanelCampo(String titulo, JTextField textField) {
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		panel.setBorder(new TitledBorder(null, titulo, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(textField, BorderLayout.CENTER);
		return panel;
	}

	private void handleVolver() {
		frmRegistro.dispose();
		VentanaLogin ventanaLogin = new VentanaLogin();
		ventanaLogin.mostrarVentana();
	}

	private void handleRegister(JPanel panelBotones) {
		String email = textEmail.getText();
		Date fechaNac = calendar.getDate();
		String user = textUser.getText();
		String password = new String(passwordField.getPassword()); // Convert password field to string
		boolean premium = false; // TODO

		try {
			// Check for empty fields
			if (email.isEmpty() || fechaNac == null || user.isEmpty() || password.isEmpty()) {
				throw new IllegalArgumentException("Todos los campos son obligatorios.");
			}

			// Attempt to register the user
			boolean registrado = appMusic.registrarUsuario(email, fechaNac, user, password, premium);
			if (registrado) {
				JOptionPane.showMessageDialog(panelBotones, "Usuario dado de alta", "Registrar usuario",
						JOptionPane.PLAIN_MESSAGE);
				frmRegistro.dispose();
				VentanaLogin ventanaLogin = new VentanaLogin();
				ventanaLogin.mostrarVentana();
			} else {
				JOptionPane.showMessageDialog(panelBotones, "No se pudo registrar el usuario", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (ValidationException e) {
			JOptionPane.showMessageDialog(panelBotones, e.getMessage(), "Error de validación",
					JOptionPane.ERROR_MESSAGE);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(panelBotones, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
