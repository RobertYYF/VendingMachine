package VendingMachine;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SellerView {
	private String jsonFile;
    private Stage sellerTable;
    private TableView<Item> table = new TableView<Item>();
    private List<Item> products =ItemJsonHandler.readFromJson("Items.json");
    private final ObservableList<Item> data = FXCollections.observableArrayList();
    private Text limiterror;
    private int index;
    private Scene scene;
    private Pane root;
    private Text codeerror;
    private Text nameerror;
    private Text categoryerror;
    public Button Sold;
    public Button Avaliable;
    private Button Return;

    public SellerView(String jsonFile){
    	this.jsonFile = jsonFile;
        this.sellerTable = new Stage();
        this.limiterror = new Text();
        this.codeerror = new Text();
        this.nameerror = new Text();
        this.codeerror = new Text();
        this.categoryerror = new Text();
        this.Sold = new Button("Sold Report");
        this.Avaliable = new Button("Available Report");
        this.Return = new Button("Return");

    }

    public void setElementsLocation() {
        // set button on the pane
        this.Sold.setLayoutX(100);
        this.Sold.setLayoutY(400);

        this.Avaliable.setLayoutX(250);
        this.Avaliable.setLayoutY(400);

        this.Return.setLayoutX(450);
        this.Return.setLayoutY(400);
    }


    @SuppressWarnings("uncheck")
    public void setButtonActions() {
        Sold.setOnAction(actionEvent -> {
            // to be implemented
            //this.ShoppingList.hide();
            //App.stages.get("DefaultPage").show();
            SellerReport.SellerSoldReport("SellerSoldReport.txt", "Items.json", "userPurchaseHistory.json", "anonPurchaseHistory.json");
        });

        Avaliable.setOnAction(actionEvent -> {
            // to be implemented
            SellerReport.SellerAvailableReport("SellerAvailableReport.txt");
        });

        Return.setOnAction(actionEvent -> {
            // to be implemented

            try{
                AccountJsonHandler handler = new AccountJsonHandler(jsonFile);
                User user = handler.getUser(DefaultPage.getCurrentUser());
                this.sellerTable.hide();
                if(user instanceof Owner) {
                    App.stages.get("OwnerWelcomePage").show();
                    App.sellerview.Sold.setVisible(true);
                    App.sellerview.Avaliable.setVisible(true);
                }else {
                    App.defaultPage.updateTreeTable();
                    App.stages.get("DefaultPage").show();
                }
            }catch(java.lang.ClassCastException e){
            }

        });
    }

    public void setData(){
        for(int i =0 ;i<products.size();i++){
            data.add(products.get(i));
        }

    }

    public void setTable(){
        table.setEditable(true);


        TableColumn CategoryCol = new TableColumn("Category");
        CategoryCol.setMinWidth(100);
        CategoryCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("Category"));
        CategoryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        CategoryCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<Item, String>>) t -> {

                    Item one= (Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    boolean find = false;
                    for(int i =0 ;i<products.size();i++){
                        if(t.getNewValue().equals(products.get(i).getCategory())){
                            find = true;
                        }
                    }
                    if(!find){
                        //judge whether the product is exist
                        for(int i =0 ;i<products.size();i++){
                            if(products.get(i).equals(one)){
                                this.index = i;
                            }
                        }
                        try {
                            ItemJsonHandler.changeToItemJson("Items.json", t.getNewValue(),one.getName(),one.getPrice(),one.getAmount(),one.getCode());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        one.setCategory(t.getNewValue());

                    }else{
                        one.setCategory(one.getCategory());
                        this.limiterror = null;
                        this.codeerror = null;
                        this.nameerror = null;
                        this.codeerror = null;
                        categoryerror = new Text(100,400,"conflicting item category");
                        //root.getChildren().add(limiterror);

                        table.getItems().clear();

                        App.sellerview.display();
                        //to refuse incorrect input
                        table.getColumns().get(0).setVisible(false);
                        table.getColumns().get(0).setVisible(true);

                    }

                }
        );




        TableColumn NameCol = new TableColumn("Name");
        NameCol.setMinWidth(100);
        NameCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("Name"));
        NameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        NameCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<Item, String>>) t -> {

                    Item one= (Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    boolean find = false;
                    for(int i =0 ;i<products.size();i++){
                        if(t.getNewValue().equals(products.get(i).getName())){
                            find = true;
                        }
                    }
                    if(!find){
                        //judge whether the product is exist
                        for(int i =0 ;i<products.size();i++){
                            if(products.get(i).equals(one)){
                                this.index = i;
                            }
                        }
                        try {
                            ItemJsonHandler.changeToItemJson("Items.json", one.getCategory(),t.getNewValue(),one.getPrice(),one.getAmount(),one.getCode());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        one.setName(t.getNewValue());

                    }else{
                        one.setName(one.getName());
                        this.limiterror = null;
                        this.codeerror = null;
                        this.codeerror = null;
                        this.categoryerror = null;
                        nameerror = new Text(100,400,"conflicting item name");

                        //to refuse incorrect input
                        //table.getColumns().get(3).setVisible(false);
                        //table.getColumns().get(3).setVisible(true);

                        table.getItems().clear();

                        App.sellerview.display();
                        //to refuse incorrect input
                        table.getColumns().get(1).setVisible(false);
                        table.getColumns().get(1).setVisible(true);

                    }

                }
        );

        TableColumn PriceCol = new TableColumn("Price");
        PriceCol.setMinWidth(100);
        PriceCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("Price"));
        PriceCol.setCellFactory(TextFieldTableCell.forTableColumn());
        PriceCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<Item, String>>) t -> {

                    Item one= (Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow());

                        //judge whether the product is exist
                        for(int i =0 ;i<products.size();i++){
                            if(products.get(i).equals(one)){
                                this.index = i;
                            }
                        }
                        try {
                            ItemJsonHandler.changeToItemJson("Items.json", one.getCategory(),one.getName(),t.getNewValue(),one.getAmount(),one.getCode());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        one.setAmount(t.getNewValue());



                }
        );




        TableColumn AmountCol = new TableColumn("Amount");
        AmountCol.setMinWidth(100);
        AmountCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("Amount"));
        AmountCol.setCellFactory(TextFieldTableCell.forTableColumn());

        AmountCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<Item, String>>) t -> {

                    Item one= (Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    if(Integer.parseInt(t.getNewValue())<= 15){

                        //judge whether the product is exist
                        for(int i =0 ;i<products.size();i++){
                            if(products.get(i).equals(one)){
                                this.index = i;
                            }
                        }
                        try {
                            ItemJsonHandler.changeToItemJson("Items.json", one.getCategory(),one.getName(),one.getPrice(),t.getNewValue(),one.getCode());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        one.setAmount(t.getNewValue());

                    }else{
                        one.setAmount(one.getAmount());

                        this.codeerror = null;
                        this.nameerror = null;
                        this.codeerror = null;
                        this.categoryerror = null;
                        limiterror = new Text(20,400,"quantity added will be over than the limit");

                        //to refuse incorrect input
                        //table.getColumns().get(3).setVisible(false);
                        //table.getColumns().get(3).setVisible(true);

                        table.getItems().clear();

                        App.sellerview.display();

                        table.getColumns().get(3).setVisible(false);
                        table.getColumns().get(3).setVisible(true);


                    }

                }
        );

        TableColumn CodeCol = new TableColumn("Code");
        CodeCol.setMinWidth(100);
        CodeCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("Code"));
        CodeCol.setCellFactory(TextFieldTableCell.forTableColumn());

        CodeCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<Item, String>>) t -> {

                    Item one= (Item) t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    boolean find = false;
                    for(int i =0 ;i<products.size();i++){
                        if(t.getNewValue().equals(products.get(i).getCode())){
                            find = true;
                        }
                    }
                    if(!find){

                        //judge whether the product is exist
                        for(int i =0 ;i<products.size();i++){
                            if(products.get(i).equals(one)){
                                this.index = i;
                            }
                        }
                        try {
                            ItemJsonHandler.changeToItemJson("Items.json", one.getCategory(),one.getName(),one.getPrice(),one.getAmount(),t.getNewValue());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        one.setCode(t.getNewValue());

                    }else{
                        one.setCode(one.getCode());

                        this.limiterror = null;
                        this.nameerror = null;
                        this.codeerror = null;
                        this.categoryerror = null;
                        codeerror = new Text(100,400,"conflicting item code");


                        //to update the view
                        table.getItems().clear();

                        App.sellerview.display();

                        //to refuse incorrect input
                        table.getColumns().get(4).setVisible(false);
                        table.getColumns().get(4).setVisible(true);

                    }

                }
        );




        table.setItems(data);
        table.getColumns().addAll(CategoryCol,NameCol,PriceCol,AmountCol,CodeCol);








    }


    public Stage display(){
        //SellerReport.SellerAvailableReport();
        //SellerReport.SellerSoldReport();
        setElementsLocation();
        setButtonActions();

        setData();
        setTable();
        if(categoryerror!=null){
            this.categoryerror.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        }
        if(nameerror!=null){
            this.nameerror.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        }
        if(limiterror!=null){
            this.limiterror.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        }
        if(codeerror!=null){
            this.codeerror.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        }


        List<Text> list  = new ArrayList<>();
        list.add(limiterror);
        list.add(codeerror);
        list.add(nameerror);
        list.add(categoryerror);

        this.root = new Pane(table,Return,Avaliable,Sold);
        for(Text t:list){
            if(t!=null){
                this.root.getChildren().add(t);
            }
        }

        this.scene = new Scene(root, 500, 500);
        sellerTable.setTitle("SellerView");
        sellerTable.setScene(scene);
        return sellerTable;
    }

    public int getIndex(){
        return this.index;
    }

    public Pane getRoot(){
        return this.root;
    }

}
