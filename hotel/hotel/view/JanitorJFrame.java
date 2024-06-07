package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import manager.CleaningManager;
import manager.ManagerManager;
import manager.ReservationManager;
import rooms.Reservation;
import rooms.Room;
import rooms.RoomStatus;

public class JanitorJFrame extends JFrame implements ActionListener{
	JFrame frame;
	ManagerManager managerManager;
	JPanel tablePanel;
	String username;
	JTable table;
	
	public void table() {
		// TODO implement table with all rooms to clean
		tablePanel = new JPanel();
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		String[] columnNames = {"Janitor","Number", "Floor", "Status"};
		
		CleaningManager cleaningManager = ManagerManager.cleaningManager;
		ArrayList<Room> rooms = cleaningManager.getRoomsToBeCleaned(username);
		if (rooms == null) {
			return;
		}
		String[][] data = new String[rooms.size()][4];
		int i = 0;
		for (Room room: rooms) {
			data[i][0] = username;
			data[i][1] = String.valueOf(room.getNumber());
			data[i][2] = String.valueOf(room.getFloor());
			data[i][3] = RoomStatus.getStatus(room.getStatus());
			i++;
		}
		table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(900, 800));
		tablePanel.add(scrollPane);
	}
	
public JanitorJFrame(ManagerManager managerManager, String username){
		
		this.managerManager = managerManager;
		this.username = username;
		
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout());
		upperPanel.setBounds(0,0, 1080, 50);
		
		JButton cleanButton = new JButton("Clean Room");
		cleanButton.setBounds(10,0, 100, 50);
		upperPanel.add(cleanButton);
		cleanButton.addActionListener(ActionEvent -> {
			try {
				AbstractTableModel model = (AbstractTableModel) table.getModel();
				int selectedRow = table.getSelectedRow();
				String roomNumber = (String) model.getValueAt(selectedRow, 1);
				CleaningManager cleaningManager = ManagerManager.cleaningManager;
				Room room = ManagerManager.roomManager.find(Integer.parseInt(roomNumber));
				cleaningManager.cleanRoom(username, room);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Please select a reservation", "Failure", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(120,0, 100, 50);
		upperPanel.add(buttonRefresh);
		buttonRefresh.addActionListener(ActionEvent -> {
				this.remove(tablePanel);
				tablePanel = new JPanel();
				this.table();
				this.add(tablePanel, BorderLayout.CENTER);
				this.repaint();
				this.setVisible(true);

		});
		
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(960,0, 100, 50);
		logoutButton.addActionListener(ActionEvent -> {
            this.dispose();
            new LoginJFrame(managerManager);
        });
		upperPanel.add(logoutButton);
		
		this.table();
		this.setTitle("Hotel");
		this.setSize(1080, 720);
		this.setVisible(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(new java.awt.Color(222, 224, 223));
		this.setLocationRelativeTo(null);
		this.setLayout(new java.awt.BorderLayout());
		this.add(upperPanel, BorderLayout.NORTH);
		this.add(tablePanel, BorderLayout.CENTER);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
