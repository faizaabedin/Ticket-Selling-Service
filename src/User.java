import java.io.*;
import java.util.*;

public class User {

	private String username, type;
	double credit;

	public User(String username, double credit, String type) {
		//super();
		this.username = username;
		this.credit = credit;
		this.type = type;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean checkAdmin(){
		if (this.getType().equals("admin"))
			return true;
		else return false;
	}
	
	public boolean checkNotStdBuy(){
		if (this.getType().equals("stdbuy"))
			return false;
		else return true;
	}
	
	public boolean checkNotStdSell(){
		if (this.getType().equals("stdsell"))
			return false;
		else return true;
	}
	
	
}
