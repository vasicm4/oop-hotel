package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import hotel.Service;
import manager.ManagerManager;
import manager.ReservationManager;
import manager.RoomManager;
import manager.RoomTypeManager;
import rooms.ReservationStatus;
import rooms.Room;
import rooms.RoomType;
import users.Guest;

public class AddReservation extends JFrame implements ActionListener{
	
	JDatePickerImpl startDatePicker;
	JDatePickerImpl endDatePicker;
	JComboBox<String> roomTypeCombobox;
	String username;
	JButton buttonSubmit;
	JLabel error;
	ArrayList<Service> selectedServices;
	ArrayList<String> selectedAdditionalServices;
	
	public boolean validateInput() {
		error.setText("");
		boolean valid = true;
		if (startDatePicker.getModel().getValue() == null) {
			valid =  false;
			error.setText("Please enter a start date");
		} else if (LocalDate.parse(startDatePicker.getJFormattedTextField().getText()).isBefore(LocalDate.now())) {
			error.setText("Please enter a valid start date");
			valid = false;
		} else if (LocalDate.parse(startDatePicker.getJFormattedTextField().getText()).equals(LocalDate.now())) {
			error.setText("Please enter a valid start date");
			valid = false;
		}
		
		if (endDatePicker.getModel().getValue() == null) {
			error.setText("Please enter a end date");
			valid = false;
		} else if (LocalDate.parse(endDatePicker.getJFormattedTextField().getText()).isBefore(LocalDate.parse(startDatePicker.getJFormattedTextField().getText()))) {
			error.setText("Please enter a valid end date");
			valid = false;
		} else if (LocalDate.parse(endDatePicker.getJFormattedTextField().getText()).equals(LocalDate.parse(startDatePicker.getJFormattedTextField().getText()))) {
			error.setText("Please enter a valid end date");
			valid = false;
		}
		
		if (roomTypeCombobox.getSelectedItem() == null) {
			error.setText("Please select a room type");
			valid = false;
		}
		
		return valid;
		
	}
	

	public AddReservation(ManagerManager managerManager, String username, ArrayList<String> data){
		
		this.username = username;
		Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        UtilDateModel model = new UtilDateModel();
        model.setSelected(true); // Ensure model is initialized with a selected date
        JDatePanelImpl startDateJPanel = new JDatePanelImpl(model, properties);
        if (data.size() != 0) {
			model.setDate(Integer.parseInt(data.get(1).split("-")[0]), Integer.parseInt(data.get(1).split("-")[1]) - 1,
					Integer.parseInt(data.get(1).split("-")[2]));        
			}
        startDatePicker = new JDatePickerImpl(startDateJPanel, new AbstractFormatter() {
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
        
        JPanel startDatePanel = new JPanel();
        startDatePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("StartDate"));
        startDatePanel.add(startDatePicker);
	    this.add(startDatePanel);
	    
		properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        model = new UtilDateModel();
        if (data.size() != 0) {
			model.setDate(Integer.parseInt(data.get(2).split("-")[0]), Integer.parseInt(data.get(2).split("-")[1]) - 1,
					Integer.parseInt(data.get(2).split("-")[2]));
		}
        model.setSelected(true);
        JDatePanelImpl endDateJPanel = new JDatePanelImpl(model, properties);
		endDatePicker = new JDatePickerImpl(endDateJPanel, new AbstractFormatter() {
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
		
		JPanel endDatePanel = new JPanel();
		endDatePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("EndDate"));
		endDatePanel.add(endDatePicker);
		this.add(endDatePanel);
		
		
		JPanel roomTypePanel = new JPanel();
		roomTypePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Room Type"));
		RoomTypeManager roomTypeManager = ManagerManager.getRoomTypeManager();
		HashMap<String, RoomType> roomTypes = roomTypeManager.getRoomTypes();
		String[] comboBox = new String[roomTypes.size()];
		int i = 0;
		for (String type : roomTypes.keySet()) {
			comboBox[i] = type;
			i++;
		}
		roomTypeCombobox = new JComboBox<String>(comboBox);
		if (data.size() != 0) {
			roomTypeCombobox.setSelectedItem(data.get(4));
		}
		roomTypePanel.add(roomTypeCombobox);
		this.add(roomTypePanel);
		
		JPanel additionalServicesPanel = new JPanel();
		additionalServicesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Additional Services"));
		additionalServicesPanel.setLayout(new GridLayout(2, 3));
		this.add(additionalServicesPanel);
		selectedAdditionalServices = new ArrayList<String>();
		for (String service : ManagerManager.getRoomManager().getAllAdditionalServices()) {
			JCheckBox checkBox = new JCheckBox(service);
			checkBox.setSelected(false);
			additionalServicesPanel.add(checkBox);
			checkBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (checkBox.isSelected()) {
						selectedAdditionalServices.add(service);
					} else {
						selectedAdditionalServices.remove(service);
					}
					roomTypeCombobox.removeAllItems();
					for (String type : ManagerManager.getRoomManager().findRoomTypes(selectedAdditionalServices)) {
						roomTypeCombobox.addItem(type);
					}
				}
			});
		}
		
		JPanel servicesPanel = new JPanel();
		servicesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Services"));
		selectedServices = new ArrayList<Service>();
		for (Service service : ManagerManager.getServiceManager().getServices().values()) {
			if (service.isDeleted() == false) {
				JCheckBox checkBox = new JCheckBox(service.getType());
				checkBox.setSelected(true);
				selectedServices.add(service);
				servicesPanel.add(checkBox);
				checkBox.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (checkBox.isSelected()) {
							selectedServices.add(service);
						} else {
							selectedServices.remove(service);
						}
					}
				});
			}
		}
		servicesPanel.setLayout(new GridLayout(2, 3));
		this.add(servicesPanel);
		
		JPanel buttonPanel = new JPanel();
	    buttonSubmit = new JButton("Submit");
	    buttonSubmit.addActionListener(this);
	    buttonPanel.add(buttonSubmit);
	    buttonPanel.setLayout(new GridLayout(2, 1));
	    error = new JLabel("");
	    error.setForeground(new java.awt.Color(255, 0, 0));
	    error.setFont(new java.awt.Font("Tahoma", 1, 20));
	    error.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	    buttonPanel.add(error);
	    
	    this.add(buttonPanel);
		
        this.setTitle("Hotel");
        this.setSize(400, 400);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(new java.awt.Color(222, 224, 223));
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(6, 1));
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == buttonSubmit) {
			if (validateInput()) {
				ReservationManager reservationManager = ManagerManager.getReservationManager();
				RoomType roomType = ManagerManager.getRoomTypeManager().getRoomType(String.valueOf(roomTypeCombobox.getSelectedItem()));
				Guest guest = ManagerManager.getGuestManager().find(username);
				reservationManager.add(LocalDate.parse(startDatePicker.getJFormattedTextField().getText()), LocalDate.parse(endDatePicker.getJFormattedTextField().getText()),roomType, guest, selectedServices, ReservationStatus.ON_HOLD, false);

				this.dispose();
			};
		}
	}

}
