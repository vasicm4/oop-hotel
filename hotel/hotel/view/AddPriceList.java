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
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import hotel.Service;
import manager.ManagerManager;
import manager.PriceListManager;
import rooms.RoomType;

public class AddPriceList extends JFrame implements ActionListener{
	
	ManagerManager managerManager;
	JDatePickerImpl startDatePicker;
	JDatePickerImpl endDatePicker;
	JButton buttonAdd;
	JLabel error;
	HashMap<Service, JTextField> servicePrices = new HashMap<Service, JTextField>();
	HashMap<RoomType, JTextField> roomTypePrices = new HashMap<RoomType, JTextField>();
	
	public boolean validateInput() {
		error.setText("");
		boolean valid = true;
		if (startDatePicker.getModel().getValue() == null) {
			valid =  false;
			error.setText("Start date must be set");
		} else if (LocalDate.now().isBefore(LocalDate.parse(startDatePicker.getJFormattedTextField().getText()))) {
			valid =  false;
			error.setText("Start date must be in the future");
		} 
		
		if (!valid) {
			return valid;
		}
		
		if (endDatePicker.getModel().getValue() == null) {
			valid = false;
			error.setText("End date must be set");
		} else if ((LocalDate.parse(endDatePicker.getJFormattedTextField().getText()).isBefore(LocalDate.parse(startDatePicker.getJFormattedTextField().getText())))) {
			valid = false;
			error.setText("End date must be after start date");
		} else if ((LocalDate.parse(endDatePicker.getJFormattedTextField().getText()).equals(LocalDate.parse(startDatePicker.getJFormattedTextField().getText())))) {
			valid = false;
			error.setText("End date must be after start date");
		}
		
		if (!valid) {
			return valid;
		}
		
		for (JTextField price : servicePrices.values()) {
			if (price.getText().isEmpty()) {
				valid = false;
				error.setText("All service prices must be set");
			} else if (Double.parseDouble(price.getText()) < 0) {
				valid = false;
				error.setText("Service prices must be positive");
			}
		}
		
		if (!valid) {
			return valid;
		}
		
		for (JTextField price : roomTypePrices.values()) {
			if (price.getText().isEmpty()) {
				valid = false;
				error.setText("All room type prices must be set");
			} else if (Double.parseDouble(price.getText()) < 0) {
				valid = false;
				error.setText("Room type prices must be positive");
			}
		}
		
		return valid;
		
	}
	
	public AddPriceList(ManagerManager managerManager) {
		this.managerManager = managerManager;
		
		Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        UtilDateModel model = new UtilDateModel();
        model.setSelected(true); // Ensure model is initialized with a selected date
        JDatePanelImpl startDateJPanel = new JDatePanelImpl(model, properties);

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
        
		properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        model = new UtilDateModel();
        model.setSelected(true); // Ensure model is initialized with a selected date
        JDatePanelImpl endDateJPanel = new JDatePanelImpl(model, properties);
        
        JPanel startDatePanel = new JPanel();
        startDatePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("StartDate"));
        startDatePanel.add(startDatePicker);
	    this.add(startDatePanel);
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
		
		JPanel services = new JPanel();
		services.setBorder(javax.swing.BorderFactory.createTitledBorder("Services"));
		services.setLayout(new GridLayout(2, ManagerManager.getServiceManager().getActiveServices().size()/2));
		for (Service service : ManagerManager.getServiceManager().getActiveServices().values()) {
			JPanel priceService = new JPanel();
			priceService.setBorder(javax.swing.BorderFactory.createTitledBorder(service.getType()));
			JTextField priceField = new JTextField();
			priceField.setPreferredSize(new java.awt.Dimension(300, 30));
			servicePrices.put(service, priceField);
			priceService.add(priceField);
			services.add(priceService);
		}
		this.add(services);
		
		JPanel rooms = new JPanel();
		rooms.setBorder(javax.swing.BorderFactory.createTitledBorder("Rooms"));
		rooms.setLayout(new GridLayout(2, ManagerManager.getRoomTypeManager().getAvailableRoomTypes().size()/2));
		for (RoomType roomType : ManagerManager.getRoomTypeManager().getAvailableRoomTypes().values()) {
			JPanel priceRoomType = new JPanel();
			priceRoomType.setBorder(javax.swing.BorderFactory.createTitledBorder(roomType.getType()));
			JTextField priceField = new JTextField();
			priceField.setPreferredSize(new java.awt.Dimension(300, 30));
			roomTypePrices.put(roomType, priceField);
			priceRoomType.add(priceField);
			rooms.add(priceRoomType);
		}
		this.add(rooms);
		
		buttonAdd = new JButton("Add");
		buttonAdd.addActionListener(this);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1));
	    error = new JLabel("");
	    error.setFont(new java.awt.Font("Tahoma", 1, 25));
	    error.setForeground(java.awt.Color.red);
	    error.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	    buttonPanel.add(error);
		buttonPanel.add(buttonAdd);
		this.add(buttonPanel);
		
		this.setTitle("Add new Price List");
		this.setSize(1000, 800);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(5, 1));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonAdd) {
			if (validateInput()) {
				// TODO add price list
				PriceListManager priceListManager = ManagerManager.getPriceListManager();
				HashMap<Service, Double> services = new HashMap<Service, Double>();
				HashMap<RoomType, Double> roomTypes = new HashMap<RoomType, Double>();
				for (Service service : servicePrices.keySet()) {
					services.put(service, Double.parseDouble(servicePrices.get(service).getText()));
				}
				for (RoomType roomType : roomTypePrices.keySet()) {
					roomTypes.put(roomType, Double.parseDouble(roomTypePrices.get(roomType).getText()));
				}
				priceListManager.add(LocalDate.parse(startDatePicker.getJFormattedTextField().getText()), LocalDate.parse(endDatePicker.getJFormattedTextField().getText()), services, roomTypes);
			}
		}
	}
	
}
