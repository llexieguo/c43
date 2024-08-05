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
import java.sql.Connection;
import cs.toronto.edu.User;
 
public abstract class infoview {
    public Stage stage; //stage on which all is rendered
    public Connection conn;
    public String typeview;
    public Button addButton;
    public User user;
    public GridPane gridPane;
    public infoview(Stage stage, Connection conn, User user) {
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

        Label title = new Label(typeview);
        title.setFont(new Font("Arial", 60)); // 设置标题的字体大小
        title.setTextFill(Color.WHITE); // 设置标题的字体颜色
        title.setAlignment(Pos.CENTER);

        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setValignment(title, VPos.CENTER);
        gridPane.add(title, 1, 0, 3, 1); // 在第一行居中显示标题
        title.setMaxWidth(300);

        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(400);
        ColumnConstraints column3 = new ColumnConstraints(50);
        ColumnConstraints column4 = new ColumnConstraints(150);
        ColumnConstraints column5 = new ColumnConstraints(150);


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

        Button deleteButton = new Button("DELETE");
        deleteButton.setMinWidth(100);
        deleteButton.setMinHeight(5);
        deleteButton.setFont(new Font("Arial", 10));
        deleteButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10; -fx-border-color: #1976D2; -fx-border-width: 2px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        addButton.setMaxWidth(Double.MAX_VALUE); // 设置为列的最大宽度
        deleteButton.setMaxWidth(Double.MAX_VALUE);


        gridPane.add(addButton, 4, 0);

        addButton.setOnAction(e -> {
        });

        deleteButton.setOnAction(e -> {
        });

        Scene scene = new Scene(gridPane, 1000, 600);
        scene.setFill(Color.WHITE);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
    }
    public void show() {
        this.stage.show();
    }


}
