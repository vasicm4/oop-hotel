package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

import manager.AdminManager;
import manager.AgentManager;
import manager.JanitorManager;
import manager.ManagerManager;
import users.Education;
import users.Gender;

public class AddEmployee extends JFrame{
	
	JTextField usernameField;
	JTextField nameField;
	JTextField surnameField;
	JPasswordField passwordField;
	JSpinner phone;
	JComboBox<Gender> gender;
	JComboBox<Education> education;
	JSpinner experience;
	JSpinner salary;
	JDatePickerImpl datePicker;
	JLabel error;
	
	public boolean validateUsername(AdminManager adminManager) {
		boolean valid = true;
    	if (usernameField.getText().isEmpty()) {
    		valid = false;
		} else if (adminManager.find(usernameField.getText()) != null) {
			valid = false;
		}
		return valid;
	}
	
	public boolean validateUsername(AgentManager agentManager) {
        boolean valid = true;
    	if (usernameField.getText().isEmpty()) {
    		valid = false;
        } else if (agentManager.find(usernameField.getText()) != null) {
            valid = false;
        }
        return valid;
    }
	
	public boolean validateUsername(JanitorManager janitorManager) {
		boolean valid = true;
		if (usernameField.getText().isEmpty()) {
			valid = false;
		} else if (janitorManager.find(usernameField.getText()) != null) {
			valid = false;
		}
		return valid;
	}
	
	public boolean validateEmployee(boolean valid) {
		
		if (nameField.getText().isEmpty()) {
    		valid = false;
		}
		
		if (surnameField.getText().isEmpty()) {
    		valid = false;
		}
		
		if (passwordField.getPassword().length == 0) {
    		valid = false;
		} else if (passwordField.getPassword().length < 8) {
			valid = false;
		}
		
		if (phone.getValue().toString().isEmpty()) {
    		valid = false;
		} else if (phone.getValue().toString().length() != 9) {
			valid = false;
		}
		
		if (Integer.parseInt(experience.getValue().toString()) < 0) {
    		valid = false;
		} else if (Integer.parseInt(experience.getValue().toString()) > 100) {
			valid = false;
		}
		
		if (Integer.parseInt(salary.getValue().toString()) < 0) {
			valid = false;
		}
		
		if (LocalDate.parse(datePicker.getJFormattedTextField().getText()).isAfter(LocalDate.now())) {
			valid = false;
		}
		
		return valid;
		
    }
	
	public AddEmployee(ManagerManager managerManager, String type) {

		this.setTitle("Add new employee");
		this.setSize(800, 800);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(11, 1));
	    
	    JPanel usernamePanel = new JPanel();
	    usernamePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Username"));
		usernameField = new JTextField();
		usernameField.setPreferredSize(new Dimension(700,40));
		usernameField.setFont(new java.awt.Font("Tahoma", 1, 25));
		usernamePanel.add(usernameField);
		this.add(usernamePanel);
	    
	    JPanel passwordPanel = new JPanel();
	    passwordPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Password"));
	    passwordField = new JPasswordField();
	    passwordField.setPreferredSize(new Dimension(700, 40));
	    passwordField.setFont(new java.awt.Font("Tahoma", 1, 25));
	    passwordPanel.add(passwordField);
	    this.add(passwordPanel);
	    
	    JPanel namePanel = new JPanel();
	    namePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("First Name"));
	    nameField = new JTextField();
	    nameField.setPreferredSize(new Dimension(700, 40));
	    nameField.setFont(new java.awt.Font("Tahoma", 1, 25));
	    namePanel.add(nameField);
	    this.add(namePanel);
	    
	    JPanel surnamePanel = new JPanel();
	    surnamePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Last Name"));
	    surnameField = new JTextField();
	    surnameField.setPreferredSize(new Dimension(700, 40));
	    surnameField.setFont(new java.awt.Font("Tahoma", 1, 25));
	    surnamePanel.add(surnameField);
	    this.add(surnamePanel);

        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        UtilDateModel model = new UtilDateModel();
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
	    this.add(datePanel);
	    
	    JPanel phonePanel = new JPanel();
	    phonePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Phone"));
	    phone = new JSpinner();
	    phone.setPreferredSize(new Dimension(700, 40));
	    phonePanel.add(phone);
	    this.add(phonePanel);
	    
	    JPanel genderPanel = new JPanel();
	    genderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Gender"));
	    String[] genders = {"MALE", "FEMALE"};
	    gender = new JComboBox(genders);
	    genderPanel.add(gender);
	    this.add(genderPanel);
	    
	    JPanel educationPanel = new JPanel();
	    educationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Education"));
	    String[] educations = {"HIGH_SCHOOL", "BACHELOR", "MASTER", "PHD"};
	    education = new JComboBox(educations);
	    educationPanel.add(education);
	    this.add(educationPanel);
	    
	    JPanel experiencePanel = new JPanel();
	    experiencePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Experience"));
	    experience = new JSpinner();
	    experience.setPreferredSize(new Dimension(700, 40));
	    experiencePanel.add(experience);
	    this.add(experiencePanel);
	    
	    JPanel salaryPanel = new JPanel();
	    salaryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Base Salary"));
	    salary = new JSpinner();
	    salary.setPreferredSize(new Dimension(700, 40));
	    salaryPanel.add(salary);
	    this.add(salaryPanel);
	 
	    JPanel buttonPanel = new JPanel();
	    JButton buttonSubmit = new JButton("Submit");
	    error = new JLabel("");
	    error.setFont(new java.awt.Font("Tahoma", 1, 25));
	    error.setForeground(java.awt.Color.RED);
	    error.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	    buttonPanel.setLayout(new GridLayout(2, 1));
	    buttonPanel.add(error);
	    buttonPanel.add(buttonSubmit);
	    this.add(buttonPanel);
	    
	    buttonSubmit.addActionListener(ActionEvent -> {
	    	if (type.equals("admin")) {
        		AdminManager adminManager = AdminManager.getInstance();
        		if (validateUsername(adminManager)) {
        			if (validateEmployee(true)) {
        				adminManager.add(usernameField.getText(), String.valueOf(passwordField.getPassword()), nameField.getText(), surnameField.getText(), LocalDate.parse(datePicker.getJFormattedTextField().getText()), Integer.parseInt(phone.getValue().toString()), Gender.getGender(gender.getSelectedItem().toString()), Education.getEducation(education.getSelectedItem().toString()), Integer.parseInt(experience.getValue().toString()), Integer.parseInt(salary.getValue().toString()));  
        				this.dispose();
        			}
        		}
	    	} else if (type.equals("agent")) {
	    		AgentManager agentManager = AgentManager.getInstance();
	    		if (validateUsername(agentManager)) {
	    			if (validateEmployee(true)) {
	    				agentManager.add(usernameField.getText(), String.valueOf(passwordField.getPassword()), nameField.getText(), surnameField.getText(), LocalDate.parse(datePicker.getJFormattedTextField().getText()), Integer.parseInt(phone.getValue().toString()), Gender.getGender(gender.getSelectedItem().toString()), Education.getEducation(education.getSelectedItem().toString()), Integer.parseInt(experience.getValue().toString()), Integer.parseInt(salary.getValue().toString()));  
	    				this.dispose();
	    			}
	    		}
	    	} else {
	    		JanitorManager janitorManager = JanitorManager.getInstance();
        		if (validateUsername(janitorManager)) {
        			if (validateEmployee(true)) {
        				janitorManager.add(usernameField.getText(), String.valueOf(passwordField.getPassword()), nameField.getText(), surnameField.getText(), LocalDate.parse(datePicker.getJFormattedTextField().getText()), Integer.parseInt(phone.getValue().toString()), Gender.getGender(gender.getSelectedItem().toString()), Education.getEducation(education.getSelectedItem().toString()), Integer.parseInt(experience.getValue().toString()), Integer.parseInt(salary.getValue().toString()));  
        				this.dispose();
        			}
        		}
	    	}	
	    });
	}
}

