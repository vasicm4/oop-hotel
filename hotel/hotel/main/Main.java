package main;

import manager.ManagerManager;
import view.AgentJFrame;
import view.GuestJFrame;

public class Main {
	
	public static void main(String[] args) {
		ManagerManager managerManager = ManagerManager.getInstance();
		ManagerManager.loadServices();
		
		
//		GuestJFrame guestJFrame = new GuestJFrame(managerManager, "milicam@gmail.com");
//		AdminJFrame adminJFrame = new AdminJFrame(managerManager);
		AgentJFrame agentJFrame = new AgentJFrame(managerManager);
//		JanitorJFrame janitorJFrame = new JanitorJFrame(managerManager, "janaj");
//		LoginJFrame frame = new LoginJFrame(managerManager);
	}
}
