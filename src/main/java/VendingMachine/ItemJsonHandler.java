package VendingMachine;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemJsonHandler {
    private static  JSONArray lastfile;

    public static Map<String, List<Item>> readFromItemJson(String filename) {

        List<Item> drinks = new ArrayList<>();
        List<Item> chips = new ArrayList<>();
        List<Item> candies = new ArrayList<>();
        List<Item> chocolates = new ArrayList<>();
        Map<String, List<Item>> products = new HashMap<>();
        products.put("drinks", drinks); products.put("chips", chips);
        products.put("candies", candies); products.put("chocolates", chocolates);

        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(filename);
            Object obj = jsonParser.parse(fileReader);
            JSONArray itemArr = (JSONArray) obj;

            for (Object i : itemArr) {
                JSONObject temp = (JSONObject) i;
                String category = (String) temp.get("category");
                String name = (String) temp.get("name");
                String price = (String) temp.get("price");
                String amount = (String) temp.get("amount");
                String code = (String) temp.get("code");
                if (category.equals("drinks")) {
                    drinks.add(new Item(category, name, price, amount,code));
                } else if (category.equals("chips")) {
                    chips.add(new Item(category, name, price, amount,code));
                } else if (category.equals("candies")) {
                    candies.add(new Item(category, name, price, amount,code));
                } else if (category.equals("chocolates")) {
                    chocolates.add(new Item(category, name, price, amount,code));
                }
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return products;

    }

    public static List<Item> readFromJson(String filename){
        List<Item> products = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(filename);
            Object obj = jsonParser.parse(fileReader);
            JSONArray itemArr = (JSONArray) obj;

            for (Object i : itemArr) {
                JSONObject temp = (JSONObject) i;
                String category = (String) temp.get("category");
                String name = (String) temp.get("name");
                String price = (String) temp.get("price");
                String amount = (String) temp.get("amount");
                String code = (String) temp.get("code");
                products.add(new Item(category,name,price,amount,code));

            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return products;

    }

    public static void writeToItemJson(String filename, String category, String name, String price, String amount,String code){
        JSONParser jsonParser = new JSONParser();
        try {
            Object obj = jsonParser.parse(new FileReader(filename));
            JSONArray jsonArray = (JSONArray) obj;

            JSONObject newItem = new JSONObject();
            newItem.put("category", category);
            newItem.put("name", name);
            newItem.put("price", price);
            newItem.put("amount", amount);
            newItem.put("code",code);

            jsonArray.add(newItem);

            FileWriter file = new FileWriter(filename);
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("uncheck")
    public static void changeToItemJson(String filename, String category, String name, String price, String amount,String code) throws IOException {

        JSONParser jsonParser = new JSONParser();
        JSONArray itemArr = null;
        int count =0;
        try {
            FileReader fileReader = new FileReader(filename);
            Object obj = jsonParser.parse(fileReader);
            itemArr = (JSONArray) obj;

            for (int i =0;i<itemArr.size();i++) {
                JSONObject temp = (JSONObject) itemArr.get(i);
                String category1 = (String) temp.get("category");
                String name1 = (String) temp.get("name");
                String price1 = (String) temp.get("price");
                String amount1 = (String) temp.get("amount");
                String code1 = (String) temp.get("code");
                if(category1.equals(category)){
                    count += 1;
                }
                if(name1.equals(name)){
                    count +=1;
                }
                if(price1.equals(price)){
                    count +=1;
                }
                if(amount1.equals(amount)){
                    count +=1;
                }
                if(code1.equals(code)){
                    count +=1;
                }
                if(count ==4){
                    itemArr.remove(itemArr.get(i));
                }
                count =0;
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        JSONObject newItem = new JSONObject();
        newItem.put("category", category);
        newItem.put("name", name);
        newItem.put("price", price);
        newItem.put("amount", amount);
        newItem.put("code",code);

        itemArr.add(newItem);
        FileWriter file = null;
        try {
            file = new FileWriter(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            file.write(itemArr.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.flush();
        file.close();


    }

}
