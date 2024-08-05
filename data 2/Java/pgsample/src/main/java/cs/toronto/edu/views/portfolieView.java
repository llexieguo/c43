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

import javafx.util.Pair;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class portfolieView{
    public Stage stage; //stage on which all is rendered
    public Connection conn;
    public String typeview;
    public Button addButton;
    public User user;
    public GridPane gridPane;
    private TableView<Portfolio> portfolioTable = new TableView<>();
    private ObservableList<Portfolio> portfolioData = FXCollections.observableArrayList();

    public portfolieView(Stage stage, Connection conn, User user) {
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
        Label title = new Label("Portfolio");
        title.setFont(new Font("Arial", 60)); // 设置标题的字体大小
        title.setTextFill(Color.WHITE); // 设置标题的字体颜色
        title.setAlignment(Pos.CENTER);

        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setValignment(title, VPos.CENTER);
        gridPane.add(title, 1, 0, 3, 1); // 在第一行居中显示标题
        title.setMaxWidth(300);

        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(500);
        ColumnConstraints column3 = new ColumnConstraints(50);
        ColumnConstraints column4 = new ColumnConstraints(400);
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

        addButton.setMaxWidth(Double.MAX_VALUE); // 设置为列的最大宽度

        gridPane.add(addButton, 4, 0);

//        addButton.setOnAction(e -> displayAddStockDialog());

        addButton.setOnAction(e -> showAddPortfolioDialog());

        TableColumn<Portfolio, Integer> portfolioIDColumn = new TableColumn<>("Portfolio ID");
        portfolioIDColumn.setCellValueFactory(new PropertyValueFactory<>("portfolioID"));

        TableColumn<Portfolio, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Portfolio, Double> balanceColumn = new TableColumn<>("Balance");
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        portfolioTable.getColumns().addAll(portfolioIDColumn, nameColumn, balanceColumn);
        portfolioTable.setItems(portfolioData);

        // 加载数据
        loadPortfolioData();
        gridPane.add(portfolioTable, 1, 1,1,1);

        portfolioTable.setOnMouseClicked(event ->{
            if (event.getClickCount() == 2) {
                Portfolio selectedPortfolio = portfolioTable.getSelectionModel().getSelectedItem();
                if (selectedPortfolio != null) {
                    showPortfolioDetails(selectedPortfolio);
                }
            }
        });


        Scene scene = new Scene(gridPane, 1000, 600);
        scene.setFill(Color.WHITE);
        this.stage.setScene(scene);
        this.stage.setResizable(false);

    }

    public void show() {
        this.stage.show();
    }


    private void loadPortfolioData() {
        portfolioData.clear();
        try {
            String query = "SELECT p.PortfolioID, p.Name, c.Balance, c.AccountID, p.UserID FROM Portfolios p " +
                    "JOIN CashAccounts c ON p.CashAccountID = c.AccountID " +
                    "WHERE p.UserID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, user.getID());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int portfolioID = rs.getInt("PortfolioID");
                String name = rs.getString("Name");
                double balance = rs.getDouble("Balance");
                int accountID = rs.getInt("AccountID");
                int userID = rs.getInt("UserID");
                CashAccount cashAccount = new CashAccount(accountID, userID, balance);
                Portfolio portfolio = new Portfolio(portfolioID, userID, name, cashAccount);
                portfolioData.add(portfolio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error loading portfolio data: ", e.getMessage());
        }
    }


    private void showAddPortfolioDialog() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Portfolio");

        GridPane dialogPane = new GridPane();
        dialogPane.setHgap(10);
        dialogPane.setVgap(10);

        Label nameLabel = new Label("Portfolio Name:");
        TextField nameField = new TextField();

        Button addPortfolioButton = new Button("Add");
        addPortfolioButton.setOnAction(e -> {
            String name = nameField.getText();
            if (!name.trim().isEmpty()) {
                addPortfolio(name);
                dialogStage.close();
            } else {
                showAlert("Portfolio name cannot be empty.", "");
            }
        });

        dialogPane.add(nameLabel, 0, 0);
        dialogPane.add(nameField, 1, 0);
        dialogPane.add(addPortfolioButton, 1, 1);

        Scene dialogScene = new Scene(dialogPane, 300, 200);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }

    private void addPortfolio(String name) {
        try {
            // 插入新的现金账户
            String cashAccountQuery = "INSERT INTO CashAccounts (UserID, Balance) VALUES (?, ?)";
            PreparedStatement cashStmt = conn.prepareStatement(cashAccountQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            cashStmt.setInt(1, user.getID());
            cashStmt.setDouble(2, 0.0); // 初始余额为0
            cashStmt.executeUpdate();

            ResultSet cashRs = cashStmt.getGeneratedKeys();
            if (cashRs.next()) {
                int cashAccountID = cashRs.getInt(1);

                // 插入新的投资组合
                String portfolioQuery = "INSERT INTO Portfolios (UserID, Name, CashAccountID) VALUES (?, ?, ?)";
                PreparedStatement portfolioStmt = conn.prepareStatement(portfolioQuery);
                portfolioStmt.setInt(1, user.getID());
                portfolioStmt.setString(2, name);
                portfolioStmt.setInt(3, cashAccountID);
                portfolioStmt.executeUpdate();

                loadPortfolioData(); // 重新加载数据
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error adding portfolio", "Error adding portfolio: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // 可选，不显示标题
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void showPortfolioDetails(Portfolio portfolio) {
        Stage window = new Stage();
        window.setTitle("Portfolio Details");
        window.setMinWidth(400);
        window.setMinHeight(600);

        Label balanceLabel = new Label("Balance: " + portfolio.getCashAccount().getBalance());
        Button addMoneyButton = new Button("Add Money");
        Button withdrawMoneyButton = new Button("Withdraw Money");

        addMoneyButton.setOnAction(e -> {
// 添加钱的逻辑
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Money");
            dialog.setHeaderText("Enter amount to add:");
            dialog.setContentText("Amount:");

            dialog.showAndWait().ifPresent(amountStr -> {
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount > 0) {
                        String updateQuery = "UPDATE CashAccounts SET Balance = Balance + ? WHERE AccountID = ?";
                        PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                        pstmt.setDouble(1, amount);
                        pstmt.setInt(2, portfolio.getCashAccount().getAccountID());
                        pstmt.executeUpdate();

                        portfolio.getCashAccount().setBalance(portfolio.getCashAccount().getBalance() + amount);
                        balanceLabel.setText("Balance: " + portfolio.getCashAccount().getBalance());
                        loadPortfolioData();
                    } else {
                        showAlert("Error", "Amount must be positive.");
                    }
                } catch (NumberFormatException | SQLException ex) {
                    ex.printStackTrace();
                    showAlert("Error", "Invalid amount: " + ex.getMessage());
                }
            });
        });

        withdrawMoneyButton.setOnAction(e -> {
            // 取钱的逻辑
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Withdraw Money");
            dialog.setHeaderText("Enter amount to withdraw:");
            dialog.setContentText("Amount:");

            dialog.showAndWait().ifPresent(amountStr -> {
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount > 0 && amount <= portfolio.getCashAccount().getBalance()) {
                        String updateQuery = "UPDATE CashAccounts SET Balance = Balance - ? WHERE AccountID = ?";
                        PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                        pstmt.setDouble(1, amount);
                        pstmt.setInt(2, portfolio.getCashAccount().getAccountID());
                        pstmt.executeUpdate();

                        portfolio.getCashAccount().setBalance(portfolio.getCashAccount().getBalance() - amount);
                        balanceLabel.setText("Balance: " + portfolio.getCashAccount().getBalance());
                        loadPortfolioData();
                    } else {
                        showAlert("Error", "Invalid amount.");
                    }
                } catch (NumberFormatException | SQLException ex) {
                    ex.printStackTrace();
                    showAlert("Error", "Invalid amount: " + ex.getMessage());
                }
            });

        });

        ListView<String> List = new ListView<>();
        loadPortfolioStockListsFromDB(portfolio.getPortfolioID(),List);
        ListView<String> stockList = new ListView<>();
        loadStockHoldingsFromDB(portfolio.getPortfolioID(),stockList);


        List.setOnMouseClicked(event -> {
           if (event.getClickCount() == 2) { // 检测双击事件
               String selectedItem = List.getSelectionModel().getSelectedItem();
               if (selectedItem != null) {
            // 提取 ListID
                   String[] parts = selectedItem.split(",");
                   String listIDPart = parts[0].trim(); // "ListID: 1"
                   int listID = Integer.parseInt(listIDPart.split(":")[1].trim());

            // 创建一个新的窗口来展示该列表中的股票
                  Stage stockWindow = new Stage();
                  stockWindow.setTitle("Stocks in List");
                  stockWindow.setMinWidth(300);

                  ListView<String> stockListView = new ListView<>();
                  loadStocksInList(listID, stockListView);

                  VBox layout = new VBox(10);
                  layout.getChildren().addAll(new Label("Stocks in List ID: " + listID), stockListView);

                  Scene scene = new Scene(layout);
                  stockWindow.setScene(scene);
                  stockWindow.show();
              }
            }
        });


        Button addStockListButton = new Button("Add Stock List");
        addStockListButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Stock List");
            dialog.setHeaderText("Enter Stock List Name:");
            dialog.showAndWait().ifPresent(listName -> {
                if (!listName.trim().isEmpty()) {
                    addStockListToPortfolio(portfolio, listName.trim());
                    loadPortfolioStockListsFromDB(portfolio.getPortfolioID(), List);
                }
            });
        });

        Button deleteStockListButton = new Button("Delete Selected Stock List");
        deleteStockListButton.setOnAction(e -> {
            String selectedListName = List.getSelectionModel().getSelectedItem();
            String name = extractListName(selectedListName);
            if (selectedListName != null) {
                int listID = getListIDByName(name);
                deleteStockListFromPortfolio(portfolio, listID);
                loadPortfolioStockListsFromDB(portfolio.getPortfolioID(), List);
            }
        });
        Button addStockButton = new Button("Add Stock");
        addStockButton.setOnAction(e -> {
            Dialog<Pair<String, Integer>> dialog = new Dialog<>();
            dialog.setTitle("Add Stock to Portfolio");
            dialog.setHeaderText("Enter Stock Symbol and Volume");

            ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField symbolField = new TextField();
            symbolField.setPromptText("Symbol");
            TextField volumeField = new TextField();
            volumeField.setPromptText("Volume");

            grid.add(new Label("Symbol:"), 0, 0);
            grid.add(symbolField, 1, 0);
            grid.add(new Label("Volume:"), 0, 1);
            grid.add(volumeField, 1, 1);

            dialog.getDialogPane().setContent(grid);

            Platform.runLater(() -> symbolField.requestFocus());

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {
                    try {
                        int volume = Integer.parseInt(volumeField.getText());
                        return new Pair<>(symbolField.getText(), volume);
                    } catch (NumberFormatException ex) {
                        showAlert("Error", "Volume must be a number.");
                        return null;
                    }
                }
                return null;
            });

            dialog.showAndWait().ifPresent(result -> {
                if (result != null) {
                    String symbol = result.getKey();
                    int volume = result.getValue();
                    addStockToPortfolio(portfolio, symbol, volume, stockList);
                    loadStockHoldingsFromDB(portfolio.getPortfolioID(), stockList); // 更新显示
                }
            });
        });


        Button removeStockButton = new Button("Delete Selected Stock");
        removeStockButton.setOnAction(e -> {
              String selectedItem = stockList.getSelectionModel().getSelectedItem();
              if (selectedItem != null) {
        // 从选中项中提取符号
                   String symbol = selectedItem.substring(selectedItem.indexOf("Symbol: ") + 8, selectedItem.indexOf(",")).trim();
                   System.out.println("Selected symbol: " + symbol);  // 调试输出
                   deleteSymbolFromPortfolio(portfolio, symbol);
                   loadStockHoldingsFromDB(portfolio.getPortfolioID(), stockList);
            }
       });



        VBox layout = new VBox(10);
        Scene scene = new Scene(layout);
        layout.getChildren().addAll(balanceLabel, addMoneyButton, withdrawMoneyButton,stockList, addStockButton, removeStockButton);
        window.setScene(scene);
        window.show();
    }

  

    private void addMoney(Portfolio portfolio, double amount) {
        try {
            String query = "UPDATE CashAccounts SET Balance = Balance + ? WHERE AccountID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, portfolio.getCashAccount().getAccountID());
            pstmt.executeUpdate();
            portfolio.getCashAccount().setBalance(portfolio.getCashAccount().getBalance() + amount);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error adding money: " + e.getMessage());
        }
    }

    private void withdrawMoney(Portfolio portfolio, double amount) {
        try {
            String query = "UPDATE CashAccounts SET Balance = Balance - ? WHERE AccountID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, portfolio.getCashAccount().getAccountID());
            pstmt.executeUpdate();
            portfolio.getCashAccount().setBalance(portfolio.getCashAccount().getBalance() - amount);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error withdrawing money: " + e.getMessage());
        }
    }

    private void removeStockFromPortfolio(Portfolio portfolio, StockHolding holding) {
        try {
            String query = "DELETE FROM StockHoldings WHERE PortfolioID = ? AND Symbol = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, portfolio.getPortfolioID());
            pstmt.setString(2, holding.getSymbol());
            pstmt.executeUpdate();
            portfolio.removeStockHolding(holding);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error removing stock: " + e.getMessage());
        }
    }

    private void loadStockHoldingsFromDB(int portfolioID, ListView<String> stockListView) {
        stockListView.getItems().clear();
        try {
            String query = "SELECT Symbol, Shares FROM StockHoldings WHERE PortfolioID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, portfolioID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String symbol = rs.getString("Symbol");
                int shares = rs.getInt("Shares");
                stockListView.getItems().add("Symbol: " + symbol + ", Shares: " + shares);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error loading stock holdings: " + e.getMessage());
        }
    }

    private void addStockToPortfolio(Portfolio portfolio, String symbol, int volume, ListView<String> stockListView) {
        try {
            // 检查股票符号是否存在
            String checkQuery = "SELECT COUNT(*) FROM Stocks WHERE symbol = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, symbol);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // 股票符号存在，执行插入操作
                String query = "INSERT INTO StockHoldings (PortfolioID, Symbol, Shares) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, portfolio.getPortfolioID());
                pstmt.setString(2, symbol);
                pstmt.setInt(3, volume);
                pstmt.executeUpdate();
                portfolio.addStockHolding(new StockHolding(portfolio.getPortfolioID(), symbol, volume));
                loadStockHoldingsFromDB(portfolio.getPortfolioID(), stockListView);

            } else {
                // 股票符号不存在，显示错误信息
                showAlert("Error", "Stock symbol does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error adding stock: " + e.getMessage());
        }
    }
    private void deleteSymbolFromPortfolio(Portfolio portfolio, String symbol) {
        try {
            // 删除股票记录
            String query = "DELETE FROM StockHoldings WHERE PortfolioID = ? AND Symbol = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, portfolio.getPortfolioID());
            pstmt.setString(2, symbol);
            pstmt.executeUpdate();
            // 从 portfolio 中移除股票持有记录
            StockHolding tempHolding = new StockHolding(portfolio.getPortfolioID(), symbol);
            portfolio.removeStockHolding(tempHolding);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error deleting stock: " + e.getMessage());
        }
    }

   private void loadPortfolioStockListsFromDB(int portfolioID, ListView<String> stockListView) {
      stockListView.getItems().clear();
      try {
          String query = "SELECT sl.ListID, sl.ListName FROM PortfolioStockLists psl " +
                       "JOIN StockLists sl ON psl.stocklistID = sl.listID " +
                       "WHERE psl.portfolioID = ?";
          PreparedStatement pstmt = conn.prepareStatement(query);
          pstmt.setInt(1, portfolioID);
          ResultSet rs = pstmt.executeQuery();

          while (rs.next()) {
              int listID = rs.getInt("ListID");
              String listName = rs.getString("ListName");
              stockListView.getItems().add("ListID: " + listID + ", ListName: " + listName);
          }
       } catch (SQLException e) {
          e.printStackTrace();
          showAlert("Error", "Error loading stock lists: " + e.getMessage());
    }
  }

    private void loadStocksInList(int listID, ListView<String> stockListView) {
        stockListView.getItems().clear();
        try {
            String query = "SELECT sli.symbol FROM StockListItems sli " +
                       "WHERE sli.listid = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, listID);
            ResultSet rs = pstmt.executeQuery();
 
            while (rs.next()) {
                String symbol = rs.getString("symbol");
                stockListView.getItems().add(symbol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error loading stocks: " + e.getMessage());
        }
    }

    private int getListIDByName(String listName) {
        try {
            String query = "SELECT listID FROM StockLists WHERE ListName = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, listName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("listID");  
            } else {
                throw new SQLException("List not found: " + listName);
           }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error fetching list ID: " + e.getMessage());
            return -1;
        }
    }    

    private void addStockListToPortfolio(Portfolio portfolio, String listName) {
        try {
            String query = "SELECT ListID FROM StockLists WHERE ListName = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, listName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int listID = rs.getInt("ListID");

                String insertQuery = "INSERT INTO PortfolioStockLists (portfolioID, stocklistID) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setInt(1, portfolio.getPortfolioID());
                insertStmt.setInt(2, listID);
                insertStmt.executeUpdate();
            } else {
                showAlert("Error", "Stock list not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error adding stock list: " + e.getMessage());
        }
    }

    private void deleteStockListFromPortfolio(Portfolio portfolio, int listID) {
        try {
        // 删除 PortfolioStockLists 表中的记录
            String deletePortfolioListQuery = "DELETE FROM PortfolioStockLists WHERE portfolioID = ? AND stocklistID = ?";
            PreparedStatement deletePortfolioListStmt = conn.prepareStatement(deletePortfolioListQuery) ;
            deletePortfolioListStmt.setInt(1, portfolio.getPortfolioID());
            deletePortfolioListStmt.setInt(2, listID);
            int rowsAffected = deletePortfolioListStmt.executeUpdate();

        // 检查是否成功删除
            if (rowsAffected == 0) {
                showAlert("Error", "No such stock list found in the portfolio.");
                return;
            }

        // 更新视图
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error deleting stock list: " + e.getMessage());
       } 
   } 

    private String extractListName(String errorMessage) {
        String pattern = "ListName: (.+)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(errorMessage);
        if (m.find()) {
            return m.group(1);
        } else {
            return "unknown";
        }
}

}
