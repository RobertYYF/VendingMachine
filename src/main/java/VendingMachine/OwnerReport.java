package VendingMachine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwnerReport {
	public static Map<String,String> getUserwithRole(String jsonFile){
		Map<String,String> userRole = new HashMap<String,String>();
		AccountJsonHandler handler = new AccountJsonHandler(jsonFile);
		List<User> Users = handler.getUsers();
		for(int i = 0; i < Users.size(); i++) {
			String rawRole = Users.get(i).getClass().toString();
			String role = rawRole.replace("class VendingMachine.","").strip().toLowerCase();
			userRole.put(Users.get(i).getUsername(), role);
		}
		return userRole;
	}

	public static void buildUsernameReport(Map<String,String> userRole, String reportName) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(reportName));
			out.write("Current Available Users\t\n");
			for (Map.Entry item : userRole.entrySet()) {
				out.write("Username:  "+ item.getKey()+"\t\t");
				out.write("Role:  "+item.getValue()+"\t\t");
				out.write("\n");
//				System.out.println("Key: "+item.getKey() + " & Value: " + item.getValue());
			}
			out.close();
		} catch (IOException e) {
		}
	}
	
	public static void buildCancelTransReport(String jsonFile, String reportName) {
		CancelTransactionHandler handler = new CancelTransactionHandler(jsonFile);
		try {
			List<CancelTransaction> trans = handler.getTransList();
			BufferedWriter out = new BufferedWriter(new FileWriter(reportName));
			out.write("Cancel Transaction Details\t\n");
			for(int i =0;i<trans.size();i++){
                out.write("Time:  "+trans.get(i).getCurrentTime()+"\t\t");
                out.write("Username:  "+trans.get(i).getUsername()+"\t\t");
                out.write("Cancelled Reason:  "+trans.get(i).getReason()+"\t\t");


                out.write("\n");
            }
			out.close();
		} catch (IOException e) {
		}
	}
}
