package VendingMachine;

import org.junit.*;
import org.junit.jupiter.api.AfterAll;

import static org.junit.Assert.*;
import VendingMachine.CancelTransactionHandler;
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

public class CancelTrasactionHandlerTest {
	private CancelTransactionHandler handler;

	@Before
	public void setup() {
		String file = "src/test/java/VendingMachine/CancelTransactionSample.json";
		this.handler = new CancelTransactionHandler(file);
	}
	@After
	public void cleanup() {
		try {
			String writeStr = "[{\"reason\":\"user cancelled\",\"time\":\"2020-11-09 14:41:59\",\"username\":\"1\"},{\"reason\":\"user cancelled\",\"time\":\"2020-11-09 14:51:55\",\"username\":\"anonymous\"},{\"reason\":\"timeout\",\"time\":\"2020-11-09 16:00:11\",\"username\":\"anonymous\"},{\"reason\":\"timeout\",\"time\":\"2020-11-09 16:00:21\",\"username\":\"anonymous\"},{\"reason\":\"timeout\",\"time\":\"2020-11-09 16:00:31\",\"username\":\"anonymous\"},{\"reason\":\"timeout\",\"time\":\"2020-11-10 20:42:57\",\"username\":\"anonymous\"}]";
			FileWriter file = new FileWriter("src/test/java/VendingMachine/CancelTransactionSample.json");
			file.write(writeStr);
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addTransactionTest() {
		assertEquals(this.handler.getTransList().size(),6);
		this.handler.addTrans(new CancelTransaction("2020-11-01 14:39:10","1","timeout"));
		assertEquals("2020-11-09 14:41:59",this.handler.getTransList().get(0).getCurrentTime());
		assertEquals("user cancelled",this.handler.getTransList().get(0).getReason());
		assertEquals("1",this.handler.getTransList().get(0).getUsername());
	}
	
	@Test
	public void handleNewTransTest() {
		CancelTransaction newTrans = this.handler.handleNewTrans("timeout");
		assertEquals("timeout",newTrans.getReason());
//		assertEquals(this.handler.getTransList().size(),6);
//		this.handler.addTrans(new CancelTransaction("2020-11-01 14:39:10","1","timeout"));
//		assertEquals("2020-11-09 14:41:59",this.handler.getTransList().get(0).getCurrentTime());
//		assertEquals("user cancelled",this.handler.getTransList().get(0).getReason());
//		assertEquals("1",this.handler.getTransList().get(0).getUsername());
	}
}
