package views;

import javafx.stage.Stage;
import java.sql.Connection;
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
import java.sql.Connection;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseButton;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.Duration;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class friendsView{
    public Stage stage; //stage on which all is rendered
    public Connection conn;
    public String typeview;
    public Button addButton;
    public User user;
    public GridPane gridPane;
    private TableView<User> friendsTable = new TableView<>();
    private TableView<FriendRequest> requestsTable = new TableView<>();
    private ObservableList<User> friendsData = FXCollections.observableArrayList();
    private ObservableList<FriendRequest> requestsData = FXCollections.observableArrayList();


    public friendsView(Stage stage, Connection conn, User user) {
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
        Label title = new Label("Friends");
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

        addButton.setMaxWidth(100); // 设置为列的最大宽度

        gridPane.add(addButton, 4, 0);

        addButton.setOnAction(e -> showAddFriendDialog());


        // 进一步设置子类特定的UI元素
        friendsTable = new TableView<>();
        requestsTable = new TableView<>();

        // 设置Friends TableView
        TableColumn<User, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        friendsTable.getColumns().addAll(idColumn, nameColumn);

        // 设置Friend Requests TableView
        TableColumn<FriendRequest, Integer> requestIdColumn = new TableColumn<>("Request ID");
        requestIdColumn.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        TableColumn<FriendRequest, String> senderNameColumn = new TableColumn<>("Sender");
        senderNameColumn.setCellValueFactory(new PropertyValueFactory<>("senderName"));
        TableColumn<FriendRequest, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        TableColumn<FriendRequest, Timestamp> requestTimeColumn = new TableColumn<>("Request Time");
        requestTimeColumn.setCellValueFactory(new PropertyValueFactory<>("requestTime"));

        requestsTable.getColumns().addAll(requestIdColumn, senderNameColumn, statusColumn, requestTimeColumn);
        // 加载数据
        loadFriendsData();
        loadFriendRequestsData();
        
        requestsTable.setItems(requestsData);
        friendsTable.setItems(friendsData); 
        // 添加 TableView 到 GridPane 中的特定位置
        GridPane.setConstraints(friendsTable, 1, 2, 1, 2);
        GridPane.setConstraints(requestsTable, 3, 2, 2, 2);
        gridPane.getChildren().addAll(friendsTable, requestsTable);

        requestsTable.setRowFactory(tv -> {
          TableRow<FriendRequest> row = new TableRow<>();
          row.setOnMouseClicked(event -> {
          if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                FriendRequest clickedRequest = row.getItem();
                showRequestActionDialog(clickedRequest);
            }
          });
           return row;
        });
    
        Scene scene = new Scene(gridPane, 1000, 600);
        scene.setFill(Color.WHITE);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
    }

    public void show() {
        this.stage.show();
    }
   
    private void showAddFriendDialog() {
    // 创建一个新对话框
       Dialog<String> dialog = new Dialog<>();
       dialog.setTitle("Add Friend");
       dialog.setHeaderText("Send Friend Request");

    // 设置按钮类型
       ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
       dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

    // 创建用户名输入字段
       TextField nameField = new TextField();
       nameField.setPromptText("Friend's name");

    // 将输入字段添加到对话框中
       GridPane grid = new GridPane();
       grid.add(new Label("Name:"), 0, 0);
       grid.add(nameField, 1, 0);
       dialog.getDialogPane().setContent(grid);

    // 请求焦点在输入字段
       Platform.runLater(() -> nameField.requestFocus());

    // 处理对话框的结果
       dialog.setResultConverter(dialogButton -> {
           if (dialogButton == addButtonType) {
              return nameField.getText();
        }
           return null;
    });

       dialog.showAndWait().ifPresent(name -> {
          sendFriendRequest(name);
    });
   }


    private void sendFriendRequest(String friendName) {
        try {
        // 查找 friendName 对应的用户 ID
               Statement stmt = conn.createStatement();
               String findUserQuery = "SELECT ID FROM users WHERE name = '" + friendName + "'";
               ResultSet rs = stmt.executeQuery(findUserQuery);

               if (rs.next()) {
                   int friendId = rs.getInt("ID");

            // 检查是否已经是好友
                   String checkFriendQuery = "SELECT 1 FROM friends WHERE (user_id1 = ? AND user_id2 = ?) OR (user_id1 = ? AND user_id2 = ?)";
                   PreparedStatement checkFriendStmt = conn.prepareStatement(checkFriendQuery);
                   checkFriendStmt.setInt(1, user.getID());
                   checkFriendStmt.setInt(2, friendId);
                   checkFriendStmt.setInt(3, friendId);
                   checkFriendStmt.setInt(4, user.getID());
                   ResultSet checkFriendRs = checkFriendStmt.executeQuery();

                   if (checkFriendRs.next()) {
                       showAlert("Request Blocked", "You are already friends with this user.");
                       return;
                  }

            // 检查是否有五分钟的限制
                   String checkRequestQuery = "SELECT request_time FROM friend_requests WHERE sender_id = ? AND receiver_id = ? AND status = 'rejected'";
                   PreparedStatement checkStmt = conn.prepareStatement(checkRequestQuery);
                   checkStmt.setInt(1, user.getID());
                   checkStmt.setInt(2, friendId);
                   ResultSet checkRs = checkStmt.executeQuery();

                   if (checkRs.next()) {
                        Timestamp lastRequestTime = checkRs.getTimestamp("request_time");
                        Instant now = Instant.now();
                        Instant lastRequestInstant = lastRequestTime.toInstant();
                        Duration duration = Duration.between(lastRequestInstant, now);

                     if (duration.toMinutes() < 5) {
                          showAlert("Request Blocked", "You cannot resend a request to this user within 5 minutes of rejection.");
                         return;
                      }
                  }

            // 插入好友请求
                String insertRequestQuery = "INSERT INTO friend_requests (sender_id, receiver_id, status, request_time) VALUES (?, ?, 'pending', ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertRequestQuery);
                insertStmt.setInt(1, user.getID());
                insertStmt.setInt(2, friendId);
                insertStmt.setTimestamp(3, Timestamp.from(Instant.now()));
                insertStmt.executeUpdate();
   
                showAlert("Friend Request Sent", "Friend request sent to " + friendName);
              } else {
            // 用户不存在
                showAlert("User Not Found", "The user " + friendName + " does not exist.");
            }

        } catch (Exception e) {
             e.printStackTrace();
            showAlert("Error", "An error occurred while sending the friend request.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // 可选，不显示标题
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void loadFriendsData() {
        friendsData.clear();
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT u.id, u.name FROM friends f " +
                       "JOIN users u ON (f.user_id1 = u.id OR f.user_id2 = u.id) " +
                       "WHERE (f.user_id1 = " + user.getID() + 
                       " OR f.user_id2 = " + user.getID() + ") " +
                       "AND u.id != " + user.getID();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                friendsData.add(new User(rs.getInt("ID"), rs.getString("name"), ""));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadFriendRequestsData() {
        requestsData.clear();
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT fr.id AS requestId,fr.receiver_id as rid, u.name AS senderName, fr.status, fr.request_time " +
                       "FROM friend_requests fr " +
                       "JOIN users u ON (fr.sender_id = u.ID) " +
                       "WHERE fr.receiver_id = " + user.getID() + 
                       " AND (fr.status = 'pending' OR fr.status = 'rejected')"; 
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                requestsData.add(new FriendRequest(rs.getInt("rid"), rs.getString("senderName"),rs.getString("status"),rs.getTimestamp("request_time")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 显示请求操作对话框
    private void showRequestActionDialog(FriendRequest request) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Friend Request");
        alert.setHeaderText("Friend Request from " + request.getSenderName());
        alert.setContentText("Would you like to accept or reject this friend request?");

        ButtonType acceptButton = new ButtonType("Accept");
        ButtonType rejectButton = new ButtonType("Reject");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(acceptButton, rejectButton, cancelButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == acceptButton) {
                updateRequestStatus(request, "accepted");
            } else if (type == rejectButton) {
                updateRequestStatus(request, "rejected");
            }
        });
    }

    // 更新请求状态
    private void updateRequestStatus(FriendRequest request, String status) {
        try {
            Statement stmt = conn.createStatement();
            int friendId = -1;
            String findUserQuery = "SELECT ID FROM users WHERE name = '" + request.getSenderName() + "'";
            // 更新 friend_requests 表中的状
             ResultSet rs = stmt.executeQuery(findUserQuery);
             if (rs.next()) {
                 friendId = rs.getInt("ID");
            }

            String updateQuery = "UPDATE friend_requests SET status = '" + status + "' WHERE (sender_id = " + friendId + " AND receiver_id = " + user.getID() + ")";
            stmt.executeUpdate(updateQuery);


            if (status.equals("accepted")) {
                // 如果请求被接受，添加到 friends 表中
                String addFriendQuery = "INSERT INTO friends (user_id1, user_id2) VALUES (" + user.getID() + ", " + friendId + ")";
                stmt.executeUpdate(addFriendQuery);

                // 添加到 friendsData 以显示在 friendsTable 中
                friendsData.add(new User(request.getRequestId(), request.getSenderName(), "")); // 假设 User 类有合适的构造函数
            }

            // 重新加载请求数据和好友数据
            loadFriendRequestsData();
            loadFriendsData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
