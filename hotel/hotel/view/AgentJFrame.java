package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import hotel.Service;
import manager.CleaningManager;
import manager.GuestManager;
import manager.ManagerManager;
import manager.ReservationManager;
import rooms.Reservation;
import rooms.ReservationStatus;
import rooms.Room;
import rooms.RoomStatus;
import rooms.RoomType;
import users.Guest;

public class AgentJFrame extends JFrame implements ActionListener{
	JFrame frame;
	ManagerManager managerManager;
	JPanel tablePanel;
	JMenuItem guestsItem;
	JMenuItem reservationsItem;
	JMenuItem roomsItem;
	JMenuItem dailyOccupancyItem;
	
	JPanel upperPanel;
	ArrayList<Service> selectedServices;
	ArrayList<RoomType> roomTypes;
	ArrayList<Room> rooms;
	HashMap<Double, Double> priceRange;
	ArrayList<String> data;
	JTable table;
	String screen = "reservations";
	String filter = "";
	
	
	
	public JPanel tableGuests() {
		tablePanel = new JPanel();
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		GuestManager guestManager = ManagerManager.guestManager;
		String[] columnNames = new String[] {"Username","Password","First Name", "Last Name", "Date of Birth", "Phone", "Gender"};
		HashMap<String, Guest> guests = guestManager.getGuests();
		String[][] data = new String[guests.size()][7];
		int i = 0;
		for (Guest guest : guests.values()) {
			if (guest.isDeleted()) {
				continue;
			}
			data[i][0] = guest.getUsername();
			data[i][1] = guest.getPassword();
			data[i][2] = guest.getName();
			data[i][3] = guest.getSurname();
			data[i][4] = String.valueOf(guest.getDateOfBirth());
			data[i][5] = String.valueOf(guest.getPhoneNumber());
			data[i][6] = String.valueOf(guest.getGender());
			i++;
		}
		table = new JTable(data, columnNames);	
		table.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(900, 800));
		tablePanel.add(scrollPane);
		return tablePanel;
	}
	
	public JPanel tableReservations() {
		tablePanel = new JPanel();
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		String[] columnNames = { "Username", "Start Date", "End Date", "Room", "RoomType", "Status", "Services", "Price"};
		
		ReservationManager reservationManager = ManagerManager.reservationManager;
		ArrayList<Reservation> reservations = reservationManager.getAvailableReservations();
		ArrayList<Reservation> reservationsCopy = new ArrayList<Reservation>();
		reservationsCopy.addAll(reservations);
		if (selectedServices.size() != 0 || roomTypes.size() != 0 || rooms.size() != 0 || priceRange.size() != 0) {
			for (Reservation reservation : reservations) {
				if (!reservation.getServices().containsAll(selectedServices) && selectedServices.size() != 0) {
					reservationsCopy.remove(reservation);
				}
				if (!roomTypes.contains(reservation.getRoomType()) && roomTypes.size() != 0) {
					reservationsCopy.remove(reservation);
				}
				if (!rooms.contains(reservation.getRoom()) && rooms.size() != 0) {
					reservationsCopy.remove(reservation);
				}
				if (priceRange.size() != 0) {
					boolean found = false;
					for (Double key : priceRange.keySet()) {
						if (reservation.getPrice(ManagerManager.getPriceListManager()) >= key && reservation.getPrice(ManagerManager.getPriceListManager()) <= priceRange.get(key)) {
							found = true;
							break;
						}
					}
					if (!found) {
						reservationsCopy.remove(reservation);
					}
				}
			}
		}
		reservations = reservationsCopy;
		for (int i = 0; i < reservations.size(); i++) {
			for (int j = i + 1; j < reservations.size(); j++) {
				if (reservations.get(i).getPrice(ManagerManager.getPriceListManager()) < reservations.get(j).getPrice(ManagerManager.getPriceListManager())) {
					Reservation temp = reservations.get(i);
					reservations.set(i, reservations.get(j));
					reservations.set(j, temp);	
				} else {
					continue;
				}
			}
		}
        String[][] data = new String[reservations.size()][8];
		int i = 0;
		for (Reservation reservation : reservations) {
			data[i][0] = reservation.getGuest().getUsername();
			data[i][1] = reservation.getCheckIn().toString();
			data[i][2] = reservation.getCheckOut().toString();
			if (reservation.getRoom() == null) {
				data[i][3] = "";
			} else {
				data[i][3] = reservation.getRoom().toString();
			}
			data[i][4] = reservation.getRoomType().toString();
			data[i][5] = ReservationStatus.getStatus(reservation.getStatus());
			if (reservation.getServices() != null) {
				data[i][6] = reservation.getServices().toString();
			} else {
				data[i][6] = "";
			}
			data[i][7] = String.valueOf(reservation.getPrice(ManagerManager.getPriceListManager()));
			i++;
		}
		table = new JTable(data, columnNames);
		table.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(900, 800));
		scrollPane.setVisible(true);
		add(scrollPane);
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		return tablePanel;
	}

	
	
	public JPanel tableDailyOccupancy(LocalDate date) {
		tablePanel = new JPanel();
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		String[] columnNames = { "Username", "Start Date", "End Date", "Room", "RoomType", "Status", "Services", "Price"};

		ReservationManager reservationManager = ManagerManager.reservationManager;
		HashMap<String, Reservation> reservations = reservationManager.getDailyReservations(date);
		String[][] data = new String[reservations.size() + 1][8];
		int i = 0;
		for (Reservation reservation : reservations.values()) {
			data[i][0] = reservation.getGuest().getUsername();
			data[i][1] = reservation.getCheckIn().toString();
			data[i][2] = reservation.getCheckOut().toString();
			if (reservation.getRoom() == null) {
				data[i][3] = "";
			} else {
				data[i][3] = reservation.getRoom().toString();
			}
			data[i][4] = reservation.getRoomType().toString();
			data[i][5] = ReservationStatus.getStatus(reservation.getStatus());
			if (reservation.getServices() != null) {
				data[i][6] = reservation.getServices().toString();
			} else {
				data[i][6] = "";
			}
			data[i][7] = String.valueOf(reservation.getPrice(ManagerManager.getPriceListManager()));
			i++;
		}
		data[reservations.size()][0] = "Daily Occupancy";
		data[reservations.size()][7] = String.valueOf(reservationManager.getOccupiedRooms(date));
		
		table = new JTable(data, columnNames);
		table.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(900, 800));
		tablePanel.add(scrollPane);
		return tablePanel;
	}
	
	public JPanel tableRooms() {
		tablePanel = new JPanel();
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		String[] columnNames = {"Room Number", "Room Type", "Number of Beds", "Additional services", "Status"};
		HashMap<String,Room> rooms = ManagerManager.roomManager.getRooms();
		String[][] data = new String[rooms.size()][5];
		int i = 0;
		for (Room room : rooms.values()) {
			data[i][0] = String.valueOf(room.getNumber());
			data[i][1] = String.valueOf(room.getType());
			data[i][2] = String.valueOf(room.getFloor());
			data[i][3] = String.valueOf(room.getAdditionalServices());
			data[i][4] = String.valueOf(room.getStatus());
			i++;
		}
		table = new JTable(data, columnNames);
		table.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1000, 800));
		tablePanel.add(scrollPane);
		return tablePanel;
	}
	
	public void updateTablePanel() {
		if (tablePanel != null) {
			this.remove(tablePanel);
		}
		if (screen == "guests") {
			tablePanel = tableGuests();
		} else if (screen == "reservations") {
			filter = "";
			selectedServices = new ArrayList<Service>();
			roomTypes = new ArrayList<RoomType>();
			rooms = new ArrayList<Room>();
			priceRange = new HashMap<Double, Double>();
			tablePanel = tableReservations();
		} else if (screen == "rooms") {
			tablePanel = tableRooms();
		}
		this.add(tablePanel, BorderLayout.CENTER);
		this.repaint();
		this.setVisible(true);
	}
	
	public void addServices(ArrayList<Service> alreadySelectedServices) {
		JFrame servicesFrame = new JFrame();
		JPanel servicesPanel = new JPanel();
		
		servicesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Services"));
		selectedServices = new ArrayList<Service>();
		selectedServices.addAll(alreadySelectedServices);
		for (Service service : ManagerManager.getServiceManager().getServices().values()) {
			if (service.isDeleted() == false) {
				JCheckBox checkBox = new JCheckBox(service.getType());
				if (alreadySelectedServices.contains(service)) {
					checkBox.setSelected(true);
				}
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
		JButton buttonSubmit = new JButton("Submit");
		buttonSubmit.addActionListener(ActionEvent -> {
			servicesFrame.dispose();
			JOptionPane.showMessageDialog(null, "Services added", "Success", JOptionPane.INFORMATION_MESSAGE);
		});
		servicesPanel.setLayout(new GridLayout(2, 3));
		servicesFrame.add(servicesPanel);
		servicesFrame.add(buttonSubmit);
		servicesFrame.setLayout(new GridLayout(2, 1));
		servicesFrame.setSize(200, 200);
		servicesFrame.setLocationRelativeTo(null);
		servicesFrame.setVisible(true);
	}

	public void guestsPage() {
		JButton buttonAdd = new JButton("Add new guest");
		buttonAdd.setBounds(10,0, 100, 50);
		data = new ArrayList<String>();
		upperPanel.add(buttonAdd);
		buttonAdd.addActionListener(ActionEvent -> {
			new AddGuest(managerManager, data);
		});
		
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(120,0, 100, 50);
		upperPanel.add(buttonRefresh);
		buttonRefresh.addActionListener(ActionEvent -> {
			this.remove(tablePanel);
			tablePanel = new JPanel();
			this.tableGuests();
			this.add(tablePanel, BorderLayout.CENTER);
			this.repaint();
			this.setVisible(true);
		});
		
		JButton buttonReservation = new JButton("Add new reservation");
		buttonReservation.setBounds(850,0, 100, 50);
		upperPanel.add(buttonReservation);
		data = new ArrayList<String>();
		buttonReservation.addActionListener(ActionEvent -> {
			try {
				AbstractTableModel model = (AbstractTableModel) table.getModel();
				int selectedRow = table.getSelectedRow();
				new AddReservation(managerManager, model.getValueAt(selectedRow, 0).toString(), data);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Please select a guest", "Failure", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(960,0, 100, 50);
		logoutButton.addActionListener(ActionEvent -> {
            this.dispose();
            new LoginJFrame(managerManager);
        });
		upperPanel.add(logoutButton);


		this.updateTablePanel();
	}
	
	
	
	public void reservationsPage() {	
		JButton buttonAccept = new JButton("Accept");
		buttonAccept.setBounds(20,0, 100, 50);
		upperPanel.add(buttonAccept);
		buttonAccept.addActionListener(ActionEvent -> {
			try {
				AbstractTableModel model = (AbstractTableModel) table.getModel();
				int selectedRow = table.getSelectedRow();
				Reservation reservation = ManagerManager.reservationManager.findReservationString(model.getValueAt(selectedRow, 0).toString(), model.getValueAt(selectedRow, 1).toString(), model.getValueAt(selectedRow, 2).toString());
				if (reservation.getStatus() == ReservationStatus.ACCEPTED) {
					JOptionPane.showMessageDialog(null, "This reservation is already accepted", "Failure",JOptionPane.ERROR_MESSAGE);
					return;
				} else if (reservation.getStatus() == ReservationStatus.REJECTED) {
					JOptionPane.showMessageDialog(null, "This reservation is already rejected", "Failure", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (reservation.getStatus() == ReservationStatus.CANCELLED) {
					JOptionPane.showMessageDialog(null, "This reservation is already canceled", "Failure",JOptionPane.ERROR_MESSAGE);
					return;
				} else if (reservation.getStatus() == ReservationStatus.CHECKED_IN) {
					JOptionPane.showMessageDialog(null, "This reservation is already checked in", "Failure",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (reservation.getStatus() == ReservationStatus.CHECKED_OUT) {
					JOptionPane.showMessageDialog(null, "This reservation is checked out", "Failure",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				ReservationManager reservationManager = ManagerManager.reservationManager;
				Room room = reservationManager.roomAvailable(ManagerManager.roomManager.getRooms(), reservation.getCheckIn(), reservation.getCheckOut(), reservation.getRoomType());
				if (room == null) {
					JOptionPane.showMessageDialog(null, "No rooms available for this reservation", "Failure",JOptionPane.ERROR_MESSAGE);
					reservation.reject();
					return;
				} else {
					reservation.setRoom(room);
					reservation.accept();
					JOptionPane.showMessageDialog(null, "Reservation accepted", "Success", JOptionPane.INFORMATION_MESSAGE);
				}
				
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Please select a reservation", "Failure", JOptionPane.ERROR_MESSAGE);
			}
			
		
		});
		
		JButton buttonCheckIn = new JButton("Check In");
		buttonCheckIn.setBounds(20,0, 100, 50);
		upperPanel.add(buttonCheckIn);
		buttonCheckIn.addActionListener(ActionEvent -> {
			try {
				AbstractTableModel model = (AbstractTableModel) table.getModel();
				int selectedRow = table.getSelectedRow();
				Reservation reservation = ManagerManager.reservationManager.findReservationString(model.getValueAt(selectedRow, 0).toString(), model.getValueAt(selectedRow, 1).toString(), model.getValueAt(selectedRow, 2).toString());
				if (reservation.getStatus() == ReservationStatus.REJECTED) {
					JOptionPane.showMessageDialog(null, "This reservation is already rejected", "Failure", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (reservation.getStatus() == ReservationStatus.CANCELLED) {
					JOptionPane.showMessageDialog(null, "This reservation is already canceled", "Failure",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (reservation.getStatus() == ReservationStatus.CHECKED_IN) {
					JOptionPane.showMessageDialog(null, "This reservation is already checked in", "Failure",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (reservation.getStatus() == ReservationStatus.CHECKED_OUT) {
					JOptionPane.showMessageDialog(null, "This reservation is checked out", "Failure",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				ArrayList<Service> alreadySelectedServices = new ArrayList<Service>();
				if (reservation.getServices() != null) {
					for (Service service : reservation.getServices()) {
						alreadySelectedServices.add(service);
					}
				}
				ReservationManager reservationManager = ManagerManager.reservationManager;
				if (reservation.getRoom() != null) {
					Room room = reservation.getRoom();
					if (room.getStatus() == RoomStatus.CLEANED) {
						JOptionPane.showMessageDialog(null, "This room is cleaned", "Failure",
								JOptionPane.ERROR_MESSAGE);
						return;
					} else if (room.getStatus() == RoomStatus.OCCUPIED) {
						JOptionPane.showMessageDialog(null, "This room is occupied", "Failure",
								JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						reservation.checkIn(room);
						JOptionPane.showMessageDialog(null, "Checked in", "Success", JOptionPane.INFORMATION_MESSAGE);
						int reply = JOptionPane.showConfirmDialog(null, "Do you want to add services?", "Services", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (JOptionPane.YES_OPTION == reply) {
							this.addServices(alreadySelectedServices);
							reservation.setServices(selectedServices);
						} else {
							JOptionPane.showMessageDialog(null, "Checked in", "Success", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} else {
					if (reservationManager.roomAvailable(ManagerManager.roomManager.getRooms(),
							reservation.getCheckIn(), reservation.getCheckOut(), reservation.getRoomType()) == null) {
						JOptionPane.showMessageDialog(null, "No rooms available for this reservation", "Failure",
								JOptionPane.ERROR_MESSAGE);
						reservation.reject();
					} else {
						Room room = reservationManager.roomAvailable(ManagerManager.roomManager.getRooms(), reservation.getCheckIn(), reservation.getCheckOut(), reservation.getRoomType());
						reservation.setRoom(room);
						reservation.checkIn(room);
						int reply = JOptionPane.showConfirmDialog(null, "Do you want to add services?", "Services", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (JOptionPane.YES_OPTION == reply) {
							this.addServices(alreadySelectedServices);
							reservation.setServices(selectedServices);
							
						} else {
							JOptionPane.showMessageDialog(null, "Checked in", "Success", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Please select a reservation", "Failure", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JButton buttonCheckOut = new JButton("Check Out");
		buttonCheckOut.setBounds(140,0, 100, 50);
		upperPanel.add(buttonCheckOut);
		buttonCheckOut.addActionListener(ActionEvent -> {
			try {
				AbstractTableModel model = (AbstractTableModel) table.getModel();
				int selectedRow = table.getSelectedRow();
				Reservation reservation = ManagerManager.reservationManager.findReservationString(
						model.getValueAt(selectedRow, 0).toString(), model.getValueAt(selectedRow, 1).toString(),
						model.getValueAt(selectedRow, 2).toString());
				if (reservation.getStatus() == ReservationStatus.REJECTED) {
					JOptionPane.showMessageDialog(null, "This reservation is rejected", "Failure",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (reservation.getStatus() == ReservationStatus.CANCELLED) {
					JOptionPane.showMessageDialog(null, "This reservation is canceled", "Failure",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (reservation.getStatus() == ReservationStatus.ACCEPTED) {
					JOptionPane.showMessageDialog(null, "This reservation is not checked in", "Failure",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (reservation.getStatus() == ReservationStatus.ON_HOLD) {
					JOptionPane.showMessageDialog(null, "This reservation is on hold", "Failure",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (reservation.getStatus() == ReservationStatus.CHECKED_OUT) {
					JOptionPane.showMessageDialog(null, "This reservation is checked out", "Failure",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				reservation.checkOut();
				CleaningManager cleanerManager = ManagerManager.getCleaningManager();
				String janitorFound = cleanerManager.findFreeJanitor();
				if (janitorFound == "") {
					JOptionPane.showMessageDialog(null, "No janitors available", "Failure", JOptionPane.ERROR_MESSAGE);
					return;
				} 
				
				cleanerManager.addDailyTask(janitorFound, reservation.getRoom());

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Please select a reservation", "Failure", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(240,0, 100, 50);
		upperPanel.add(buttonRefresh);
		buttonRefresh.addActionListener(ActionEvent -> {
			this.remove(tablePanel);
			tablePanel = new JPanel();
			this.tableReservations();
			this.add(upperPanel, BorderLayout.NORTH);
			this.add(tablePanel, BorderLayout.CENTER);
			this.repaint();
			this.setVisible(true);
		});
		
		this.updateTablePanel();
		
		JButton buttonFilter = new JButton("Filter");
		buttonFilter.setBounds(350,0, 100, 50);
		upperPanel.add(buttonFilter);
		buttonFilter.addActionListener(ActionEvent -> {
			JFrame filterFrame = new JFrame();
			
			JPanel roomTypePanel = new JPanel();
			roomTypePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Room type"));
			roomTypes = new ArrayList<RoomType>();
			for (RoomType type : ManagerManager.getRoomTypeManager().getRoomTypes().values()) {
				JCheckBox checkBox = new JCheckBox(type.getType());
				roomTypePanel.add(checkBox);
				checkBox.addActionListener(ActionEvent2 -> {
					if (checkBox.isSelected()) {
						roomTypes.add(type);
					} else {
						rooms.remove(type);
					}				
				});
			}
			
			
			JPanel roomPanel = new JPanel();
			roomPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Room"));
			rooms = new ArrayList<Room>();
			for (Room room : ManagerManager.getRoomManager().getRooms().values()) {
				JCheckBox checkBox = new JCheckBox(String.valueOf(room.getNumber()));
				roomPanel.add(checkBox);
				checkBox.addActionListener(ActionEvent2 -> {
					if (checkBox.isSelected()) {
						rooms.add(room);
					} else {
						rooms.remove(room);
					}
				});
			}
			
			JPanel pricePanel = new JPanel();
			pricePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Price"));
		    priceRange = new HashMap<Double, Double>();
		    JFormattedTextField priceFrom = new JFormattedTextField();
		    priceFrom.setPreferredSize(new Dimension(100, 50));
		    JFormattedTextField priceTo = new JFormattedTextField();
		    priceTo.setPreferredSize(new Dimension(100, 50));
		    pricePanel.add(priceFrom);
		    pricePanel.add(priceTo);
			
			
			JPanel servicesPanel = new JPanel();
			servicesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Services"));
			selectedServices = new ArrayList<Service>();
			for (Service service : ManagerManager.getServiceManager().getServices().values()) {
				JCheckBox checkBox = new JCheckBox(service.getType());
				servicesPanel.add(checkBox);
				checkBox.addActionListener(ActionEvent2 -> {
					if (checkBox.isSelected()) {
						selectedServices.add(service);
					} else {
						selectedServices.remove(service);
					}
				});
			}
			
			
			JPanel buttonPanel = new JPanel();
			JButton buttonSubmit = new JButton("Submit");
			buttonSubmit.addActionListener(statusCombobox -> {
				if (!priceFrom.getText().isEmpty() && priceTo.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter price range", "Failure", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (priceFrom.getText().isEmpty() && !priceTo.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter price range", "Failure",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (!priceFrom.getText().isEmpty() && !priceTo.getText().isEmpty()) {
					try {
						priceRange.put(Double.parseDouble(priceFrom.getText()), Double.parseDouble(priceTo.getText()));
						if (Double.parseDouble(priceFrom.getText()) > Double.parseDouble(priceTo.getText())) {
							JOptionPane.showMessageDialog(null, "Please enter valid price range", "Failure", JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Please enter valid price range", "Failure", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				filterFrame.dispose();
				this.remove(tablePanel);
				tablePanel = new JPanel();
				this.tableReservations();
				this.add(upperPanel, BorderLayout.NORTH);
				this.add(tablePanel, BorderLayout.CENTER);
				this.repaint();
				this.setVisible(true);
			});
			buttonPanel.add(buttonSubmit);
			filterFrame.add(roomTypePanel);
			filterFrame.add(roomPanel);
			filterFrame.add(pricePanel);
			filterFrame.add(servicesPanel);
			filterFrame.add(buttonPanel);
			filterFrame.setLayout(new GridLayout(5, 1));
			filterFrame.setSize(500, 500);
			filterFrame.setLocationRelativeTo(null);
			filterFrame.setVisible(true);
		});
			
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(960,0, 100, 50);
		logoutButton.addActionListener(ActionEvent -> {
            this.dispose();
            new LoginJFrame(managerManager);
        });
		upperPanel.add(logoutButton);
		
	}
	
	
	public void dailyOccupancyPage() {
		JButton pickDateButton = new JButton("Pick date");
		pickDateButton.setBounds(10,0, 100, 50);
		upperPanel.add(pickDateButton);
		pickDateButton.addActionListener(ActionEvent -> {
			Properties properties = new Properties();
			JDatePickerImpl date;
	        properties.put("text.today", "Today");
	        properties.put("text.month", "Month");
	        properties.put("text.year", "Year");
			 UtilDateModel model = new UtilDateModel();
		        model.setSelected(true); // Ensure model is initialized with a selected date
		        JDatePanelImpl startDateJPanel = new JDatePanelImpl(model, properties);
	
		        date = new JDatePickerImpl(startDateJPanel, new AbstractFormatter() {
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
			JFrame dateFrame = new JFrame();
			JPanel datePanel = new JPanel();
			JButton buttonSubmit = new JButton("Submit");
			datePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Pick date"));
			datePanel.add(date);
			dateFrame.add(datePanel);
			dateFrame.add(buttonSubmit);
			dateFrame.setSize(300, 300);
			dateFrame.setLayout(new GridLayout(2, 1));
			dateFrame.setLocationRelativeTo(null);
			dateFrame.setVisible(true);
			buttonSubmit.addActionListener(ActionEvent2 -> {
				dateFrame.dispose();
				this.remove(tablePanel);
				tablePanel = new JPanel();
				this.tableDailyOccupancy(LocalDate.parse(date.getJFormattedTextField().getText()));
				this.add(tablePanel, BorderLayout.CENTER);
				this.repaint();
				this.setVisible(true);
			});
		});
		
		this.remove(tablePanel);
		tablePanel = new JPanel();
		this.tableDailyOccupancy(LocalDate.now());
		this.add(upperPanel, BorderLayout.NORTH);
		this.add(tablePanel, BorderLayout.CENTER);
		this.repaint();
		
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(240,0, 100, 50);
		upperPanel.add(buttonRefresh);
		buttonRefresh.addActionListener(ActionEvent -> {
			this.remove(tablePanel);
			tablePanel = new JPanel();
			this.add(upperPanel, BorderLayout.NORTH);
			this.tableDailyOccupancy(LocalDate.now());
			this.add(tablePanel, BorderLayout.CENTER);
			this.repaint();
			this.setVisible(true);
		});
	}

	
	public void roomsPage() {
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(120,0, 100, 50);
		upperPanel.add(buttonRefresh);
		buttonRefresh.addActionListener(ActionEvent -> {
			this.remove(tablePanel);
			tablePanel = new JPanel();
			this.tableRooms();
			this.add(upperPanel, BorderLayout.NORTH);
			this.add(tablePanel, BorderLayout.CENTER);
			this.repaint();
			this.setVisible(true);
		});
		
		this.updateTablePanel();		
		
	}
		
	public void updateUpperPanel() {
		if (upperPanel != null) {
			this.remove(upperPanel);
		}
		upperPanel = new JPanel();
		if (screen == "guests") {
			guestsPage();
		} else if (screen == "reservations") {
			reservationsPage();
		} else if (screen == "rooms") {
			roomsPage();
		} else if (screen == "dailyOccupancy") {
			dailyOccupancyPage();
		}
		this.add(upperPanel, BorderLayout.NORTH);
		this.repaint();
		this.setVisible(true);
	}
	
	public AgentJFrame(ManagerManager managerManager){
		
		this.managerManager = managerManager;
		
		JMenuBar menuBar = new JMenuBar();
		JMenu guestsMenu = new JMenu("Guests");
		JMenu hotelMenu = new JMenu("Hotel");
		
		guestsItem = new JMenuItem("Guests");
		guestsMenu.add(guestsItem);
		guestsItem.addActionListener(this);
		
		reservationsItem = new JMenuItem("Reservations");
		hotelMenu.add(reservationsItem);
		reservationsItem.addActionListener(this);
		
		roomsItem = new JMenuItem("Rooms");
		hotelMenu.add(roomsItem);
		roomsItem.addActionListener(this);
		
		dailyOccupancyItem = new JMenuItem("Daily Occupancy");
		hotelMenu.add(dailyOccupancyItem);
		dailyOccupancyItem.addActionListener(this);
		
		menuBar.add(guestsMenu);
		menuBar.add(hotelMenu);
		this.add(menuBar);
		
		updateUpperPanel();
			
		this.setTitle("Hotel");
		this.setSize(1280, 720);
		this.setVisible(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "Do you want to save the changes?");
				if (reply == JOptionPane.YES_OPTION) {
					ManagerManager.saveServices();
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} else if (reply == JOptionPane.NO_OPTION){
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} else {
                   setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});
		this.getContentPane().setBackground(new java.awt.Color(222, 224, 223));
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setJMenuBar(menuBar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == guestsItem) {
            screen = "guests";	
		} else if (e.getSource() == reservationsItem) {
			screen = "reservations";
		} else if (e.getSource() == roomsItem) {
			screen = "rooms";
		} else if (e.getSource() == dailyOccupancyItem) {
			screen = "dailyOccupancy";
		}
        this.updateUpperPanel();		
	}
}
