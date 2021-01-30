package VendingMachine;

import java.util.List;

public class CompleteHistory {

    private List<String> items;
    private String paid;
    private String change;
    private String time;
    private String method;

    public CompleteHistory(List<String> items, String paid, String change, String time, String method) {
        this.items = items;
        this.paid = paid;
        this.change = change;
        this.time = time;
        this.method = method;
    }

    public List<String> getItems() {
        return items;
    }

    public String getPaid() {
        return paid;
    }

    public String getChange() {
        return change;
    }

    public String getTime() {
        return time;
    }

    public String getMethod() {
        return method;
    }
}
