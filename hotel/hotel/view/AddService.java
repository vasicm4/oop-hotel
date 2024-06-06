package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import manager.ManagerManager;

public class AddService extends JFrame implements ActionListener{
	ManagerManager managerManager;
	JTextField typeField;
	JButton addServiceButton;
	JLabel error;
	
	public AddService(ManagerManager managerManager) {	
		
		JPanel typePanel = new JPanel();
		typePanel.setBorder(BorderFactory.createTitledBorder("Type"));
		typeField = new JTextField();
		typeField.setPreferredSize(new Dimension(250,50));
		typeField.setFont(new java.awt.Font("Tahoma", 1, 25));
		typePanel.add(typeField);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1));
	    error = new JLabel("");
	    error.setFont(new java.awt.Font("Tahoma", 1, 25));
	    error.setForeground(java.awt.Color.red);
	    error.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	    buttonPanel.add(error);
		addServiceButton = new JButton("Add service");
        addServiceButton.setBounds(10,0, 50, 50);
		
		this.managerManager = managerManager;
        this.setTitle("Hotel");
        this.setSize(300, 300);
        this.setVisible(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new java.awt.Color(222, 224, 223));
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(2, 1));
        this.add(typePanel);
        this.add(addServiceButton);
        this.addServiceButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addServiceButton) {
			// TODO implement add service
			if (typeField.getText().equals("")) {
				error.setText("Type field is empty");
				return;
			}
			ManagerManager.getServiceManager().add(typeField.getText());
			this.dispose();
		}
	}
}
