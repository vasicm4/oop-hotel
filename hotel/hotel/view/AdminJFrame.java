package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import hotel.PriceList;
import manager.AdminManager;
import manager.AgentManager;
import manager.CleaningManager;
import manager.GuestManager;
import manager.JanitorManager;
import manager.ManagerManager;
import manager.ReservationManager;
import manager.RoomManager;
import rooms.Reservation;
import rooms.ReservationStatus;
import rooms.Room;
import rooms.RoomType;
import users.Admin;
import users.Agent;
import users.Guest;
import users.Janitor;

public class AdminJFrame extends JFrame implements ActionListener{
	JFrame frame;
	ManagerManager managerManager;
	
	JMenuItem adminsItem;
	JMenuItem agentsItem;
	JMenuItem janitorsItem;
	JMenuItem guestsItem;
	
	JMenuItem priceListItem;
	JMenuItem earningsItem;
	
	JMenuItem cleaningListItem;
	JMenuItem roomListItem;
	JMenuItem reservationListItem;
	
	String currentScreen = "admins";
	JPanel upperPanel;
	JPanel tablePanel;
	JTable table;
	
	ArrayList<String> data;
	
//	public JPanel tablePriceList() {
//		JPanel tablePanel = new JPanel();
//		JTable table = new JTable();
//		String[] columnNames = {"Start Date", "End Date", "Room Type", "Service"};
//		PriceListManager priceListManager = ManagerManager.priceListManager;		
//	}
	
	public JPanel tableUser() {
		tablePanel = new JPanel();
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		String[] columnNames = {"Username","Password","First Name", "Last Name", "Date of Birth", "Phone", "Gender", "Education", "Experience", "Base Salary"};
		
		if (currentScreen == "guests") {
			GuestManager guestManager = ManagerManager.guestManager;
			columnNames = new String[] {"Username","Password","First Name", "Last Name", "Date of Birth", "Phone", "Gender"};
			HashMap<String, Guest> guests = guestManager.getAvailableGuests();
			String[][] data = new String[guests.size()][7];
			int i = 0;
			for (Guest guest : guests.values()) {
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
		} else if (currentScreen == "admins") {
			ManagerManager.getInstance();
			AdminManager adminManager = ManagerManager.adminManager;
			HashMap<String, Admin> admins = adminManager.getAvailableAdmins();
			String[][] data = new String[admins.size()][10];
			int i = 0;
			for (Admin admin: admins.values()) {
				data[i][0] = admin.getUsername();
				data[i][1] = admin.getPassword();
	            data[i][2] = admin.getName();
	            data[i][3] = admin.getSurname();
	            data[i][4] = String.valueOf(admin.getDateOfBirth());
	            data[i][5] = String.valueOf(admin.getPhoneNumber());
	            data[i][6] = String.valueOf(admin.getGender());
	            data[i][7] = String.valueOf(admin.getEducationLevel());
	            data[i][8] = String.valueOf(admin.getExperience());
	            data[i][9] = String.valueOf(admin.getBaseSalary());
	            i++;
			}
			
			table = new JTable(data, columnNames);
			
		} else if (currentScreen == "agents") {			
			ManagerManager.getInstance();
			AgentManager agentManager = ManagerManager.agentManager;
			HashMap<String, Agent> agents = agentManager.getAvailableAgents();
			String[][] data = new String[agents.size()][10];
			int i = 0;
			for (Agent agent: agents.values()) {
				data[i][0] = agent.getUsername();
				data[i][1] = agent.getPassword();
	            data[i][2] = agent.getName();
	            data[i][3] = agent.getSurname();
	            data[i][4] = String.valueOf(agent.getDateOfBirth());
	            data[i][5] = String.valueOf(agent.getPhoneNumber());
	            data[i][6] = String.valueOf(agent.getGender());
	            data[i][7] = String.valueOf(agent.getEducationLevel());
	            data[i][8] = String.valueOf(agent.getExperience());
	            data[i][9] = String.valueOf(agent.getBaseSalary());
	            i++;
			}
			
			table = new JTable(data, columnNames);
			
		} else if (currentScreen == "janitors") {			
			ManagerManager.getInstance();
			JanitorManager janitorManager = ManagerManager.janitorManager;
			HashMap<String, Janitor> janitors = janitorManager.getAvailableJanitors();
			String[][] data = new String[janitors.size()][10];
			int i = 0;
			for (Janitor janitor: janitors.values()) {
				data[i][0] = janitor.getUsername();
				data[i][1] = janitor.getPassword();
	            data[i][2] = janitor.getName();
	            data[i][3] = janitor.getSurname();
	            data[i][4] = String.valueOf(janitor.getDateOfBirth());
	            data[i][5] = String.valueOf(janitor.getPhoneNumber());
	            data[i][6] = String.valueOf(janitor.getGender());
	            data[i][7] = String.valueOf(janitor.getEducationLevel());
	            data[i][8] = String.valueOf(janitor.getExperience());
	            data[i][9] = String.valueOf(janitor.getBaseSalary());
	            i++;
			}
			table = new JTable(data, columnNames);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(900, 800));
		tablePanel.add(scrollPane);
		return tablePanel;
	}
	
	public JPanel tableRooms() {
		tablePanel = new JPanel();
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		String[] columnNames = {"Room Number", "Room Type", "Floor", "Status"};
		HashMap<String,Room> rooms = ManagerManager.roomManager.getRooms();
		String[][] data = new String[rooms.size()][4];
		int i = 0;
		for (Room room : rooms.values()) {
			data[i][0] = String.valueOf(room.getNumber());
			data[i][1] = String.valueOf(room.getType());
			data[i][2] = String.valueOf(room.getFloor());
			data[i][3] = String.valueOf(room.getStatus());
			i++;
		}
		table = new JTable(data, columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1000, 800));
		tablePanel.add(scrollPane);
		return tablePanel;
	}
	
	public JPanel tablePriceList() {
		tablePanel = new JPanel();
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		String[] columnNames = {"Start Date", "End Date", "Room Type", "Service"};
		HashMap<String, PriceList> priceLists = ManagerManager.priceListManager.getActivePriceLists();
		String[][] data = new String[priceLists.size()][4];
		int i = 0;
		for (PriceList priceList : priceLists.values()) {
			data[i][0] = priceList.getStartDate().toString();
			data[i][1] = priceList.getEndDate().toString();
			data[i][2] = priceList.servicesToString();
			data[i][3] = priceList.roomPricesToString();
			i++;
		}
		table = new JTable(data, columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1000, 800));
		tablePanel.add(scrollPane);
		return tablePanel;
		
	}
	
	public JPanel tableEarnings(LocalDate startDate, LocalDate endDate) {
		tablePanel = new JPanel();
		table = new JTable();
		table.setDefaultEditor(Object.class, null);		
		String[] columnNames = {"Category", "Total Value"};	
		String[][] data = new String[3][2];
		double expenses = 0;
		expenses += ManagerManager.adminManager.calculateAllSalaries();
		expenses += ManagerManager.agentManager.calculateAllSalaries();
		expenses += ManagerManager.janitorManager.calculateAllSalaries();
		data[0][0] = "Total Earnings";
		data[0][1] = String.valueOf(ManagerManager.reservationManager.getTotalEarnings(startDate, endDate));
		data[1][0] = "Total Expenses";
		data[1][1] = String.valueOf(expenses);
		data[2][0] = "Profit";
		data[2][1] = String.valueOf(Double.parseDouble(data[0][1]) - Double.parseDouble(data[1][1]));
		table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(900, 800));
		tablePanel.add(scrollPane);
		return tablePanel;
	}
	public JPanel tableCleaning() {
		tablePanel = new JPanel();
		table = new JTable();
		table.setDefaultEditor(Object.class, null);		
		String[] columnNames = {"Janitor", "Date", "Room"};
		CleaningManager cleaningManager = ManagerManager.getCleaningManager();
		HashMap<String, ArrayList<Room>> roomsToBeCleaned = cleaningManager.getRoomsToBeCleaned();
		String[][] data;
		if (roomsToBeCleaned != null) {
			data = new String[roomsToBeCleaned.size()][2];
		} else {
			data = new String[0][2];
			return tablePanel;
		}
	    int i = 0;
		for (String janitor : roomsToBeCleaned.keySet()) {
			for (Room room : roomsToBeCleaned.get(janitor)) {
				data[i][0] = janitor;
				data[i][1] = String.valueOf(room.getNumber());
				i++;
			}
		}
		table = new JTable(data, columnNames);
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
		HashMap<String, Reservation> reservations = reservationManager.getAvailableReservations();
		String[][] data = new String[reservations.size()][8];
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
			data[i][7] = String.valueOf(reservation.getPrice(ManagerManager.getPriceListManager(), reservation.getRoomType()));
			i++;
		}
		table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(900, 800));
		tablePanel.add(scrollPane);
		return tablePanel;
	}
	
	public void usersPage() {
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(10,0, 100, 50);
		upperPanel.add(buttonRefresh);
		buttonRefresh.addActionListener(ActionEvent -> {
			this.remove(tablePanel);
			tablePanel = new JPanel();
			this.tableUser();
			this.add(tablePanel, BorderLayout.CENTER);
			this.repaint();
			this.setVisible(true);
		});
		
		JButton addUser = new JButton("Add new");
		addUser.setBounds(120,0, 100, 50);
		data = new ArrayList<String>();
		addUser.addActionListener(ActionEvent -> {
			if (currentScreen == "guests") {
				new AddGuest(managerManager, data);
			} else if (currentScreen == "admins") {
				new AddEmployee(managerManager, currentScreen, data);
			} else if (currentScreen == "agents") {
				new AddEmployee(managerManager, currentScreen, data);
			} else if (currentScreen == "janitors") {
				new AddEmployee(managerManager, currentScreen, data);
			}
		});
		upperPanel.add(addUser);
		
		JButton editUser = new JButton("Edit");
		editUser.setBounds(230,0, 100, 50);
		upperPanel.add(editUser);
		this.data = new ArrayList<String>();
		editUser.addActionListener(ActionListener -> {
			try {
				int row = table.getSelectedRow();
				for (int i = 0; i < table.getColumnCount(); i++) {
	                data.add(String.valueOf(table.getValueAt(row, i)));
	            }
				if (currentScreen == "guests") {
					new AddGuest(managerManager, data);
				} else if (currentScreen == "admins") {
					new AddEmployee(managerManager, currentScreen, data);
				} else if (currentScreen == "agents") {
					new AddEmployee(managerManager, currentScreen, data);
				} else if (currentScreen == "janitors") {
					new AddEmployee(managerManager, currentScreen, data);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Please select a row to edit");
			}
			
		});
		
		JButton deleteUser = new JButton("Delete");
		deleteUser.setBounds(850, 0, 100, 50);
		upperPanel.add(deleteUser);
		deleteUser.addActionListener(ActionEvent -> {
			int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?");
			if (reply == JOptionPane.YES_OPTION) {
				if (currentScreen == "guests") {
					GuestManager guestManager = ManagerManager.guestManager;
					int row = table.getSelectedRow();
					String username = String.valueOf(table.getValueAt(row, 0));
					Guest guest = guestManager.find(username);
					guest.delete();
				} else if (currentScreen == "admins") {
					AdminManager adminManager = ManagerManager.adminManager;
					int row = table.getSelectedRow();
					String username = String.valueOf(table.getValueAt(row, 0));
					Admin admin = adminManager.find(username);
					admin.delete();
				} else if (currentScreen == "agents") {
					AgentManager agentManager = ManagerManager.agentManager;
					int row = table.getSelectedRow();
					String username = String.valueOf(table.getValueAt(row, 0));
					Agent agent = agentManager.find(username);
					agent.delete();
				} else if (currentScreen == "janitors") {
					JanitorManager janitorManager = ManagerManager.janitorManager;
					int row = table.getSelectedRow();
					String username = String.valueOf(table.getValueAt(row, 0));
					Janitor janitor = janitorManager.find(username);
					janitor.delete();
				}			
			}
		});

		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(960,0, 100, 50);
		logoutButton.addActionListener(ActionEvent -> {
            this.dispose();
            new LoginJFrame(managerManager);
        });
		upperPanel.add(logoutButton);
		
		updateTablePanel();
	}
	
	public void reservationsPage() {
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(10,0, 100, 50);
		upperPanel.add(buttonRefresh);
		buttonRefresh.addActionListener(ActionEvent -> {
			this.remove(tablePanel);
			tablePanel = new JPanel();
			this.tableReservations();
			this.add(tablePanel, BorderLayout.CENTER);
			this.repaint();
			this.setVisible(true);
		});
		
		JButton editReservation = new JButton("Edit");
		editReservation.setBounds(230,0, 100, 50);
		upperPanel.add(editReservation);
		editReservation.addActionListener(ActionEvent -> {
			int row = table.getSelectedRow();
			for (int i = 0; i < table.getColumnCount(); i++) {
				data.add(String.valueOf(table.getValueAt(row, i)));
			}
			new AddReservation(managerManager, String.valueOf(table.getValueAt(row, 0)), data);
		});
		
		JButton deleteReservation = new JButton("Delete");
		deleteReservation.setBounds(850, 0, 100, 50);
		upperPanel.add(deleteReservation);
		deleteReservation.addActionListener(ActionEvent -> {
			int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this reservation?");
			if (reply == JOptionPane.YES_OPTION) {
				ReservationManager reservationManager = ManagerManager.reservationManager;
				int row = table.getSelectedRow();
				String username = String.valueOf(table.getValueAt(row, 0));
				String startDate = String.valueOf(table.getValueAt(row, 1));
				String endDate = String.valueOf(table.getValueAt(row, 2));
				Guest guest = ManagerManager.guestManager.find(username);
				reservationManager.findReservation(guest, LocalDate.parse(startDate),LocalDate.parse(endDate)).delete();
			}
		});
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(960,0, 100, 50);
		logoutButton.addActionListener(ActionEvent -> {
			this.dispose();
			new LoginJFrame(managerManager);
		});
		upperPanel.add(logoutButton);
		
		updateTablePanel();
		
	}
	
	public void financesPage() {	
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(10,0, 100, 50);
		upperPanel.add(buttonRefresh);
		buttonRefresh.addActionListener(ActionEvent -> {
			this.remove(tablePanel);
			tablePanel = new JPanel();
//			this.table();
			this.add(tablePanel, BorderLayout.CENTER);
			this.repaint();
			this.setVisible(true);
		});
		
		JButton monthlyEarnings = new JButton("Monthly Earnings");
		monthlyEarnings.setBounds(120,0, 150, 50);
		upperPanel.add(monthlyEarnings);
		monthlyEarnings.addActionListener(ActionEvent -> {
			JFrame earningsFrame = new JFrame();
			JPanel date = new JPanel();
			date.setLayout(new GridLayout(1, 3));
			earningsFrame.add(date);
			date.setBorder(javax.swing.BorderFactory.createTitledBorder("Date"));
			JComboBox<String> month = new JComboBox<String>();
			for (int i = 1; i <= 12; i++) {
				if (i < 10) {
					month.addItem("0" + String.valueOf(i));
				} else {
					month.addItem(String.valueOf(i));
				}
			}
			JComboBox<String> year = new JComboBox<String>();
			for (int i = 2024; i <= 2025; i++) {
				year.addItem(String.valueOf(i));
			}
			date.add(month);
			date.add(year);
			JButton buttonSubmit = new JButton("Submit");
			buttonSubmit.addActionListener(ActionEvent2 -> {
				earningsFrame.dispose();
				String endDate = null;
				String startDate = year.getSelectedItem() + "-" + month.getSelectedItem() + "-01";
				if (month.getSelectedItem().equals("02")) {
					endDate = year.getSelectedItem() + "-" + month.getSelectedItem() + "-28";
				} else if (month.getSelectedItem().equals("04") || month.getSelectedItem().equals("06") || month.getSelectedItem().equals("09") || month.getSelectedItem().equals("11")) {
					endDate = year.getSelectedItem() + "-" + month.getSelectedItem() + "-30";
				} else {
					endDate = year.getSelectedItem() + "-" + month.getSelectedItem() + "-31";
				}
				this.remove(tablePanel);
				tablePanel = new JPanel();
				this.tableEarnings(LocalDate.parse(startDate), LocalDate.parse(endDate));
				this.add(tablePanel, BorderLayout.CENTER);
				this.repaint();
				this.setVisible(true);
			});
			earningsFrame.add(buttonSubmit);
			earningsFrame.add(buttonSubmit);
			earningsFrame.setLayout(new GridLayout(2, 1));
			earningsFrame.setSize(200, 200);
			earningsFrame.setLocationRelativeTo(null);
			earningsFrame.setVisible(true);
		});
	
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(960,0, 100, 50);
		logoutButton.addActionListener(ActionEvent -> {
	        this.dispose();
	        new LoginJFrame(managerManager);
	    });
		upperPanel.add(logoutButton);
		
		if (tablePanel != null) {
			this.remove(tablePanel);
		}
		tablePanel = new JPanel();
		this.tableEarnings(LocalDate.now().minusMonths(1).withDayOfMonth(1), LocalDate.now().withDayOfMonth(1));
		this.add(tablePanel, BorderLayout.CENTER);
        this.repaint();
        this.setVisible(true);
        
		
	}
	
	public void roomsPage() {
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(10,0, 100, 50);
		upperPanel.add(buttonRefresh);
		buttonRefresh.addActionListener(ActionEvent -> {
			this.remove(tablePanel);
			tablePanel = new JPanel();
			this.tableRooms();
			this.add(tablePanel, BorderLayout.CENTER);
			this.repaint();
			this.setVisible(true);
		});
		
		JButton addRoom = new JButton("Add new");
		addRoom.setBounds(120,0, 100, 50);
		addRoom.addActionListener(ActionEvent -> {
			JFrame addRoomFrame = new JFrame();
			JPanel addRoomPanel = new JPanel();
			addRoomFrame.add(addRoomPanel);
			addRoomPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Add Room"));
			JComboBox<String> roomTypeCombobox = new JComboBox<String>();
			for (RoomType roomType : ManagerManager.getRoomTypeManager().getAvailableRoomTypes().values()) {
				roomTypeCombobox.addItem(roomType.toString());
			}
			addRoomPanel.add(roomTypeCombobox);
			JButton buttonSubmit = new JButton("Submit");
			addRoomPanel.add(buttonSubmit);
			buttonSubmit.addActionListener(ActionEvent2 -> {
				RoomManager roomManager = ManagerManager.getRoomManager();
				RoomType roomType = ManagerManager.getRoomTypeManager().getRoomType(roomTypeCombobox.getSelectedItem().toString());
				roomManager.add(roomType, 1);
				addRoomFrame.dispose();
				this.remove(tablePanel);
				tablePanel = new JPanel();
				this.tableRooms();
				this.add(tablePanel, BorderLayout.CENTER);
				this.repaint();
				this.setVisible(true);
			});
			
			addRoomPanel.setLayout(new GridLayout(2, 1));
			addRoomPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Add Room"));
			addRoomFrame.setSize(200, 200);
			addRoomFrame.setLocationRelativeTo(null);
			addRoomFrame.setVisible(true);
		});
		upperPanel.add(addRoom);
		
		JButton editRoom = new JButton("Edit");
		editRoom.setBounds(230,0, 100, 50);
		upperPanel.add(editRoom);
		editRoom.addActionListener(ActionEvent -> {
			try {
				int row = table.getSelectedRow();
				String roomNumber = String.valueOf(table.getValueAt(row, 0));
				JFrame addRoomFrame = new JFrame();
				JPanel addRoomPanel = new JPanel();
				addRoomFrame.add(addRoomPanel);
				addRoomPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Add Room"));
				JComboBox<String> roomTypeCombobox = new JComboBox<String>();
				for (RoomType roomType : ManagerManager.getRoomTypeManager().getAvailableRoomTypes().values()) {
					roomTypeCombobox.addItem(roomType.toString());
				}
				addRoomPanel.add(roomTypeCombobox);
				JButton buttonSubmit = new JButton("Submit");
				addRoomPanel.add(buttonSubmit);
				buttonSubmit.addActionListener(ActionEvent2 -> {
					RoomManager roomManager = ManagerManager.getRoomManager();
					RoomType roomType = ManagerManager.getRoomTypeManager().getRoomType(roomTypeCombobox.getSelectedItem().toString());
					roomManager.find(Integer.parseInt(roomNumber)).setType(roomType);
					addRoomFrame.dispose();
					this.remove(tablePanel);
					tablePanel = new JPanel();
					this.tableRooms();
					this.add(tablePanel, BorderLayout.CENTER);
					this.repaint();
					this.setVisible(true);
				});
				
				addRoomPanel.setLayout(new GridLayout(2, 1));
				addRoomPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Add Room"));
				addRoomFrame.setSize(200, 200);
				addRoomFrame.setLocationRelativeTo(null);
				addRoomFrame.setVisible(true);		
			} catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please select a room to edit");
            }
		});
		
		JButton addRoomType = new JButton("Add Room Type");
		addRoomType.setBounds(340,0, 150, 50);
		upperPanel.add(addRoomType);
		addRoomType.addActionListener(ActionEvent -> {
			JFrame addRoomTypeFrame = new JFrame();
			JPanel addRoomTypePanel = new JPanel();
			addRoomTypeFrame.add(addRoomTypePanel);
			addRoomTypePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Add Room Type"));
			JTextField roomTypeTextField = new JTextField();
			roomTypeTextField.setPreferredSize(new Dimension(200, 30));
			roomTypeTextField.setFont(new java.awt.Font("Tahoma", 1, 25));
			addRoomTypePanel.add(roomTypeTextField);
			JPanel addRoomCapacityPanel = new JPanel();
			JTextField capacityTextField = new JTextField();
			capacityTextField.setPreferredSize(new Dimension(200, 30));
			capacityTextField.setFont(new java.awt.Font("Tahoma", 1, 25));
			addRoomCapacityPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Capacity"));
			addRoomCapacityPanel.add(capacityTextField);
			addRoomTypeFrame.add(addRoomCapacityPanel);
			JButton buttonSubmit = new JButton("Submit");
			addRoomTypeFrame.add(buttonSubmit);
			buttonSubmit.addActionListener(ActionEvent2 -> {
				ManagerManager.getRoomTypeManager().add(String.valueOf(roomTypeTextField.getText()), Integer.parseInt(capacityTextField.getText()));
				addRoomTypeFrame.dispose();
			});
			addRoomTypeFrame.setLayout(new GridLayout(3, 1));
			addRoomTypeFrame.setSize(300, 300);
			addRoomTypeFrame.setLocationRelativeTo(null);
			addRoomTypeFrame.setVisible(true);
		});
		
		JButton deleteRoom = new JButton("Delete");
		deleteRoom.setBounds(850, 0, 100, 50);
		upperPanel.add(deleteRoom);
		deleteRoom.addActionListener(ActionEvent -> {
			int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this room?");
			if (reply == JOptionPane.YES_OPTION) {		
				int row = table.getSelectedRow();
				String roomNumber = String.valueOf(table.getValueAt(row, 0));
				Room room = ManagerManager.getRoomManager().find(Integer.parseInt(roomNumber));
				room.delete();
			}
		});

		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(960,0, 100, 50);
		logoutButton.addActionListener(ActionEvent -> {
            this.dispose();
            new LoginJFrame(managerManager);
        });
		upperPanel.add(logoutButton);
		
		updateTablePanel();
	}
	
	public void priceListPage() {
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(10,0, 100, 50);
		upperPanel.add(buttonRefresh);
		buttonRefresh.addActionListener(ActionEvent -> {
			this.remove(tablePanel);
			tablePanel = new JPanel();
			this.tablePriceList();
			this.add(tablePanel, BorderLayout.CENTER);
			this.repaint();
			this.setVisible(true);
		});
		
		JButton addPriceList = new JButton("Add new");
		addPriceList.setBounds(120,0, 100, 50);
		addPriceList.addActionListener(ActionEvent -> {
			new AddPriceList(managerManager);
		});
		upperPanel.add(addPriceList);
		
		JButton editPriceList = new JButton("Edit");
		editPriceList.setBounds(230,0, 100, 50);
		upperPanel.add(editPriceList);
		//TODO: Implement edit priceList
		
		JButton deletePriceList = new JButton("Delete");
		deletePriceList.setBounds(850, 0, 100, 50);
		upperPanel.add(deletePriceList);
		deletePriceList.addActionListener(ActionEvent -> {
			int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this Price List?");
			if (reply == JOptionPane.YES_OPTION) {	
				int row = table.getSelectedRow();
				String startDate = String.valueOf(table.getValueAt(row, 0));
				String endDate = String.valueOf(table.getValueAt(row, 1));
				PriceList priceList = ManagerManager.getPriceListManager().find(LocalDate.parse(startDate), LocalDate.parse(endDate));
                priceList.delete();
			}
		});
	
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(960,0, 100, 50);
		logoutButton.addActionListener(ActionEvent -> {
	        this.dispose();
	        new LoginJFrame(managerManager);
	    });
		upperPanel.add(logoutButton);
		
		updateTablePanel();
	};
	
	public void cleaningListPage() {
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(10,0, 100, 50);
		upperPanel.add(buttonRefresh);
		buttonRefresh.addActionListener(ActionEvent -> {
			this.remove(tablePanel);
			tablePanel = new JPanel();
			this.tableRooms();
			this.add(tablePanel, BorderLayout.CENTER);
			this.repaint();
			this.setVisible(true);
		});
		
		JButton addCleaningList = new JButton("Add new");
		addCleaningList.setBounds(120,0, 100, 50);
		addCleaningList.addActionListener(ActionEvent -> {
			//TODO implement add cleaning list
		});
		upperPanel.add(addCleaningList);
		
		JButton editCleaningList = new JButton("Edit");
		editCleaningList.setBounds(230,0, 100, 50);
		upperPanel.add(editCleaningList);
		editCleaningList.addActionListener(ActionEvent -> {
			// TODO implement edit cleaning list
		});
		
		JButton deleteRoom = new JButton("Delete");
		deleteRoom.setBounds(850, 0, 100, 50);
		upperPanel.add(deleteRoom);
		deleteRoom.addActionListener(ActionEvent -> {
			int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this room?");
			if (reply == JOptionPane.YES_OPTION) {		
				int row = table.getSelectedRow();
				String roomNumber = String.valueOf(table.getValueAt(row, 0));
				Room room = ManagerManager.getRoomManager().find(Integer.parseInt(roomNumber));
				room.delete();
			}
		});

		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(960,0, 100, 50);
		logoutButton.addActionListener(ActionEvent -> {
            this.dispose();
            new LoginJFrame(managerManager);
        });
		upperPanel.add(logoutButton);
		
		updateTablePanel();
	}
	
	public AdminJFrame(ManagerManager managerManager) {
		
		this.managerManager = managerManager;
		JMenuBar menuBar = new JMenuBar();
		
		JMenu usersMenu = new JMenu("Users");
		
		adminsItem = new JMenuItem("Admins");
		adminsItem.addActionListener(this);
		usersMenu.add(adminsItem);
		
		agentsItem = new JMenuItem("Agents");
		agentsItem.addActionListener(this);
		usersMenu.add(agentsItem);
		
		janitorsItem = new JMenuItem("Janitors");
		janitorsItem.addActionListener(this);
		usersMenu.add(janitorsItem);
		
		guestsItem = new JMenuItem("Guests");
		guestsItem.addActionListener(this);
		usersMenu.add(guestsItem);
		
		menuBar.add(usersMenu);
		
		JMenu financesMenu = new JMenu("Finances");

		priceListItem = new JMenuItem("Price List");
		priceListItem.addActionListener(this);
		financesMenu.add(priceListItem);
		
		earningsItem = new JMenuItem("Earnings");
		earningsItem.addActionListener(this);
		financesMenu.add(earningsItem);
		
		menuBar.add(financesMenu);
		
		JMenu hotelMenu = new JMenu("Hotel");
		
		cleaningListItem = new JMenuItem("Cleaning List");
		cleaningListItem.addActionListener(this);
		hotelMenu.add(cleaningListItem);
		
		roomListItem = new JMenuItem("Room List");
		roomListItem.addActionListener(this);
		hotelMenu.add(roomListItem);
		
		reservationListItem = new JMenuItem("Reservations");
		reservationListItem.addActionListener(this);
		hotelMenu.add(reservationListItem);
		
		menuBar.add(hotelMenu);
		
		upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout());
		upperPanel.setBounds(0,0, 1080, 50);
		
		screenManager();
		
		this.setTitle("Hotel");
		this.setSize(1080, 720);
		this.setVisible(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(new java.awt.Color(222, 224, 223));
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setJMenuBar(menuBar);
	}
	
	public void updateUpperPanel() {
		if (upperPanel != null) {
			this.remove(upperPanel);
		}
		upperPanel = new JPanel();
		this.add(upperPanel, BorderLayout.NORTH);
		this.repaint();
		this.setVisible(true);
	}

	public void updateTablePanel() {
		if (tablePanel != null) {
			this.remove(tablePanel);
		}
		if (currentScreen == "roomList") {
			tablePanel = this.tableRooms();
		} else if (currentScreen == "cleaningList") {
			tablePanel = this.tableCleaning();
		} else if (currentScreen == "priceList") {
			tablePanel = this.tablePriceList();
		} else if (currentScreen == "reservations") {
            tablePanel = this.tableReservations();
		} else {
			tablePanel = this.tableUser();
		}
		this.add(tablePanel, BorderLayout.CENTER);
		this.repaint();
		this.setVisible(true);
	}
	
	public void screenManager() {
		updateUpperPanel();
		if (currentScreen == "admins") {
			this.usersPage();
		} else if (currentScreen == "agents") {
			this.usersPage();
		} else if (currentScreen == "janitors") {
			this.usersPage();
		} else if (currentScreen == "guests") {
			this.usersPage();
		} else if (currentScreen == "priceList") {
			this.priceListPage();
		} else if (currentScreen == "earnings") {
			this.financesPage();
		} else if (currentScreen == "cleaningList") {
			this.cleaningListPage();
		} else if (currentScreen == "roomList") {
			this.roomsPage();
		} else if (currentScreen == "reservations") {
			this.reservationsPage();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == adminsItem) {
			currentScreen = "admins";
		} else if (e.getSource() == agentsItem) {
			currentScreen = "agents";
		} else if (e.getSource() == janitorsItem) {
			currentScreen = "janitors";
		} else if (e.getSource() == guestsItem) {
			currentScreen = "guests";
		} else if (e.getSource() == priceListItem) {
			currentScreen = "priceList";
		} else if (e.getSource() == earningsItem) {
			currentScreen = "earnings";
		} else if (e.getSource() == cleaningListItem) {
			currentScreen = "cleaningList";
		} else if (e.getSource() == roomListItem) {
			currentScreen = "roomList";
		} else if (e.getSource() == reservationListItem) {
			currentScreen = "reservations";
		}
		this.screenManager();

	}
}
