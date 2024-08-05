package cs.toronto.edu;

import java.util.List;
import java.util.ArrayList;

public class Portfolio {
    private int portfolioID;
    private int userID;
    private String name;
    private CashAccount cashAccount;
    private List<StockHolding> stockHoldings;

    // Constructor without portfolioID, for creating new portfolios
    public Portfolio(int userID, String name, CashAccount cashAccount) {
        this.userID = userID;
        this.name = name;
        this.cashAccount = cashAccount;
        this.stockHoldings = new ArrayList<>();
    }

    // Constructor with portfolioID, for retrieving existing portfolios
    public Portfolio(int portfolioID, int userID, String name, CashAccount cashAccount) {
        this.portfolioID = portfolioID;
        this.userID = userID;
        this.name = name;
        this.cashAccount = cashAccount;
        this.stockHoldings = new ArrayList<>();
    }

    // Getters and Setters
    public int getPortfolioID() {
        return portfolioID;
    }

    public void setPortfolioID(int portfolioID) {
        this.portfolioID = portfolioID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CashAccount getCashAccount() {
        return cashAccount;
    }

    public void setCashAccount(CashAccount cashAccount) {
        this.cashAccount = cashAccount;
    }

    public double getBalance() {
        return cashAccount.getBalance();
    }


    public void setStockHoldings(List<StockHolding> stockHoldings) {
        this.stockHoldings = stockHoldings;
    }

    // Methods to manage stock holdings

    public void removeStockHolding(StockHolding holding) {
        this.stockHoldings.remove(holding);
    }

    public List<StockHolding> getStockHoldings() {
        return stockHoldings;
    }

    public void addStockHolding(StockHolding holding) {
        this.stockHoldings.add(holding);
    }

    public void updateStockHolding(StockHolding holding) {
        for (int i = 0; i < this.stockHoldings.size(); i++) {
            if (this.stockHoldings.get(i).getSymbol().equals(holding.getSymbol())) {
                this.stockHoldings.set(i, holding);
                break;
            }
        }
    }
}

