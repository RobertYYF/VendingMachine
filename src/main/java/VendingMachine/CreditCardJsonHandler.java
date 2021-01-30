package VendingMachine;

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
import java.io.Reader;

public class CreditCardJsonHandler {

    private String jsonFile;
    private Map<String, String> existCards;

    public CreditCardJsonHandler(String jsonFile) {
        this.jsonFile = jsonFile;
        this.existCards = new HashMap<>();
        this.init();
    }

    public void init() {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(jsonFile);
            Object obj = jsonParser.parse(fileReader);

            JSONArray cardArr = (JSONArray) obj;
            for(Object object: cardArr) {
                JSONObject card = (JSONObject) object;
                String name = (String) card.get("name");
                String number = (String) card.get("number");

                this.existCards.put(name, number);
            }

        }catch(IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getExistCards() {
        this.init();
        return this.existCards;
    }

    public boolean whetherExist(String name, String number) {
        this.init();
        boolean result = false;
        if(this.existCards.containsKey(name)) {
            if(this.existCards.get(name).equals(number)) {
                result = true;
            }
        }
        return result;
    }

}
