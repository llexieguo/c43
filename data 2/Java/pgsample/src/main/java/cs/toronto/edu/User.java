package cs.toronto.edu;

public class User {
    private int id;
    private String username;
    private String password;
    private static int idCounter = 0; 

    public User(String username, String password) {
        this.id = generateId();
        this.username = username;
        this.password = password;
    }

    public User(int id, String name, String password) {
        this.id = id;
        this.username = name;
        this.password = password;
    }

    private synchronized int generateId() {
        return idCounter++;
    }

    public int getID() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
