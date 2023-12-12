package umu.tds.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class LoginView extends JFrame {

	private JPanel contentPane;
	private JTextField textUsername;
	private JTextField textPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
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
	public LoginView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorte = new JPanel();
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		JLabel lblTitulo = new JLabel("AppMusic");
		lblTitulo.setFont(new Font("Apple Braille", Font.PLAIN, 21));
		panelNorte.add(lblTitulo);
		
		JPanel panelCentral = new JPanel();
		panelCentral.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCampos = new JPanel();
		panelCentral.add(panelCampos, BorderLayout.CENTER);
		panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
		
		JPanel panelUsername = new JPanel();
		panelUsername.setBorder(new TitledBorder(null, "Email or username", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCampos.add(panelUsername);
		panelUsername.setLayout(new BorderLayout(0, 0));
		
		textUsername = new JTextField();
		panelUsername.add(textUsername, BorderLayout.CENTER);
		textUsername.setColumns(10);
		
		JPanel panelPassword = new JPanel();
		panelPassword.setBorder(new TitledBorder(null, "Password", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCampos.add(panelPassword);
		panelPassword.setLayout(new BorderLayout(0, 0));
		
		textPassword = new JTextField();
		panelPassword.add(textPassword, BorderLayout.CENTER);
		textPassword.setColumns(10);
		
		JPanel panelBotones = new JPanel();
		panelCentral.add(panelBotones, BorderLayout.SOUTH);
		panelBotones.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBotonesLogin = new JPanel();
		panelBotones.add(panelBotonesLogin, BorderLayout.WEST);
		
		JButton btnLogin = new JButton("Login");
		panelBotonesLogin.add(btnLogin);
		
		JButton btnGitHubLogin = new JButton("GitHub");
		panelBotonesLogin.add(btnGitHubLogin);
		
		JPanel panelBotonesRegistro = new JPanel();
		panelBotones.add(panelBotonesRegistro, BorderLayout.EAST);
		
		JButton btnRegister = new JButton("Register");
		panelBotonesRegistro.add(btnRegister);
	}

}
