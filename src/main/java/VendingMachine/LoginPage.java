package VendingMachine;


import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPage {
	private Stage loginPageStage;
	private GridPane root;
	public String jsonFile;
	private Label title;
	private Label usernameLabel;
	private Label passwordLabel;
	public TextField usernameBox;
	public TextField passwordBox;
	public String usernamePrompt;
	public String passwordPrompt;
	private Button returnButton;
	private Button submitButton;
	public Text actiontarget;
	private AccountJsonHandler handler;


	public LoginPage(String jsonfile){
		this.loginPageStage = new Stage();
		loginPageStage.setTitle("User Login");
		this.jsonFile = jsonfile;
		this.root = new GridPane();
		this.actiontarget = new Text();
		this.title = new Label("User Login");
		this.usernameLabel = new Label("Username:");
		this.passwordLabel = new Label("Password:");
		this.usernameBox = new TextField();
		this.passwordBox = new PasswordField();
		this.returnButton = new Button("Return");
		this.submitButton = new Button("Sign In");
	}

	public void setLayout() {
		root.setAlignment(Pos.CENTER);
		root.setHgap(10);
		root.setVgap(10);

		this.title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

		root.add(this.title, 1, 0);
		root.add(this.usernameLabel, 0, 1);
		root.add(this.passwordLabel, 0, 2);
		root.add(this.usernameBox, 1, 1);
		root.add(this.passwordBox, 1, 2);
		root.add(actiontarget, 1, 3);
		root.add(this.submitButton, 1, 5);
		GridPane.setHalignment(submitButton, HPos.RIGHT);
		root.add(this.returnButton, 0, 5);
	}

	public boolean processInput() {
		this.handler = new AccountJsonHandler(this.jsonFile);
		this.usernamePrompt = this.usernameBox.getText();
		this.passwordPrompt = this.passwordBox.getText();
		if(this.usernamePrompt == null || this.usernamePrompt.equals("")) {
			actiontarget.setText("Username Cannot Be Empty");
			actiontarget.setFill(Color.FIREBRICK);
			return false;
		}
		if(this.passwordPrompt == null || this.passwordPrompt.equals("")) {
			actiontarget.setText("Password Cannot Be Empty");
			actiontarget.setFill(Color.FIREBRICK);
			return false;
		}
		return true;
	}

	public void submitButtonAction() {
		this.submitButton.setOnAction(action -> {
			boolean processSuccess = processInput();
			if (processSuccess == true) {
				boolean validResult = LoginValidChecker.checkValidInput(this.jsonFile,
						this.usernamePrompt, this.passwordPrompt);
				if(validResult == true) {
					LoginValidChecker.clean(this.actiontarget, this.usernameBox, this.passwordBox);
					// this.loginPageStage.hide();
					String userType = LoginValidChecker.checkUserType(this.jsonFile,usernamePrompt);
//					System.out.println(userType);
					if(userType.equals("customer")) {
						this.loginPageStage.hide();
						App.defaultPage.changeToUser(this.usernamePrompt);
				        App.stages.get("DefaultPage").show();
					}
					if (userType.equals("seller")) {
						this.loginPageStage.hide();
						App.defaultPage.changeToSpecialUser(this.usernamePrompt);
				        App.stages.get("SellerView").show();
					}
					if (userType.equals("owner")) {
						this.loginPageStage.hide();
						App.defaultPage.changeToSpecialUser(this.usernamePrompt);
						 App.stages.get("OwnerWelcomePage").show();
					}
					//need to add cashier
					if (userType.equals("cashier")){
						this.loginPageStage.hide();
						App.defaultPage.changeToSpecialUser(this.usernamePrompt);
						App.stages.get("CashierView").show();
					}
				}
				else {
					actiontarget.setText("Incorrect Username or Password");
					actiontarget.setFill(Color.FIREBRICK);
				}
			}
		});
	}

	public void returnButtonAction() {
		this.returnButton.setOnAction(action -> {
			LoginValidChecker.clean(this.actiontarget, this.usernameBox, this.passwordBox);
			this.loginPageStage.hide();
	        App.stages.get("DefaultPage").show();
		});
	}
	
	public void reset() {
		this.usernameBox.setText(null);
		this.passwordBox.setText(null);
		this.actiontarget.setText(null);
	}


	public Stage display() {
		setLayout();
		submitButtonAction();
		returnButtonAction();
		reset();

		Scene scene = new Scene(root, 400, 400);
		loginPageStage.setScene(scene);
		return this.loginPageStage;
	}
}
