package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import hotel.Service;
import manager.ManagerManager;
import rooms.RoomType;

public class AddPriceList extends JFrame implements ActionListener{
	
	ManagerManager managerManager;
	JDatePickerImpl startDatePicker;
	JDatePickerImpl endDatePicker;
	JButton buttonAdd;
	JLabel error;
	
	public boolean validateInput() {
		boolean valid = true;
		if (startDatePicker.getModel().getValue() == null) {
			valid =  false;
			error.setText("Start date must be set");
		} else if (LocalDate.now().isBefore((LocalDate) startDatePicker.getModel().getValue())) {
			valid =  false;
			error.setText("Start date must be in the future");
		} 
		
		if (endDatePicker.getModel().getValue() == null) {
			valid = false;
			error.setText("End date must be set");
		} else if (((LocalDate) endDatePicker.getModel().getValue())
				.isBefore((LocalDate) startDatePicker.getModel().getValue())) {
			valid = false;
			error.setText("End date must be after start date");
		} else if (((LocalDate) endDatePicker.getModel().getValue())
				.isEqual((LocalDate) startDatePicker.getModel().getValue())) {
			valid = false;
			error.setText("End date must be after start date");
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
        
        JPanel startDatePanel = new JPanel();
        startDatePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("StartDate"));
        startDatePanel.add(startDatePicker);
	    this.add(startDatePanel);
	    
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
		
		JPanel services = new JPanel();
		services.setBorder(javax.swing.BorderFactory.createTitledBorder("Services"));
		int i = 0;
		for (Service service : ManagerManager.getServiceManager().getServices().values()) {
			JPanel priceService = new JPanel();
			priceService.setBorder(javax.swing.BorderFactory.createTitledBorder(service.getType()));
			// TODO add price field
			services.add(priceService);
			i++;
		}
		this.add(services);
		
		JPanel rooms = new JPanel();
		rooms.setBorder(javax.swing.BorderFactory.createTitledBorder("Rooms"));
		i = 0;
		for (RoomType roomType : ManagerManager.getRoomTypeManager().getRoomTypes().values()) {
			JPanel priceRoomType = new JPanel();
			priceRoomType.setBorder(javax.swing.BorderFactory.createTitledBorder(roomType.getType()));
			// TODO add price field
			services.add(priceRoomType);
			i++;
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
		this.setSize(800, 800);
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
			}
		}
	}
	
}
