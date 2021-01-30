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

public class CreateAccount {

    private Stage createAccount;
    // private Stage previousStage;

    private String jsonFile;

    private Text title;
    private Label usernameLabel;
    private Label passwordLabel;
    private Label confirmLabel;
    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField confirmField;
    private Button returnButton;
    private Button submitButton;
    private Text usernamePrompt;
    private Text passwordPrompt;
    private Text confirmPrompt;

    private boolean acceptUsername;
    private boolean acceptPassword;
    private boolean acceptConfirm;

    public CreateAccount(String jsonFile) {
        this.createAccount = new Stage();
        // this.previousStage = null;

        this.jsonFile = jsonFile;

        this.title = new Text("Create Account");
        this.usernameLabel = new Label("Enter the username:");
        this.passwordLabel = new Label("Enter the password:");
        this.confirmLabel = new Label("Confirm the password:");
        this.usernameField = new TextField();
        this.passwordField = new PasswordField();
        this.confirmField = new PasswordField();
        this.returnButton = new Button("Return");
        this.submitButton = new Button("Submit");
        this.usernamePrompt = new Text();
        this.passwordPrompt = new Text();
        this.confirmPrompt = new Text();

        this.acceptConfirm = false;
        this.acceptPassword = false;
        this.acceptUsername = false;
    }

    // public void setPreviousStage(Stage stage) {
    //     this.previousStage = stage;
    // }

    public void setLayout() {
        this.title.setLayoutX(200);
        this.title.setLayoutY(100);
        this.title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        this.usernameLabel.setLayoutX(100);
        this.usernameLabel.setLayoutY(150);

        this.passwordLabel.setLayoutX(100);
        this.passwordLabel.setLayoutY(200);

        this.confirmLabel.setLayoutX(100);
        this.confirmLabel.setLayoutY(250);

        this.usernameField.setLayoutX(250);
        this.usernameField.setLayoutY(150);

        this.passwordField.setLayoutX(250);
        this.passwordField.setLayoutY(200);

        this.confirmField.setLayoutX(250);
        this.confirmField.setLayoutY(250);

        this.usernamePrompt.setLayoutX(250);
        this.usernamePrompt.setLayoutY(190);

        this.passwordPrompt.setLayoutX(250);
        this.passwordPrompt.setLayoutY(240);

        this.confirmPrompt.setLayoutX(250);
        this.confirmPrompt.setLayoutY(290);

        this.returnButton.setLayoutX(300);
        this.returnButton.setLayoutY(350);

        this.submitButton.setLayoutX(400);
        this.submitButton.setLayoutY(350);
    }

    public void setReturnButtonAction() {
        this.returnButton.setOnAction(actionEvent -> {
            this.clean();
            this.createAccount.hide();
            App.stages.get("DefaultPage").show();
        });
    }

    public boolean setUsernameFieldAction() {
        if(this.usernameField.getText().trim().equals("")) {
            this.usernamePrompt.setText("Error: the username can not be empty!");
            this.usernamePrompt.setFill(Color.RED);
            this.acceptUsername = false;
        }else {
            AccountJsonHandler handler = new AccountJsonHandler(this.jsonFile);
            List<User> users = handler.getUsers();
            String username = this.usernameField.getText();
            if(CreateAccountValidCheck.checkExistence(users, username) == true) {
                this.usernamePrompt.setText("Error: the username has existed!");
                this.usernamePrompt.setFill(Color.RED);
                this.acceptUsername = false;
            }else {
                this.usernamePrompt.setText("Success!");
                this.usernamePrompt.setFill(Color.GREEN);
                this.acceptUsername = true;
            }
        }
        return this.acceptUsername;
    }

    public boolean setPasswordFieldAction() {
        if(this.passwordField.getText().trim().equals("")) {
            this.passwordPrompt.setText("Error: the password can not be empty!");
            this.passwordPrompt.setFill(Color.RED);
            this.acceptPassword = false;
        }else {
            this.passwordPrompt.setText("Success!");
            this.passwordPrompt.setFill(Color.GREEN);
            this.acceptPassword = true;
        }
        return this.acceptPassword;
    }

    public boolean setConfirmFieldAction() {
        if(this.confirmField.getText().trim().equals("")) {
            this.confirmPrompt.setText("Error: the confirm password can not be empty!");
            this.confirmPrompt.setFill(Color.RED);
            this.acceptConfirm = false;
        }else {
            if(this.confirmField.getText().equals(this.passwordField.getText())) {
                this.confirmPrompt.setText("Success!");
                this.confirmPrompt.setFill(Color.GREEN);
                this.acceptConfirm = true;
            }else {
                this.confirmPrompt.setText("Error: please check the confirm password!");
                this.confirmPrompt.setFill(Color.RED);
                this.acceptConfirm = false;
            }
        }
        return this.acceptConfirm;
    }

    public void setSubmitButtonAction() {
        this.submitButton.setOnAction(action -> {
            // Check the situation of text fields:
            setUsernameFieldAction();
            setPasswordFieldAction();
            setConfirmFieldAction();

            // Success: login automately and store the information
            if(this.acceptUsername && this.acceptPassword && this.acceptConfirm) {
                Customer customer = new Customer(this.usernameField.getText(), this.passwordField.getText());
                AccountJsonHandler handler = new AccountJsonHandler(this.jsonFile);
                handler.addAccount(customer);
                App.defaultPage.changeToUser(this.usernameField.getText());
                this.createAccount.hide();
                this.clean();
		        App.stages.get("DefaultPage").show();
            }
        });
    }

    public void clean() {
        // Clean the fields and the Prompts:
        this.usernameField.clear();
        this.passwordField.clear();
        this.confirmField.clear();
        this.usernamePrompt.setText("");
        this.passwordPrompt.setText("");
        this.confirmPrompt.setText("");
    }

    public Stage display() {
        this.setLayout();
        this.setReturnButtonAction();
        this.setSubmitButtonAction();

        Pane root = new Pane(title, usernameLabel, passwordLabel, confirmLabel, usernameField, passwordField, confirmField, returnButton, submitButton, usernamePrompt, passwordPrompt, confirmPrompt);
        Scene scene = new Scene(root, 600, 400);
        createAccount.setScene(scene);

        return this.createAccount;
    }

}
