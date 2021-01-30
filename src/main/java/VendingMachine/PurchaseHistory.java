package VendingMachine;

public class PurchaseHistory {

    private String name;
    private String amount;
    private String price;
    private String id;

    public PurchaseHistory(String name, String amount, String price, String id) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }
}
