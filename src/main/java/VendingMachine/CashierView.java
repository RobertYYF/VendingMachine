package VendingMachine;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Arrays;


public class CashierView{
	private Stage CashierView;
	public Text title;
	public Text note5;
	public TextField notes5;
	public Text note10;
	public TextField notes10;
	public Text note20;
	public TextField notes20;
	public Text note50;
	public TextField notes50;
	public Text note100;
	public TextField notes100;
	public Text dollar1;
	public TextField dollars1;
	public Text dollar2;
	public TextField dollars2;
	public Text cent5;
	public TextField cents5;
	public Text cent10;
	public TextField cents10;
	public Text cent20;
	public TextField cents20;
	public Text cent50;
	public TextField cents50;

	public Button returnButton;
	public Button confirm;
	public Button reportChanges;
	public Button reportTransactions;
	private Pane root;
	private TextField[] cashArray;
	private double[] quantity;
	public Text message;
	private String jsonFile;


	public CashierView(String jsonFile){
		this.jsonFile = jsonFile;
		this.CashierView = new Stage();
		// Create button for return:
		this.returnButton = new Button("Return");
		this.returnButton.setLayoutX(540);
		this.returnButton.setLayoutY(350);

		this.confirm = new Button("Confirm");
		this.confirm.setLayoutX(150);
		this.confirm.setLayoutY(350);
		this.confirm.setOnAction(actionEvent -> {
			fillCash();
		});

		this.reportChanges = new Button("Report Changes");
		this.reportChanges.setLayoutX(270);
		this.reportChanges.setLayoutY(350);
		this.reportChanges.setOnAction(actionEvent -> {
			reportChanges();
		});

		this.reportTransactions = new Button("Report Transactions");
		this.reportTransactions.setLayoutX(400);
		this.reportTransactions.setLayoutY(350);
		this.reportTransactions.setOnAction(actionEvent -> {
			reportTransactions();
		});

		this.note5 = new Text("5 dollars");
		this.notes5 = new TextField();
		this.note10 = new Text("10 dollars");
		this.notes10 = new TextField();
		this.note20 = new Text("20 dollars");
		this.notes20 = new TextField();
		this.note50 = new Text("50 dollars");
		this.notes50 = new TextField();
		this.note100 = new Text("100 dollars");
		this.notes100 = new TextField();
		this.dollar1 = new Text("1 dollar");
		this.dollars1 = new TextField();
		this.dollar2 = new Text("2 dollars");
		this.dollars2 = new TextField();
		this.cent5 = new Text("5 cents");
		this.cents5 = new TextField();
		this.cent10 = new Text("10 cents");
		this.cents10 = new TextField();
		this.cent20 = new Text("20 cents");
		this.cents20 = new TextField();
		this.cent50 = new Text("50 cents");
		this.cents50 = new TextField();

		this.cashArray = new TextField[] {this.notes5,this.notes10,this.notes20,this.notes50,this.notes100,this.dollars1,this.dollars2,this.cents5,this.cents10,this.cents20,this.cents50};
		this.quantity = new double[] {5,10,20,50,100,1,2,0.05,0.1,0.2,0.5};
		this.message = new Text();
		this.message.setLayoutX(250);
		this.message.setLayoutY(310);
		// Create title for the window:
		this.title = new Text("Please fill Notes/Coins:");
		this.title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		this.root = new Pane();
	}
	public void setElementsPosition(){
		this.title.setLayoutX(160);
		this.title.setLayoutY(50);

		// Set positions
		this.note5.setLayoutX(150);
		this.note5.setLayoutY(100);
		this.notes5.setLayoutX(215);
		this.notes5.setLayoutY(80);
		this.notes5.setPrefWidth(50);

		this.note10.setLayoutX(150);
		this.note10.setLayoutY(140);
		this.notes10.setLayoutX(215);
		this.notes10.setLayoutY(120);
		this.notes10.setPrefWidth(50);

		this.note20.setLayoutX(150);
		this.note20.setLayoutY(180);
		this.notes20.setLayoutX(215);
		this.notes20.setLayoutY(160);
		this.notes20.setPrefWidth(50);

		this.note50.setLayoutX(150);
		this.note50.setLayoutY(220);
		this.notes50.setLayoutX(215);
		this.notes50.setLayoutY(200);
		this.notes50.setPrefWidth(50);

		this.note100.setLayoutX(150);
		this.note100.setLayoutY(260);
		this.notes100.setLayoutX(215);
		this.notes100.setLayoutY(240);
		this.notes100.setPrefWidth(50);

		this.dollar1.setLayoutX(300);
		this.dollar1.setLayoutY(100);
		this.dollars1.setLayoutX(365);
		this.dollars1.setLayoutY(80);
		this.dollars1.setPrefWidth(50);


		this.dollar2.setLayoutX(300);
		this.dollar2.setLayoutY(140);
		this.dollars2.setLayoutX(365);
		this.dollars2.setLayoutY(120);
		this.dollars2.setPrefWidth(50);

		this.cent5.setLayoutX(300);
		this.cent5.setLayoutY(180);
		this.cents5.setLayoutX(365);
		this.cents5.setLayoutY(160);
		this.cents5.setPrefWidth(50);

		this.cent10.setLayoutX(300);
		this.cent10.setLayoutY(220);
		this.cents10.setLayoutX(365);
		this.cents10.setLayoutY(200);
		this.cents10.setPrefWidth(50);

		this.cent50.setLayoutX(300);
		this.cent50.setLayoutY(260);
		this.cents50.setLayoutX(365);
		this.cents50.setLayoutY(240);
		this.cents50.setPrefWidth(50);
	}
	public void fillCash(){
		try{
			for(int i = 0;i < 5;i++){
				if (!this.cashArray[i].getText().equals("")){
					App.getCash().increaseNAvai(i,Integer.parseInt(this.cashArray[i].getText()));
				}
			}
			for(int i = 5;i < 7;i++){
				if (!this.cashArray[i].getText().equals("")){
					App.getCash().increaseDAvai(i-5,Integer.parseInt(this.cashArray[i].getText()));
				}
			}
			for(int i = 7;i < this.cashArray.length;i++){
				if (!this.cashArray[i].getText().equals("")){
					App.getCash().increaseCAvai(i-7,Integer.parseInt(this.cashArray[i].getText()));
				}
			}
			this.message.setText("Successfully filled cash");
		}catch (Exception e){
			this.message.setText("Inputs should be integers.");
		}

	}
	public void reportChanges(){
		File f = new File("AvailableChangeReport.txt");
		try{
			PrintWriter writer = new PrintWriter(f);
			// for(int i = 0;i < 3;i++){
			writer.print("Number of available notes: ");
			for(int j = 0;j < 5;j++){
				writer.print("$" + String.valueOf(App.getCash().getAllCash()[0][j]) + "x" + String.valueOf(App.getCash().getAllAvailabel()[0][j]) + " " );
			}
			writer.println();
			// }

			writer.print("Number of available coins(dollars): ");
			for(int j = 0;j < 2;j++){
				writer.print("$" + String.valueOf(App.getCash().getAllCash()[1][j]) + "x" + String.valueOf(App.getCash().getAllAvailabel()[1][j]) + " " );
			}
			writer.println();

			writer.print("Number of available coins(cents): ");
			for(int j = 0;j < 6;j++){
				writer.print("$" + String.valueOf(App.getCash().getAllCash()[2][j]) + "x" + String.valueOf(App.getCash().getAllAvailabel()[2][j]) + " " );
			}
			writer.println();
			writer.print("Total amount of changes: $");
			double sum = 0;
			for(int j = 0;j < 5;j++){
				sum += App.getCash().getAllCash()[0][j] * App.getCash().getAllAvailabel()[0][j];
			}

			for(int j = 0;j < 2;j++){
				sum += App.getCash().getAllCash()[1][j] * App.getCash().getAllAvailabel()[1][j];
			}

			for(int j = 0;j < 6;j++){
				sum += App.getCash().getAllCash()[2][j] * App.getCash().getAllAvailabel()[2][j] * 0.01;
			}
			writer.println(sum);

			writer.println();
			writer.close();

		}catch(FileNotFoundException e){
			this.message.setText("File not found!");
		}
	}
	public void reportTransactions(){
		String reportName = "TransactionsReport.txt";
		//        File inputF= new File("completePurchaseHistory.json");
		List<CompleteHistory> history = 
				PurchaseHistoryJsonHandler.readFromCompleteHistory();

		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(reportName));
			out.write("Transaction Report\t\n");
			//            Scanner scan = new Scanner(inputF);
			for(int i =0; i < history.size();i++) {
				out.write("Items:  "+ history.get(i).getItems() +"\t\t");
				out.write("Paid:  "+ history.get(i).getPaid() +"\t\t");
				out.write("Change:  "+ history.get(i).getChange() +"\t\t");
				out.write("Time:  "+ history.get(i).getTime() +"\t\t");
				out.write("Payment Method:  "+ history.get(i).getMethod() +"\t\t");
				out.write("\n");
			}
			out.close();
		}catch(Exception e){
			//		this.message.setText("File not found!");
		}
	}
	public void manageButton() {
		this.returnButton.setOnAction(actionEvent -> {
			AccountJsonHandler handler = new AccountJsonHandler(jsonFile);
			User user = handler.getUser(DefaultPage.getCurrentUser());
			if(user instanceof Owner) {
				this.CashierView.hide();
				App.stages.get("OwnerWelcomePage").show();
				App.cashierview.reportChanges.setVisible(true);
				App.cashierview.reportTransactions.setVisible(true);
				this.reset();
			}else {
				this.CashierView.hide();
				App.stages.get("DefaultPage").show();
				this.reset();
			}
		});
	}
	public Stage display(){
		manageButton();
		setElementsPosition();
		Pane root = new Pane(title,confirm,returnButton,reportChanges,reportTransactions,note5,notes5,note10,notes10,note20,notes20,note50,notes50,note100,notes100,dollar1,dollars1,dollar2,dollars2,cent5,cents5,cent10,cents10,cent50,cents50,message);
		Scene scene = new Scene(root, 600, 400);
		CashierView.setTitle("CashierView");
		CashierView.setScene(scene);
		return CashierView;
	}
	
    public void reset() {
        this.notes5.clear();
        this.notes10.clear();
        this.notes50.clear();
        this.notes100.clear();
        this.notes20.clear();
        this.dollars1.clear();
        this.dollars2.clear();
        this.cents5.clear();
        this.cents10.clear();
        this.cents20.clear();
        this.cents50.clear();
        this.message.setText("");
    }
}
