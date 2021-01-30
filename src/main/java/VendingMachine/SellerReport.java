package VendingMachine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

public class SellerReport {

    public static void SellerAvailableReport(String filename){
        List<Item> products = ItemJsonHandler.readFromJson("Items.json");
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            out.write("Current Available Items\t\n");
            for(int i =0;i<products.size();i++){
                out.write("Category:  "+products.get(i).getCategory()+"\t\t");
                out.write("Name:  "+products.get(i).getName()+"\t\t");
                out.write("Price:  "+products.get(i).getPrice()+"\t\t");
                out.write("Amount:  "+products.get(i).getAmount()+"\t\t");
                out.write("Code:  "+products.get(i).getCode()+"\t\t");
                out.write("\n");
            }
            out.close();
        } catch (IOException e) {
        }
    }

    public static void SellerSoldReport(String filename, String itemsFile, String userHistoryFile, String anonHistoryFile){
        List<SoldItem> sold = SellerReport.combineItems(itemsFile, userHistoryFile, anonHistoryFile);

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            out.write("Sold Items\t\n");
            for(int i =0;i<sold.size();i++){
                out.write("Code:  "+sold.get(i).getCode()+"\t\t");
                out.write("Name:  "+sold.get(i).getName()+"\t\t");
                out.write("Total Number Of Quantity:  "+sold.get(i).getAmount()+"\t\t");

                out.write("\n");
            }
            out.close();

        } catch (IOException e) {
        }
    }


    public  static List<SoldItem> finditems(List<Item> products,List<PurchaseHistory> history){
        List<SoldItem> soldItems = new ArrayList<>();

        for(int i=0;i<history.size();i++){
            for(int j=0;j<products.size();j++){
                if(products.get(j).getName().equals(history.get(i).getName())){
                    SoldItem one =new SoldItem(products.get(j).getCode(),products.get(j).getName(),history.get(i).getAmount());
                    soldItems.add(one);
                    break;
                }
            }
        }
        return soldItems;
    }

    public static List<SoldItem> combineItems(String itemsFile, String userHistoryFile, String anonHistoryFile){
        List<SoldItem> finalitems = new ArrayList<>();
        List<Item> products = ItemJsonHandler.readFromJson(itemsFile);
        List<PurchaseHistory> anhistroy = PurchaseHistoryJsonHandler.readFromAnonJson(anonHistoryFile);
        List<PurchaseHistory> userhistory = PurchaseHistoryJsonHandler.SimpleReadFromUserJson(userHistoryFile);
        List<SoldItem> l1 = finditems(products,anhistroy);
        List<SoldItem> l2 = finditems(products,userhistory);

        List<SoldItem> l1fix = new ArrayList<>();
        List<SoldItem> l2fix = new ArrayList<>();

        Map<String, SoldItem> m1 = new HashMap<>();
        Map<String, SoldItem> m2 = new HashMap<>();

        for(int i = 0; i < l1.size(); i++) {
            if(m1.containsKey(l1.get(i).getName())) {
                int total = Integer.parseInt(l1.get(i).getAmount()) + Integer.parseInt(m1.get(l1.get(i).getName()).getAmount());
                SoldItem item = new SoldItem(l1.get(i).getCode(), l1.get(i).getName(),Integer.toString(total));
                m1.put(l1.get(i).getName(), item);
            }else {
                SoldItem item = new SoldItem(l1.get(i).getCode(),l1.get(i).getName(),l1.get(i).getAmount());
                m1.put(l1.get(i).getName(), item);
            }
        }

        for(int i = 0; i < l2.size(); i++) {
            if(m2.containsKey(l2.get(i).getName())) {
                int total = Integer.parseInt(l2.get(i).getAmount()) + Integer.parseInt(m2.get(l2.get(i).getName()).getAmount());
                SoldItem item = new SoldItem(l2.get(i).getCode(),l2.get(i).getName(),Integer.toString(total));
                m2.put(l2.get(i).getName(), item);
            }else {
                SoldItem item = new SoldItem(l2.get(i).getCode(),l2.get(i).getName(),l2.get(i).getAmount());
                m2.put(l2.get(i).getName(), item);
            }
        }

        for(Entry<String, SoldItem> e: m1.entrySet()) {
            l1fix.add(e.getValue());
        }

        for(Entry<String, SoldItem> e: m2.entrySet()) {
            l2fix.add(e.getValue());
        }

        for(int i=0;i<l1fix.size();i++){
            for(int j=0;j<l2fix.size();j++){
                if(l1fix.get(i).getName().equals(l2fix.get(j).getName())){
                    SoldItem combine = new SoldItem(l1fix.get(i).getCode(),l1fix.get(i).getName(),Integer.toString(Integer.parseInt(l1fix.get(i).getAmount())+Integer.parseInt(l2fix.get(j).getAmount())));
                    finalitems.add(combine);
                    break;
                }
            }
        }
        for(int i=0;i<l1fix.size();i++){
            boolean whetherin = false;
            for(int j =0;j<finalitems.size();j++){
                if(!finalitems.get(j).getName().equals(l1fix.get(i).getName())){
                    whetherin = false;
                }else {
                    whetherin = true;
                    break;
                }
            }
            if(whetherin == false) {
                finalitems.add(new SoldItem(l1fix.get(i).getCode(),l1fix.get(i).getName(),l1fix.get(i).getAmount()));
            }
        }

        for(int i=0;i<l2fix.size();i++){
            boolean whetherin = false;
            for(int j =0;j<finalitems.size();j++){
                if(!finalitems.get(j).getName().equals(l2fix.get(i).getName())){
                    whetherin = false;
                }else {
                    whetherin = true;
                    break;
                }
            }
            if(whetherin == false) {
                finalitems.add(new SoldItem(l2fix.get(i).getCode(),l2fix.get(i).getName(),l2fix.get(i).getAmount()));
            }
        }

        return finalitems;


    }
}
