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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PaymentMethod {

    private Stage paymentMethod;

    private Text title;

    private Button cashButton;
    private Button cardButton;

    private Button returnButton;
    private Button cancelButton;
    
    private String jsonFile;

    public PaymentMethod(String jsonFile) {
        this.paymentMethod = new Stage();

        this.title = new Text("Payment Methods");

        this.cashButton = new Button("      Cash      ");
        this.cardButton = new Button("      Card      ");
        this.returnButton = new Button("Return");
        this.cancelButton = new Button("Cancel");
        this.jsonFile = jsonFile;
    }

    public void setLayout() {
        this.title.setLayoutX(200);
        this.title.setLayoutY(100);
        this.title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        this.cashButton.setLayoutX(250);
        this.cashButton.setLayoutY(160);

        this.cardButton.setLayoutX(250);
        this.cardButton.setLayoutY(250);

        this.cancelButton.setLayoutX(350);
        this.cancelButton.setLayoutY(350);

        this.returnButton.setLayoutX(450);
        this.returnButton.setLayoutY(350);
    }

    public void setCashButtonAction() {
        this.cashButton.setOnAction(actionEvent -> {
            this.paymentMethod.hide();
            App.stages.get("CashPage").show();
        });
    }

    public void setCardButtonAction() {
        this.cardButton.setOnAction(actionEvent -> {
            App.cardMainPage.checkLogin();
            App.cardMainPage.setTotalMoneyAction();
            this.paymentMethod.hide();
            App.stages.get("CardMainPage").show();
        });
    }

    public void setReturnButtonAction() {
        this.returnButton.setOnAction(actionEvent -> {
            this.paymentMethod.hide();
            App.stages.get("ShoppingList").show();
        });
    }

    public void setCancelButtonAction() {
        this.cancelButton.setOnAction(actionEvent -> {
            // To be implemented
        	//edit by Ivy -- add cancel record
        	CancelTransactionHandler handler = new CancelTransactionHandler(this.jsonFile);
        	handler.addTrans(handler.handleNewTrans("user cancelled"));
            this.paymentMethod.hide();
            App.stages.get("ShoppingList").show();
        });
    }

    public Stage display() {
        this.setLayout();
        this.setCashButtonAction();
        this.setCardButtonAction();
        this.setReturnButtonAction();
        this.setCancelButtonAction();

        Pane root = new Pane(title, cashButton, cardButton, returnButton, cancelButton);
        Scene scene = new Scene(root, 600, 400);
        paymentMethod.setScene(scene);

        return this.paymentMethod;
    }

}
