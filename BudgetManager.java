import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BudgetManager {
    private int bank;
    private int budget;
    private int balance;
    private double fod;
    private double tran;
    private double ent;
    private double util;
    private double oth;    
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public BudgetManager() {
        this.bank = 0;
        this.budget = 0;
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public void viewTransactions() {
        System.out.println("Income:");
        for(Transaction i: transactions) {
            if (i.getType().equals("Income")) {
                System.out.println("Amount: $" + i.getAmount());
            }
        }

        System.out.println("Expenses:");
        for(Transaction i: transactions) {
            if (i.getType().equals("Expense")) {
                System.out.println("Amount: $" + i.getAmount() + " Category: " + i.getCategory());
            }
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
                writer.write(String.format("%s,%.2f,%s\n", transaction.getType(), transaction.getAmount(), transaction.getCategory()));
            }
            System.out.println("Transactions saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
     }

    public String getAvailableFilename(String baseName, String extension) {
        int counter = 1;
        String filename = baseName + "." + extension;
        File file = new File(filename);

        while (file.exists()) {
            filename = baseName + "(" + counter + ")." + extension;
            file = new File(filename);
            counter++;
        }
        return filename;
    }

    public void loadFromFile(String filename) { 
        transactions.clear(); // Clear existing transactions
        this.bank = 0; // Reset bank
        this.budget = 0; // Reset budget
        this.balance = 0; // Reset balance
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String type = parts[0].trim();
                    double amount = Double.parseDouble(parts[1].trim());
                    String category = parts[2].trim();
                    Transaction transaction = new Transaction(type, amount, category);
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

    public void cateSummary() {
        this.fod = 0;
        this.tran = 0;
        this.ent = 0;
        this.util = 0;
        this.oth = 0;
        System.out.println("Category Summary:");
        System.out.println("===================================");
        for (Transaction i: transactions) {
            if (i.getType().equals("Income")) {
                continue; // Skip income transactions
            }
            switch (i.getCategory()) {
                case "FOOD":
                    this.fod += i.getAmount();
                    break;
                case "TRANSPORTATION":
                    this.tran += i.getAmount();
                    break;
                case "ENTERTAINMENT":
                    this.ent += i.getAmount();
                    break;
                case "UTILITIES":
                    this.util += i.getAmount();
                    break;
                case "OTHER":
                    this.oth += i.getAmount();
                    break;
            }
        }
        if (this.fod != 0) {
            System.out.println("Food: $" + this.fod);
        }
        if (this.tran != 0) {
            System.out.println("Transportation: $" + this.tran);
        }
        if (this.ent != 0) {
            System.out.println("Entertainment: $" + this.ent);
        }
        if (this.util != 0) {
            System.out.println("Utilities: $" + this.util);
        }
        if (this.oth != 0) {
            System.out.println("Other: $" + this.oth);
        }
        System.out.println("===================================");
        System.out.println("Total Expenses: $" + getTotalExpenses());
    }

}