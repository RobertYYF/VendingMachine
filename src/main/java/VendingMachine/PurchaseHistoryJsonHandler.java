package VendingMachine;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PurchaseHistoryJsonHandler {

    public static List<PurchaseHistory> readFromAnonJson(String filename) {

        List<PurchaseHistory> items = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(filename);
            Object obj = jsonParser.parse(fileReader);
            JSONArray itemArr = (JSONArray) obj;

            for (Object i : itemArr) {
                JSONObject temp = (JSONObject) i;
                String name = (String) temp.get("name");
                String price = (String) temp.get("price");
                String amount = (String) temp.get("amount");
                String id = (String) temp.get("id");
                items.add(new PurchaseHistory(name, amount, price, id));

            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return items;

    }

    public static List<PurchaseHistory> readFromUserJson(String filename, String username) {

        List<PurchaseHistory> items = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(filename);
            Object obj = jsonParser.parse(fileReader);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) jsonObject.get(username);
            if(jsonArray!=null){
                for (Object i : jsonArray) {
                    JSONObject temp = (JSONObject) i;
                    String name = (String) temp.get("name");
                    String price = (String) temp.get("price");
                    String amount = (String) temp.get("amount");
                    String id = (String) temp.get("id");
                    items.add(new PurchaseHistory(name, amount, price, id));
                }
            }


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return items;

    }

    public static void writeToItemJson(String username, String name, String price, String amount){

        JSONParser jsonParser = new JSONParser();
        try {
            if (username == null) {
                Object obj = jsonParser.parse(new FileReader("anonPurchaseHistory.json"));

                JSONArray jsonArray = (JSONArray) obj;
                JSONObject newItem = new JSONObject();
                newItem.put("name", name);
                newItem.put("price", price);
                newItem.put("amount", amount);
                List<Integer> ids = new ArrayList<>();
                jsonArray.stream().forEach(e -> ids.add(Integer.parseInt(((JSONObject) e).get("id").toString())));
                int max = 0;
                if (ids.size() != 0) {
                    max = Collections.max(ids);
                }
                newItem.put("id",Integer.toString(max + 1));
                jsonArray.add(newItem);

                FileWriter file = new FileWriter("anonPurchaseHistory.json");
                file.write(jsonArray.toJSONString());
                file.flush();
                file.close();
            } else {
                Object obj = jsonParser.parse(new FileReader("userPurchaseHistory.json"));
                JSONObject jsonObject = (JSONObject) obj;
                JSONArray jsonArray = (JSONArray) jsonObject.get(username);
                if (jsonArray == null) {
                    jsonObject.put(username, new JSONArray());
                    jsonArray = (JSONArray) jsonObject.get(username);
                }
                JSONObject newItem = new JSONObject();
                newItem.put("name", name);
                newItem.put("price", price);
                newItem.put("amount", amount);
                List<Integer> ids = new ArrayList<>();
                jsonArray.stream().forEach(e -> ids.add(Integer.parseInt(((JSONObject) e).get("id").toString())));
                int max = 0;
                if (ids.size() != 0) {
                    max = Collections.max(ids);
                }
                newItem.put("id",Integer.toString(max + 1));
                jsonArray.add(newItem);

                FileWriter file = new FileWriter("userPurchaseHistory.json");
                file.write(jsonObject.toJSONString());
                file.flush();
                file.close();

            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    //chengyufei
    public static List<PurchaseHistory> SimpleReadFromUserJson(String filename) {
        List<PurchaseHistory> results = new ArrayList<>();
        List<String> names = AccountJsonHandler.readFromJson("Account.json");
        for(int i =0;i<names.size();i++){
            List<PurchaseHistory> step = readFromUserJson(filename,names.get(i));
            for(int j =0;j<step.size();j++){
                results.add(step.get(j));
            }
        }
        return results;

    }

    public static void writeCompleteHistory(List<String> name, String money, String time, String change, String method){

        JSONParser jsonParser = new JSONParser();
        try {

            Object obj = jsonParser.parse(new FileReader("completePurchaseHistory.json"));

            JSONArray jsonArray = (JSONArray) obj;
            JSONObject newItem = new JSONObject();

            JSONArray temp = new JSONArray();
            name.stream().forEach(e -> temp.add(e));
            newItem.put("name", temp);
            newItem.put("money", money);
            newItem.put("time", time);
            newItem.put("change", change);
            newItem.put("method", method);
            jsonArray.add(newItem);

            FileWriter file = new FileWriter("completePurchaseHistory.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static List<CompleteHistory> readFromCompleteHistory() {

        List<CompleteHistory> items = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader("completePurchaseHistory.json");
            Object obj = jsonParser.parse(fileReader);
            JSONArray jsonArray = (JSONArray) obj;
                for (Object i : jsonArray) {
                    JSONObject temp = (JSONObject) i;
                    JSONArray arr = (JSONArray) temp.get("name");
                    List<String> name = new ArrayList<>();
                    arr.stream().forEach(e -> name.add((String) e));
                    String money = (String) temp.get("money");
                    String time = (String) temp.get("time");
                    String change = (String) temp.get("change");
                    String method = (String) temp.get("method");
                    items.add(new CompleteHistory(name, money, change, time, method));
                }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return items;

    }


}
