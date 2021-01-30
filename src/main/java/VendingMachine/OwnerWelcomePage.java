package VendingMachine;

import java.util.Map;

import com.sun.prism.paint.Color;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OwnerWelcomePage {
	private Label selectFunctionLabel;
	private Stage OwnerWelcomePage;
	private Text actionTarget;
	private GridPane root;
	public String jsonFile;
	private String transFile;
	private Label title;
	private Button ItemButton,MoneyButton, changeUserButton,
	cancelTransButton,showUserButton,availableChangeButton,transSumButton,
	itemInStockButton,soldItemButton;
	private Button LogoutButton;
	private AccountJsonHandler handler;


	public OwnerWelcomePage(String jsonfile,String transFile) {
		this.jsonFile = jsonfile;
		this.transFile = transFile;
		this.actionTarget = new Text();
		this.OwnerWelcomePage = new Stage();
		OwnerWelcomePage.setTitle("Owner Welcome Page");
		this.root = new GridPane();
		this.title = new Label("Welcome, Owner!");
		this.LogoutButton = new Button("Logout");

		this.ItemButton = new Button("Manage Items");
		this.MoneyButton = new Button("Manage Exchange");
		this.changeUserButton = new Button("Manage User");

		this.cancelTransButton = new Button("Cancel Transaction Report");
		this.showUserButton = new Button("User Report");
		this.availableChangeButton = new Button("Available Change Report");
		this.transSumButton = new Button("Transaction Summary Report");
		this.itemInStockButton = new Button("Item InStock Report");
		this.soldItemButton = new Button("Sold Item Report");

	}
	public void setup() {
		root.setAlignment(Pos.CENTER);
		root.setHgap(20);
		root.setVgap(20);

		this.title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

		root.add(this.title, 1, 0);
		root.add(this.ItemButton, 0, 1);
		root.add(this.MoneyButton, 1, 1);
		root.add(this.changeUserButton, 2, 1);
		root.add(this.cancelTransButton, 0, 2);
		root.add(this.showUserButton, 1, 2);
		root.add(this.availableChangeButton, 2, 2);
		root.add(this.transSumButton, 0, 3);
		root.add(this.itemInStockButton, 1, 3);
		root.add(this.soldItemButton, 2, 3);
		root.add(this.actionTarget, 1, 4);
		//		GridPane.setHalignment(submitButton, HPos.RIGHT);
		root.add(this.LogoutButton, 2, 5);

		//implemented after pages has been created
		ItemButton.setOnAction(actionEvent -> {
			this.reset();
			this.OwnerWelcomePage.hide();
			App.stages.get("SellerView").show();
			App.sellerview.Sold.setVisible(false);
			App.sellerview.Avaliable.setVisible(false);
		});

		MoneyButton.setOnAction(actionEvent -> {
			this.reset();
			this.OwnerWelcomePage.hide();
			App.stages.get("CashierView").show();
			App.cashierview.reportChanges.setVisible(false);
			App.cashierview.reportTransactions.setVisible(false);
			//	        App.stages.get("SellerView").show();
		});

		changeUserButton.setOnAction(actionEvent -> {
			this.reset();
			this.OwnerWelcomePage.hide();
			App.stages.get("OwnerUserManagementPage").show();
		});

		cancelTransButton.setOnAction(actionEvent -> {
			this.reset();
			OwnerReport.buildCancelTransReport(transFile,"CancelTransactionReport.txt");
			actionTarget.setText("Cancel Transaction Report Generated");
			actionTarget.setFill(javafx.scene.paint.Color.BLUE);
			//			this.OwnerWelcomePage.hide();
			//	        App.stages.get("SellerView").show();
		});

		showUserButton.setOnAction(actionEvent -> {
			this.reset();
			Map<String,String> users = OwnerReport.getUserwithRole(jsonFile);
			OwnerReport.buildUsernameReport(users,"UsernameInfoReport.txt");
			actionTarget.setText("User Report Generated");
			actionTarget.setFill(javafx.scene.paint.Color.BLUE);
			//			this.OwnerWelcomePage.hide();
			//	        App.stages.get("SellerView").show();
		});

		availableChangeButton.setOnAction(actionEvent -> {
			this.reset();
			App.cashierview.reportChanges();
			actionTarget.setText("Change Available Report Generated");
			actionTarget.setFill(javafx.scene.paint.Color.BLUE);
			//			this.OwnerWelcomePage.hide();
			//	        App.stages.get("SellerView").show();
		});

		transSumButton.setOnAction(actionEvent -> {
			this.reset();
			App.cashierview.reportTransactions();
			actionTarget.setText("Transaction Report Generated");
			actionTarget.setFill(javafx.scene.paint.Color.BLUE);
			//			this.OwnerWelcomePage.hide();
			//	        App.stages.get("SellerView").show();
		});

		soldItemButton.setOnAction(actionEvent -> {
			this.reset();
			SellerReport.SellerSoldReport("SellerSoldReport.txt", "Items.json", "userPurchaseHistory.json", "anonPurchaseHistory.json");
			actionTarget.setText("Sold Report Generated");
			actionTarget.setFill(javafx.scene.paint.Color.BLUE);
		});

		itemInStockButton.setOnAction(actionEvent -> {

			this.reset();
			SellerReport.SellerAvailableReport("SellerAvailableReport.txt");
			//	        App.stages.get("SellerView").show();
			actionTarget.setText("Item Available Report Generated");
			actionTarget.setFill(javafx.scene.paint.Color.BLUE);
		});

		LogoutButton.setOnAction(actionEvent -> {
			this.reset();
			this.OwnerWelcomePage.hide();
			App.stages.get("DefaultPage").show();
			App.defaultPage.changeBackToAnonUser();
		});

		GridPane.setHalignment(LogoutButton, HPos.RIGHT);

	}
	public void reset() {
		this.actionTarget.setText(null);
	}
	public Stage display() {
		setup();

		Scene scene = new Scene(root, 600, 350);
		OwnerWelcomePage.setScene(scene);
		return this.OwnerWelcomePage;
	}
}
