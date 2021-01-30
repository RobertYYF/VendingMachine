package VendingMachine;

import java.util.Date;

public class CancelTransaction {
	String currentTime;
	String username;
	String reason;
	
	public CancelTransaction(String currentTime, String username, String reason) {
		this.currentTime = currentTime;
		this.username = username;
		this.reason = reason;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
}
