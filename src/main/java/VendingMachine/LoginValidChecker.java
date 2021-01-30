package VendingMachine;

import java.util.List;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
//import VendingMachine.OwnerPage;

public class LoginValidChecker {
	public static boolean checkValidInput(String jsonFile, String usernamePrompt,
			String passwordPrompt) {
		AccountJsonHandler handler = new AccountJsonHandler(jsonFile);
		List<User> availableUser = handler.getUsers();
		for(int i = 0; i < availableUser.size(); i++) {
			//find the username in the system
			if(usernamePrompt.equals(availableUser.get(i).getUsername())) {
				if(usernamePrompt.equals(availableUser.get(i).getPassword())) {
					//find the account with the right password
					return true;
				}
			}
		}
		return false;
	}


    /*
     * check the type of user
     */
    public static String checkUserType(String jsonFile, String username) {
		AccountJsonHandler handler = new AccountJsonHandler(jsonFile);
		User user = handler.getUser(username);
		if(user instanceof Owner) {
			return "owner";
		}
		if(user instanceof Customer) {
			return "customer";
		}
		if(user instanceof Seller) {
			return "seller";
		}
		if(user instanceof Cashier) {
			return "cashier";
		}
		return "";
    }

	public static void clean(Text actiontarget,
			TextField usernameBox, TextField passwordBox) {
		actiontarget.setText(null);
		usernameBox.setText(null);
		passwordBox.setText(null);
	}


}
