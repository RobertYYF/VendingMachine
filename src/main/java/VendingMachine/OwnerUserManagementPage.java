package VendingMachine;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OwnerUserManagementPage {
	private Stage OwnerPageStage;
	public String jsonFile;
	private Label title;
	private Label usernameLabel;
	private Label passwordLabel,confirmLabel,deleteLabel;
	private ComboBox<String> roleBox;
	private ArrayList<String> roleList;
	private ComboBox<String> specialUserBox;
	private ArrayList<String> specialUsername;
	public TextField usernameBox,passwordBox,confirmBox;
	public String usernamePrompt, passwordPrompt, confirmPrompt;
	public Button addButton,addUserButton, deleteButton;
	private Button returnButton, submitButton, deleteUserButton,logoutButton;
	public Text actiontarget,accountTarget;
	private AccountJsonHandler handler;
	private ObservableList<String> role_list;

	public OwnerUserManagementPage(String jsonfile){
		this.OwnerPageStage = new Stage();
		OwnerPageStage.setTitle("Welcome Owner");
		this.jsonFile = jsonfile;
		//			this.actiontarget = new Text();
		this.title = new Label("Add or Delete User");
		this.usernameLabel = new Label("Username:");
		this.passwordLabel = new Label("Password:");
		this.confirmLabel = new Label("Confirm Passowrd");
		this.usernameBox = new TextField();
		this.passwordBox = new PasswordField();
		this.confirmBox = new PasswordField();
		this.returnButton = new Button("Return");
		this.logoutButton = new Button("Return");
		//			this.submitButton = new Button("Submit");
		this.addButton = new Button("add");
		this.addUserButton = new Button("add user");
		this.deleteButton = new Button("delete");
		this.deleteUserButton = new Button("delete user");
		this.role_list = FXCollections.observableArrayList();
		role_list.add("owner");
		role_list.add("cashier");
		role_list.add("seller");
		this.roleBox = new ComboBox<String>(role_list);
		this.specialUserBox = new ComboBox<String>();
		this.deleteLabel = new Label("Choose the user you want to delete");
		this.actiontarget = new Text();
		this.accountTarget = new Text();
	}

	public void setLayout(){
		this.title.setLayoutX(170);
		this.title.setLayoutY(70);

		this.deleteLabel.setLayoutX(100);
		this.deleteLabel.setLayoutY(150);

		this.specialUserBox.setLayoutX(200);
		this.specialUserBox.setLayoutY(150);

		this.deleteUserButton.setLayoutX(300);
		this.deleteUserButton.setLayoutY(150);

		this.usernameLabel.setLayoutX(100);
		this.usernameLabel.setLayoutY(150);

		this.passwordLabel.setLayoutX(100);
		this.passwordLabel.setLayoutY(200);

		this.confirmLabel.setLayoutX(100);
		this.confirmLabel.setLayoutY(250);

		this.confirmBox.setLayoutX(250);
		this.confirmBox.setLayoutY(250);

		this.usernameBox.setLayoutX(250);
		this.usernameBox.setLayoutY(150);

		this.passwordBox.setLayoutX(250);
		this.passwordBox.setLayoutY(200);

		this.roleBox.setLayoutX(250);
		this.roleBox.setLayoutY(330);

		//	        this.specialUserBox.setLayoutX(250);
		//	        this.specialUserBox.setLayoutY(250);

		//	        this.usernamePrompt.setLayoutX(250);
		//	        this.usernamePrompt.setLayoutY(190);
		//
		//	        this.passwordPrompt.setLayoutX(250);
		//	        this.passwordPrompt.setLayoutY(240);

		this.returnButton.setLayoutX(500);
		this.returnButton.setLayoutY(360);

		this.logoutButton.setLayoutX(500);
		this.logoutButton.setLayoutY(360);

		//	        this.submitButton.setLayoutX(400);
		//	        this.submitButton.setLayoutY(350);

		this.addButton.setLayoutX(250);
		this.addButton.setLayoutY(150);

		this.deleteButton.setLayoutX(245);
		this.deleteButton.setLayoutY(200);

		this.actiontarget.setLayoutX(305);
		this.actiontarget.setLayoutY(200);

		this.accountTarget.setLayoutX(250);
		this.accountTarget.setLayoutY(300);

		this.addUserButton.setLayoutX(345);
		this.addUserButton.setLayoutY(330);
	}

	public void setVisibility(boolean visible) {
		usernameLabel.setVisible(visible);
		passwordLabel.setVisible(visible);
		confirmLabel.setVisible(visible);
		deleteLabel.setVisible(visible);
		usernameBox.setVisible(visible);
		passwordBox.setVisible(visible);
		confirmBox.setVisible(visible);
		specialUserBox.setVisible(visible);
		roleBox.setVisible(visible);
		//    		submitButton.setVisible(visible);
		deleteUserButton.setVisible(visible);
		actiontarget.setVisible(visible);
		addUserButton.setVisible(visible);
		accountTarget.setVisible(visible);
		returnButton.setVisible(visible);
	}

	public void setup() {
		this.title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		this.setVisibility(false);

	}
	public void cleanInfo() {
		actiontarget.setText(null);
		specialUserBox.valueProperty().set(null);
		roleBox.valueProperty().set(null);
		accountTarget.setText(null);
		usernameBox.setText(null);
		passwordBox.setText(null);
		confirmBox.setText(null);
	}
	public void addUserButtonAction() {
		this.addUserButton.setOnAction(action -> {
			accountTarget.setVisible(true);
			AccountJsonHandler handler = new AccountJsonHandler(jsonFile);
			this.usernamePrompt = this.usernameBox.getText();
			this.passwordPrompt = this.passwordBox.getText();
			this.confirmPrompt = this.confirmBox.getText();
			String role = this.roleBox.getValue();
			if (usernamePrompt == null || passwordPrompt == null || 
					confirmPrompt == null || role == null ||
					usernamePrompt.equals("") || passwordPrompt.equals("")
					|| confirmPrompt.equals("")) {
				this.accountTarget.setText("Missing Information! " + "Please Try Again");
				this.accountTarget.setFill(Color.FIREBRICK);
			}
			else if(!passwordPrompt.equals(confirmPrompt)) {
				this.accountTarget.setText("Password do not match" + "Please Try Again");
				this.accountTarget.setFill(Color.FIREBRICK);
			}
			else {
				boolean result = CreateAccountValidCheck.checkExistence(
						handler.getUsers(), usernamePrompt);
				if (result == true) {
					this.accountTarget.setText("Account Already Exists! " + "Please Try Again");
					this.accountTarget.setFill(Color.FIREBRICK);
				}
				else {
					cleanInfo();
					User newUser = null;
					if(role.equals("owner")) {
						newUser = new Owner(this.usernamePrompt, this.passwordPrompt);
					}
					if(role.equals("seller")) {
						newUser = new Seller(this.usernamePrompt, this.passwordPrompt);
					}
					if(role.equals("cashier")) {
						newUser = new Cashier(this.usernamePrompt, this.passwordPrompt);
					}
					handler.addAccount(newUser); 
					this.accountTarget.setText("Success");
					this.accountTarget.setFill(Color.GREEN);
				}
			}
		});
	}

	public void addButtonAction() {
		this.addButton.setOnAction(action -> {
			cleanInfo();
			this.addButton.setVisible(false);
			this.deleteButton.setVisible(false);
			this.logoutButton.setVisible(false);
			returnButton.setVisible(true);
			usernameLabel.setVisible(true);
			passwordLabel.setVisible(true);
			confirmLabel.setVisible(true);
			confirmBox.setVisible(true);
			usernameBox.setVisible(true);
			passwordBox.setVisible(true);
			addUserButton.setVisible(true);
			roleBox.setVisible(true);
			actiontarget.setVisible(true);

		});
	}

	public void deleteButtonAction() {
		this.deleteButton.setOnAction(action -> {
			AccountJsonHandler handler = new AccountJsonHandler(jsonFile);
			List<String> specialUser = handler.getSpecialUserName();
			this.addButton.setVisible(false);
			this.deleteButton.setVisible(false);
			this.logoutButton.setVisible(false);
			returnButton.setVisible(true);
			usernameLabel.setVisible(true);

			ObservableList<String> specUser = FXCollections.observableArrayList();
			specUser.addAll(specialUser);
			specialUserBox.getItems().clear();
			specialUserBox.getItems().addAll(specUser);
			specialUserBox.setVisible(true);
			this.deleteUserButton.setVisible(true);
		});
	}

	public void deleteUserButtonAction() {
		this.deleteUserButton.setOnAction(action -> {
			AccountJsonHandler handler = new AccountJsonHandler(jsonFile);
			if(handler.getSpecialUserName().size() == 0) {
				actiontarget.setText("No user can be deleted anymore.");
				actiontarget.setVisible(true);    		
				actiontarget.setFill(Color.FIREBRICK);
			}
			else {
				String deleteUser = specialUserBox.getValue();
				actiontarget.setText("Sucessful");
				actiontarget.setFill(Color.GREEN);
				actiontarget.setVisible(true);
				handler.deleteAccount(deleteUser);
				cleanInfo();
				specialUserBox.getItems().remove(deleteUser);
			}
		});
	}


	public void returnButtonAction() {
		this.returnButton.setOnAction(action -> {
//			this.addButton.setVisible(true);
//			this.deleteButton.setVisible(true);
//			this.logoutButton.setVisible(true);
//			this.setVisibility(false);
			this.reset();

		});
	}

	public void LogoutButtonAction() {
		this.logoutButton.setOnAction(action -> {
			this.OwnerPageStage.hide();
			App.stages.get("OwnerWelcomePage").show();
		});
	}
	
	public void reset() {
		this.setVisibility(false);
		this.addButton.setVisible(true);
		this.deleteButton.setVisible(true);
		this.logoutButton.setVisible(true);
		this.cleanInfo();
	}
	public Stage display() {
		this.setLayout();
		this.setup();
		this.addButtonAction();
		this.deleteButtonAction();
		this.returnButtonAction();
		this.LogoutButtonAction();
		this.deleteUserButtonAction();
		this.addUserButtonAction();

		Pane root = new Pane(title, usernameLabel, passwordLabel, actiontarget,
				accountTarget, confirmBox,confirmLabel,logoutButton,
				usernameBox, passwordBox, returnButton,deleteUserButton,addUserButton,
				addButton,deleteButton,roleBox, specialUserBox);
		Scene scene = new Scene(root, 600, 400);
		OwnerPageStage.setScene(scene);

		return this.OwnerPageStage;
	}
}
