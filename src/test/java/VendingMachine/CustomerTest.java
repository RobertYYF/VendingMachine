package VendingMachine;

import org.junit.*;
import static org.junit.Assert.*;
import VendingMachine.User;
import VendingMachine.Customer;

public class CustomerTest {

	private Customer customer;

	@Before
	public void setup() {
		this.customer = new Customer("Ann", "abc");
	}

	@Test
	public void constructorTest1() {
		Customer newCustomer = new Customer("David", "khu2753");
		assertNotNull(newCustomer);
		assertTrue(newCustomer instanceof Customer);
		assertTrue(newCustomer instanceof User);
	}

	@Test
	public void constructorTest2() {
		User newCustomer = new Customer("David", "khu2753");
		assertNotNull(newCustomer);
		assertTrue(newCustomer instanceof Customer);
		assertTrue(newCustomer instanceof User);
	}

	@Test
	public void getUsernameTest() {
		assertEquals("Ann", this.customer.getUsername());
	}

	@Test
	public void setUsernameTest() {
		assertEquals("Ann", this.customer.getUsername());
		this.customer.setUsername("Anna");
		assertEquals("Anna", this.customer.getUsername());
	}

	@Test
	public void getPasswordTest() {
		assertEquals("abc", this.customer.getPassword());
	}

	@Test
	public void setPasswordTest() {
		assertEquals("abc", this.customer.getPassword());
		this.customer.setPassword("knde8hb3y8na*&jK");
		assertEquals("knde8hb3y8na*&jK", this.customer.getPassword());
	}

}
