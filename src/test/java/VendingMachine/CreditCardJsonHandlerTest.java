package VendingMachine;

import org.junit.*;
import org.junit.jupiter.api.AfterAll;

import static org.junit.Assert.*;
import VendingMachine.CreditCardJsonHandler;

import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Map;

public class CreditCardJsonHandlerTest {

    private CreditCardJsonHandler handler;

    @Before
    public void setup() {
        this.handler = new CreditCardJsonHandler("src/test/java/VendingMachine/credit_cardsSample.json");
    }

    @Test
    public void constructorTest() {
        assertNotNull(this.handler);
    }

    @Test
    public void getExistCardsTest() {
        assertEquals("40691", this.handler.getExistCards().get("Charles"));
        assertEquals("34402", this.handler.getExistCards().get("Maxine"));
        assertEquals("43114", this.handler.getExistCards().get("Naomi"));
        assertEquals("55134", this.handler.getExistCards().get("Ruth"));
        assertEquals("14138", this.handler.getExistCards().get("Blake"));
        assertEquals("27402", this.handler.getExistCards().get("Francisco"));
    }

    @Test
    public void whetherExistTest() {
        assertTrue(this.handler.whetherExist("Julie", "56907"));
        assertTrue(this.handler.whetherExist("Andy", "82050"));

        assertFalse(this.handler.whetherExist("Simone", "56907"));
        assertFalse(this.handler.whetherExist("Janet", "56907"));
    }

}
