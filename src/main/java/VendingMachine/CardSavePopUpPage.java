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

public class CardSavePopUpPage {

    private Stage cardSavePopUpPage;

    private Text title;
    private Text cardNameInfo;
    private Text cardNumberInfo;
    private Button yesButton;
    private Button noButton;

    private String accountsJson;

    public CardSavePopUpPage(String accountsJson) {
        this.cardSavePopUpPage = new Stage();

        this.title = new Text("Do you want to save the credit card information?");
        this.cardNameInfo = new Text();
        this.cardNumberInfo = new Text();
        this.yesButton = new Button("Yes");
        this.noButton = new Button("No");

        this.accountsJson = accountsJson;
    }

    public void setLayout() {
        this.title.setLayoutX(1);
        this.title.setLayoutY(100);
        this.title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));

        this.cardNameInfo.setLayoutX(150);
        this.cardNameInfo.setLayoutY(180);

        this.cardNumberInfo.setLayoutX(150);
        this.cardNumberInfo.setLayoutY(210);

        this.yesButton.setLayoutX(150);
        this.yesButton.setLayoutY(250);

        this.noButton.setLayoutX(300);
        this.noButton.setLayoutY(250);
    }

    public void setCardInfoAction() {
        
        this.cardNameInfo.setText("Card holder name: " + App.cardMainPage.cardholderNameForSuccess);
        String num = "";
        for(int i = 0; i < App.cardMainPage.cardNumberForSuccess.length(); i++) {
            num += "*";
        }
        this.cardNumberInfo.setText("Card number: " + num);
    }

    public void setYesButtonAction() {
        this.yesButton.setOnAction(actionEvent -> {
            AccountJsonHandler handler = new AccountJsonHandler(this.accountsJson);
            handler.SaveCreditCardInformation(CardMainPage.customer, App.cardMainPage.cardholderNameForSuccess, App.cardMainPage.cardNumberForSuccess);
            this.cardSavePopUpPage.hide();
        });
    }

    public void setNoButtonAction() {
        this.noButton.setOnAction(actionEvent -> {
            this.cardSavePopUpPage.hide();
        });
    }

    public Stage display() {
        this.setLayout();
        this.setYesButtonAction();
        this.setNoButtonAction();

        Pane root = new Pane(title, cardNameInfo, cardNumberInfo, yesButton, noButton);
        Scene scene = new Scene(root, 500, 300);
        this.cardSavePopUpPage.setScene(scene);

        return this.cardSavePopUpPage;
    }

}
