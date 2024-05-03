package main;

import java.time.LocalDate;
import java.util.ArrayList;

import hotel.PriceList;
import manager.AdminManager;
import manager.AgentManager;
import manager.GuestManager;
import manager.JanitorManager;
import manager.PriceListManager;
import manager.ReservationManager;
import manager.RoomManager;
import manager.ServiceManager;
import rooms.Reservation;
import rooms.Room;
import rooms.RoomType;
import users.Admin;
import users.Agent;
import users.Education;
import users.Janitor;

public class Main {
	
	public static void main(String[] args) {
		AdminManager adminManager = new AdminManager();
		AgentManager agentManager = new AgentManager();
		JanitorManager janitorManager = new JanitorManager();
		GuestManager guestManager = new GuestManager();
		
		//		Kreirati jednog administratora sistema (Pera Perić).
		//		Pera Perić dodaje recepcionere Miku Mikića i Nikolu Nikolića u sistem, kao i sobaricu Janu
		//		Janić.
		adminManager.add("admin","admin123","Pera","Peric",LocalDate.of(1998, 12, 12),123456789,true,Education.BACHELOR,5,1000.0);
		agentManager.add("mikam","mikam123","Mika","Mikic",LocalDate.of(1990, 8, 15),32719830,true,Education.BACHELOR,3,800.0);
		agentManager.add("nikolan","nikolan123","Nikola","Nikolic",LocalDate.of(1980, 10, 02),832494902,true,Education.MASTER,10,1500.0);
		janitorManager.add("janaj","janaj123","Jana","Janic",LocalDate.of(1978, 10, 12),39248213,false,Education.PHD,15,2000.0);
		
		//		prikaz svih zaposlenih
		for (Admin admin : adminManager.getAdmins()) {
			System.out.println("Admin: "+admin.get_username());
		}
		for (Agent agent : agentManager.getAgents()) {
			System.out.println("Agent: "+agent.get_username());
		}
		for (Janitor janitor : janitorManager.getJanitors()) {
			System.out.println("Janitor: "+janitor.get_username());
		}
		
		//		Administrator uklanja Nikolu Nikolića iz sistema jer nije više zaposlen u hotelu.
		agentManager.find("nikolan").delete();
		
		//		Mika dodaje nove goste Milicu Milić i Anu Anić.
		guestManager.add("milicam@gmail.com","1212001800053","Milica","Milic",LocalDate.of(2001, 12, 12),94572801,false);
		guestManager.add("anica@gmail.com","2507996810382","Ana","Anic",LocalDate.of(1996, 07, 25),94572801,false);
		RoomManager roomManager = new RoomManager();
		
		//		Dodati nekoliko soba u sistem, tako da postoji bar jedna jednokrevetna (1), dvokrevetna (2),
		//		dvokrevetna (1+1) i trokrevetna (2+1) soba. Opciono dodati još soba i tipova soba u sistem.
		roomManager.add(RoomType.SINGLE, 1, 1);
		roomManager.add(RoomType.DOUBLE, 2, 1);
		roomManager.add(RoomType.TRIPLE, 3, 1);
		roomManager.add(RoomType.SUITE, 4, 1);
		
		//Jedna dvokrevetna soba (2) postaje trokrevetna (2+1).
		roomManager.find(RoomType.DOUBLE).setType(RoomType.TRIPLE);
		
		//		Kreirati dodatne usluge: doručak, ručak, večera, bazen, spa centar.
		ServiceManager serviceManager = new ServiceManager();
		serviceManager.add("Breakfast");
		serviceManager.add("Lunch");
		serviceManager.add("Dinner");
		serviceManager.add("Pool");
		serviceManager.add("Spa");
		
		//	Uklanja se dodatna usluga za spa centar.
		serviceManager.find("Spa").delete();
		
		// dodati cenovnik
		PriceListManager priceListManager = new PriceListManager();
		priceListManager.add(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));
		PriceList priceList = priceListManager.find(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));
		priceList.addRoomPrice(RoomType.SINGLE, 50.0);
		priceList.addRoomPrice(RoomType.DOUBLE, 100.0);
		priceList.addRoomPrice(RoomType.TRIPLE, 150.0);
		priceList.addRoomPrice(RoomType.SUITE, 125.0);
		priceList.addServicePrice(serviceManager.find("Breakfast"), 10.0);
		priceList.addServicePrice(serviceManager.find("Lunch"), 20.0);
		priceList.addServicePrice(serviceManager.find("Dinner"), 30.0);
		priceList.addServicePrice(serviceManager.find("Pool"), 5.0);
		priceList.addServicePrice(serviceManager.find("Spa"), 50.0);
		
		//Cena doručka se menja u prethodno kreiranom cenovniku.
		priceList.changeServicePrice(serviceManager.find("Breakfast"), 15.0);
		
		//		Prikazati koji tipovi sobe su dostupni u terminima od 01.08.2024. do 31.08.2024. Za Milicu
		//		Milić se kreira rezervacija u periodu od 13.08.2024. do 23.8.2024 za trokrevetnu sobu (2+1).
		//		Prilikom rezervacije izabrati kao dodatnu uslugu doručak i večeru.
		ReservationManager reservations = new ReservationManager();
		ArrayList<Room> roomsAvailable = reservations.roomsAvailable(roomManager.getRooms(), LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 31));
		for (Room roomAvailable : roomsAvailable) {
			System.out.println("Room available: " + roomAvailable.getNumber() + " Type: " + roomAvailable.getType());
		}
		reservations.add(LocalDate.of(2024, 8, 13), LocalDate.of(2024, 8, 23), roomManager.find(RoomType.TRIPLE), guestManager.find("milicam@gmail.com"));
		reservations.findReservation(guestManager.find("milicam@gmail.com"), LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 31)).addService(serviceManager.find("Breakfast"));
		reservations.findReservation(guestManager.find("milicam@gmail.com"), LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 31)).addService(serviceManager.find("Dinner"));
		
		//		Prikazati koji tipovi sobe su dostupni u terminima od 01.06.2024. do 30.06.2024. Kreirati
		//		rezervaciju za Anu Anić u periodu od 6.6.2024. do 12.6.2024. za dvokrevetnu (1+1) sobu.
		roomsAvailable = reservations.roomsAvailable(roomManager.getRooms(), LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30));
		for (Room roomAvailable : roomsAvailable) {
			System.out.println("Room available: " + roomAvailable.getNumber() + " Type: " + roomAvailable.getType());
		}
		reservations.add(LocalDate.of(2024, 6, 6), LocalDate.of(2024, 6, 12), roomManager.find(RoomType.SUITE), guestManager.find("anica@gmail.com"));
		
		//		Prikazati sve rezervacije Milice Milić sa aktuelnim statusom.
		for (Reservation reservation: reservations.findReservations(guestManager.find("milicam@gmail.com"))){
			System.out.println("Reservations: " + reservation.getRoom().getNumber() + " Status:" + reservation.getStatus());
		}
	}
}
