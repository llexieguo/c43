package cs.toronto.edu;


public class StockList {
    private static int nextID = 1;
    private int listID;
    private String listName;
    private boolean isPublic;
    private String ownerName;
    // Constructor
    public StockList(int listID, String listName, boolean isPublic) {
        this.listID = listID;
        this.listName = listName;
        this.isPublic = isPublic;
        this.ownerName = "Mine";
    }

    public StockList(String listName, boolean isPublic) {
        this.listID = nextID++;
        this.listName = listName;
        this.isPublic = isPublic;
        this.ownerName = "Mine";
    }

    // New constructor with ownerName
    public StockList(int listID, String listName, boolean isPublic, String ownerName) {
        this.listID = listID;
        this.listName = listName;
        this.isPublic = isPublic;
        this.ownerName = ownerName;
    }

    // Getters and Setters
    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getIsPublic() {  // Getter 方法
        return isPublic;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

}

