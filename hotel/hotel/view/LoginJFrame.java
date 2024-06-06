package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import manager.ManagerManager;

public class LoginJFrame extends JFrame implements ActionListener{
	JTextField usernameField;
	JPasswordField passwordField;
	JLabel error;
	JButton loginButton;
	ManagerManager managerManager;
	
	public LoginJFrame(ManagerManager managerManager) {
		
		//title for the login page
		JLabel login = new JLabel("Login");
		login.setFont(new java.awt.Font("Tahoma", 1, 30));
		login.setHorizontalAlignment(JLabel.CENTER);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new java.awt.Color(222, 224, 223));
		loginPanel.setBounds(0, 0, 400, 100);
		loginPanel.setLayout(new BorderLayout());
		loginPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		loginPanel.add(login);
		
		//input form

		JPanel usernamePanel = new JPanel();
		usernamePanel.setBorder(BorderFactory.createTitledBorder("Username"));

		usernameField = new JTextField();
		usernameField.setPreferredSize(new Dimension(300,50));
//		usernameField.setBounds(100,100,100,100);
		usernameField.setFont(new java.awt.Font("Tahoma", 1, 25));
		usernamePanel.add(usernameField);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setBorder(BorderFactory.createTitledBorder("Password"));
		
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(300, 50));
		passwordField.setFont(new java.awt.Font("Tahoma", 1, 25));
		passwordPanel.add(passwordField);
		
	    JPanel buttonPanel = new JPanel();
	    loginButton = new JButton("Submit");
	    loginButton.addActionListener(this);
	    buttonPanel.setLayout(new GridLayout(2,1));
	    buttonPanel.add(loginButton);
	    error = new JLabel("");
	    error.setFont(new java.awt.Font("Tahoma", 1, 15));
	    error.setForeground(new java.awt.Color(255,0,0));
	    error.setHorizontalAlignment(JLabel.CENTER);
	    buttonPanel.add(error);
	    this.add(buttonPanel);
		
		//back panel
		JPanel backPanel = new JPanel();
		backPanel.setBackground(new java.awt.Color(255,255,255));
		backPanel.setPreferredSize(new Dimension(450,450));
		backPanel.setBorder(BorderFactory.createEmptyBorder());
		backPanel.setLayout(new GridLayout(4,1,0,0));
		backPanel.add(loginPanel);
		backPanel.add(usernamePanel);
		backPanel.add(passwordPanel);
		backPanel.add(buttonPanel);

		
		this.setTitle("Hotel");
		this.setSize(450,450);
		this.setVisible(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(new java.awt.Color(222, 224, 223));
		this.setLocationRelativeTo(null);
		this.add(backPanel);
		
	}
	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		if (e.getSource() == loginButton) {
			String username = usernameField.getText();
			char[] password = passwordField.getPassword();
			if (ManagerManager.login(username, password) == "admin") {
				this.dispose();
				new AdminJFrame(managerManager);
			} else if (ManagerManager.login(username, password) == "guest") {
				this.dispose();
				new GuestJFrame(managerManager, username);
			} else if (ManagerManager.login(username, password) == "janitor") {
				this.dispose();
				new JanitorJFrame(managerManager, username);
			} else if (ManagerManager.login(username, password) == "agent") {
				this.dispose();
				new AgentJFrame(managerManager);
			} else if (ManagerManager.login(username, password) == "password"){
				error.setText("Wrong password");
			} else {
				error.setText("Wrong username");
			}
			
		}
	}
}
