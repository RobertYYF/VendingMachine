package VendingMachine;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class AccountJsonHandler {

	private List<User> users;
	private String jsonFile;
	//chengyufei
	//public static List<String> usernamelist;

	public AccountJsonHandler(String jsonFile) {
		this.jsonFile = jsonFile;
		// this.users = new ArrayList<>();
		this.init();
	}

	/**
	 * A private method, which is used to read information from the corresponding JSON file.
	 */
	private void init() {
		this.users = new ArrayList<>();
		JSONParser jsonParser = new JSONParser();
		try {
			FileReader fileReader = new FileReader(jsonFile);
			Object obj = jsonParser.parse(fileReader);

			JSONArray accountArr = (JSONArray) obj;
			for(Object object: accountArr) {
				JSONObject account = (JSONObject) object;
				String username = (String) account.get("username");
				String password = (String) account.get("password");
				String role = (String) account.get("role");

				//chengyufei
				//usernamelist.add(username);


				JSONArray cards = (JSONArray) account.get("creditCards");
				Map<String, String> cardDetails = new HashMap<>();
				for(Object creditCard: cards) {
					JSONObject card = (JSONObject) creditCard;
					String cardholderName = (String) card.get("name");
					String number = (String) card.get("number");

					cardDetails.put(cardholderName, number);
				}

				User user = null;

				if(role.equals("customer")) {
					user = new Customer(username, password);
					((Customer) user).setCreditCards(cardDetails);
				}
				if(role.equals("owner")) {
					user = new Owner(username, password);
				}
				if(role.equals("seller")) {
					user = new Seller(username, password);
				}
				if(role.equals("cashier")) {
					user = new Cashier(username, password);
				}
				//other user type will be implemented later
				this.users.add(user);
			}

		}catch(IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete a user in the system
	 * @param user
	 */
	public void deleteAccount(String user) {
		int removeInd = -1;
		for(int i =0; i < this.users.size(); i++) {
			if(users.get(i).getUsername().equals(user)) {
				removeInd = i;
			}
		}
		if(removeInd >= 0) {
			this.users.remove(removeInd);
		}
		try {
			JSONParser jsonParser = new JSONParser();
			FileReader fileReader = new FileReader(jsonFile);
			Object obj = jsonParser.parse(fileReader);
			int removeIndex = -1;
			JSONArray accountArr = (JSONArray) obj;
			for(int i = 0; i < accountArr.size(); i++) {
				JSONObject account = (JSONObject) accountArr.get(i);
				String username = (String) account.get("username");
				String password = (String) account.get("password");
				String role = (String) account.get("role");
				System.out.println(user);
				if (username.equals(user)) {
					System.out.println("REMOVING KEY:VALUE");
					removeIndex = i;
				}
			}
			if (removeIndex >= 0) {
				accountArr.remove(removeIndex);
				FileWriter file = new FileWriter(jsonFile);
				file.write(accountArr.toJSONString());
				file.flush();
				file.close();
			}
		} catch (IOException | ParseException ex) {
			System.out.println("Error: " + ex);
		}
	}

	/**
	 * Add a new Account into the system and the JSON file.
	 * @param user
	 */
	public void addAccount(User user) {
		this.init();
		this.users.add(user);

		JSONObject accountDetails = new JSONObject();
		accountDetails.put("username", user.getUsername());
		accountDetails.put("password", user.getPassword());
		if(user instanceof Customer) {
			accountDetails.put("role", "customer");

			JSONArray creditCardDetails = new JSONArray();
			Map<String, String> allCards = ((Customer) user).getCreditCards();
			for(Entry<String, String> entry: allCards.entrySet()) {
				JSONObject card = new JSONObject();
				card.put("name", entry.getKey());
				card.put("number", entry.getValue());
				creditCardDetails.add(card);
			}
			accountDetails.put("creditCards", creditCardDetails);
		}
		//need to implement
		if(user instanceof Owner) {
			JSONArray creditCardDetails = new JSONArray();
			accountDetails.put("role", "owner");
			accountDetails.put("creditCards", creditCardDetails);
		}

		if(user instanceof Seller) {
			JSONArray creditCardDetails = new JSONArray();
			accountDetails.put("role", "seller");
			accountDetails.put("creditCards", creditCardDetails);
		}

		if(user instanceof Cashier) {
			JSONArray creditCardDetails = new JSONArray();
			accountDetails.put("role", "cashier");
			accountDetails.put("creditCards", creditCardDetails);
		}

		JSONParser jsonParser = new JSONParser();
		try {
			FileReader fileReader = new FileReader(jsonFile);
			Object obj = jsonParser.parse(fileReader);
			JSONArray accountArr = (JSONArray) obj;
			accountArr.add(accountDetails);

			FileWriter file = new FileWriter(jsonFile);
			file.write(accountArr.toJSONString());
			file.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get all other username except for the customers
	 * @return List<String>
	 */
	public List<String> getSpecialUserName() {
		this.init();
		List <String> specialUsername = new ArrayList<String>();
		for(User user: this.users) {
			if(user instanceof Seller || user instanceof Cashier 
					|| user instanceof Owner) {
				specialUsername.add(user.getUsername());
			}
		}
		return specialUsername;
	}
	
	public List<User> getSpecialUsers() {
		this.init();
		List <User> specialUser = new ArrayList<User>();
		for(User user: this.users) {
			if(user instanceof Seller || user instanceof Cashier 
					|| user instanceof Owner) {
				specialUser.add(user);
			}
		}
		return specialUser;
	}
	
	/**
	 * Save a new Credit card information into the corresponding user and the JSON file.
	 * @param user
	 */
	public void SaveCreditCardInformation(String username, String cardholderName, String cardNumber) {
		this.init();
		User target = this.getUser(username);
		((Customer) target).addCreditCard(cardholderName, cardNumber);

		JSONParser jsonParser = new JSONParser();
		try {
			FileReader fileReader = new FileReader(jsonFile);
			Object obj = jsonParser.parse(fileReader);
			JSONArray accountArr = (JSONArray) obj;

			for(Object account: accountArr) {
				JSONObject customer = (JSONObject) account;
				String customerName = (String) customer.get("username");
				if(customerName.equals(username)) {
					JSONArray cards = (JSONArray) customer.get("creditCards");
					JSONObject card = new JSONObject();
					card.put("name", cardholderName);
					card.put("number", cardNumber);
					cards.add(card);
					break;
				}
			}

			FileWriter file = new FileWriter(jsonFile);
			file.write(accountArr.toJSONString());
			file.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the user according to the username.
	 * @param username
	 * @return
	 */
	public User getUser(String username) {
		User target = null;
		for(User user: this.users) {
			if(user.getUsername().equals(username)) {
				target = user;
				break;
			}
		}
		return target;
	}

	/**
	 * get the list of all the exist account of the system.
	 * @return
	 */
	public List<User> getUsers() {
		this.init();
		return this.users;
	}

	public String getCreditCardNumber(String username, String cardholderName) {
		this.init();
		User target = this.getUser(username);
		if(target != null) {
			String result = null;
			Map<String, String> allCards = ((Customer) target).getCreditCards();
			for(Entry<String, String> entry: allCards.entrySet()) {
				if(entry.getKey().equals(cardholderName)) {
					result = entry.getValue();
					break;
				}
			}
			return result;
		}else {
			return null;
		}
	}



	public static List<String> readFromJson(String filename){
		List<String> names = new ArrayList<>();
		JSONParser jsonParser = new JSONParser();
		Object obj = null;
		try {

			try (FileReader fileReader = new FileReader(filename)) {
				obj = jsonParser.parse(fileReader);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			JSONArray accountArr = (JSONArray) obj;
			for(Object object: accountArr) {
				JSONObject account = (JSONObject) object;
				String username = (String) account.get("username");
				names.add(username);
			}} catch (Exception e) {
			e.printStackTrace();
		}
		return names;


	}

}
