package VendingMachine;

import org.junit.*;
import static org.junit.Assert.*;

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

public class ItemJsonHandlerTest {

    private String filename;

    @Before
    public void setup() {
        this.filename = "src/test/java/VendingMachine/ItemsSample.json";
    }

    @After
    public void clean() {
        try {
			String writeInStr = "[{\"amount\":\"8\",\"code\":\"CHI1\",\"price\":\"6\",\"name\":\"Smiths\",\"category\":\"chips\"},{\"amount\":\"3\",\"code\":\"CHO1\",\"price\":\"10\",\"name\":\"M&M\",\"category\":\"chocolates\"},{\"amount\":\"4\",\"code\":\"CAN1\",\"price\":\"8\",\"name\":\"Skittles\",\"category\":\"candies\"},{\"amount\":\"6\",\"code\":\"DRI1\",\"price\":\"4\",\"name\":\"Sprite\",\"category\":\"drinks\"}]";
			FileWriter file = new FileWriter(this.filename);
			file.write(writeInStr);
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Test
    public void readFromItemJsonTest() {
        Map<String, List<Item>> products = ItemJsonHandler.readFromItemJson(this.filename);

        assertEquals(4, products.size());

        List<Item> chips = products.get("chips");
        assertEquals(1, chips.size());
        assertEquals("Smiths", chips.get(0).getName());
    }

    @Test
    public void readFromJsonTest() {
        List<Item> products = ItemJsonHandler.readFromJson(this.filename);

        assertEquals(4, products.size());
        assertEquals("Smiths", products.get(0).getName());
    }

    @Test
    public void writeToItemJsonTest() {
        Map<String, List<Item>> products = ItemJsonHandler.readFromItemJson(this.filename);
        assertEquals(4, products.size());
        List<Item> chips = products.get("chips");
        assertEquals(1, chips.size());
        assertEquals("Smiths", chips.get(0).getName());

        ItemJsonHandler.writeToItemJson(this.filename, "chips", "Lays", "3", "3","CHI2");


        products = ItemJsonHandler.readFromItemJson(this.filename);
        assertEquals(4, products.size());
        chips = products.get("chips");
        assertEquals(2, chips.size());
        assertEquals("Lays", chips.get(1).getName());
    }

}
