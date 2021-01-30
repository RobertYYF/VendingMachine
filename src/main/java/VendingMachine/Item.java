package VendingMachine;

public class Item {
    //added by chengyufei
    private String name;
    private String category;
    private String code;
    private String amount;
    private String price;


    public Item(String category, String name, String price, String amount,String code) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCode(){
        return this.code;
    }

    public void setCategory(String category){
        this.category = category;
    }
    public void setCode(String code){
        this.code = code;
    }
}