package cs.toronto.edu;

public class StockHolding {
    private int portfolioID;
    private String symbol;
    private int shares;

    // Constructor
    public StockHolding(int portfolioID, String symbol, int shares) {
        this.portfolioID = portfolioID;
        this.symbol = symbol;
        this.shares = shares;
    }

    public StockHolding(int portfolioID, String symbol) {
        this.portfolioID = portfolioID;
        this.symbol = symbol;
        this.shares = 0; // 默认的 shares 可以设置为 0 或其他适当的值
    }

    // Getters and Setters
    public int getPortfolioID() {
        return portfolioID;
    }

    public void setPortfolioID(int portfolioID) {
        this.portfolioID = portfolioID;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }
}
;
