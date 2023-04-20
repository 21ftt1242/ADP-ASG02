import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.security.*;
import java.sql.*;
import java.time.LocalDate;


public class RegistrationPage extends JPanel implements ActionListener {

	//Database (IGNORE)
	String username = "";
	String password = "";

	//Error dialog
	JFrame message = new JFrame();

	//Text fields
	private JTextField inputFullname;
	private JPasswordField inputPassword;
	private JPasswordField inputConfirmPassword;
	private JTextField inputEmail;
	private JTextField inputPhoneNo;

	//Strings to store converted JPasswordFields
	private String userPassword;
	private String confirmPass;

	//String to store encrypted password, placed here as two methods uses this variable
	String encryptedPassword = null;

	//------------------------------------------------------------------------------------------------------------
	public RegistrationPage() {

		//Absolute Layout
		setLayout(null);

		//----------Labels------------------------------------------------------
		
		//Register
		JLabel lblRegister = new JLabel("Register Now");
		lblRegister.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblRegister.setBounds(263, 11, 165, 50);
		add(lblRegister);
		
		//Name
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblName.setBounds(147, 99, 111, 27);
		add(lblName);
		
		//Email
		JLabel lblEmail = new JLabel("Email Address:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEmail.setBounds(147, 188, 177, 27);
		add(lblEmail);
		
		//Password
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(147, 276, 111, 27);
		add(lblPassword);
		
		//Confirm password
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblConfirmPassword.setBounds(147, 361, 223, 27);
		add(lblConfirmPassword);

		//Phone number
		JLabel lblPhoneNumber = new JLabel("Phone Number:");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPhoneNumber.setBounds(147, 454, 223, 27);
		add(lblPhoneNumber);

		//----------Input------------------------------------------------------

		//Input Fullname
		inputFullname = new JTextField();
		inputFullname.setBounds(147, 137, 414, 35);
		add(inputFullname);
		inputFullname.setColumns(10);

		//Input Email
		inputEmail = new JTextField();
		inputEmail.setColumns(10);
		inputEmail.setBounds(147, 219, 414, 35);
		add(inputEmail);

		//Input Password
		inputPassword = new JPasswordField();
		inputPassword.setEchoChar('*');
		inputPassword.setColumns(10);
		inputPassword.setBounds(147, 302, 414, 35);
		add(inputPassword);

		//Input Confirm Password
		inputConfirmPassword = new JPasswordField();
		inputConfirmPassword.setEchoChar('*');
		inputConfirmPassword.setColumns(10);
		inputConfirmPassword.setBounds(147, 396, 414, 35);
		add(inputConfirmPassword);

		//Input Phone Number
		inputPhoneNo = new JTextField();
		inputPhoneNo.setColumns(10);
		inputPhoneNo.setBounds(147, 492, 414, 35);
		add(inputPhoneNo);


		//----------Buttons------------------------------------------------------

		//Cancel Button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayoutManager.showPage(1); // Go to 
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnCancel.setBounds(182, 556, 142, 42);
		add(btnCancel);

		//Confirm Button
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register(); //run confirm button method
			}
		});
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnConfirm.setBounds(364, 556, 142, 42);
		add(btnConfirm);

	}

	// Confirm button method-----------------------------------------------------------------------------------
	public void register() {

		// Connection related code goes inside this method because before, 
		// the connection does not reach inside the method's try/catch statement

		ResultSet resultSet = null;
		Connection connection = null;

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

		// Make variables to store the textField's input
		String fullName = inputFullname.getText();
		String email = inputEmail.getText();
		String phoneNo = inputPhoneNo.getText();

		// Date of account created (get current date)
		LocalDate currentDate = LocalDate.now();

		// Convert the JPasswordField to actual strings
		userPassword = String.valueOf(inputPassword.getPassword());
		confirmPass = String.valueOf(inputConfirmPassword.getPassword());

		if (userPassword.equals(confirmPass)) {

			// Password gets encrypted using this method
			encryptPassword();
			PreparedStatement preparedStatement;


			try {
				preparedStatement = connection.prepareStatement("INSERT INTO customer "
						+ "(CustomerName, CustomerEmail, CustomerPassword, CustomerPhoneNo, DateCreated) "
						+ "values('" + fullName + "', '" + email + "', '" + encryptedPassword + "', '" + phoneNo + "', '" + currentDate + "')");

				preparedStatement.executeUpdate(); //executeUpdate for INSERT, UPDATE or DELETE
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(message, "Password does not match!");
		}
	}

	//MD5 Encryption Process---------------------------------------------------------------------------------
	public void encryptPassword() {
		try {
			//Message Digest instance for MD5
			MessageDigest m = MessageDigest.getInstance("MD5");

			//Add plain-text password bytes to digest using MD5 update() method
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
