import java.io.*;
import java.util.*;

public class Tickets {

	private String eventName, sellerName;
	private int numOfTickets; 
	double pricePerTicket;
	
	public Tickets(String eventName, String sellerName, int numOfTickets, double pricePerTicket) {
		
		this.eventName = eventName;
		this.sellerName = sellerName;
        this.numOfTickets = numOfTickets;
        this.pricePerTicket = pricePerTicket;
	}
	
	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

    public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
    }
    public int getNumOfTickets() {
		return numOfTickets;
	}

	public void setNumOfTickets(int numOfTickets) {
		this.numOfTickets = numOfTickets;
    }
    
    public double getPricePerTicket() {
		return pricePerTicket;
	}

	public void setPricePerTicket(int pricePerTicket) {
		this.pricePerTicket = pricePerTicket;
	}
	
}
