package VendingMachine;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class PuchaseHistoryJsonTest {

    @Test
    public void test1() {
        PurchaseHistoryJsonHandler.writeToItemJson(null, "Stuff","1","1");
        assertNotNull(PurchaseHistoryJsonHandler.readFromAnonJson("anonPurchaseHistory.json"));
    }

    @Test
    public void test2() {
        PurchaseHistoryJsonHandler.writeToItemJson("charles1234", "Stuff","1","1");
        assertNotNull(PurchaseHistoryJsonHandler.readFromUserJson("userPurchaseHistory.json", "charles1234"));
    }

    @Test
    public void test3() {
        assertNotNull(PurchaseHistoryJsonHandler.SimpleReadFromUserJson("userPurchaseHistory.json"));
    }

    @Test
    public void test4() {
        List<String> ls = new ArrayList<>();
        ls.add("coco");
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm").format(Calendar.getInstance().getTime());
        PurchaseHistoryJsonHandler.writeCompleteHistory(ls, "1", timeStamp, "0", "card");
        assertNotNull(PurchaseHistoryJsonHandler.readFromCompleteHistory());
    }

}
