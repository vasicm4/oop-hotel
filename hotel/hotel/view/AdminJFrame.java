package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import manager.AdminManager;
import manager.AgentManager;
import manager.GuestManager;
import manager.JanitorManager;
import manager.ManagerManager;
import manager.PriceListManager;
import users.Admin;
import users.Agent;
import users.Guest;
import users.Janitor;

public class AdminJFrame extends JFrame implements ActionListener{
	JFrame frame;
	JMenuItem adminsItem;
	JMenuItem agentsItem;
	JMenuItem janitorsItem;
	JMenuItem guestsItem;
	JMenuItem priceList;
	
	String currentTable = "";
	JScrollPane scrollPane;
	ManagerManager managerManager;
	JPanel tablePanel;
	JButton deleteUser;

//	public JPanel tablePriceList() {
//		JPanel tablePanel = new JPanel();
//		JTable table = new JTable();
//		String[] columnNames = {"Start Date", "End Date", "Room Type", "Service"};
//		PriceListManager priceListManager = ManagerManager.priceListManager;		
//	}
	
	public JPanel tableUser(String currentTable) {
		JPanel tablePanel = new JPanel();
		JTable table = new JTable();
		String[] columnNames = {"Username","Password","First Name", "Last Name", "Date of Birth", "Phone", "Gender", "Education", "Experience", "Base Salary"};

		
		if (currentTable == "guests") {
			GuestManager guestManager = ManagerManager.guestManager;
			columnNames = new String[] {"Username","Password","First Name", "Last Name", "Date of Birth", "Phone", "Gender"};
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
		} else if (currentTable == "admins") {
			ManagerManager.getInstance();
			AdminManager adminManager = ManagerManager.adminManager;
			HashMap<String, Admin> admins = adminManager.getAdmins();
			String[][] data = new String[admins.size()][10];
			int i = 0;
			for (Admin admin: admins.values()) {
				if (admin.isDeleted()) {
					continue;
				}
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
			
		} else if (currentTable == "agents") {			
			ManagerManager.getInstance();
			AgentManager agentManager = ManagerManager.agentManager;
			HashMap<String, Agent> agents = agentManager.getAgents();
			String[][] data = new String[agents.size()][10];
			int i = 0;
			for (Agent agent: agents.values()) {
				if (agent.isDeleted()) {
					continue;
				}
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
			
		} else if (currentTable == "janitors") {			
			ManagerManager.getInstance();
			JanitorManager janitorManager = ManagerManager.janitorManager;
			HashMap<String, Janitor> janitors = janitorManager.getJanitors();
			String[][] data = new String[janitors.size()][10];
			int i = 0;
			for (Janitor janitor: janitors.values()) {
				if (janitor.isDeleted()) {
					continue;
				}
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
	
	public AdminJFrame(ManagerManager managerManager) {
		
		this.managerManager = managerManager;
	
		JMenuBar menuBar = new JMenuBar();
		JMenu usersMenu = new JMenu("Users");
		JMenu financesMenu = new JMenu("Finances");
		JMenu roomsMenu = new JMenu("Rooms");
		
		
		priceList = new JMenuItem("Price List");
		priceList.addActionListener(this);
		financesMenu.add(priceList);
		
		menuBar.add(usersMenu);
		menuBar.add(financesMenu);
		menuBar.add(roomsMenu);
		
		adminsItem = new JMenuItem("Admins");
		agentsItem = new JMenuItem("Agents");
		janitorsItem = new JMenuItem("Janitors");
		guestsItem = new JMenuItem("Guests");
		
		usersMenu.add(adminsItem);
		usersMenu.add(agentsItem);
		usersMenu.add(janitorsItem);
		usersMenu.add(guestsItem);
		
		adminsItem.addActionListener(this);
		agentsItem.addActionListener(this);
		janitorsItem.addActionListener(this);
		guestsItem.addActionListener(this);
		
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout());
		upperPanel.setBounds(0,0, 1080, 50);
		
		JButton buttonAdd = new JButton("Add new");
		buttonAdd.setBounds(10,0, 100, 50);
		upperPanel.add(buttonAdd);
		buttonAdd.addActionListener(ActionEvent -> {
			if (currentTable == "guests") {
				new AddGuest(managerManager);
			} else if (currentTable == "admins") {
				new AddEmployee(managerManager, currentTable);
			} else if (currentTable == "agents") {
				new AddEmployee(managerManager, currentTable);
			} else if (currentTable == "janitors") {
				new AddEmployee(managerManager, currentTable);
			}
		});
		
		JButton buttonDelete = new JButton("Delete");
		buttonDelete.setBounds(850, 0, 100, 50);
		buttonDelete.addActionListener(this);
		upperPanel.add(buttonDelete);
		//TODO make it delete selected user from table
		
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(120,0, 100, 50);
		upperPanel.add(buttonRefresh);
		buttonRefresh.addActionListener(ActionEvent -> {
			this.repaint();
		});
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(960,0, 100, 50);
		logoutButton.addActionListener(ActionEvent -> {
            this.dispose();
            new LoginJFrame(managerManager);
        });
		upperPanel.add(logoutButton);
		
		this.setTitle("Hotel");
		this.setSize(1080, 720);
		this.setVisible(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(new java.awt.Color(222, 224, 223));
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setJMenuBar(menuBar);
		this.add(upperPanel, BorderLayout.NORTH);
	}

	public void updateTablePanel(String currentTable) {
		if (tablePanel != null) {
			this.remove(tablePanel);
		}
		tablePanel = this.tableUser(currentTable);
		this.add(tablePanel, BorderLayout.CENTER);
		this.repaint();
		this.setVisible(true);
	}
	
	public void updateTablePanel() {
		if (tablePanel != null) {
			this.remove(tablePanel);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource() == deleteUser) {
			// TODO delete selected user from table
		}
		
		if (e.getSource() == priceList) {

		}
		
		if(e.getSource() == adminsItem) {
			this.updateTablePanel("admins");
		}else if(e.getSource() == agentsItem) {
			this.updateTablePanel("agents");
		}else if(e.getSource() == janitorsItem) {
			this.updateTablePanel("janitors");
		}else if (e.getSource() == guestsItem) {
			this.updateTablePanel("guests");
		}

	}
}
