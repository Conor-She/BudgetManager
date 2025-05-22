import java.util.Scanner;

public class Main {

    enum Category {
        FOOD,
        TRANSPORT,
        ENTERTAINMENT,
        UTILITIES,
        OTHER
    }

    private static void Menu() {
        System.out.println("=== Budget Manager ===");
        System.out.println("1. Add Transaction");
        System.out.println("2. View Transactions");
        System.out.println("3. Show Balance");
        System.out.println("4. Categorical Summary");
        System.out.println("5. Save");
        System.out.println("6. Load");
        System.out.println("7. Exit");
        System.out.println("=======================");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BudgetManager budgetManager = new BudgetManager();
        String[] parts;

        while (true) {           

            Menu();
            System.out.print("Choose an option: ");
            int choice2 = scanner.nextInt();

            switch (choice2) {
                case 1:
                    System.out.println("");

                    System.out.print("Enter transaction amount: ");
                    while (!scanner.hasNextDouble()) {
                        System.out.print("Invalid input. Please enter a number: ");
                        scanner.next(); // clear invalid input
                    }
                    double amount = scanner.nextDouble();
                    while (amount <= 0) {
                        System.out.print("Invalid amount. Please enter a positive number: ");
                        while (!scanner.hasNextDouble()) {
                            System.out.print("Invalid input. Please enter a number: ");
                            scanner.next();
                        }
                        amount = scanner.nextDouble();
                    }


                    System.out.print("Enter transaction type (income/expense): ");
                    String type = scanner.next();
                    while (!("income".contains(type)) && !("expense".contains(type))) {
                        System.out.print("Invalid type. Please enter 'income' or 'expense': ");
                        type = scanner.next();
                    }
                    if ("income".contains(type) && !(type.equalsIgnoreCase("e"))) {
                        type = "Income";
                    }
                    if  ("expense".contains(type)) {
                        type = "Expense";
                    }

                    if (type.equals("Expense")) {

                        System.out.print("Enter transaction category: ");
                        String cat = scanner.next();
                        switch(cat.toUpperCase()) {
                            case "FOOD", "TRANSPORT", "ENTERTAINMENT", "UTILITIES":
                                cat = cat.toUpperCase();
                                break;
                            default:
                                System.out.println("Not a category. Defaulting to OTHER.");
                                cat = "OTHER";
                                break;
                        }
                        Transaction transaction = new Transaction(type, amount, cat);
                        budgetManager.addTransaction(transaction);
                    }
                    else {
                        Transaction transaction = new Transaction(type, amount);
                        budgetManager.addTransaction(transaction);
                    }


                    System.out.println("");
                    break;

                case 2:
                    System.out.println("");

                    budgetManager.viewTransactions();

                    System.out.println("");
                    break;
                case 3:
                    System.out.println("");

                    System.out.println("Summary of Transactions:");
                    System.out.println("===================================");
                    budgetManager.printSummary();

                    System.out.println("");
                    break;
                case 4:
                    System.out.println("");
                    budgetManager.cateSummary();
                    System.out.println("");
                    break;
                case 5:
                    System.out.println("");

                    System.out.println("Enter a filename for the saved transactions: ");

                    String filenameS = scanner.next();
                    filenameS = budgetManager.getAvailableFilename(filenameS, "csv");

                    budgetManager.saveToFile(filenameS);

                    System.out.println("Transactions saved to " + filenameS);
                    System.out.println("You can open the file in a spreadsheet program like Excel or Google Sheets.");
                    
                    System.out.println("");
                    break;
                case 6:
                    System.out.println("");

                    System.out.println("Enter a filename to load transactions from: ");
                   
                    String filenameL = scanner.next();
                    filenameL = filenameL + ".csv";

                    budgetManager.loadFromFile(filenameL);
                   
                    System.out.println("");
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}