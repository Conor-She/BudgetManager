import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Transaction transaction : transactions) {
                writer.write(String.format("%s,%.2f\n", transaction.getType(), transaction.getAmount()));
            }
            System.out.println("Transactions saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
     }

    public void loadFromFile(String filename) { 
        transactions.clear(); // Clear existing transactions
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String type = parts[0].trim();
                    double amount = Double.parseDouble(parts[1].trim());
                    Transaction transaction = new Transaction(type, amount);
                    transactions.add(transaction);
                }
            }
            System.out.println("Transactions loaded from " + filename);
        }catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing transaction amount: " + e.getMessage());
        }

     }
}