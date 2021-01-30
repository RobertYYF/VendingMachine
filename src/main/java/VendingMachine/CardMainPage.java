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

public class CardMainPage {

    private Stage cardMainPage;

    private String creditCardsJson;
    private String accountsJson;

    private Text title;

    private Label nameLabel;
    private Label numberLabel;
    private TextField nameField;
    private PasswordField numberField;
    private Text prompt;

    private Button returnButton;
    private Button payButton;
    private Button cancelButton;

    private Text totalMoney;

    public static boolean whetherLogin;
    public static String customer;

    private boolean whetherCanPay;
    private boolean paySuccess;

    public String cardholderNameForSuccess;
    public String cardNumberForSuccess;
    public String cancelJson;
    
    private Map<Item, Integer> userCart;
    private Map<Item, Integer> anonymousCart;

    public CardMainPage(String creditCardsJson, String accountsJson,String cancelJson) {
        this.cardMainPage = new Stage();
        
        this.cancelJson = cancelJson;
        this.creditCardsJson = creditCardsJson;
        this.accountsJson = accountsJson;

        this.title = new Text("Details of the Credit Card");
        this.nameLabel = new Label("Cardholder Name");
        this.numberLabel = new Label("Credit Card Number");
        this.nameField = new TextField();
        this.numberField = new PasswordField();
        this.prompt = new Text();

        this.returnButton = new Button("Return");
        this.payButton = new Button("Pay");
        this.cancelButton = new Button("Cancel");

        this.totalMoney = new Text("Total: $" + App.shoppingList.getTotal());

        whetherLogin = false;
        customer = App.defaultPage.getCurrentUser();

        this.whetherCanPay = false;
        this.paySuccess = false;

        this.cardholderNameForSuccess = null;
        this.cardNumberForSuccess = null;
    }

    public void setLayout() {
        this.title.setLayoutX(180);
        this.title.setLayoutY(100);
        this.title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        this.nameLabel.setLayoutX(100);
        this.nameLabel.setLayoutY(150);

        this.numberLabel.setLayoutX(100);
        this.numberLabel.setLayoutY(200);

        this.nameField.setLayoutX(250);
        this.nameField.setLayoutY(150);

        this.numberField.setLayoutX(250);
        this.numberField.setLayoutY(200);
       //  numberField.setOnAction(event -> {
       //      // When click the field and then press "ENTER" on keyboard
       //      System.out.println("in");
       //     numberField.setText("yes");
       //     // commitEdit(t);
       //     // event.consume();
       // });
      //  numberField.setOnMouseClicked(event -> {
      //      // When click the field and then press "ENTER" on keyboard
      //      System.out.println("in");
      //     numberField.setText("yes");
      //     // commitEdit(t);
      //     // event.consume();
      // });

        this.prompt.setLayoutX(100);
        this.prompt.setLayoutY(250);

        this.payButton.setLayoutX(300);
        this.payButton.setLayoutY(350);

        this.cancelButton.setLayoutX(350);
        this.cancelButton.setLayoutY(350);

        this.returnButton.setLayoutX(420);
        this.returnButton.setLayoutY(350);

        this.totalMoney.setLayoutX(400);
        this.totalMoney.setLayoutY(300);
    }

    public void setReturnButtonAction() {
        this.returnButton.setOnAction(actionEvent -> {
            this.clean();
            this.cardMainPage.hide();
            App.stages.get("PaymentMethod").show();
        });
    }

    public void setCancelButtonAction() {
        this.cancelButton.setOnAction(actionEvent -> {
            // To be implemented
        	CancelTransactionHandler handler = new CancelTransactionHandler(this.cancelJson);
        	handler.addTrans(handler.handleNewTrans("user cancelled"));
            this.clean();
            App.shoppingList.cancel();
            this.cardMainPage.hide();
            App.stages.get("DefaultPage").show();
        });
    }

    public void setPayButtonAction() {
        this.payButton.setOnAction(actionEvent -> {
            this.setPromptAction();
            if(this.whetherCanPay == true && App.shoppingList.getTotal() > 0.0) {
                this.cardMainPage.hide();
                App.cardPaySuccess.setReceiptContent();
                App.cardPaySuccess.setReceiptAction();
                App.defaultPage.cleanCart();
                App.stages.get("CardPaySuccess").show();
                this.paySuccess = true;
                if(whetherLogin == true && this.whetherNewCard(this.cardholderNameForSuccess, this.cardNumberForSuccess) == true) {
                    App.cardSavePopUpPage.setCardInfoAction();
                    App.stages.get("CardSavePopUpPage").show();
                }
            }else if(Math.abs(App.shoppingList.getTotal() - 0.0) <= 0.1) {
                this.prompt.setText("The shopping card is empty!");
                this.prompt.setFill(Color.RED);
                this.paySuccess = false;
            }else {
                this.paySuccess = false;
            }
        });
    }

    public boolean whetherNewCard(String name, String num) {
        AccountJsonHandler handler = new AccountJsonHandler(this.accountsJson);
        if(num.equals((handler.getCreditCardNumber(customer, name)))) {
            return false;
        }else {
            return true;
        }
    }

    public void setNumberFieldAction() {
        // need a time line.
        // to be implemented later.

        numberField.setOnMouseClicked(event -> {
            AccountJsonHandler handler = new AccountJsonHandler(this.accountsJson);
            if(whetherLogin == true) {
                String number = handler.getCreditCardNumber(customer, this.nameField.getText());
                if(number != null) {
                    // Load autoamtically
                    this.numberField.setText(number);
                }else {
                    // Do nothing.
                }
            }else {
                // Do nothing
            }

       });
    }

    public void setPromptAction() {
        boolean whetherExist = false;
        if(this.nameField.getText().trim().equals("") || this.numberField.getText().trim().equals("")) {
            this.prompt.setText("The field can not be empty! Please check again.");
            this.prompt.setFill(Color.RED);
            this.whetherCanPay = false;
            this.cardholderNameForSuccess = null;
            this.cardNumberForSuccess = null;
        }else {
            // check whether exist card information in the credit_cards.json
            CreditCardJsonHandler handler = new CreditCardJsonHandler(this.creditCardsJson);
            String cardholder = this.nameField.getText();
            String cardNumber = this.numberField.getText();
            if(handler.whetherExist(cardholder, cardNumber)) {
                this.prompt.setText("Success!");
                this.prompt.setFill(Color.GREEN);
                this.whetherCanPay = true;
                this.cardholderNameForSuccess = cardholder;
                this.cardNumberForSuccess = cardNumber;
                this.clean();
                App.timeline.stop();
            }else {
                this.prompt.setText("This card information is not exist! Please check again.");
                this.prompt.setFill(Color.RED);
                this.whetherCanPay = false;
                this.cardholderNameForSuccess = null;
                this.cardNumberForSuccess = null;
            }
        }
    }

    public static void checkLogin() {
        // be called by paymentMethod
        if(App.defaultPage.getLoginStatus() == true) {
            whetherLogin = true;
            customer = App.defaultPage.getCurrentUser();
        }else {
            whetherLogin = false;
            customer = App.defaultPage.getCurrentUser();
        }

    }

    public void setTotalMoneyAction() {
        this.totalMoney.setText("Total: $" + App.shoppingList.getTotal());
    }

    public void update(Map<Item, Integer> anonymousCart, Map<Item, Integer> userCart) {

        this.userCart = userCart;
        this.anonymousCart = anonymousCart;
        App.cardPaySuccess.update(anonymousCart, userCart);
    }

    public Map<Item, Integer> getCurrentCart() {
        if(whetherLogin == true) {
            return this.userCart;
        }else {
            return this.anonymousCart;
        }
    }

    public Map<Item, Integer> getAnonymousCart() {
        return this.anonymousCart;
    }

    public Map<Item, Integer> getUserCart() {
        return this.userCart;
    }

    public boolean getCardPayStatus() {
        return this.paySuccess;
    }

    public void clean() {
        this.nameField.clear();
        this.numberField.clear();
        this.prompt.setText("");
    }

    public Stage display() {

        this.setLayout();
        this.setReturnButtonAction();
        this.setCancelButtonAction();
        this.setNumberFieldAction();
        this.setPayButtonAction();

        Pane root = new Pane(title, nameLabel, numberLabel, nameField, numberField, prompt, returnButton, cancelButton, payButton, totalMoney);
        Scene scene = new Scene(root, 600, 400);
        cardMainPage.setScene(scene);

        return this.cardMainPage;
    }

    public void reset() {
        this.clean();
    }

}
