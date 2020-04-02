import java.io.*;
import java.util.*;

public class Transactions {

	private String command, eventName, sellerName;
	private int numOfTickets, pricePerTicket;
	private boolean isLoggedIn = false;

	public Transactions(String command, String eventName, String sellerName, int numOfTickets, int pricePerTicket) {
        
        this.command = command;
		this.eventName = eventName;
		this.sellerName = sellerName;
        this.numOfTickets = numOfTickets;
        this.pricePerTicket = pricePerTicket;
	}
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
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

	public void setsellerName(String sellerName) {
		this.sellerName = sellerName;
    }
    public int getNumOfTickets() {
		return numOfTickets;
	}

	public void setNumOfTickets(int numOfTickets) {
		this.numOfTickets = numOfTickets;
    }
    
    public int getPricePerTicket() {
		return pricePerTicket;
	}

	public void setPricePerTicket(int pricePerTicket) {
		this.pricePerTicket = pricePerTicket;
	}
	
}
