package VendingMachine;


import com.sun.source.tree.Tree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

import static VendingMachine.App.*;

public class DefaultPage {
    private Stage defaultPage;

    //added static to loginStatus
    private static boolean loginStatus = false;
    private boolean sellerLogin = false;
    private boolean cashierLogin = false;
    private boolean ownerLogin = false;

    //add static to Cart
    private static Map<Item, Integer> anonymousCart;
    private static Map<Item, Integer> userCart;
    private static String currentUser;
    private ObservableList<Item> drinksList;
    private TableView drinksTable;
    private ObservableList<Item> chocolatesList;
    private TableView chocolatesTable;
    private ObservableList<Item> chipsList;
    private TableView chipsTable;
    private ObservableList<Item> candiesList;
    private TableView candiesTable;
    private ObservableList<PurchaseHistory> purchaseList;
    private TableView purchaseHistory;

    private TreeTableView productTable;

    private Button showDrinks;
    private Button showChocolates;
    private Button showChips;
    private Button showCandies;
    private Button login;
    private Button createAcc;
    private Button menu;
    private Button cart;
    private Button logout;
    private Label label1, label2, label3, label4, label5, label6, label7, label8;
    private Label welcomeAnonymous;
    private Label welcomeUser;
    private List<Item> candies;
    private List<Item> chips;
    private List<Item> chocolates;
    private List<Item> drinks;
    Comparator<PurchaseHistory> purchaseHistoryComparator;
    SortedList<PurchaseHistory> sortedHistory;
    FilteredList<PurchaseHistory> filteredList;

    List<TreeItem> drinksTree;
    List<TreeItem> chipsTree;
    List<TreeItem> chocolatesTree;
    List<TreeItem> candiesTree;

    TreeItem rootNode;
    TreeItem drinksTop;
    TreeItem chipsTop;
    TreeItem chocolatesTop;
    TreeItem candiesTop;

    public DefaultPage(List<Item> products, List<Item> candies, List<Item> chips, List<Item> chocolates, List<Item> drinks) {

        this.defaultPage = new Stage();
        this.candies = candies;
        this.chips = chips;
        this.chocolates = chocolates;
        this.drinks = drinks;
        this.showDrinks = new Button("Drinks");
        this.showChocolates = new Button("Chocolates");
        this.showChips = new Button("Chips");
        this.showCandies = new Button("Candies");
        this.login = new Button("login");
        this.createAcc = new Button("Create account");
        this.menu = new Button("Menu");
        this.cart = new Button("View\nCart");
        this.logout = new Button("Log Out");

        this.label1 = new Label("or");
        this.label2 = new Label("Category");
        this.label3 = new Label("Drinks");
        this.label4 = new Label("Chips");
        this.label5 = new Label("Chocolates");
        this.label6 = new Label("Candies");
        this.label7 = new Label("Products");
        this.label8 = new Label("Purchase History");
        this.welcomeAnonymous = new Label("Welcome, anonymous");
        this.welcomeUser = new Label("Welcome, user");

        // append items into each observableList
        this.drinksList = FXCollections.observableArrayList( );
        drinks.stream().forEach(e -> drinksList.add(e));

        this.chocolatesList = FXCollections.observableArrayList();
        chocolates.stream().forEach(e -> chocolatesList.add(e));

        this.chipsList = FXCollections.observableArrayList();
        chips.stream().forEach(e -> chipsList.add(e));

        this.candiesList = FXCollections.observableArrayList();
        candies.stream().forEach(e -> candiesList.add(e));

        this.purchaseList = FXCollections.observableArrayList();

        this.drinksTable = new TableView();
        this.chocolatesTable = new TableView();
        this.chipsTable = new TableView();
        this.candiesTable = new TableView();
        this.productTable = new TreeTableView();
        this.purchaseHistory = new TableView();

        this.anonymousCart = new HashMap<>();
        this.userCart = new HashMap<>();

        drinksTable.setVisible(false);
        chocolatesTable.setVisible(false);
        chipsTable.setVisible(false);
        candiesTable.setVisible(false);

        this.purchaseHistoryComparator = Comparator.comparing(PurchaseHistory::getId);
        this.sortedHistory = new SortedList<>(purchaseList, purchaseHistoryComparator.reversed());
        this.filteredList = new FilteredList<>(
                sortedHistory,
                e -> sortedHistory.indexOf(e) < 5
        );
    }

    public void setElementsLocation() {

        // set buttons location in the pane
        showDrinks.setLayoutX(60); showDrinks.setLayoutY(80);
        showChocolates.setLayoutX(60); showChocolates.setLayoutY(120);
        showChips.setLayoutX(60); showChips.setLayoutY(160);
        showCandies.setLayoutX(60); showCandies.setLayoutY(200);
        login.setLayoutX(355); login.setLayoutY(370);
        createAcc.setLayoutX(330); createAcc.setLayoutY(426);
        menu.setLayoutX(60); menu.setLayoutY(80);
        cart.setLayoutX(60); cart.setLayoutY(380);
        logout.setLayoutX(370); logout.setLayoutY(420);
        logout.setVisible(false);
        // menu button should only be visible when a user selects a category
        menu.setVisible(false);

        // set tables location in the pane
        candiesTable.setLayoutX(150); candiesTable.setLayoutY(80);
        chipsTable.setLayoutX(150); chipsTable.setLayoutY(80);
        chocolatesTable.setLayoutX(150); chocolatesTable.setLayoutY(80);
        drinksTable.setLayoutX(150); drinksTable.setLayoutY(80);
        productTable.setLayoutX(150); productTable.setLayoutY(50);
        purchaseHistory.setLayoutX(480); purchaseHistory.setLayoutY(50);

        // set labels location
        label3.setLayoutX(188); label3.setLayoutY(60); label3.setVisible(false);
        label4.setLayoutX(188); label4.setLayoutY(60); label4.setVisible(false);
        label5.setLayoutX(186); label5.setLayoutY(60); label5.setVisible(false);
        label6.setLayoutX(188); label6.setLayoutY(60); label6.setVisible(false);
        label1.setLayoutX(370); label1.setLayoutY(400);
        label2.setLayoutX(50); label2.setLayoutY(50);
        label7.setLayoutX(250); label7.setLayoutY(25);
        label8.setLayoutX(525); label8.setLayoutY(25);
        welcomeAnonymous.setLayoutX(320); welcomeAnonymous.setLayoutY(470);
        welcomeUser.setLayoutX(320); welcomeUser.setLayoutY(470);
        welcomeUser.setVisible(false);
    }

    public void setButtonActions() {
        showDrinks.setOnAction(actionEvent -> {
            drinksTable.setVisible(true);
            showCandies.setVisible(false); showChips.setVisible(false); showDrinks.setVisible(false); showChocolates.setVisible(false);
            label2.setVisible(false);
            label7.setVisible(false);
            label3.setVisible(true);
            menu.setVisible(true);
            productTable.setVisible(false);
        });

        showChips.setOnAction(actionEvent -> {
            chipsTable.setVisible(true);
            showCandies.setVisible(false); showChips.setVisible(false); showDrinks.setVisible(false); showChocolates.setVisible(false);
            label2.setVisible(false);
            label7.setVisible(false);
            label4.setVisible(true);
            menu.setVisible(true);
            productTable.setVisible(false);
        });

        showChocolates.setOnAction(actionEvent -> {
            chocolatesTable.setVisible(true);
            showCandies.setVisible(false); showChips.setVisible(false); showDrinks.setVisible(false); showChocolates.setVisible(false);
            label2.setVisible(false);
            label7.setVisible(false);
            label5.setVisible(true);
            menu.setVisible(true);
            productTable.setVisible(false);
        });

        showCandies.setOnAction(actionEvent -> {
            candiesTable.setVisible(true);
            showCandies.setVisible(false); showChips.setVisible(false); showDrinks.setVisible(false); showChocolates.setVisible(false);
            label2.setVisible(false);
            label7.setVisible(false);
            label6.setVisible(true);
            menu.setVisible(true);
            productTable.setVisible(false);
        });

        menu.setOnAction(actionEvent -> {
            if (drinksTable.isVisible()) {
                drinksTable.setVisible(false);
                label3.setVisible(false);
            } else if (chipsTable.isVisible()) {
                chipsTable.setVisible(false);
                label4.setVisible(false);
            } else if (chocolatesTable.isVisible()) {
                chocolatesTable.setVisible(false);
                label5.setVisible(false);
            } else if (candiesTable.isVisible()) {
                candiesTable.setVisible(false);
                label6.setVisible(false);
            }
            productTable.setVisible(true);
            label2.setVisible(true);
            label7.setVisible(true);
            menu.setVisible(false);
            showCandies.setVisible(true); showChips.setVisible(true); showDrinks.setVisible(true); showChocolates.setVisible(true);
        });

        login.setOnAction(actionEvent -> {
            // to be implemented
        	this.defaultPage.hide();
        	App.stages.get("Login").show();
        });

        //ivy added the logout function
        logout.setOnAction(actionEvent ->{
        	changeBackToAnonymous();
        });

        createAcc.setOnAction(actionEvent -> {
            // to be implemented
            this.defaultPage.hide();
            App.stages.get("CreateAccount").show();
        });

        cart.setOnAction(actionEvent -> {
            // to be implemented
            this.defaultPage.hide();
            App.stages.get("ShoppingList").show();
            App.shoppingList.update();
            anonymousCart.keySet().stream().forEach(e -> System.out.println(e.getName() + " " + anonymousCart.get(e)));
        });

    }

    public void updateTables() {
        drinksList.clear(); chocolatesList.clear(); chipsList.clear(); candiesList.clear();
        drinks.stream().forEach(e -> drinksList.add(e));
        chocolates.stream().forEach(e -> chocolatesList.add(e));
        chips.stream().forEach(e -> chipsList.add(e));
        candies.stream().forEach(e -> candiesList.add(e));
    }

    public void updateHistory() {
        if (cardMainPage.getCardPayStatus() || cashPage.paySuccessfully()) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm").format(Calendar.getInstance().getTime());
            if (loginStatus) {
                Map<Item, Integer> items = userCart;
                items.keySet().stream().forEach(e -> {
                    PurchaseHistoryJsonHandler.writeToItemJson(currentUser, e.getName(), e.getPrice(), items.get(e).toString());
                });

                if(cardMainPage.getCardPayStatus()) {
                    List<String> temp = new ArrayList<>();
                    items.keySet().stream().forEach(e -> temp.add(e.getName()));
                    PurchaseHistoryJsonHandler.writeCompleteHistory(temp, String.valueOf(cardPaySuccess.getAmountOfMoneyPaid()), timeStamp, "0", "card");
                } else if (cashPage.paySuccessfully()) {
                    List<String> temp = new ArrayList<>();
                    items.keySet().stream().forEach(e -> temp.add(e.getName()));
                    PurchaseHistoryJsonHandler.writeCompleteHistory(temp, "10", timeStamp, "1", "cash");
                }

                showUserHistory();
                userCart.clear();
            } else {
                Map<Item, Integer> items = anonymousCart;
                items.keySet().stream().forEach(e -> {
                    PurchaseHistoryJsonHandler.writeToItemJson(null, e.getName(), e.getPrice(), items.get(e).toString());
                });

                if(cardMainPage.getCardPayStatus()) {
                    List<String> temp = new ArrayList<>();
                    items.keySet().stream().forEach(e -> temp.add(e.getName()));
                    PurchaseHistoryJsonHandler.writeCompleteHistory(temp, String.valueOf(cardPaySuccess.getAmountOfMoneyPaid()), timeStamp, "0", "card");

                } else if (cashPage.paySuccessfully()) {
                    List<String> temp = new ArrayList<>();
                    items.keySet().stream().forEach(e -> temp.add(e.getName()));
                    PurchaseHistoryJsonHandler.writeCompleteHistory(temp, "10", timeStamp, "1", "cash");

                }

                showAnonHistory();
                System.out.println("here");
                anonymousCart.clear();
            }
        }

    }

    public void showAnonHistory() {
        purchaseList.clear();
        List<PurchaseHistory> histories = PurchaseHistoryJsonHandler.readFromAnonJson("anonPurchaseHistory.json");
        purchaseList.addAll(histories);
    }

    public void showUserHistory() {
        purchaseList.clear();
        List<PurchaseHistory> histories = PurchaseHistoryJsonHandler.readFromUserJson("userPurchaseHistory.json", currentUser);
        purchaseList.addAll(histories);
    }


    @SuppressWarnings("uncheck")
    public void setTables() {

        // fetch cell values (name & price) from Candies / Chips / Chocolates / Drinks
        TableColumn drinksCol = new TableColumn("Items");
        drinksCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn drinksPriceCol = new TableColumn("Price");
        drinksPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn drinkAmountCol = new TableColumn("Amount");
        drinkAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn chocolatesCol = new TableColumn("Items");
        chocolatesCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn chocolatesPriceCol = new TableColumn("Price");
        chocolatesPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn chocolateAmountCol = new TableColumn("Amount");
        chocolateAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn chipsCol = new TableColumn("Items");
        chipsCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn chipsPriceCol = new TableColumn("Price");
        chipsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn chipAmountCol = new TableColumn("Amount");
        chipAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn candiesCol = new TableColumn("Items");
        candiesCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn candiesPriceCol = new TableColumn("Price");
        candiesPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn candiesAmountCol = new TableColumn("Amount");
        candiesAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TreeTableColumn categoryCol = new TreeTableColumn("Category");
        categoryCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("category"));
        categoryCol.setPrefWidth(100);
        TreeTableColumn itemCol = new TreeTableColumn("Items");
        itemCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        TreeTableColumn priceCol = new TreeTableColumn("Price");
        priceCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));
        TreeTableColumn amountCol = new TreeTableColumn("Amount");
        amountCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        //chengyufei: whether you want to display the code
        //TreeTableColumn codeCol = new TreeTableColumn("Code");
        //amountCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));

        rootNode = new TreeItem(new Item("Category", "...", "...", "...","..."));
        drinksTop = new TreeItem(new Item("drinks", "...", "..." , "...","..."));
        chipsTop = new TreeItem(new Item("chips", "...", "...", "...","..."));
        chocolatesTop = new TreeItem(new Item("chocolates", "...", "...", "...","..."));
        candiesTop = new TreeItem(new Item("candies", "...", "...", "...","..."));

        drinksTree = drinks.stream().map(e -> new TreeItem(e)).collect(Collectors.toList());
        chipsTree = chips.stream().map(e -> new TreeItem(e)).collect(Collectors.toList());
        chocolatesTree = chocolates.stream().map(e -> new TreeItem(e)).collect(Collectors.toList());
        candiesTree = candies.stream().map(e -> new TreeItem(e)).collect(Collectors.toList());

        drinksTop.getChildren().addAll(drinksTree);
        chipsTop.getChildren().addAll(chipsTree);
        chocolatesTop.getChildren().addAll(chocolatesTree);
        candiesTop.getChildren().addAll(candiesTree);

        drinksTop.setExpanded(true); chipsTop.setExpanded(true);
        chocolatesTop.setExpanded(true); candiesTop.setExpanded(true);
        rootNode.getChildren().addAll(drinksTop, chipsTop, chocolatesTop, candiesTop);

        // create table for purchaseHistory
        TableColumn purchaseItemCol = new TableColumn("Items");
        purchaseItemCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn purchasePriceCol = new TableColumn("Price");
        purchasePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn purchaseAmountCol = new TableColumn("Amount");
        purchaseAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // match tables with corresponding observableLists
        // set table size
        drinksTable.setItems(drinksList); drinksTable.setMaxWidth(200); drinksTable.setMaxHeight(250);
        chocolatesTable.setItems(chocolatesList); chocolatesTable.setMaxWidth(170); chocolatesTable.setMaxHeight(250);
        chipsTable.setItems(chipsList); chipsTable.setMaxWidth(170); chipsTable.setMaxHeight(250);
        candiesTable.setItems(candiesList); candiesTable.setMaxWidth(180); candiesTable.setMaxHeight(250);
        purchaseHistory.setItems(filteredList);

        // append columns to tables
        drinksTable.getColumns().addAll(drinksCol, drinksPriceCol, drinkAmountCol);
        chocolatesTable.getColumns().addAll(chocolatesCol, chocolatesPriceCol, chocolateAmountCol);
        chipsTable.getColumns().addAll(chipsCol, chipsPriceCol, chipAmountCol);
        candiesTable.getColumns().addAll(candiesCol, candiesPriceCol, candiesAmountCol);
        productTable.getColumns().addAll(categoryCol, itemCol, priceCol, amountCol);
        productTable.setRoot(rootNode);
        productTable.setShowRoot(false);
        productTable.setPrefWidth(300);
        productTable.setPrefHeight(300);
        purchaseHistory.getColumns().addAll(purchaseItemCol, purchaseAmountCol, purchasePriceCol);
        purchaseHistory.setPrefHeight(300);
        purchaseHistory.setPrefWidth(200);
        // by default, tables should be invisible
    }

    public void updateTreeTable() {
        Map<String, List<Item>> storage = ItemJsonHandler.readFromItemJson("items.json");
        candies =  storage.get("candies");
        chips = storage.get("chips");
        chocolates = storage.get("chocolates");
        drinks = storage.get("drinks");

        drinksTree.clear(); chipsTree.clear(); chocolatesTree.clear(); candiesTree.clear();

        drinksTree = drinks.stream().map(e -> new TreeItem(e)).collect(Collectors.toList());
        chipsTree = chips.stream().map(e -> new TreeItem(e)).collect(Collectors.toList());
        chocolatesTree = chocolates.stream().map(e -> new TreeItem(e)).collect(Collectors.toList());
        candiesTree = candies.stream().map(e -> new TreeItem(e)).collect(Collectors.toList());

        drinksTop.getChildren().clear();
        chipsTop.getChildren().clear();
        chocolatesTop.getChildren().clear();
        candiesTop.getChildren().clear();

        drinksTop.getChildren().addAll(drinksTree);
        chipsTop.getChildren().addAll(chipsTree);
        chocolatesTop.getChildren().addAll(chocolatesTree);
        candiesTop.getChildren().addAll(candiesTree);
        updateTables();
    }

    public void handleTableClick(TableView table) {
        table.setOnMouseClicked(actionEvent -> {
            if (table.getSelectionModel().getSelectedItem() != null) {

                table.getSelectionModel().getSelectedItem();
                Stage popout = new Stage();
                Item item = (Item) table.getSelectionModel().getSelectedItem();
                popout.setTitle(item.getName());

                Button plus = new Button("+");
                Button minus = new Button("-");
                Button confirm = new Button("confirm");
                Text amount = new Text("0");
                plus.setPadding(new Insets(5));
                minus.setPadding(new Insets(5));
                amount.setLayoutX(60); amount.setLayoutY(28);
                plus.setLayoutX(100); plus.setLayoutY(10);
                minus.setLayoutX(10); minus.setLayoutY(10);
                confirm.setLayoutX(134); confirm.setLayoutY(12);

                plus.setOnAction(event -> {
                    if (Integer.parseInt(amount.getText()) + 1 <= Integer.parseInt(item.getAmount())) {
                        amount.setText(((Integer) (Integer.parseInt(amount.getText()) + 1)).toString());
                    }
                });

                minus.setOnAction(event -> {
                    if (Integer.parseInt(amount.getText()) - 1 >= 0) {
                        amount.setText(((Integer) (Integer.parseInt(amount.getText()) - 1)).toString());
                    }
                });

                confirm.setOnAction(event -> {
                    if (Integer.parseInt(amount.getText()) > 0) {
                        item.setAmount(Integer.toString((Integer.parseInt(item.getAmount()) - Integer.parseInt(amount.getText()))));
                        updateTables();
                        setTables();
                        if (loginStatus) {
                            if (containItem(item, userCart)) {
                                userCart.put(item, Integer.parseInt(amount.getText()) + userCart.get(getItem(item, userCart)));
                            } else {
                                userCart.put(item, Integer.parseInt(amount.getText()));
                            }
                            userCart.put(item, Integer.parseInt(amount.getText()));
                        } else {
                            if (containItem(item, anonymousCart)) {
                                anonymousCart.put(item, Integer.parseInt(amount.getText()) + anonymousCart.get(getItem(item, anonymousCart)));
                            } else {
                                anonymousCart.put(item, Integer.parseInt(amount.getText()));
                            }
                        }
                    }
                    popout.close();
                });

                Pane root = new Pane(plus, minus, amount, confirm);
                Scene scene = new Scene(root, 200, 50);
                popout.setScene(scene);
                popout.show();

            }
        });
    }

    public void handleTreeTableClick(TreeTableView table) {
        table.setOnMouseClicked(actionEvent -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                Item item = (Item) ((TreeItem) table.getSelectionModel().getSelectedItem()).getValue();
                if (!item.getName().equals("...")) {

                    Stage popout = new Stage();
                    popout.setTitle(item.getName());

                    Button plus = new Button("+");
                    Button minus = new Button("-");
                    Button confirm = new Button("confirm");
                    Text amount = new Text("0");
                    plus.setPadding(new Insets(5));
                    minus.setPadding(new Insets(5));
                    amount.setLayoutX(60); amount.setLayoutY(28);
                    plus.setLayoutX(100); plus.setLayoutY(10);
                    minus.setLayoutX(10); minus.setLayoutY(10);
                    confirm.setLayoutX(134); confirm.setLayoutY(12);

                    plus.setOnAction(event -> {
                        if (Integer.parseInt(amount.getText()) + 1 <= Integer.parseInt(item.getAmount())) {
                            amount.setText(((Integer) (Integer.parseInt(amount.getText()) + 1)).toString());
                        }
                    });

                    minus.setOnAction(event -> {
                        if (Integer.parseInt(amount.getText()) - 1 >= 0) {
                            amount.setText(((Integer) (Integer.parseInt(amount.getText()) - 1)).toString());
                        }
                    });

                    confirm.setOnAction(event -> {
                        if (Integer.parseInt(amount.getText()) > 0) {
                            item.setAmount(Integer.toString((Integer.parseInt(item.getAmount()) - Integer.parseInt(amount.getText()))));
                            updateTables();
                            setTables();
                            if (loginStatus) {
                                if (containItem(item, userCart)) {
                                    userCart.put(item, Integer.parseInt(amount.getText()) + userCart.get(getItem(item, userCart)));
                                } else {
                                    userCart.put(item, Integer.parseInt(amount.getText()));
                                }
                                userCart.put(item, Integer.parseInt(amount.getText()));
                            } else {
                                if (containItem(item, anonymousCart)) {
                                    anonymousCart.put(item, Integer.parseInt(amount.getText()) + anonymousCart.get(getItem(item, anonymousCart)));
                                } else {
                                    anonymousCart.put(item, Integer.parseInt(amount.getText()));
                                }
                            }
                        }
                        popout.close();
                    });

                    Pane root = new Pane(plus, minus, amount, confirm);
                    Scene scene = new Scene(root, 200, 50);
                    popout.setScene(scene);
                    popout.show();

                }

            }
        });
    }

    public boolean containItem(Item item, Map<Item, Integer> cart) {
        return cart.keySet().stream().filter(e -> e.getName().equals(item.getName())).findFirst().isPresent();
    }

    public Item getItem(Item item, Map<Item, Integer> cart) {
         return cart.keySet().stream().filter(e -> e.getName().equals(item.getName())).findFirst().get();
    }

    // After user enters username & password
    public void changeToUser(String username) {
        anonymousCart.keySet().stream().forEach(e -> userCart.put(e, anonymousCart.get(e)));
        loginStatus = true;
        currentUser = username;
        //added by Ivy
        this.welcomeUser.setText("Welcome " + username + " !");
        this.welcomeUser.setVisible(true);
        logout.setVisible(true);
        this.welcomeAnonymous.setVisible(false);
        this.login.setVisible(false);
        this.createAcc.setVisible(false);
        this.label1.setVisible(false);
        anonymousCart.clear();
        showUserHistory();
    }

    public void changeToSpecialUser(String username) {
        loginStatus = true;
        currentUser = username;
    }

    public void changeBackToAnonUser() {
        loginStatus = false;
        currentUser = null;
    }


    // After log out
    //modified
    public void changeBackToAnonymous() {
        anonymousCart.clear();
        userCart.clear();
        loginStatus = false;
        currentUser = null;
        this.welcomeUser.setVisible(false);
        logout.setVisible(false);
        this.welcomeAnonymous.setVisible(true);
        this.login.setVisible(true);
        this.createAcc.setVisible(true);
        this.label1.setVisible(true);
        showAnonHistory();
    }

    public void setup() {
        setElementsLocation();
        setTables();
        setButtonActions();
        handleTableClick(drinksTable);
        handleTableClick(chipsTable);
        handleTableClick(chocolatesTable);
        handleTableClick(candiesTable);
        handleTreeTableClick(productTable);
        showAnonHistory();
    }

    public static Map<VendingMachine.Item, java.lang.Integer> getAnonymousCart() {
      return anonymousCart;
    }

    public static Map<Item, Integer> getUserCart() {
        return userCart;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public Stage display() {
        setup();
        Pane root = new Pane(showDrinks, showCandies, showChocolates, showChips, login, createAcc, logout,cart,
                candiesTable, chipsTable, chocolatesTable, drinksTable, menu, label1, label2, label3, label4,
                label5, label6, label7, label8, welcomeAnonymous, welcomeUser, productTable, purchaseHistory);
        Scene scene = new Scene(root, 750, 550);
        defaultPage.setTitle("Vending Machine");
        defaultPage.setScene(scene);
        return defaultPage;
    }

    //added by yufei cheng, to return  whether the user is user or anonymous
    public static boolean getLoginStatus(){
        return loginStatus;
    }

    //added by Yuyun Liu, to clean the cart when card pay success
    public void cleanCart() {
        // minus the amount in the item need to be implemented
        //userCart.clear();
        //anonymousCart.clear();
        App.shoppingList.update();

    }

    public void reset() {
        menu.setVisible(true);
        drinksTable.setVisible(false);
        candiesTable.setVisible(false);
        chocolatesTable.setVisible(false);
        chipsTable.setVisible(false);
        productTable.setVisible(true);
        label2.setVisible(true);
        label7.setVisible(true);
        menu.setVisible(false);
        showCandies.setVisible(true); showChips.setVisible(true); showDrinks.setVisible(true); showChocolates.setVisible(true);
        userCart.clear();
        anonymousCart.clear();
        loginStatus = false;
        currentUser = null;
        changeBackToAnonymous();
    }
}
