package VendingMachine;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
public class Cash{
    int[] note;
    int[] nAvailable;

    int[] dollar;
    int[] dAvailable;

    int[] cent;
    int[] cAvailable;

    int[][] allCash;
    int[][] allAvailable;
    //Cash Constructor
    public Cash(){
        this.note = new int[] {5,10,20,50,100};
        this.nAvailable = new int[] {5,5,5,5,5};
        this.dollar = new int[] {1,2};
        this.dAvailable = new int[] {5,5};
        this.cent = new int[] {1,2,5,10,20,50};
        this.cAvailable = new int[] {5,5,5,5,5,5};
        this.allCash = new int[][]{this.note,this.dollar,this.cent};
        this.allAvailable = new int[][]{this.nAvailable,this.dAvailable,this.cAvailable};
    }

    public int[][] getAllCash(){
        return this.allCash;
    }
    public int[][] getAllAvailabel(){
        return this.allAvailable;
    }

    public int[] getNote(){
        return this.note;
    }
    public int[] getnAvailable(){
        return this.nAvailable;
    }
    public void decreaseNAvai(int index){
        this.nAvailable[index] -= 1;
        System.out.println(Arrays.toString(getnAvailable()));
    }
    public void increaseNAvai(int index,int quan){
        this.nAvailable[index] += quan;
        System.out.println(Arrays.toString(getnAvailable()));
    }
    public int[] getDollar(){
        return this.dollar;
    }
    public int[] getdAvailable(){
        return this.dAvailable;
    }
    public void decreaseDAvai(int index){
        this.dAvailable[index] -= 1;
        System.out.println(Arrays.toString(getdAvailable()));
    }
    public void increaseDAvai(int index,int quan){
        this.dAvailable[index] += quan;
        System.out.println(Arrays.toString(getdAvailable()));
    }
    public int[] getCent(){
        return this.cent;
    }
    public int[] getcAvailable(){
        return this.cAvailable;
    }
    public void decreaseCAvai(int index){
        this.cAvailable[index] -= 1;
    }
    public void increaseCAvai(int index,int quan){
        this.cAvailable[index] += quan;
    }
    public void setAvailable(int[] n,int[] d,int[] c){
        this.nAvailable = n;
        this.dAvailable = d;
        this.cAvailable = c;
    }
}
