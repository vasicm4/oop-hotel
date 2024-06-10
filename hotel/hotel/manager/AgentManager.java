package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.HashMap;

import users.Admin;
import users.Agent;
import users.Education;
import users.Gender;

public class AgentManager {
	private static AgentManager instance;
	protected HashMap<String, Agent> agents;
	protected String fileName;
	protected String filePath;
	
	public static AgentManager getInstance() {
		if (instance == null) {
			instance = new AgentManager();
		}
		return instance;
	}
	
	public AgentManager() {
		agents = new HashMap<String, Agent>();
		fileName = "agents.csv";
		filePath = "data/";
	}
	
	public Agent find(String username) {
		if (agents.containsKey(username)) {
			return agents.get(username);
		} else {
			return null;
		}
	}
	
	public HashMap<String, Agent> getAgents() {
		return agents;
	}
	
	public Double calculateAllSalaries() {
		double sum = 0;
		for (Agent agent : agents.values()) {
			if (!agent.isDeleted()) {
				sum += agent.calculateSalary();
			}
		}
		return sum;	
	}
	
	public HashMap<String, Agent> getAvailableAgents() {
		HashMap<String, Agent> agents = new HashMap<String, Agent>();
		for (Agent agent : this.agents.values()) {
			if (agent.isDeleted() == false) {
				agents.put(agent.getUsername(), agent);
			}
		}
		return agents;
	}
	
	public void add(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, Gender gender, Education education, int experience, double baseSalary) {
		this.agents.put(username,new Agent(username, password, name, surname, dateOfBirth, phoneNumber, gender, education, experience, baseSalary));
	}
	
	public void add(String username, String password, String name, String surname, LocalDate dateOfBirth, int phoneNumber, Gender gender, Education education, int experience, double baseSalary, Boolean deleted) {
		this.agents.put(username,new Agent(username, password, name, surname, dateOfBirth, phoneNumber, gender, education, experience, baseSalary, deleted));
	}
	
	public void remove(Agent agent) {
		agent.delete();
	}
	
	public void readData() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath + fileName));
			String line;
			while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
				this.add(data[0], data[1], data[2], data[3], LocalDate.parse(data[4]), Integer.parseInt(data[5]), Gender.valueOf(data[6]), Education.valueOf(data[7]), Integer.parseInt(data[8]), Double.parseDouble(data[9]), Boolean.parseBoolean(data[10]));
			}
		}catch (Exception e) {
			System.out.println("Error reading file" + fileName);
		}
	
	}
	
	public void writeData() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + fileName));
			for (Agent agent : agents.values()) {
				writer.write(agent.getUsername() + "," + agent.getPassword() + "," + agent.getName() + "," + agent.getSurname() + "," + agent.getDateOfBirth().toString() + "," + String.valueOf(agent.getPhoneNumber()) + "," + String.valueOf(agent.getGender()) + "," + String.valueOf(agent.getEducationLevel()) + "," + String.valueOf(agent.getExperience()) + "," + String.valueOf(agent.getBaseSalary()) +"," + String.valueOf(agent.isDeleted()) + "\n");
			}
			writer.flush();
		} catch (Exception e) {
			System.out.println("Error writing to file" + fileName);
		}
	}
	
}
