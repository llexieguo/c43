package views;
import javafx.scene.control.CheckBox;
import java.sql.PreparedStatement;
import javafx.stage.Stage;
import javafx.stage.Modality;
import cs.toronto.edu.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import java.sql.ResultSet;
import java.sql.Statement;
import cs.toronto.edu.User;
import cs.toronto.edu.FriendRequest;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import cs.toronto.edu.User;
import cs.toronto.edu.FriendRequest;
import cs.toronto.edu.StockList;
import cs.toronto.edu.SharedStock;
import cs.toronto.edu.Review;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseButton;
import javafx.scene.control.ListView;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

public class stockView{
    public Stage stage; //stage on which all is rendered
    public Connection conn;
    public String typeview;
    public Button shareButton;
    public Button addButton;
    public User user;
    public GridPane gridPane;
    private TableView<StockList> stockListsTable = new TableView<>();
    private TableView<SharedStock> sharedStocksTable = new TableView<>();
    private ObservableList<StockList> stockListsData = FXCollections.observableArrayList();
    private ObservableList<SharedStock> sharedStocksData = FXCollections.observableArrayList();
 

    public stockView(Stage stage, Connection conn, User user) {
        this.stage = stage;
        this.conn = conn;
        this.user = user;
        initUI();

    }

    public void initUI() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.BLACK,
                new CornerRadii(0),
                new Insets(0)
        )));
        Label title = new Label("Stock List");
        title.setFont(new Font("Arial", 60)); // 设置标题的字体大小
        title.setTextFill(Color.WHITE); // 设置标题的字体颜色
        title.setAlignment(Pos.CENTER);

        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setValignment(title, VPos.CENTER);
        gridPane.add(title, 1, 0, 2, 1); // 在第一行居中显示标题
        title.setMaxWidth(300);

        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(300);
        ColumnConstraints column3 = new ColumnConstraints(50);
        ColumnConstraints column4 = new ColumnConstraints(80);
        ColumnConstraints column5 = new ColumnConstraints(50);


        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow(Priority.SOMETIMES);
        row3.setVgrow(Priority.SOMETIMES);

        Button backButton = new Button("BACK");
        backButton.setMinWidth(20);
        backButton.setMinHeight(5);
        backButton.setFont(new Font("Arial", 10));
        backButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10; -fx-border-color: #1976D2; -fx-border-width: 2px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        // Add action to back button
        backButton.setOnAction(e -> {
           personinfo newview = new personinfo(stage, conn,user);
           newview.initUI();
           newview.show();
        });
        gridPane.add(backButton, 0, 0);

        gridPane.getColumnConstraints().addAll(column1, column2, column1);
        gridPane.getRowConstraints().addAll(row1, row2, row1);


        Button addButton = new Button("ADD");
        addButton.setMinWidth(100);
        addButton.setMinHeight(5);
        addButton.setFont(new Font("Arial", 10));
        addButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10; -fx-border-color: #1976D2; -fx-border-width: 2px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        gridPane.add(addButton, 4, 0);

        addButton.setOnAction(e -> displayAddStockDialog());

        Button shareButton = new Button("SHARE");
        shareButton.setMinWidth(100);
        shareButton.setMinHeight(5);
        shareButton.setFont(new Font("Arial", 10));
        shareButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10; -fx-border-color: #388E3C; -fx-border-width: 2px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        gridPane.add(shareButton, 3, 0);
        shareButton.setOnAction(e -> displayshareStockDialog());

        // 进一步设置子类特定的UI元素
        stockListsTable = new TableView<>();
        sharedStocksTable = new TableView<>();



        // 设置Stock Lists TableView
    // 创建 List ID 列
        TableColumn<StockList, Integer> listIDColumn = new TableColumn<>("List ID");
        listIDColumn.setCellValueFactory(new PropertyValueFactory<>("listID"));
        TableColumn<StockList, String> listNameColumn = new TableColumn<>("List Name");
        listNameColumn.setCellValueFactory(new PropertyValueFactory<>("listName"));
        TableColumn<StockList, Boolean> isPublicColumn = new TableColumn<>("Is Public");
        isPublicColumn.setCellValueFactory(new PropertyValueFactory<>("isPublic"));
        stockListsTable.getColumns().addAll(listIDColumn, listNameColumn, isPublicColumn);


    // 设置Shared Stocks TableView
        TableColumn<SharedStock, Integer> sharelistIDColumn = new TableColumn<>("List ID");
        sharelistIDColumn.setCellValueFactory(new PropertyValueFactory<>("listID"));
        TableColumn<SharedStock, String> sharedListNameColumn = new TableColumn<>("List Name");
        sharedListNameColumn.setCellValueFactory(new PropertyValueFactory<>("listName"));
        TableColumn<SharedStock, String> ownerNameColumn = new TableColumn<>("Owner Name");
        ownerNameColumn.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        sharedStocksTable.getColumns().addAll(sharelistIDColumn, sharedListNameColumn, ownerNameColumn);  // 修正了 listNameColumn 的拼写错误

    // 加载数据
        loadStockListsData();
        loadSharedStocksData();

        stockListsTable.setItems(stockListsData);
        sharedStocksTable.setItems(sharedStocksData);

        stockListsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                StockList selectedStockList = stockListsTable.getSelectionModel().getSelectedItem();
                if (selectedStockList != null) {
                    showStockSymbols(selectedStockList);
                }
            }
        });


        sharedStocksTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                SharedStock selectedStockList = sharedStocksTable.getSelectionModel().getSelectedItem();
                if (selectedStockList != null) {
                    showsharedStockSymbols(selectedStockList);
               }
            }
        });


        // 添加 TableView 到 GridPane 中的特定位置
        GridPane.setConstraints(stockListsTable, 1, 2, 1, 2);
        GridPane.setConstraints(sharedStocksTable, 3, 2, 1, 2);
        gridPane.getChildren().addAll(stockListsTable, sharedStocksTable);


        Scene scene = new Scene(gridPane, 1000, 600);
        scene.setFill(Color.WHITE);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
    }

    public void show() {
        this.stage.show();
    }
   
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // 可选，不显示标题
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void loadStockListsData() {
        stockListsData.clear();
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT sl.ListID, sl.ListName, sl.IsPublic FROM StockLists sl " +
                           "WHERE sl.UserID = " + user.getID();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int listID = rs.getInt("ListID");
                String listName = rs.getString("ListName");
                boolean isPublic = rs.getBoolean("IsPublic");
                stockListsData.add(new StockList(listID, listName, isPublic));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadSharedStocksData() {
        sharedStocksData.clear();
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT sl.ListID, sl.ListName, u.name AS ownerName FROM SharedStockLists ssl " +
                           "JOIN StockLists sl ON ssl.ListID = sl.ListID " +
                           "JOIN Users u ON sl.Userid = u.id " +
                           "WHERE ssl.user_id = " + user.getID();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                sharedStocksData.add(new SharedStock(rs.getInt("ListID"), rs.getString("ListName"), rs.getString("ownerName")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void displayAddStockDialog() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Create New Stock List");
        window.setMinWidth(300);

        Label listNameLabel = new Label("Stock List Name:");
        TextField listNameInput = new TextField();

        Label isPublicLabel = new Label("Is Public:");
        CheckBox isPublicCheckbox = new CheckBox();

        Button createListButton = new Button("Create");
        createListButton.setOnAction(e -> {
            String listName = listNameInput.getText().trim();
            boolean isPublic = isPublicCheckbox.isSelected();
            if (!listName.isEmpty()) {
                createNewStockList(listName, isPublic);
                window.close();
            } else {
                showAlert("Invalid input. Please enter a valid list name.");
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(listNameLabel, listNameInput, isPublicLabel, isPublicCheckbox, createListButton);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private void displayshareStockDialog() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Share Stocklist to Friends");
        window.setMinWidth(300);
     
        // Fetch friends and stocklists from the database
        ObservableList<String> friends = getFriends();
        ObservableList<String> stocklists = getStockLists();

        // ComboBox for selecting friend
        ComboBox<String> friendComboBox = new ComboBox<>(friends);
        friendComboBox.setPromptText("Select Friend");

        // ComboBox for selecting stocklist
        ComboBox<String> stocklistComboBox = new ComboBox<>(stocklists);
        stocklistComboBox.setPromptText("Select Stocklist");

        // Share button
        Button shareButton = new Button("Share");
        shareButton.setOnAction(e -> {
            String selectedFriend = friendComboBox.getValue();
            String selectedStocklist = stocklistComboBox.getValue();
            if (selectedFriend != null && selectedStocklist != null) {
                shareStocklistToFriend(selectedFriend, selectedStocklist);
                window.close();
            } else {
                showAlert("Error", "Please select both a friend and a stocklist.");
            }
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(friendComboBox, stocklistComboBox, shareButton);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private ObservableList<String> getFriends() {
        ObservableList<String> friends = FXCollections.observableArrayList();
        try {
            String query = "SELECT u.id, u.name FROM friends f " +
                           "JOIN users u ON (f.user_id1 = u.id OR f.user_id2 = u.id) " +
                           "WHERE (f.user_id1 = ? OR f.user_id2 = ?) " +
                           "AND u.id != ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, user.getID());
            pstmt.setInt(2, user.getID());
            pstmt.setInt(3, user.getID());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                friends.add(rs.getString("name"));
            }
        } catch (SQLException e) {
             e.printStackTrace();
            showAlert("Error", "Error fetching friends: " + e.getMessage());
        }
        return friends;
    }


    private ObservableList<String> getStockLists() {
        ObservableList<String> stocklists = FXCollections.observableArrayList();
        try {
            String query = "SELECT listname FROM stocklists WHERE userid = ? AND isPublic = true ";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, user.getID());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                stocklists.add(rs.getString("listname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error fetching stocklists: " + e.getMessage());
        }
        return stocklists;
    }

    private void shareStocklistWithFriend(String friendName, String stocklistName) {
       try {
        // 根据好友名字查询好友的 user_id
            String friendQuery = "SELECT id FROM users WHERE name = ?";
            PreparedStatement friendPstmt = conn.prepareStatement(friendQuery);
            friendPstmt.setString(1, friendName);
            ResultSet friendRs = friendPstmt.executeQuery();
            if (friendRs.next()) {
                int friendId = friendRs.getInt("id");

            // 根据 stocklist 名字查询 stocklist 的 id
                 String stocklistQuery = "SELECT listID FROM StockLists WHERE listName = ?";
                 PreparedStatement stocklistPstmt = conn.prepareStatement(stocklistQuery);
                 stocklistPstmt.setString(1, stocklistName);
                 ResultSet stocklistRs = stocklistPstmt.executeQuery();
                 if (stocklistRs.next()) {
                   int stocklistId = stocklistRs.getInt("listID");

                // 插入 sharedstocklists 表
                    String insertQuery = "INSERT INTO sharedstocklists (user_id, listid, owner_id) VALUES (?, ?, ?)";
                    PreparedStatement insertPstmt = conn.prepareStatement(insertQuery);
                    insertPstmt.setInt(1, friendId);
                    insertPstmt.setInt(2, stocklistId);
                    insertPstmt.setInt(3, user.getID());
                    insertPstmt.executeUpdate();
                    showAlert("Success", "Stocklist shared successfully.");
                 }
             }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error sharing stocklist: " + e.getMessage());
        }
    }


    private void shareStocklistToFriend(String friendName, String stocklistName) {
        try {
            // Get friend's ID
            int friendID = getUserIDByName(friendName);

            // Get stocklist ID
            int stocklistID = getStockListIDByName(stocklistName);

            // Insert into sharedstocklists table
            String insertQuery = "INSERT INTO sharedstocklists (user_id, listid, owner_id) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setInt(1, friendID);
            pstmt.setInt(2, stocklistID);
            pstmt.setInt(3, user.getID());
            pstmt.executeUpdate();

            showAlert("Success", "Stocklist shared successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error sharing stocklist: " + e.getMessage());
        }
    }

    private int getUserIDByName(String name) throws SQLException {
        String query = "SELECT id FROM users WHERE name = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        }
        throw new SQLException("User not found");
    }

    private int getStockListIDByName(String name) throws SQLException {
        String query = "SELECT listid FROM stocklists WHERE listname = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("listid");
        }
        throw new SQLException("Stocklist not found");
    }

    private void createNewStockList(String listName, boolean isPublic) {
        try {
            String query = "INSERT INTO StockLists (listName, isPublic, userID) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, listName);
            pstmt.setBoolean(2, isPublic);
            pstmt.setInt(3, user.getID()); // 使用正确的方法来获取用户ID
            pstmt.executeUpdate();
            loadStockListsData(); // 插入后重新加载数据
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error creating new stock list: " + e.getMessage());
        }
    }

    private void addStockToList(String listID, String stockSymbol) {
        try {
            // 首先检查 stockSymbol 是否存在于 Stocks 表中
            String checkQuery = "SELECT COUNT(*) FROM Stocks WHERE symbol = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, stockSymbol);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                // symbol 存在于 Stocks 表中，可以插入到 StockListItems 表
                String query = "INSERT INTO StockListItems (listID, symbol) VALUES (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, listID);
                pstmt.setString(2, stockSymbol);
                pstmt.executeUpdate();
                loadStockListItemsData(); // 插入后重新加载数据
            } else {
                showAlert("The symbol '" + stockSymbol + "' does not exist in the Stocks table.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error adding stock to list: " + e.getMessage());
        }
    }


    private void loadStockListItemsData() {
        // Load or refresh StockListItems data, if applicable
    }

    private void loadSymbols(StockList stockList, ObservableList<String> symbols) {
        symbols.clear();
        try {
            String query = "SELECT symbol FROM StockListItems WHERE listID = " + stockList.getListID();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                symbols.add(rs.getString("symbol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error retrieving symbols: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    

    private void showStockSymbols(StockList stockList) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Stock Symbols in List");
        window.setMinWidth(300);

        Label label = new Label("Symbols in list: " + stockList.getListName());

        ListView<String> listView = new ListView<>();
        ObservableList<String> symbols = FXCollections.observableArrayList();
        listView.setItems(symbols);

        try {
            String query = "SELECT symbol FROM StockListItems WHERE listID = " + stockList.getListID();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                symbols.add(rs.getString("symbol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error retrieving symbols: " + e.getMessage());
        }
        
        CheckBox isPublicCheckbox = new CheckBox("Public");
        isPublicCheckbox.setSelected(stockList.isPublic()); // Assumes StockList has an isPublic() method
        isPublicCheckbox.setOnAction(e -> {
           boolean newIsPublic = isPublicCheckbox.isSelected();
           updateIsPublic(stockList, newIsPublic);
          });
        Button addButton = new Button("Add Symbol");
        addButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Symbol");
            dialog.setHeaderText("Enter Symbol to Add");
            dialog.showAndWait().ifPresent(symbol -> {
                if (!symbol.trim().isEmpty()) {
                    addSymbolToList(stockList, symbol.trim());
                    symbols.add(symbol.trim());
                    loadSymbols(stockList, symbols); 
                }
            });
        });

        Button deleteButton = new Button("Delete Selected Symbol");
        deleteButton.setOnAction(e -> {
            String selectedSymbol = listView.getSelectionModel().getSelectedItem();
            if (selectedSymbol != null) {
                deleteSymbolFromList(stockList, selectedSymbol);
                symbols.remove(selectedSymbol);
            }
        });

       ListView<String> reviewListView = new ListView<>();
       loadReviews(stockList.getListID(), reviewListView);

      // 添加评论的按钮
       Button addReviewButton = new Button("Add Review");
       addReviewButton.setOnAction(e -> addReview(stockList.getListID(), reviewListView));

       // 删除评论的按钮
       Button deleteReviewButton = new Button("Delete Review");
       deleteReviewButton.setOnAction(e -> {
           String selectedReview = reviewListView.getSelectionModel().getSelectedItem();
           if (selectedReview != null) {
                deleteReview(selectedReview, reviewListView);
           }
        });


        VBox leftVBox = new VBox(10, listView, addButton, deleteButton);
        VBox rightVBox = new VBox(10, reviewListView, addReviewButton, deleteReviewButton);

        HBox hBox = new HBox(10, leftVBox, rightVBox);

        VBox layout = new VBox(10, label, isPublicCheckbox, hBox);
        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.setOnHidden(event -> updateMainPage());
        window.show();

    }

    private void showsharedStockSymbols(SharedStock stockList) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Stock Symbols in List");
        window.setMinWidth(300);
        System.out.printf("1");
        Label label = new Label("Symbols in list: " + stockList.getListName());

        ListView<String> listView = new ListView<>();
        ObservableList<String> symbols = FXCollections.observableArrayList();
        listView.setItems(symbols);

        try {
            String query = "SELECT symbol FROM StockListItems WHERE listID = " + stockList.getListID();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                symbols.add(rs.getString("symbol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error retrieving symbols: " + e.getMessage());
        }

       ListView<String> reviewListView = new ListView<>();
       loadReviews(stockList.getListID(), reviewListView);

      // 添加评论的按钮
       Button addReviewButton = new Button("Add Review");
       addReviewButton.setOnAction(e -> addReview(stockList.getListID(), reviewListView));

       // 删除评论的按钮
       Button deleteReviewButton = new Button("Delete Review");
       deleteReviewButton.setOnAction(e -> {
           String selectedReview = reviewListView.getSelectionModel().getSelectedItem();
           if (selectedReview != null) {
                deleteReview(selectedReview, reviewListView);
           }
        });

        VBox leftVBox = new VBox(10, listView);
        VBox rightVBox = new VBox(10, reviewListView, addReviewButton);

        HBox hBox = new HBox(10, leftVBox, rightVBox);

        VBox layout = new VBox(10, label, hBox);
        Scene scene = new Scene(layout);
        window.setOnHidden(event -> updateMainPage());
        window.setScene(scene);
        window.show();
    }

    private void addSymbolToList(StockList stockList, String symbol) {
        try {
            // 首先检查 symbol 是否存在于 StockSymbols 表中
            String checkQuery = "SELECT COUNT(*) FROM StockSymbols WHERE symbol = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, symbol);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                System.out.println("Symbol exists in StockSymbols table, adding to StockListItems.");
                // symbol 存在于 StockSymbols 表中，可以插入到 StockListItems 表
                String query = "INSERT INTO StockListItems (listID, symbol) VALUES (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, stockList.getListID());
                pstmt.setString(2, symbol);
                int rowsInserted = pstmt.executeUpdate();
                System.out.println("Rows inserted: " + rowsInserted);
                loadStockListItemsData(); // 插入后重新加载数据
            } else {
                System.out.println("Symbol does not exist in StockSymbols table.");
                showAlert("The symbol '" + symbol + "' does not exist in the StockSymbols table.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error adding symbol: " + e.getMessage());
        }
    }

    private void deleteSymbolFromList(StockList stockList, String symbol) {
        try {
            String query = "DELETE FROM StockListItems WHERE listID = ? AND symbol = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, stockList.getListID());
            pstmt.setString(2, symbol);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error deleting symbol: " + e.getMessage());
        }
    }

    private void updateIsPublic(StockList stockList, boolean isPublic) {
        try {
            String query = "UPDATE StockLists SET isPublic = ? WHERE listID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, isPublic);
            pstmt.setInt(2, stockList.getListID());
            pstmt.executeUpdate();
            stockList.setPublic(isPublic); // Assuming StockList has setPublic method
            loadStockListItemsData();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error updating public status: " + e.getMessage());
        }
    }
    private void updateMainPage() {
    // 重新加载页面数据并刷新 UI
        loadStockListsData();
        loadSharedStocksData();
    // 其他需要刷新的数据和 UI 元素
    }

   private void loadReviews(int stocklistID, ListView<String> reviewListView) {
        reviewListView.getItems().clear();
        try {
            String query = "SELECT reviewID, reviewText, reviewerName FROM Reviews WHERE stocklistID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, stocklistID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int reviewID = rs.getInt("reviewID");
                String reviewText = rs.getString("reviewText");
                String reviewerName = rs.getString("reviewerName");
                reviewListView.getItems().add("ID: " + reviewID + ", " + reviewerName + ": " + reviewText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error loading reviews: " + e.getMessage());
        }
    }

    private void addReview(int stocklistID, ListView<String> reviewListView) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Review");
        dialog.setHeaderText("Enter your review:");
        dialog.showAndWait().ifPresent(reviewText -> {
            try {
                String query = "INSERT INTO Reviews (stocklistID, userID, reviewText, reviewerName) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, stocklistID);
                pstmt.setInt(2, user.getID());
                pstmt.setString(3, reviewText);
                pstmt.setString(4, user.getName());
                pstmt.executeUpdate();
                loadReviews(stocklistID, reviewListView); // 添加后重新加载评论
            } catch (SQLException e) {
                e.printStackTrace();
               showAlert("Error", "Error adding review: " + e.getMessage());
            }
        });
    }

    private void deleteReview(String selectedReview, ListView<String> reviewListView) {
        try {
            int reviewID = Integer.parseInt(selectedReview.split(",")[0].split(":")[1].trim());
            String query = "DELETE FROM Reviews WHERE reviewID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, reviewID);
            pstmt.executeUpdate();
            reviewListView.getItems().remove(selectedReview);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error deleting review: " + e.getMessage());
        }
    }
  
}

