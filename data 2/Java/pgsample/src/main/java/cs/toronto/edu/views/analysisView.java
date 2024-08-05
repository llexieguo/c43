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
import cs.toronto.edu.StockHolding;
import cs.toronto.edu.CashAccount;
import cs.toronto.edu.Portfolio;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseButton;
import javafx.scene.control.ListView;
import javafx.scene.control.ComboBox;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class analysisView{
    public Stage stage; //stage on which all is rendered
    public Connection conn;
    public String typeview;
    public Button addButton;
    public User user;
    public GridPane gridPane;
    private TableView<StockList> stockTable = new TableView<>();
    private ObservableList<StockList> stockData = FXCollections.observableArrayList(); 

    public analysisView(Stage stage, Connection conn, User user) {
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
        Label title = new Label("Analysis");
        title.setFont(new Font("Arial", 60)); // 设置标题的字体大小
        title.setTextFill(Color.WHITE); // 设置标题的字体颜色
        title.setAlignment(Pos.CENTER);

        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setValignment(title, VPos.CENTER);
        gridPane.add(title, 1, 0, 3, 1); // 在第一行居中显示标题
        title.setMaxWidth(300);

        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(300);
        ColumnConstraints column3 = new ColumnConstraints(50);
        ColumnConstraints column4 = new ColumnConstraints(50);
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


        Button addButton = new Button("Predict");
        addButton.setMinWidth(100);
        addButton.setMinHeight(5);
        addButton.setFont(new Font("Arial", 10));
        addButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10; -fx-border-color: #1976D2; -fx-border-width: 2px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        addButton.setMaxWidth(Double.MAX_VALUE); // 设置为列的最大宽度

        gridPane.add(addButton, 3, 0);

        Button hisButton = new Button("Historical");
        hisButton.setMinWidth(100);
        hisButton.setMinHeight(5);
        hisButton.setFont(new Font("Arial", 10));
        hisButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10; -fx-border-color: #388E3C; -fx-border-width: 2px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        hisButton.setMaxWidth(Double.MAX_VALUE); // 设置为列的最大宽度

        gridPane.add(hisButton, 4, 0);

// 设置Shared Stocks TableView
        TableColumn<StockList, Integer> sharelistIDColumn = new TableColumn<>("List ID");
        sharelistIDColumn.setCellValueFactory(new PropertyValueFactory<>("listID"));
        TableColumn<StockList, String> sharedListNameColumn = new TableColumn<>("List Name");
        sharedListNameColumn.setCellValueFactory(new PropertyValueFactory<>("listName"));
        TableColumn<StockList, String> ownerNameColumn = new TableColumn<>("Owner Name");
        ownerNameColumn.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        stockTable.getColumns().addAll(sharelistIDColumn, sharedListNameColumn, ownerNameColumn);


        loadStockListsData();
        stockTable.setItems(stockData);

//        gridPane.add(stockTable, 1,1,3,1);

        stockTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                StockList selectedStockList = stockTable.getSelectionModel().getSelectedItem();
                if (selectedStockList != null) {
                    showStockSymbols(selectedStockList);
                }
            }
        });

        ComboBox<String> viewTypeComboBox = new ComboBox<>();
        viewTypeComboBox.getItems().addAll("portfolio", "stock");
        viewTypeComboBox.setPromptText("Select Type");

        // 将下拉框添加到gridPane
        gridPane.add(viewTypeComboBox, 0, 1);

        // 添加时间戳下拉框
        ComboBox<String> timestampComboBox = new ComboBox<>();
        timestampComboBox.setItems(getTimestamps());
        timestampComboBox.setPromptText("Select Timestamp");
        gridPane.add(timestampComboBox, 2, 1);

        ComboBox<String> thirdComboBox = new ComboBox<>();
        thirdComboBox.setPromptText("Select Detail");
        viewTypeComboBox.setOnAction(e -> updateThirdComboBox(viewTypeComboBox.getValue(), thirdComboBox));
        gridPane.add(thirdComboBox,1, 1);

        // 创建时间间隔下拉框
        ComboBox<String> intervalComboBox = new ComboBox<>();
        intervalComboBox.getItems().addAll("a week", "a month", "a quarter", "a year", "five years");
        intervalComboBox.setPromptText("Select Interval");
        gridPane.add(intervalComboBox, 3, 1);

        Scene scene = new Scene(gridPane, 1000, 600);
        scene.setFill(Color.WHITE);
        this.stage.setScene(scene);
        this.stage.setResizable(false);

    }

    public void show() {
        this.stage.show();
    }
   
   
    private void loadStockListsData() {
        stockData.clear();
        try {
            // 加载用户自己的股票列表
            String query = "SELECT sl.ListID, sl.ListName, sl.IsPublic FROM StockLists sl " +
                    "WHERE sl.UserID = " + user.getID();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int listID = rs.getInt("ListID");
                String listName = rs.getString("ListName");
                boolean isPublic = rs.getBoolean("IsPublic");
                stockData.add(new StockList(listID, listName, isPublic, "Mine"));
            }

            // 加载分享给用户的股票列表
            query = "SELECT sl.ListID, sl.ListName, sl.IsPublic, u.name AS ownerName FROM SharedStockLists ssl " +
                    "JOIN StockLists sl ON ssl.ListID = sl.ListID " +
                    "JOIN Users u ON sl.UserID = u.id " +
                    "WHERE ssl.user_id = " + user.getID();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                int listID = rs.getInt("ListID");
                String listName = rs.getString("ListName");
                boolean isPublic = rs.getBoolean("IsPublic");
                String ownerName = rs.getString("ownerName");
                stockData.add(new StockList(listID, listName, isPublic, ownerName));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // 可选，不显示标题
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showStockSymbols(StockList stockList) {
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
            showAlert("Error retrieving symbols: ", e.getMessage());
        }

        ListView<String> reviewListView = new ListView<>();
        loadReviews(stockList.getListID(), reviewListView);
        // 添加评论的按钮
        Button addReviewButton = new Button("Add Review");
        addReviewButton.setOnAction(e -> addReview(stockList.getListID(), reviewListView));

        // 删除评论的按钮

        VBox leftVBox = new VBox(10, listView);
        VBox rightVBox = new VBox(10, reviewListView, addReviewButton);

        HBox hBox = new HBox(10, leftVBox, rightVBox);

        VBox layout = new VBox(10, label, hBox);
        Scene scene = new Scene(layout);

        window.setScene(scene);
	        window.show();
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

    private ObservableList<String> getTimestamps() {
        List<String> timestamps = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT timestamp FROM stock_timestamps");
            while (rs.next()) {
                timestamps.add(rs.getString("timestamp"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(timestamps);
    }

    private void updateThirdComboBox(String viewType, ComboBox<String> thirdComboBox) {
        if (viewType.equals("portfolio")) {
            thirdComboBox.setItems(getPortfolios(user.getId()));
            thirdComboBox.setPromptText("Select Portfolio");
        } else if (viewType.equals("stock")) {
            thirdComboBox.setItems(getStockSymbols());
            thirdComboBox.setPromptText("Select Symbol");
        }else {
            thirdComboBox.getItems().clear();
            thirdComboBox.setPromptText("N/A");
        }
    }

    private ObservableList<String> getPortfolios(int userId) {
        List<String> portfolios = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT p.portfolioid, p.userid, p.name, c.accountid, c.balance " +
                           "FROM portfolios p " +
                           "JOIN cashaccounts c ON p.cashaccountid = c.accountid " +
                           "WHERE p.userid = " + userId;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int portfolioID = rs.getInt("portfolioid");
                int userID = rs.getInt("userid");
                String name = rs.getString("name");
                int accountID = rs.getInt("accountid");
                double balance = rs.getDouble("balance");
                CashAccount cashAccount = new CashAccount(accountID, userID, balance);
                portfolios.add(portfolioID + " - " + name + " (Balance: " + balance + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(portfolios);
    }

    private ObservableList<String> getStockSymbols() {
        List<String> symbols = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT symbol FROM stocksymbols");
            while (rs.next()) {
                symbols.add(rs.getString("symbol"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(symbols);
    }

}
