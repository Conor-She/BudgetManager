import java.util.ArrayList;

public class BudgetManager {
    private int bank;
    private int budget;
    private int balance;
    
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public BudgetManager() {
        this.bank = 0;
        this.budget = 0;
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public void viewTransactions() {
        for(Transaction i: transactions) {
            System.out.println("Transaction Type: " + i.getType() + "   Transaction Amount: " + i.getAmount());  
        }
    }

    public double getTotalIncome() { 
        for (Transaction i: transactions) {
            if (i.getType().equalsIgnoreCase("income") && !i.isCheck()) {
                i.setCheck(true);
                this.bank += i.getAmount();
            }
        }
        return this.bank;
     }

    public double getTotalExpenses() {
        for (Transaction i: transactions) {
            if (i.getType().equalsIgnoreCase("expense") && !i.isCheck()) {
                i.setCheck(true);
                this.budget += i.getAmount();
            }
        }
        return this.budget;
     }

    public void printSummary() {
        System.out.println("Total Income: $" + getTotalIncome());
        System.out.println("Total Expenses: $" + getTotalExpenses());
        this.balance = this.bank - this.budget;
        System.out.println("Net Balance: $" + this.balance);
    }

    public void saveToFile(String filename) {

     }

    public void loadFromFile(String filename) { 

     }
}