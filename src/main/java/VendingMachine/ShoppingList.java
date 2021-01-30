package VendingMachine;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.HashMap;
import java.util.Map;

public class ShoppingList{
    private Stage ShoppingList;
    private Map<Item, Integer> anonymousCart;
    private Map<Item, Integer> userCart;
    public Button backButton;
    public Button cancelButton;
    public Button payButton;
    public TableView table;
    private double login_total_amout;
    private double any_total_amout;
    private boolean login;
    private Text tell_amout;
    private Pane root;
    private String jsonFile;

    public ShoppingList(String jsonFile){
    	this.jsonFile = jsonFile;
        this.ShoppingList = new Stage();

        this.backButton = new Button("Return");
        this.cancelButton = new Button("Cancel");
        this.payButton = new Button("Pay");
        this.table = new TableView();
        this.tell_amout = new Text();


        this.anonymousCart = new HashMap<>();
        this.userCart = new HashMap<>();

        this.root = new Pane();

    }

    public void setElementsLocation() {
        // set button on the pane
        this.backButton.setLayoutX(320);
        this.backButton.setLayoutY(400);

        this.cancelButton.setLayoutX(380);
        this.cancelButton.setLayoutY(400);

        this.payButton.setLayoutX(440);
        this.payButton.setLayoutY(400);
    }

    public void setButtonActions() {
        backButton.setOnAction(actionEvent -> {
            // to be implemented
            this.ShoppingList.hide();
            App.stages.get("DefaultPage").show();
            App.timeline.stop();
        });

        cancelButton.setOnAction(actionEvent -> {
            // to be implemented
            this.userCart.clear();
            this.anonymousCart.clear();
            table.getItems().clear();
        	CancelTransactionHandler handler = new CancelTransactionHandler(this.jsonFile);
        	handler.addTrans(handler.handleNewTrans("user cancelled"));
        	this.ShoppingList.hide();
            App.stages.get("DefaultPage").show();
            App.timeline.stop();
        });

        payButton.setOnAction(actionEvent -> {
            // to be implemented
            this.ShoppingList.hide();
            App.stages.get("PaymentMethod").show();
            App.timeline.play();
        });
    }

    // Add by Yuyun for cancel function in other pages.
    public void cancel() {
        this.userCart.clear();
        this.anonymousCart.clear();
        table.getItems().clear();
    }

    public void update(){
        this.anonymousCart = DefaultPage.getAnonymousCart();
        this.anonymousCart = DefaultPage.getAnonymousCart();
        this.userCart = DefaultPage.getUserCart();
        App.cardMainPage.update(this.anonymousCart, this.userCart);
        setCart();
        //root.getChildren().addAll(tell_amout);
        //tell_amout = null;
        tell_amout = new Text(70,430,"Your total amount is: "+getTotal());
        //System.out.println(getTotal());
        //table.getItems().clear();
        //display();
        //this.anonymousCart.clear();
        //this.userCart.clear();
        //root = new Pane(table,backButton,cancelButton,payButton,tell_amout);
    }

    public void setTable(){
        TableColumn itemsCol = new TableColumn("Items");
        itemsCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn PriceCol = new TableColumn("Price");
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn AmountCol = new TableColumn("Amount");
        AmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // append columns to tables
        table.getColumns().addAll(itemsCol,PriceCol,AmountCol);

        //always can see the table
        table.setEditable(true);
        table.setVisible(true);
    }



    //need to use the filed variable to clear the cart
    public void setCart(){
        //here the user login
        table.getItems().clear();
        if(DefaultPage.getLoginStatus()){
            //if the cart is not empty
            if(this.userCart.size() != 0){
                login_total_amout = 0;
                for(Map.Entry<Item,Integer> en: this.userCart.entrySet()){
                    //set each row
                    Item specific_item = new Item(en.getKey().getCategory(), en.getKey().getName(),en.getKey().getPrice(),en.getValue().toString(),en.getKey().getCode());

                    table.getItems().add(specific_item);
                    login_total_amout += Integer.parseInt(en.getKey().getPrice()) * (en.getValue());
                    login = true;
                }
            }
        }else{
            //here the anonymous
            if(this.anonymousCart.size() != 0){
                any_total_amout = 0;
                for(Map.Entry<Item,Integer> en: this.anonymousCart.entrySet()){
                    //set each row
                    Item specific_item = new Item(en.getKey().getCategory(), en.getKey().getName(),en.getKey().getPrice(),en.getValue().toString(),en.getKey().getCode());

                    table.getItems().add(specific_item);
                    any_total_amout += Integer.parseInt(en.getKey().getPrice()) * en.getValue();
                    login = false;

                }
            }
        }
    }

    public double getTotal(){
        if(login){
            //System.out.println(login_total_amout);
            return login_total_amout;
        }else{
            //System.out.println(any_total_amout);
            return any_total_amout;
        }
    }

    public Stage display() {
        //tell_amout = new Text(70,430,"Your total amount is: "+getTotal());
        setElementsLocation();
        setTable();
        setButtonActions();
        setCart();
        Pane root = new Pane(table,backButton,cancelButton,payButton,tell_amout);
        //root = new  Pane(table,backButton,cancelButton,payButton);
        Scene scene = new Scene(root, 500, 500);
        ShoppingList.setTitle("Shopping List");
        ShoppingList.setScene(scene);
        return ShoppingList;
    }


}
