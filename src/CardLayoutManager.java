import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class CardLayoutManager {

	static JFrame mainWindow = new JFrame("Hotel Room Booking");
	static CardLayout cl;
	static JPanel container;

	//Main method
	//throws the three because of music related imports
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		System.out.println("test");
		//Set JFrame properties
		mainWindow.setSize(720, 820);
		mainWindow.setVisible(true);
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Introduce new layout
		cl = new CardLayout();
		container = new JPanel(cl); //makes the container inherit card layout
		mainWindow.add(container); //Inside JFrame now have a JPanel with a Card Layout

		//import MainMenu java class
		StartScreen startPage = new StartScreen();
		container.add(startPage, "StartScreen"); //adds StartScreen page inside container
		
		//import GameRules java class
		RegistrationPage registerPage = new RegistrationPage();
		container.add(registerPage, "RegisterPage"); //adds RegistrationPage inside container
		
		LoginPage pageLogin = new LoginPage();
		container.add(pageLogin, "LoginPage"); //adds LoginPage inside container
		
		//Responsible for showing the very first screen of the application
		showPage(1);
	}
	
	//assigns number to pages, can be used in other java files/classes
	public static void showPage(int pageNumber) {
		switch(pageNumber) {
		case 1: cl.show(container, "StartScreen"); 
		break;
		case 2: cl.show(container, "RegisterPage"); 
		break;
		case 3: cl.show(container, "LoginPage"); 
		break;
		}
	}
}
