package VendingMachine;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
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
import java.text.SimpleDateFormat;

public class CancelTransactionHandler {
	private String jsonFile;
	private List<CancelTransaction> transList;

	public CancelTransactionHandler(String jsonFile) {
		this.jsonFile = jsonFile;
//		this.transList = new ArrayList<>();
		this.init();
	}

	public void init() {
		this.transList = new ArrayList<>();
		JSONParser jsonParser = new JSONParser();
		try {
			FileReader fileReader = new FileReader(jsonFile);
			Object obj = jsonParser.parse(fileReader);

			JSONArray transactionArr = (JSONArray) obj;
			for(Object object: transactionArr) {
				JSONObject transaction = (JSONObject) object;
				String time = (String) transaction.get("time");
				String username = (String) transaction.get("username");
				String reason = (String) transaction.get("reason");

				this.transList.add(new CancelTransaction(time, username,reason));
			}

		}catch(IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	public void addTrans(CancelTransaction trans) {
		this.init();
		this.transList.add(trans);

		JSONObject transaction = new JSONObject();
		transaction.put("time", trans.getCurrentTime());
		transaction.put("username", trans.getUsername());
		transaction.put("reason", trans.getReason());

		JSONParser jsonParser = new JSONParser();
		try {
			FileReader fileReader = new FileReader(jsonFile);
			Object obj = jsonParser.parse(fileReader);
			JSONArray transArr = (JSONArray) obj;
			transArr.add(transaction);

			FileWriter file = new FileWriter(jsonFile);
			file.write(transArr.toJSONString());
			file.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	public List<CancelTransaction> getTransList() {
		return this.transList;
	}
	
	public CancelTransaction handleNewTrans(String reason) {
    	Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String user = DefaultPage.getCurrentUser();
    	if (user == null) {
    		user = "anonymous";
    	}
    	CancelTransaction newTrans = new CancelTransaction(formatter.format(currentTime), 
    			user, reason);
    	return newTrans;
	}
}
