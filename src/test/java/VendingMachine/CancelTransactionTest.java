package VendingMachine;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;

public class CancelTransactionTest {
	CancelTransaction trans;

	@Test
	public void constructorTest(){
		CancelTransaction trans = new CancelTransaction("2020-11-09 14:39:10",
				"1","user cancelled");
		assertNotNull(trans);
	}
	
	
	@Test
	public void getVariableTest(){
		CancelTransaction trans = new CancelTransaction("2020-11-09 14:39:10",
				"1","user cancelled");
//		CancelTransaction trans = new CancelTransaction("2020-11-09 14:39:10",
//				"1","user cancelled");
		assertNotNull(trans);
		assertEquals(trans.getCurrentTime(),"2020-11-09 14:39:10");
		assertEquals(trans.getUsername(),"1");
		assertEquals(trans.getReason(),"user cancelled");
	}
	
	@Test
	public void setVariableTest(){
		CancelTransaction trans = new CancelTransaction("2020-11-09 14:39:10",
				"1","user cancelled");
		assertNotNull(trans);
		trans.setCurrentTime("2020-11-10 14:39:11");
		trans.setUsername("abcd");
		trans.setReason("timeout");
		assertEquals(trans.getCurrentTime(),"2020-11-10 14:39:11");
		assertEquals(trans.getUsername(),"abcd");
		assertEquals(trans.getReason(),"timeout");
	}
	
}
