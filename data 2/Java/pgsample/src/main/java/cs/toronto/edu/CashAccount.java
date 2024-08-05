package cs.toronto.edu;

public class CashAccount {
    private int accountID;
    private int userID;
    private double balance;


    // Constructor
    public CashAccount(int userID, double balance) {
        this.userID = userID;
        this.balance = balance;
    }

    public CashAccount(int accountID, int userID, double balance) {
        this.accountID = accountID;
        this.userID = userID;
        this.balance = balance;
    }

    // Getters and Setters
    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Methods to deposit and withdraw cash
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            return true;
        } else {
            return false;
        }
    }
}

