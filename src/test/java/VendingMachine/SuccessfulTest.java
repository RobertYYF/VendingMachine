package VendingMachine;

import org.junit.Test;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.*;
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
public class SuccessfulTest extends ApplicationTest{
    Successful successful;
    Cash cash;
    @Override
    public void start(Stage stage) {
        successful = new Successful();
        cash = new Cash();
    }
    @Test
    public void getChangeTest(){
        double userChange = 17;
        String changes = "";
        changes = successful.getChange(userChange,cash);
        assertNotEquals("",changes);
    }
}
