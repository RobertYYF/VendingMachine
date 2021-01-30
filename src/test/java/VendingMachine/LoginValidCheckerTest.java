package VendingMachine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import VendingMachine.LoginValidChecker;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;


public class LoginValidCheckerTest extends ApplicationTest {
	String jsonFile;
	String usernamePrompt;
	String passwordPrompt;
	TextField usernameBox;
	PasswordField passwordBox;
	Text actiontarget;
	
    @Override
    public void start(Stage stage) {
		usernameBox = new TextField();
		passwordBox = new PasswordField();
		actiontarget = new Text();
    }
	@Before
	public void init() {
		jsonFile = "src/test/java/VendingMachine/AccountSample.json";
	}
	
	@Test
	public void emptyInputTest() {
		usernamePrompt = "";
		passwordPrompt = "";
		boolean result = 
				LoginValidChecker.checkValidInput(jsonFile, usernamePrompt,passwordPrompt);
		assertFalse(result);
		
	}
	@Test
	public void notexistInputTest() {
		usernamePrompt = "3";
		passwordPrompt = "3";
		boolean result = 
				LoginValidChecker.checkValidInput(jsonFile, usernamePrompt,passwordPrompt);
		assertFalse(result);
	}
	
	@Test
	public void SuccessfulLoginTest() {
		usernamePrompt = "1";
		passwordPrompt = "1";
		boolean result = 
				LoginValidChecker.checkValidInput(jsonFile, usernamePrompt,passwordPrompt);
		assertTrue(result);
	}
	
	@Test
	public void cleanTest() {
		usernameBox.setText("1");
		passwordBox.setText("1");
		actiontarget.setText("1");
		LoginValidChecker.clean(actiontarget,usernameBox,passwordBox);
		assertEquals("",actiontarget.getText());
		assertNull(usernameBox.getText());
		assertNull(passwordBox.getText());
	}
	
	@Test
	public void checkUserTypeTest() {
		String userType = LoginValidChecker.checkUserType(jsonFile,"1");
		assertEquals(userType,"customer");
		String userType1 = LoginValidChecker.checkUserType(jsonFile,"a");
		assertEquals(userType1,"owner");
		String userType2 = LoginValidChecker.checkUserType(jsonFile,"c");
		assertEquals(userType2,"cashier");
		String userType3 = LoginValidChecker.checkUserType(jsonFile,"q");
		assertEquals(userType3,"seller");
	}
}
