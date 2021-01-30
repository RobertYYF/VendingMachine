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

public class Successful{
	private Stage Successful;
	public Text title;

	public Button returnButton;
	public Button changeButton;
	// private Pane root;
	public Text message;
	public boolean gotChange;

	public Successful(){
		this.Successful = new Stage();
		this.returnButton = new Button("Return");
		this.returnButton.setLayoutX(450);
		this.returnButton.setLayoutY(350);
		//        this.returnButton.setOnAction(actionEvent -> {App.defaultPage.updateHistory(); App.stages.get("DefaultPage").show();this.Successful.hide();});
		this.changeButton = new Button("get changes");
		this.changeButton.setLayoutX(300);
		this.changeButton.setLayoutY(350);
		this.gotChange = false;
		this.changeButton.setOnAction(actionEvent -> {
			if(!gotChange){
				String change = getChange(App.cashPage.getSum() - App.shoppingList.getTotal(),App.getCash());
				if (!change.equals("")){
					// if (!getChange(App.cashPage.getSum() - App.shoppingList.getTotal(),App.getCash()).equals("")){
					this.message.setText("Here are your Changes: \n" + change);
					this.gotChange = true;
				}else{
					this.message.setText("No available changes.\nPlease insert different notes/coins to complete the payment :) \nor cancel the transaction :(\n");
				}
			}else{
				this.message.setText("Changes have already come out:)");
			}
		});

		this.returnButton.setOnAction(actionEvent -> {
			App.defaultPage.updateHistory();
			App.stages.get("DefaultPage").show();
			this.Successful.hide();
			App.cashPage.reset();
			this.message.setText(null);
			this.gotChange = false;
		});

		this.message = new Text("");
		this.message.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,15));
		this.message.setLayoutX(50);
		this.message.setLayoutY(100);
		// Create title for the window:
		this.title = new Text("Thanks for shopping with us !");
		this.title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		// this.root = new Pane();
		this.title.setLayoutX(160);
		this.title.setLayoutY(50);


	}
	//    public void setElementsPosition(){
	//
	//    }


	public String getChange(double userChange,Cash c){
		int[] avaiNote = c.getNote();
		int[] avaiDollar = c.getDollar();
		int[] avaiCent = c.getCent();
		int[] noteAvai = c.getnAvailable();
		int[] dollarAvai = c.getdAvailable();
		int[] centAvai = c.getcAvailable();
		// int[][] result = new int[2][6];
		int[][][] result = new int[3][2][];
		result[0][0] = new int[5];
		result[0][1] = new int[5];
		result[1][0] = new int[2];
		result[1][1] = new int[2];
		result[2][0] = new int[6];
		result[2][1] = new int[6];
		for(int i = (avaiNote.length - 1);i >= 0;i--){
			if(userChange == 0){
				break;
			}
			if(userChange >= avaiNote[i]){
				result[0][0][i] = avaiNote[i];
				int maxNumNotes = 1;
				while(userChange >= avaiNote[i]*maxNumNotes){
					c.decreaseNAvai(i);
					maxNumNotes += 1;
				}
				maxNumNotes -= 1;
				result[0][1][i] += maxNumNotes;
				userChange -= avaiNote[i]*maxNumNotes;
			}
		}
		for(int i = (avaiDollar.length - 1);i >= 0;i--){
			if(userChange == 0){
				break;
			}
			if(userChange >= avaiDollar[i]){
				result[1][0][i] = avaiDollar[i];
				int maxNumDollars = 1;
				while(userChange >= avaiDollar[i]*maxNumDollars){
					c.decreaseDAvai(i);
					maxNumDollars += 1;
				}
				maxNumDollars -= 1;
				result[1][1][i] += maxNumDollars;
				userChange -= avaiDollar[i]*maxNumDollars;
			}
		}
		for(int i = (avaiCent.length - 1);i >= 0;i--){
			if(userChange == 0){
				break;
			}
			if(userChange*100 >= avaiCent[i]){
				result[2][0][i] = avaiCent[i];
				int maxNumCents = 1;
				while(userChange*100 >= avaiCent[i]*maxNumCents){
					c.decreaseCAvai(i);
					maxNumCents += 1;
				}
				maxNumCents -= 1;
				result[2][1][i] += maxNumCents;
				userChange -= avaiCent[i]*maxNumCents*0.01;
			}
		}
		if (userChange > 0){
			App.getCash().setAvailable(noteAvai,dollarAvai,centAvai);
			return "";
		}
		//result = {   {{5,0,20,0,0},{1,0,2,0,0}},
		//             {{0,0},{0,0}},
		//             {{0,0,0,0,0,0},{0,0,0,0,0,0}}
		//         }
		String changes = "";
		for (int i = 0;i < result.length;i++){
			int[] element1 = result[i][0];
			int[] element2 = result[i][1];
			if(i == 0){
				changes += "\tNotes: ";
			}
			if(i == 1){
				changes += "\tDollars: ";
			}
			if(i == 2){
				changes += "\tCents: ";
			}
			for(int j = 0;j < element1.length;j++){
				if(element1[j] != 0){
					changes += "$" + String.valueOf(element1[j]) + " x ";
					changes += String.valueOf(element2[j]);
				}
			}
			changes += "\n";
		}
		return changes;
		// for(int i = 0;i < result.length;i++){
		//     for(int j = 0;j < result[i].length;j++){
		//         for(int k = 0;k < result[i][j].length;k++){
		//             System.out.print(result[i][j][k] + " ");
		//         }
		//         System.out.println();
		//     }
		//     System.out.println();
		// }
	}


	public Stage display(){
		//        setElementsPosition();
		Pane root = new Pane(title,returnButton,changeButton,message);
		Scene scene = new Scene(root, 600, 400);
		Successful.setTitle("Payment Successful");
		Successful.setScene(scene);
		return Successful;
	}
}