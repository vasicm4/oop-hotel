package manager;

import java.time.LocalDate;
import java.util.ArrayList;

import hotel.PriceList;

public class PriceListManager {
	ArrayList<PriceList> priceLists;
	String fileName;
	String filePath;
	
	public PriceListManager() {
		priceLists = new ArrayList<PriceList>();
		fileName = "priceLists.txt";
		filePath = "data" + System.getProperty("file.separator");
	}
	
	public PriceList find(LocalDate startDate, LocalDate endDate) {
		for (PriceList priceList : priceLists) {
			if (priceList.getStartDate().equals(startDate) && priceList.getEndDate().equals(endDate)) {
				return priceList;
			}
		}
		System.out.println("Price list not found");
		return null;
	}
	
	public ArrayList<PriceList> getPriceLists() {
		return priceLists;
	}
	
	public void add(LocalDate startDate, LocalDate endDate) {
		this.priceLists.add(new PriceList(startDate, endDate));
	}
	
	public void remove(PriceList priceList) {
		priceList.delete();
	}
	
	
}
