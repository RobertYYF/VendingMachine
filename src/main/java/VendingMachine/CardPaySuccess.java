package VendingMachine;


import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

public class CardPaySuccess {

    private Stage cardPaySuccess;

    private String receiptContent;

    private Text title;
    private Text receipt;
    private Button returnButton;

    private Map<Item, Integer> userCart;
    private Map<Item, Integer> anonymousCart;

    private double amountOfMoneyPaid;

    public CardPaySuccess() {
        this.cardPaySuccess = new Stage();

        this.receiptContent = "";
        this.title = new Text("Receipt");
        this.receipt = new Text();
        this.returnButton = new Button("Return");

        this.userCart = new HashMap<>();
        this.anonymousCart = new HashMap<>();
    }

    public void setLayout() {
        this.title.setLayoutX(180);
        this.title.setLayoutY(100);
        this.title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        this.receipt.setLayoutX(100);
        this.receipt.setLayoutY(150);

        this.returnButton.setLayoutX(420);
        this.returnButton.setLayoutY(350);
    }

    public void setReturnButtonAction() {
        this.returnButton.setOnAction(actionEvent -> {
            this.clean();// clean shopping list.
            this.cardPaySuccess.hide();
            App.defaultPage.updateHistory();
            App.stages.get("DefaultPage").show();
        });
    }

    public void clean() {
        // clean shopping list.
        // clean card information in CardMainPage
        App.cardMainPage.cardholderNameForSuccess = null;
        App.cardMainPage.cardNumberForSuccess = null;
        this.receiptContent = "";
    }

    public void setReceiptContent() {
        this.receiptContent += "Shopping List:\n";
        if(App.cardMainPage.whetherLogin == true) {

            for(Entry<Item, Integer> single : this.userCart.entrySet()) {
                this.receiptContent += single.getKey().getName() + "  x" + single.getValue().toString() + "      ......... $" + (Double.parseDouble(single.getKey().getPrice()) * single.getValue()) + "\n";
            }
        } else {

            for(Entry<Item, Integer> single : this.anonymousCart.entrySet()) {
                this.receiptContent += single.getKey().getName() + "  x" + single.getValue().toString() + "      ......... $" + (Double.parseDouble(single.getKey().getPrice()) * single.getValue()) + "\n";
            }
        }

        this.amountOfMoneyPaid = App.shoppingList.getTotal();
        System.out.println(this.getAmountOfMoneyPaid());

        this.receiptContent += "\n";

        this.receiptContent += "Total pay: $" + App.shoppingList.getTotal() + "\n\n";
        this.receiptContent += "Card holder name: " + App.cardMainPage.cardholderNameForSuccess + "\n\n";
        String num = "";
        for(int i = 0; i < App.cardMainPage.cardNumberForSuccess.length(); i++) {
            num += "*";
        }
        this.receiptContent += "Credit card number: " + num;

    }

    public void update(Map<Item, Integer> anonymousCart, Map<Item, Integer> userCart) {

        this.userCart = userCart;
        this.anonymousCart = anonymousCart;
    }

    public void setReceiptAction() {
        this.receipt.setText(this.receiptContent);
    }

    public Stage display() {
        this.setLayout();
        this.setReturnButtonAction();

        Pane root = new Pane(title, receipt, returnButton);
        Scene scene = new Scene(root, 600, 400);
        cardPaySuccess.setScene(scene);

        return this.cardPaySuccess;
    }

    public double getAmountOfMoneyPaid() {
        this.amountOfMoneyPaid = App.shoppingList.getTotal();
        return this.amountOfMoneyPaid;
    }

}
