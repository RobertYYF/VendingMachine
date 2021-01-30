package VendingMachine;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Before;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class OwnerReportTest {
	String jsonFile;
	String transJson;

	@Before
	public void setUp() {
		jsonFile = "src/test/java/VendingMachine/AccountSample.json";
		transJson = "src/test/java/VendingMachine/CancelTransactionSample.json";
	}

	@Test
	public void getUserwithRoleTest() {
		Map<String,String> users = OwnerReport.getUserwithRole(jsonFile);
		assertNotNull(users);
		assertEquals(users.get("1"),"customer");
		assertEquals(users.get("2"),"customer");
	}

	@Test
	public void buildUsernameReportTest() {
		String txt = "src/test/java/VendingMachine/UsernameInfoReportSample.txt";
		Map<String,String> users = OwnerReport.getUserwithRole(jsonFile);
		ArrayList<String> data = new ArrayList<String>();
		OwnerReport.buildUsernameReport(users,txt);
		try {
			File file = new File(txt);
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				data.add(line);
				//				System.out.println(data);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		assertEquals(data.size(),6);
		assertEquals(data.get(0).strip(), "Current Available Users");
		assertEquals(data.get(1).strip(), "Username:  1		Role:  customer");
		assertEquals(data.get(2).strip(), "Username:  a		Role:  owner");
	}

	//need json file first
	@Test
	public void buildCancelTransReport() {
		String txt = "src/test/java/VendingMachine/CancelTransactionReportSample.txt";
		OwnerReport.buildCancelTransReport(transJson,txt);
		ArrayList<String> data = new ArrayList<String>();
		try {
			File file = new File(txt);
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				data.add(line);
				//				System.out.println(data);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		assertEquals(data.size(),7);
		assertEquals(data.get(0).strip(), "Cancel Transaction Details");
		assertEquals(data.get(1).strip(), "Time:  2020-11-09 14:41:59		Username:  1		Cancelled Reason:  user cancelled");
//		assertEquals(data.get(2).strip(), "Username:  2		Role:  customer");
	}
}



