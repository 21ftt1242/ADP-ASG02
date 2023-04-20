import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JLabel;

public class StartScreen extends JPanel implements ActionListener {

	final JButton btnLogin, btnRegister, btnViewRoom;
	
	public StartScreen() throws IOException {

		setLayout(null);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayoutManager.showPage(3);
			}
		});
		btnLogin.setBounds(280, 181, 155, 56);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 22));
		add(btnLogin);
		
		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayoutManager.showPage(2);
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnRegister.setBounds(280, 276, 155, 56);
		add(btnRegister);
		
		JLabel lblHotel = new JLabel("Hotel Room Booking System");
		lblHotel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblHotel.setBounds(170, 60, 384, 91);
		add(lblHotel);
		
		btnViewRoom = new JButton("View Room");
		btnViewRoom.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnViewRoom.setBounds(280, 368, 155, 56);
		add(btnViewRoom);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
