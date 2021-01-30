package VendingMachine;

import java.util.Map;
import java.util.HashMap;

public class Customer extends User {

    // private List<Order> orderList;
    private Map<String, String> creditCards;

    public Customer(String username, String password) {
        super(username, password);
        this.creditCards = new HashMap<>();
    }

    public void addCreditCard(String cardholderName, String creditCardNumber) {
        this.creditCards.put(cardholderName, creditCardNumber);
    }

    public void setCreditCards(Map<String, String> creditCards) {
        this.creditCards = creditCards;
    }

    public Map<String, String> getCreditCards() {
        return this.creditCards;
    }

    public String getCreditCardNumber(String cardholderName) {
        if(this.creditCards.containsKey(cardholderName)) {
            return this.creditCards.get(cardholderName);
        }else {
            return null;
        }
    }

}
