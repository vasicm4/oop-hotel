package manager;

import java.time.LocalDate;
import java.util.ArrayList;

import users.Agent;
import users.Education;

public class AgentManager {
	ArrayList<Agent> agents;
	String fileName;
	String filePath;
	
	public AgentManager() {
		agents = new ArrayList<Agent>();
		fileName = "agents.txt";
		filePath = "data/";
	}
	
	public Agent find(String username) {
		for (Agent agent : agents) {
			if (agent.get_username().equals(username)) {
				return agent;
			}
		}
		System.out.println("Agent not found");
		return null;
	}
	
	public ArrayList<Agent> getAgents() {
		return agents;
	}
	
	public void add(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, boolean male, Education education, int experience, double baseSalary) {
		this.agents.add(new Agent(username, password, name, surname, dateOfBirth, phoneNumber, male, education, experience, baseSalary));
	}
	
	public void remove(Agent agent) {
		agent.delete();
	}
	
}
