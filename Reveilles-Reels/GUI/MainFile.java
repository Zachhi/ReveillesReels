import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;

public class MainFile{
	public static void main(String[] args) {

		Scanner keyboard = new Scanner(System.in);

		// ask the user which view they want and take it in as a char
		System.out.println("Enter V for client view or A for content analyst: ");
		char input = keyboard.next().charAt(0);		//error handling for incorrect input prolly needs to be done

		

		if (input == 'V') //For client
		{ 
			System.out.println("Enter your Client ID: ");
			String clientID = keyboard.next();
			ClientManager clientGUI = new ClientManager(clientID); // Creating ClientManager
			clientGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Function to set default operation of JFrame.
			clientGUI.setVisible(true); // Function to set visibility of JFrame.
		} 
		else if (input == 'A') //For analyst
		{ 
			AnalystManager analystGUI = new AnalystManager(); // Creating ClientManager
			analystGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Function to set default operation of JFrame.
			analystGUI.setVisible(true); // Function to set visibility of JFrame.
		} 
		else //For neither
		{
			System.out.println("Invalid input. Exiting.");
			return;
		}

	}
}