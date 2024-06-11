package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import manager.ManagerManager;
import manager.ReservationManager;
import rooms.Reservation;
import rooms.ReservationStatus;

public class GuestJFrame extends JFrame implements ActionListener{
	JFrame frame;
	JPanel tablePanel;
	String username;
	JTable table;

	public void table() {
		tablePanel = new JPanel();
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
				
		String[] columnNames = {"Start Date", "End Date", "Room", "Room Type", "Status", "Services", "Price"};
		
		ReservationManager reservationManager = ManagerManager.reservationManager;
		ArrayList<Reservation> reservations = reservationManager.findReservations(ManagerManager.getGuestManager().find(username));
	
		String[][] data = new String[reservations.size() + 1][7];
		int i = 0;
		for (Reservation reservation : reservations) {
			data[i][0] = reservation.getCheckIn().toString();
			data[i][1] = reservation.getCheckOut().toString();
			if (reservation.getRoom() == null) {
				data[i][2] = "N/A";
			} else {
				data[i][2] = String.valueOf(reservation.getRoom().getNumber());
			}
			data[i][3] = reservation.getRoomType().toString();
			data[i][4] = ReservationStatus.getStatus(reservation.getStatus());
			data[i][5] = reservation.getServices().toString();
			data[i][6] = String.valueOf(reservation.getPrice(ManagerManager.getPriceListManager()));
			i++;
		}
		data[reservations.size()][0] = "Total";
		data[reservations.size()][6] = String.valueOf(reservationManager.allReservationsExpenses(ManagerManager.getGuestManager().find(username)));
		table = new JTable(data, columnNames);
		table.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(900, 800));
		tablePanel.add(scrollPane);
	}
	
	public GuestJFrame(ManagerManager managerManager, String username){
		
		this.username = username;
		
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout());
		upperPanel.setBounds(0,0, 1080, 50);
		
		JButton buttonAdd = new JButton("Add new reservation");
		buttonAdd.setBounds(10,0, 100, 50);
		ArrayList<String> data = new ArrayList<String>();
		upperPanel.add(buttonAdd);
		buttonAdd.addActionListener(ActionEvent -> {
			new AddReservation(managerManager, username, data);
		});
		
		JButton buttonCancel = new JButton("Cancel reservation");
		buttonCancel.setBounds(20,0, 100, 50);
		upperPanel.add(buttonCancel);
		buttonCancel.addActionListener(ActionEvent -> {
			try {
				AbstractTableModel model = (AbstractTableModel) table.getModel();
				int selectedRow = table.getSelectedRow();
				Reservation reservation = ManagerManager.reservationManager.findReservationString(username, model.getValueAt(selectedRow, 0).toString(), model.getValueAt(selectedRow, 1).toString());
				reservation.cancel();
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
		this.add(upperPanel, BorderLayout.NORTH);
		this.add(tablePanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
