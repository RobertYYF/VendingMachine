package VendingMachine;

import org.junit.*;
import org.junit.jupiter.api.AfterAll;

import static org.junit.Assert.*;
import VendingMachine.AccountJsonHandler;
import VendingMachine.User;
import VendingMachine.Customer;

import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AccountJsonHandlerTest {

	private AccountJsonHandler handler;

	@Before
	public void setup() {
		this.handler = new AccountJsonHandler("src/test/java/VendingMachine/AccountSample.json");
	}

	@After
	public void clean() {
		try {
			String writeInStr = "[{\"password\":\"1\",\"role\":\"customer\",\"creditCards\":[{\"number\":\"40691\",\"name\":\"Charles\"}],\"username\":\"1\"},{\"password\":\"2\",\"role\":\"customer\",\"creditCards\":[],\"username\":\"2\"},{\"password\":\"a\",\"role\":\"owner\",\"creditCards\":[],\"username\":\"a\"},{\"password\":\"q\",\"role\":\"seller\",\"creditCards\":[],\"username\":\"q\"},{\"password\":\"c\",\"role\":\"cashier\",\"creditCards\":[],\"username\":\"c\"}]";
			FileWriter file = new FileWriter("src/test/java/VendingMachine/AccountSample.json");
			file.write(writeInStr);
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void constructorTestValid() {
		assertNotNull(this.handler);
		assertTrue(this.handler instanceof AccountJsonHandler);
	}

	@Test
	public void constructorTestInvalid() {
		AccountJsonHandler invalidHandler = new AccountJsonHandler("FileNotExist.txt");
		assertNotNull(this.handler);
		assertTrue(this.handler instanceof AccountJsonHandler);

		List<User> users = invalidHandler.getUsers();
		assertNotNull(users);
		assertEquals(users.size(), 0);
	}

	@Test
	public void getUsersTest() {
		List<User> users = this.handler.getUsers();
		assertNotNull(users);
		assertEquals(users.size(), 5);

		assertEquals("1", users.get(0).getUsername());
		assertEquals("1", users.get(0).getPassword());

		assertEquals("2", users.get(1).getUsername());
		assertEquals("2", users.get(1).getPassword());
	}

	@Test
	public void addAccountTest1() {
		Customer customer = new Customer("3", "3");
		this.handler.addAccount(customer);

		List<User> users = this.handler.getUsers();
		assertNotNull(users);
		assertEquals(users.size(), 6);

		assertEquals("1", users.get(0).getUsername());
		assertEquals("1", users.get(0).getPassword());

		assertEquals("2", users.get(1).getUsername());
		assertEquals("2", users.get(1).getPassword());

		assertEquals("3", users.get(users.size()-1).getUsername());
		assertEquals("3", users.get(users.size()-1).getPassword());
	}

	@Test
	public void addAccountTest2() {
		Customer customer = new Customer("3", "3");
		customer.addCreditCard("Kasey", "60146");
		this.handler.addAccount(customer);

		List<User> users = this.handler.getUsers();
		assertNotNull(users);
		assertEquals(users.size(), 6);

		assertEquals("3", users.get(users.size()-1).getUsername());
		assertEquals("3", users.get(users.size()-1).getPassword());

		assertEquals(1, ((Customer) users.get(users.size()-1)).getCreditCards().size());
	}

	@Test
	public void deleteAccountTest() {
		List<User> users = this.handler.getUsers();
		assertNotNull(users);
		assertEquals(users.size(), 5);

		this.handler.deleteAccount("1");
		users = this.handler.getUsers();
		assertNotNull(users);
		assertEquals(users.size(), 4);

		assertEquals("2", users.get(0).getUsername());
		assertEquals("2", users.get(0).getPassword());
	}

	@Test
	public void getSpecialUserNameTest() {
		List<User> users = this.handler.getUsers();
		assertNotNull(users);
		assertEquals(users.size(), 5);

		Seller seller = new Seller("4", "4");
		Owner owner = new Owner("a","a");
		this.handler.addAccount(seller);
		this.handler.addAccount(owner);

		users = this.handler.getUsers();
		assertNotNull(users);
		assertEquals(users.size(), 7);

		List<String> username = this.handler.getSpecialUserName();
		assertNotNull(username);
		assertEquals(username.size(), 5);
		User singleUser = this.handler.getUser("a");
		assertTrue(singleUser instanceof Owner);

	}

	@Test
	public void getCreditCardNumberTestValid() {
		assertEquals("40691", this.handler.getCreditCardNumber("1", "Charles"));
	}

	@Test
	public void getCreditCardNumberTestInvalidUserName() {
		assertNull(this.handler.getCreditCardNumber("invalid name", "Charles"));
	}

	@Test
	public void getCreditCardNumberTestInvalidCardholderName() {
		assertNull(this.handler.getCreditCardNumber("1", "not exist"));
	}

	@Test
	public void SaveCreditCardInformationTest() {
		assertEquals(1, ((Customer) this.handler.getUsers().get(0)).getCreditCards().size());
		this.handler.SaveCreditCardInformation("1", "Sergio", "42689");
		assertEquals(2, ((Customer) this.handler.getUsers().get(0)).getCreditCards().size());
	}

	@Test
	public void readFromJsonTest() {
		assertEquals(5, AccountJsonHandler.readFromJson("src/test/java/VendingMachine/AccountSample.json").size());

		assertEquals("1", AccountJsonHandler.readFromJson("src/test/java/VendingMachine/AccountSample.json").get(0));
		assertEquals("2", AccountJsonHandler.readFromJson("src/test/java/VendingMachine/AccountSample.json").get(1));
	}

	@Test
	public void getSpecialUsersTest() {
		assertEquals(3, this.handler.getSpecialUsers().size());
	}

}
