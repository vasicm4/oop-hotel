package view;

import java.awt.Dimension;
import java.awt.GridLayout;
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
	ArrayList<String> data;
	
	public boolean validateUsername(AdminManager adminManager) {
		boolean valid = true;
    	if (usernameField.getText().isEmpty()) {
    		valid = false;
    		error.setText("Username field cannot be empty");
		} else if (adminManager.find(usernameField.getText()) != null) {
			valid = false;
			error.setText("Username already exists");
		}
		return valid;
	}
	
	public boolean validateUsername(AgentManager agentManager) {
        boolean valid = true;
    	if (usernameField.getText().isEmpty()) {
    		valid = false;
    		error.setText("Username field cannot be empty");
        } else if (agentManager.find(usernameField.getText()) != null) {
            valid = false;
            error.setText("Username already exists");
        }
        return valid;
    }
	
	public boolean validateUsername(JanitorManager janitorManager) {
		boolean valid = true;
		if (usernameField.getText().isEmpty()) {
			valid = false;
			error.setText("Username field cannot be empty");
		} else if (janitorManager.find(usernameField.getText()) != null) {
			valid = false;
			error.setText("Username already exists");
		}
		return valid;
	}
	
	public boolean validateEmployee(boolean valid) {
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
		
		if (phone.getValue().toString().isEmpty()) {
    		valid = false;
    		error.setText("Phone field cannot be empty");
		} else if (phone.getValue().toString().length() < 8) {
			valid = false;
			error.setText("Phone number must be 9 characters long");
		}
		
		if (Integer.parseInt(experience.getValue().toString()) < 0) {
    		valid = false;
    		error.setText("Experience cannot be negative");
		} else if (Integer.parseInt(experience.getValue().toString()) > 100) {
			valid = false;
			error.setText("Experience cannot be more than 100 years");
		}
		
		if (Double.parseDouble(salary.getValue().toString()) < 0) {
			valid = false;
			error.setText("Salary cannot be negative");
		}
		
		if (LocalDate.parse(datePicker.getJFormattedTextField().getText()).isAfter(LocalDate.now())) {
			valid = false;
			error.setText("Date of birth cannot be in the future");
		}
		
		return valid;
		
    }
	
	public AddEmployee(ManagerManager managerManager, String type, ArrayList<String> data) {
		this.data = data;
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
		if (data.size() != 0) {
			usernameField.setText(data.get(0));
		}
		usernameField.setPreferredSize(new Dimension(700,40));
		usernameField.setFont(new java.awt.Font("Tahoma", 1, 25));
		usernamePanel.add(usernameField);
		this.add(usernamePanel);
	    
	    JPanel passwordPanel = new JPanel();
	    passwordPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Password"));
	    passwordField = new JPasswordField();
		if (data.size() != 0) {
	    	passwordField.setText(data.get(1));
	    }
	    passwordField.setPreferredSize(new Dimension(700, 40));
	    passwordField.setFont(new java.awt.Font("Tahoma", 1, 25));
	    passwordPanel.add(passwordField);
	    this.add(passwordPanel);
	    
	    JPanel namePanel = new JPanel();
	    namePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("First Name"));
	    nameField = new JTextField();
		if (data.size() != 0) {
	    	nameField.setText(data.get(2));
	    }
	    nameField.setPreferredSize(new Dimension(700, 40));
	    nameField.setFont(new java.awt.Font("Tahoma", 1, 25));
	    namePanel.add(nameField);
	    this.add(namePanel);
	    
	    JPanel surnamePanel = new JPanel();
	    surnamePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Last Name"));
	    surnameField = new JTextField();
		if (data.size() != 0) {
	    	surnameField.setText(data.get(3));
	    }
	    surnameField.setPreferredSize(new Dimension(700, 40));
	    surnameField.setFont(new java.awt.Font("Tahoma", 1, 25));
	    surnamePanel.add(surnameField);
	    this.add(surnamePanel);

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
	    this.add(datePanel);
	    
	    JPanel phonePanel = new JPanel();
	    phonePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Phone"));
	    phone = new JSpinner();
		if (data.size() != 0) {
			phone.setValue(Integer.parseInt(data.get(5)));
		}
	    phone.setPreferredSize(new Dimension(700, 40));
	    phonePanel.add(phone);
	    this.add(phonePanel);
	    
	    JPanel genderPanel = new JPanel();
	    genderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Gender"));
	    String[] genders = {"MALE", "FEMALE"};
	    gender = new JComboBox(genders);
		if (data.size() != 0) {
			gender.setSelectedItem(data.get(6));
		}
	    genderPanel.add(gender);
	    this.add(genderPanel);
	    
	    JPanel educationPanel = new JPanel();
	    educationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Education"));
	    String[] educations = {"HIGH_SCHOOL", "BACHELOR", "MASTER", "PHD"};
	    education = new JComboBox(educations);
		if (data.size() != 0) {
			education.setSelectedItem(data.get(7));
		}
	    educationPanel.add(education);
	    this.add(educationPanel);
	    
	    JPanel experiencePanel = new JPanel();
	    experiencePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Experience"));
	    experience = new JSpinner();
		if (data.size() != 0) {
        	experience.setValue(Integer.parseInt(data.get(8)));
        }
	    experience.setPreferredSize(new Dimension(700, 40));
	    experiencePanel.add(experience);
	    this.add(experiencePanel);
	    
	    JPanel salaryPanel = new JPanel();
	    salaryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Base Salary"));
	    salary = new JSpinner();
		if (data.size() != 0) {
			salary.setValue(Double.parseDouble(data.get(9)));
        }
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
	    buttonPanel.add(buttonSubmit);
	    buttonPanel.add(error);
	    this.add(buttonPanel);
	    
	    buttonSubmit.addActionListener(ActionEvent -> {
	    	if (type.equals("admins")) {
        		AdminManager adminManager = AdminManager.getInstance();
        		if (validateUsername(adminManager) || data.size() != 0) {
        			if (validateEmployee(true)) {
        				adminManager.add(usernameField.getText(), String.valueOf(passwordField.getPassword()), nameField.getText(), surnameField.getText(), LocalDate.parse(datePicker.getJFormattedTextField().getText()), Integer.parseInt(phone.getValue().toString()), Gender.getGender(gender.getSelectedItem().toString()), Education.getEducation(education.getSelectedItem().toString()), Integer.parseInt(experience.getValue().toString()), Double.parseDouble(salary.getValue().toString()));  
        				this.dispose();
        			}
        		}
	    	} else if (type.equals("agents")) {
	    		AgentManager agentManager = AgentManager.getInstance();
	    		if (validateUsername(agentManager) || data.size() != 0) {
	    			if (validateEmployee(true)) {
	    				agentManager.add(usernameField.getText(), String.valueOf(passwordField.getPassword()), nameField.getText(), surnameField.getText(), LocalDate.parse(datePicker.getJFormattedTextField().getText()), Integer.parseInt(phone.getValue().toString()), Gender.getGender(gender.getSelectedItem().toString()), Education.getEducation(education.getSelectedItem().toString()), Integer.parseInt(experience.getValue().toString()), Double.parseDouble(salary.getValue().toString()));  
	    				this.dispose();
	    			}
	    		}
	    	} else {
	    		JanitorManager janitorManager = JanitorManager.getInstance();
        		if (validateUsername(janitorManager)) {
        			if (validateEmployee(true) || data.size() != 0) {
        				janitorManager.add(usernameField.getText(), String.valueOf(passwordField.getPassword()), nameField.getText(), surnameField.getText(), LocalDate.parse(datePicker.getJFormattedTextField().getText()), Integer.parseInt(phone.getValue().toString()), Gender.getGender(gender.getSelectedItem().toString()), Education.getEducation(education.getSelectedItem().toString()), Integer.parseInt(experience.getValue().toString()), Double.parseDouble(salary.getValue().toString()));  
        				this.dispose();
        			}
        		}
	    	}	
	    });
	}
}

