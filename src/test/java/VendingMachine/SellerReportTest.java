package VendingMachine;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SellerReportTest {

    String itemsFile = "src/test/java/VendingMachine/ItemsSample.json";
    String anonHistoryFile = "src/test/java/VendingMachine/anonPurchaseHistorySample.json";
    String userHistoryFile = "src/test/java/VendingMachine/userPurchaseHistorySample.json";

    @Test
    public void finditemsTest() {
        List<Item> products = ItemJsonHandler.readFromJson(itemsFile);
        assertEquals(4, products.size());

        List<PurchaseHistory> anhistroy = PurchaseHistoryJsonHandler.readFromAnonJson(anonHistoryFile);
        List<PurchaseHistory> userhistory = PurchaseHistoryJsonHandler.SimpleReadFromUserJson(userHistoryFile);
        List<SoldItem> l1 = SellerReport.finditems(products,anhistroy);
        List<SoldItem> l2 = SellerReport.finditems(products,userhistory);

        assertNotNull(l1);
        assertNotNull(l2);

        assertEquals(5, l1.size());
        assertEquals(5, l2.size());
    }

    @Test
    public void combineItemsTest() {
        List<SoldItem> sold = SellerReport.combineItems(itemsFile, userHistoryFile, anonHistoryFile);

        assertEquals(4, sold.size());
    }

    @Test
    public void SellerSoldReportTest() {
        SellerReport.SellerSoldReport("src/test/java/VendingMachine/SellerSoldReportSample.txt", itemsFile, userHistoryFile, anonHistoryFile);
        try {
            File f = new File("src/test/java/VendingMachine/SellerSoldReportSample.txt");
            Scanner sc = new Scanner(f);
            String s = sc.nextLine();
        }catch(FileNotFoundException e) {

        }
    }

    @Test
    public void SellerAvailableReportTest() {

    }

}
