package views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import cs.toronto.edu.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class registerpage {

    private Stage stage; //stage on which all is rendered
    private Connection conn;
    public registerpage(Stage stage, Connection conn) {
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



        Label title = new Label("Please enter your user name and password to register");
        title.setFont(new Font("Arial", 20)); // 设置标题的字体大小
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
            
            // 创建 User 对象
                User newUser = new User(username, password);
            
            // 在这里处理新创建的 User 对象
                System.out.println("New User: " + newUser);
                int newID = newUser.getID();
                try{
                     String sqlInsert = "INSERT INTO users (name, password) VALUES (?, ?)";
                     PreparedStatement pstmt = this.conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                     pstmt.setString(1, username);
                     pstmt.setString(2, password);
                     pstmt.executeUpdate();

                     // 获取生成的ID
                     ResultSet rs = pstmt.getGeneratedKeys();
                 if (rs.next()) {
                     int generatedID = rs.getInt(1);
                     newUser.setID(generatedID); // 假设 User 类有 setID 方法
             }

                   System.out.println("New user created successfully with ID: " + newUser.getID());
                  } catch(SQLException e) {
                      e.printStackTrace();
                 }

                personinfo personinfo = new personinfo(stage, conn,newUser);
                personinfo.initUI();
                personinfo.show();
            }
        });
    }

    public void show() {
        this.stage.show();
    }


}
