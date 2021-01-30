package VendingMachine;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {
	@Test
	public void constructorTest(){
		Item item = new Item("drinks","coca cola", "5", "6", "DRI1");
		assertNotNull(item);
	}

	@Test
	public void getAmountTest(){
		Item item = new Item("drinks","coca cola", "5", "6", "DRI1");
		assertEquals("6", item.getAmount());
	}

	@Test
	public void setAmountTest(){
		Item item = new Item("drinks","coca cola", "5", "6", "DRI1");
		item.setAmount("10");
		assertEquals("10", item.getAmount());
	}

	@Test
	public void getNameTest(){
		Item item = new Item("drinks","coca cola", "5", "6", "DRI1");
		assertEquals("coca cola", item.getName());
	}

	@Test
	public void getPriceTest(){
		Item item = new Item("drinks","coca cola", "5", "6", "DRI1");
		assertEquals("5", item.getPrice());
	}

	@Test
	public void setPriceTest(){
		Item item = new Item("drinks","coca cola", "5", "6", "DRI1");
		item.setPrice("8.0");
		assertEquals("8.0",item.getPrice());
	}

	@Test
	public void setNameTest(){
		Item item = new Item("drinks","coca cola", "5", "6", "DRI1");
		item.setName("cola");
		assertEquals("cola",item.getName());
	}

    @Test
    public void getCategoryTest() {
        Item item = new Item("drinks","coca cola", "5", "6", "DRI1");
		assertEquals("drinks",item.getCategory());
    }

    @Test
    public void getCodeTest() {
        Item item = new Item("drinks","coca cola", "5", "6", "DRI1");
		assertEquals("DRI1",item.getCode());
    }

    @Test
    public void setCategoryTest() {
        Item item = new Item("drinks","coca cola", "5", "6", "DRI1");
		item.setCategory("soft drink");
		assertEquals("soft drink",item.getCategory());
    }

    @Test
    public void setCodeTest() {
        Item item = new Item("drinks","coca cola", "5", "6", "DRI1");
		item.setCode("DRI2");
		assertEquals("DRI2",item.getCode());
    }
}
