public class Transaction {
    private String type; // "income" or "expense"
    //private String description;
    private double amount;
    private boolean check;
    //private String category;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        System.out.println("Transaction created: " + type + " of amount $" + amount);
        this.check = false;
    }

    public String getType() {
        return this.type;
    }

    public double getAmount() {
        return this.amount;
    }

    public boolean isCheck() {
        return this.check;
    }

    public void setCheck(boolean check) {
        this.check = true;
    }


}