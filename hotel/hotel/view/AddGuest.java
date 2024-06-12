package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import manager.GuestManager;
import manager.ManagerManager;
import users.Gender;

public class AddGuest extends JFrame implements ActionListener{
	JTextField usernameField;
	JTextField nameField;
	JTextField surnameField;
	JPasswordField passwordField;
	JSpinner phone;
	JComboBox<String> gender;
	JDatePickerImpl datePicker;
	JLabel error;
	JFrame AddGuest = this;
	
	public boolean validateUsername(GuestManager guestManager) {
		boolean valid = true;
    	if (usernameField.getText().isEmpty()) {
    		valid = false;
		} else if (guestManager.find(usernameField.getText()) != null) {
			valid = false;
		} else if (usernameField.getText().contains("@") == false || usernameField.getText().contains(".") == false) {
			valid = false;
		}
		return valid;
	}
	
	public boolean validateGuest(boolean valid) {
		error.setText("");
		if (nameField.getText().isEmpty()) {
    		valid = false;
    		error.setText("Name field cannot be empty");
		}
		
		if (surnameField.getText().isEmpty()) {
    		valid = false;
    		error.setText("Surname field cannot be empty");
		}
		
		if (passwordField.getPassword().length == 0) {
    		valid = false;
    		error.setText("Password field cannot be empty");
		} else if (passwordField.getPassword().length < 8) {
			valid = false;
			error.setText("Password must be at least 8 characters long");
		}
		
		try {
			Integer.parseInt(String.valueOf(passwordField.getPassword()));
        } catch (NumberFormatException e) {
        	valid = false;
        	error.setText("Password must be a number");
		}
		
		if (phone.getValue().toString().isEmpty()) {
    		valid = false;
    		error.setText("Phone field cannot be empty");
		} else if (phone.getValue().toString().length() < 8) {
			valid = false;
			error.setText("Phone number must be 9 characters long");
		}
		
		if (LocalDate.parse(datePicker.getJFormattedTextField().getText()).isAfter(LocalDate.now())) {
			valid = false;
			error.setText("Date of birth cannot be in the future");
		}
		
		return valid;
		
    }
	
	public AddGuest(ManagerManager managerManager, ArrayList<String> data) {
		JFrame addEmployeeFrame = new JFrame();
		addEmployeeFrame.setTitle("Add new guest");
		addEmployeeFrame.setSize(800, 800);
		addEmployeeFrame.setVisible(true);
		addEmployeeFrame.setResizable(false);
	    addEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    addEmployeeFrame.setLocationRelativeTo(null);
	    addEmployeeFrame.setLayout(new GridLayout(8, 1));
	    
	    JPanel usernamePanel = new JPanel();
	    usernamePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Username"));
		usernameField = new JTextField();
		if (data.size() != 0) {
			usernameField.setText(data.get(0));
		}
		usernameField.setPreferredSize(new Dimension(700,40));
		usernameField.setFont(new java.awt.Font("Tahoma", 1, 25));
		usernamePanel.add(usernameField);
	    addEmployeeFrame.add(usernamePanel);
	    
	    JPanel passwordPanel = new JPanel();
	    passwordPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Password"));
	    passwordField = new JPasswordField();
	    if (data.size() != 0) {
	    	passwordField.setText(data.get(1));
	    }
	    passwordField.setPreferredSize(new Dimension(700, 40));
	    passwordField.setFont(new java.awt.Font("Tahoma", 1, 25));
	    passwordPanel.add(passwordField);
	    addEmployeeFrame.add(passwordPanel);
	    
	    JPanel namePanel = new JPanel();
	    namePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("First Name"));
	    nameField = new JTextField();
		if (data.size() != 0) {
			nameField.setText(data.get(2));
		}
	    nameField.setPreferredSize(new Dimension(700, 40));
	    nameField.setFont(new java.awt.Font("Tahoma", 1, 25));
	    namePanel.add(nameField);
	    addEmployeeFrame.add(namePanel);
	    
	    JPanel surnamePanel = new JPanel();
	    surnamePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Last Name"));
	    surnameField = new JTextField();
        if (data.size() != 0) {
        	surnameField.setText(data.get(3));
        }
	    surnameField.setPreferredSize(new Dimension(700, 40));
	    surnameField.setFont(new java.awt.Font("Tahoma", 1, 25));
	    surnamePanel.add(surnameField);
	    addEmployeeFrame.add(surnamePanel);

        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        UtilDateModel model = new UtilDateModel();
		if (data.size() != 0) {
			model.setDate(Integer.parseInt(data.get(4).split("-")[0]), Integer.parseInt(data.get(4).split("-")[1]) - 1,
					Integer.parseInt(data.get(4).split("-")[2]));
		}
        model.setSelected(true); // Ensure model is initialized with a selected date

        JDatePanelImpl dateOfBirthPanel = new JDatePanelImpl(model, properties);

        datePicker = new JDatePickerImpl(dateOfBirthPanel, new AbstractFormatter() {
            @Override
            public String valueToString(Object value) throws ParseException {
                if (value != null) {
                    Calendar cal = (Calendar) value;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    return format.format(cal.getTime());
                }
                return "";
            }

            @Override
            public Object stringToValue(String text) throws ParseException {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.setTime(format.parse(text));
                return cal;
            }
        });
        
        JPanel datePanel = new JPanel();
        datePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Date of Birth"));
	    datePanel.add(datePicker);
        addEmployeeFrame.add(datePanel);
	    
	    JPanel phonePanel = new JPanel();
	    phonePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Phone"));
	    phone = new JSpinner();
		if (data.size() != 0) {
			phone.setValue(Integer.parseInt(data.get(5)));
		}
	    phone.setPreferredSize(new Dimension(700, 40));
	    phonePanel.add(phone);
	    addEmployeeFrame.add(phonePanel);
	    
	    JPanel genderPanel = new JPanel();
	    genderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Gender"));
	    
	    gender = new JComboBox<String>();
	    gender.addItem("MALE");
	    gender.addItem("FEMALE");
	    genderPanel.add(gender);
	    addEmployeeFrame.add(genderPanel);
	 
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new GridLayout(2, 1));
	    JButton buttonSubmit = new JButton("Submit");
	    error = new JLabel("");
	    error.setFont(new java.awt.Font("Tahoma", 1, 25));
	    error.setForeground(java.awt.Color.red);
	    error.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	    buttonPanel.add(error);
	    buttonPanel.add(buttonSubmit);
	    addEmployeeFrame.add(buttonPanel);
	    
	    buttonSubmit.addActionListener(ActionEvent -> {
	    	GuestManager guestManager = ManagerManager.getGuestManager();
	    	boolean valid = true;
			if (data.size() == 0) {
				valid = validateUsername(guestManager);
			}
	    	valid = validateGuest(valid);
	    	if (valid) {
	    		guestManager.add(usernameField.getText(), String.valueOf(passwordField.getPassword()), nameField.getText(), surnameField.getText(), LocalDate.parse(datePicker.getJFormattedTextField().getText()), Integer.parseInt(phone.getValue().toString()), Gender.getGender(String.valueOf(gender.getSelectedItem())));
	    		addEmployeeFrame.dispose();
	    	}
	    });
}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub		
	}
}
