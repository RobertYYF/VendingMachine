package VendingMachine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class App extends Application {

    public static Map<String, Stage> stages = new HashMap<>();
    private static Map<String, List<Item>> storage = ItemJsonHandler.readFromItemJson("items.json");
    private static List<Item> candies =  storage.get("candies");
    private static List<Item> chips = storage.get("chips");
    private static List<Item> chocolates = storage.get("chocolates");
    private static List<Item> drinks = storage.get("drinks");
    private static List<Item> products = ItemJsonHandler.readFromJson("items.json");

    public static Cash cash = new Cash();
    public static DefaultPage defaultPage = new DefaultPage(products, candies, chips, chocolates, drinks);
    public static ShoppingList shoppingList = new ShoppingList("cancelTransaction.json");

    public static PaymentMethod paymentMethod = new PaymentMethod("cancelTransaction.json");

    public static CashPage cashPage = new CashPage("cancelTransaction.json");
    public static Successful successful = new Successful();

    public static CardMainPage cardMainPage = new CardMainPage("credit_cards.json", "Account.json", "cancelTransaction.json");
    public static CardPaySuccess cardPaySuccess = new CardPaySuccess();
    public static CardSavePopUpPage cardSavePopUpPage = new CardSavePopUpPage("Account.json");

    public static CreateAccount createAccount = new CreateAccount("Account.json");
    public static LoginPage loginPage = new LoginPage("Account.json");

    public static SellerView sellerview = new SellerView("Account.json");
    public static OwnerWelcomePage ownerwelcomePage =  new OwnerWelcomePage("Account.json","cancelTransaction.json");
    public static OwnerUserManagementPage ownerUserManagementPage = new OwnerUserManagementPage("Account.json");

    public static CashierView cashierview = new CashierView("Account.json");

    public static Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(120), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            defaultPage.display().show();
            defaultPage.reset();
            loginPage.reset();
//            ownerwelcomePage.reset();
//            ownerUserManagementPage.reset();
            cardMainPage.reset();
            cashPage.reset();
        	CancelTransactionHandler handler = new CancelTransactionHandler("cancelTransaction.json");
        	handler.addTrans(handler.handleNewTrans("timeout"));
            stages.keySet().stream().forEach(e -> {
                if (!e.equals("DefaultPage")) {
                    stages.get(e).hide();
                }
            });
        }
    }));

    @Override
    public void start(Stage stage) {
        stages.put("DefaultPage", defaultPage.display());
        stages.put("ShoppingList", shoppingList.display());
        stages.put("CreateAccount", createAccount.display());
        stages.put("Login", loginPage.display());
        stages.put("PaymentMethod", paymentMethod.display());
        stages.put("CashPage",cashPage.display());
        stages.put("Successful",successful.display());
        stages.put("CardMainPage", cardMainPage.display());
        stages.put("CardPaySuccess", cardPaySuccess.display());
        stages.put("CardSavePopUpPage", cardSavePopUpPage.display());
        stages.put("SellerView", sellerview.display());
        stages.put("OwnerUserManagementPage", ownerUserManagementPage.display());
        stages.put("OwnerWelcomePage", ownerwelcomePage.display());
        stages.put("CashierView",cashierview.display());

        timeline.setCycleCount(Timeline.INDEFINITE);
        defaultPage.display().show();
        //timeline.play();

    }

    public static Cash getCash(){
        return cash;
    }

//    public static boolean overtime() {
//
//    }

//    public static void main(String[] args) {
//        launch();
//    }
}
