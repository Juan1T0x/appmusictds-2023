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

public class RegistroView extends JFrame {

	private JPanel contentPane;
	private JTextField textUser;
	private JTextField textPassword;
	private JTextField textEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroView frame = new RegistroView();
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
	public RegistroView() {
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
		contentPane.add(panelCentral, BorderLayout.CENTER);
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
		
		textPassword = new JTextField();
		panelPassword.add(textPassword, BorderLayout.CENTER);
		textPassword.setColumns(10);
		
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
		
		JPanel panelBotones = new JPanel();
		panelCentral.add(panelBotones, BorderLayout.SOUTH);
		
		JButton btnRegister = new JButton("Register");
		panelBotones.add(btnRegister);
	}

}
