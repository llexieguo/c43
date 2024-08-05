package cs.toronto.edu;

public class Review {
    private int reviewID;
    private int stocklistID;
    private int userID;
    private String reviewText;
    private String reviewerName;

    public Review(int reviewID, int stocklistID, String reviewText, String reviewerName) {
        this.reviewID = reviewID;
        this.stocklistID = stocklistID;
        this.userID = userID;
        this.reviewText = reviewText;
        this.reviewerName = reviewerName;
    }

    // Getters and setters
    public int getReviewID() { return reviewID; }
    public int userID() { return userID; }
    public void setReviewID(int reviewID) { this.reviewID = reviewID; }

    public int getStocklistID() { return stocklistID; }
    public void setStocklistID(int stocklistID) { this.stocklistID = stocklistID; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public String getReviewerName() { return reviewerName; }
    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }
}
