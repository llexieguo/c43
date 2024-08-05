package cs.toronto.edu;

public class SharedStock {
    private int listID;
    private String listName;
    private String ownerName;

    // Constructor
    public SharedStock(int listID, String listName, String ownerName) {
        this.listID = listID;
        this.listName = listName;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
