package VendingMachine;

import org.junit.*;
import static org.junit.Assert.*;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import VendingMachine.CreateAccountValidCheck;
import VendingMachine.User;
import VendingMachine.AccountJsonHandler;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateAccountTest extends ApplicationTest {

	private String jsonFile;
	private AccountJsonHandler handler;
	private List<User> users;

	@Override
	public void start(Stage stage) {

	}

	@Before
	public void init() {
		jsonFile = "src/test/java/VendingMachine/AccountSample.json";
		this.handler = new AccountJsonHandler(jsonFile);
		this.users = this.handler.getUsers();
	}

	@Test
	public void checkExistenceTest1() {
		String username = "1";
		boolean result = CreateAccountValidCheck.checkExistence(this.users, username);
		assertTrue(result);
	}

	@Test
	public void checkExistenceTest2() {
		String username = "Ann";
		boolean result = CreateAccountValidCheck.checkExistence(this.users, username);
		assertFalse(result);
	}

}
