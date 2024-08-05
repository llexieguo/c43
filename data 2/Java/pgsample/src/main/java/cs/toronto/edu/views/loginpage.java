package views;
import cs.toronto.edu.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.Connection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class loginpage {

    private Stage stage; //stage on which all is rendered
    private Connection conn;

    public loginpage(Stage stage, Connection conn) {
        this.stage = stage;
        this.conn = conn;
    }

    public void initUI() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.BLACK,
                new CornerRadii(0),
                new Insets(0)
        )));



        Label title = new Label("Please enter your user name and password to login");
        title.setFont(new Font("Arial", 30)); // 设置标题的字体大小
        title.setTextFill(Color.WHITE); // 设置标题的字体颜色
        title.setAlignment(Pos.CENTER);

        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setValignment(title, VPos.CENTER);
        gridPane.add(title, 1, 0, 4, 1); // 在第一行居中显示标题

        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(300);
        ColumnConstraints column3 = new ColumnConstraints(150);
        column3.setHgrow(Priority.SOMETIMES);
        column1.setHgrow(Priority.SOMETIMES);

        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow(Priority.SOMETIMES);
        row3.setVgrow(Priority.SOMETIMES);

        gridPane.getColumnConstraints().addAll(column1, column2, column1);
        gridPane.getRowConstraints().addAll(row1, row2, row1);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("Arial", 20));
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", 20));
        PasswordField passwordField = new PasswordField();
        usernameLabel.setTextFill(Color.WHITE);
        passwordLabel.setTextFill(Color.WHITE);

        Button backButton = new Button("BACK");
        backButton.setMinWidth(20);
        backButton.setMinHeight(5);
        backButton.setFont(new Font("Arial", 10));
        backButton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10; -fx-border-color: #FF0000; -fx-border-width: 2px;");

        // Add action to back button
        backButton.setOnAction(e -> {
            loginview loginView = new loginview(stage, conn);
        });
        gridPane.add(backButton, 0, 0);

        GridPane.setHalignment(usernameField, HPos.RIGHT); // 用户名标签右对齐
        GridPane.setHalignment(passwordField, HPos.RIGHT);

        // 在第二行左边添加用户名标签和右边添加用户名输入框
        gridPane.add(usernameLabel, 1, 1);
        gridPane.add(usernameField, 2, 1);

// 在第三行左边添加密码标签和右边添加密码输入框
        gridPane.add(passwordLabel, 1, 2);
        gridPane.add(passwordField, 2, 2);

        Scene scene = new Scene(gridPane, 1000, 600);
        scene.setFill(Color.WHITE);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
       
        gridPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
           if (event.getCode() == KeyCode.ENTER) {
                String username = usernameField.getText();
                String password = passwordField.getText();

        // 尝试从数据库中查找用户名和密码
                      try {
                              Statement stmt = this.conn.createStatement();
                              String sqlQuery = "SELECT * FROM users WHERE name = '" + username + "' AND password = '" + password + "';";
                              ResultSet rs = stmt.executeQuery(sqlQuery);

                               if (rs.next()) {
                // 用户存在，跳转到新页面
                                     User existingUser = new User(rs.getInt("ID"), username, password);
                                     System.out.println("User found: " + existingUser);

                                     personinfo personinfo = new personinfo(stage, conn, existingUser);
                                     personinfo.initUI();
                                     personinfo.show();
                          } else {
                // 用户不存在，显示错误信息
                         showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password.");
                // 在这里可以添加代码来显示错误信息，例如弹出对话框或在界面上显示提示
                  }

                  } catch (SQLException e) {
                      e.printStackTrace();
                 }
    }
});

    }

    public void show() {
        this.stage.show();
    }

    public void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // 可选，不需要显示标题
        alert.setContentText(message);
        alert.showAndWait();
    }


}
