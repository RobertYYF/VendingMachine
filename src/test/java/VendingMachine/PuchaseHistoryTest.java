package VendingMachine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PuchaseHistoryTest {

    PurchaseHistory purchaseHistory;

    @Before
    public void init() {
        purchaseHistory = new PurchaseHistory("james", "1","1","0");
    }

    @Test
    public void test1() {
        assertEquals("james", purchaseHistory.getName());
        assertEquals("1", purchaseHistory.getAmount());
        assertEquals("1", purchaseHistory.getPrice());
        assertEquals("0", purchaseHistory.getId());
    }

}
