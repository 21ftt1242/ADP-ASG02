import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.security.*;
import java.sql.*;


public class LoginPage extends JPanel implements ActionListener {
	
	//Database (IGNORE)
	String username = "";
	String password = "";
	
	//Error dialog
	JFrame message = new JFrame();
	
	//Text fields
	private JTextField inputEmail;
	private JPasswordField inputPassword;
	
	private String encryptedPassword = null;

	//String to store converted JPasswordFields
	private String userPassword;

	
	//------------------------------------------------------------------------------------------------------------
	public LoginPage() {
		//Absolute Layout
		setLayout(null);
		
		//----------Labels------------------------------------------------------
		
		//Title
		JLabel lblTitle = new JLabel("Sign in to your account");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTitle.setBounds(210, 11, 260, 50);
		add(lblTitle);
		
		//Email
		JLabel lblEmailAddress = new JLabel("Email Address:");
		lblEmailAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEmailAddress.setBounds(147, 99, 216, 27);
		add(lblEmailAddress);

		//Password
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(147, 200, 111, 27);
		add(lblPassword);

		//----------Input------------------------------------------------------
		
		//Input Email
		inputEmail = new JTextField();
		inputEmail.setBounds(147, 137, 414, 35);
		add(inputEmail);
		inputEmail.setColumns(10);

		//Input Password
		inputPassword = new JPasswordField();
		inputPassword.setEchoChar('*');
		inputPassword.setColumns(10);
		inputPassword.setBounds(147, 247, 414, 35);
		add(inputPassword);
		
		//----------Buttons------------------------------------------------------
		
		//Cancel button
		JButton btnCancelLogin = new JButton("Back");
		btnCancelLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayoutManager.showPage(1);
			}
		});
		btnCancelLogin.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnCancelLogin.setBounds(147, 330, 142, 42);
		add(btnCancelLogin);
		
		//Sign-in button
		JButton btnSignIn = new JButton("Sign in");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btnSignIn.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnSignIn.setBounds(334, 330, 142, 42);
		add(btnSignIn);
	}

	
	//Login button method---------------------------------------------------------------------------------
	public void login() {
		
		// Connection related code goes inside this method because before, 
		// the connection does not reach inside the method's try/catch statement
		
		ResultSet resultSet = null;
		Connection connection = null;
		PreparedStatement preparedStatement;
		
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connection = DriverManager.getConnection("jdbc:mysql://localhost/hbs", username, password);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			String email = inputEmail.getText(); //convert inputField from email to String
			encryptPassword(); //run Password Encryption method
			preparedStatement = connection.prepareStatement("SELECT CustomerEmail, CustomerPassword FROM customer "
					+ "WHERE CustomerEmail ='" + email + "'");

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				if(resultSet.getString(2).equals(encryptedPassword)) {
					JOptionPane.showMessageDialog(message, "Login succesful!");
					break;
				} else {
					JOptionPane.showMessageDialog(message, "Incorrect email or password.");
					break;
				}
			} 

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//MD5 Encryption Process---------------------------------------------------------------------------------
		public void encryptPassword() {
			try {
				//Message Digest instance for MD5
				MessageDigest m = MessageDigest.getInstance("MD5");

				//Add plain-text password bytes to digest using MD5 update() method
				userPassword = String.valueOf(inputPassword.getPassword());
				m.update(userPassword.getBytes());

				//Convert the has value into bytes
				byte[] bytes = m.digest();

				//The bytes array has bytes in decimal form. Converting it into hexadecimal format
				StringBuilder s = new StringBuilder();
				for(int i = 0; i<bytes.length; i++) {
					s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
				}

				//Complete hashed password in hexadecimal format
				encryptedPassword = s.toString();

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
