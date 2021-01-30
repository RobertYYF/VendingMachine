package VendingMachine;

public class SoldItem {
    private String code;
    private String name;
    private String amount;

    public SoldItem(String code,String name,String amount){
        this.code = code;
        this.name = name;
        this.amount = amount;
    }

    public String getCode(){
        return this.code;
    }
    public String getName(){
        return this.name;
    }
    public String getAmount(){
        return this.amount;
    }
}
