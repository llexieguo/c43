package views;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class loginview{

    Stage stage; //stage on which all is rendered
    Button registerbutton, loginbutton; //buttons
    Boolean helpToggle = false; //is help on display?
    Connection conn;
    GridPane gridPane = new GridPane(); //to hold images and buttons
    TextField inputTextField; //for user input

    public loginview(Stage stage, Connection conn) {
        this.stage = stage;
        this.conn = conn;
         intiUI();
    }

    public void start(Stage stage, Connection conn) {
        new loginview(stage, conn);
    }

    public void intiUI() {

        // setting up the stage
        this.stage.setTitle("Stock Network"); //Replace <YOUR UTORID> with your UtorID

        // GridPane setup
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.BLACK,
                new CornerRadii(0),
                new Insets(0)
        )));

        Label title = new Label("Welcome to Stock Network");
        title.setFont(new Font("Arial", 60)); // 设置标题的字体大小
        title.setTextFill(Color.WHITE); // 设置标题的字体颜色
        title.setAlignment(Pos.CENTER);

        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setValignment(title, VPos.CENTER);
        gridPane.add(title, 0, 0, 3, 1); // 在第一行居中显示标题

        // Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(650);
        ColumnConstraints column3 = new ColumnConstraints(150);
        column3.setHgrow(Priority.SOMETIMES); //let some columns grow to take any extra space
        column1.setHgrow(Priority.SOMETIMES);

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints(550);
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow(Priority.SOMETIMES);
        row3.setVgrow(Priority.SOMETIMES);

        gridPane.getColumnConstraints().addAll(column1, column2, column1);
        gridPane.getRowConstraints().addAll(row1, row2, row1);

        // Buttons
        registerbutton = new Button("register");
        registerbutton.setId("register");
        registerbutton.setMinWidth(200); // 设置按钮的最小宽度
        registerbutton.setMinHeight(50); // 设置按钮的最小高度
        registerbutton.setFont(new Font("Arial", 20)); // 设置按钮的字体大小
        registerbutton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10; -fx-border-color: #388E3C; -fx-border-width: 2px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        loginbutton = new Button("login");
        loginbutton.setId("login");
        loginbutton.setMinWidth(200); // 设置按钮的最小宽度
        loginbutton.setMinHeight(50); // 设置按钮的最小高度
        loginbutton.setFont(new Font("Arial", 20)); // 设置按钮的字体大小
        loginbutton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10; -fx-border-color: #1976D2; -fx-border-width: 2px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        // Create a VBox to hold the buttons and align them vertically
        VBox buttonBox = new VBox(20); // 20 is the spacing between buttons
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(registerbutton, loginbutton);

        // Add the VBox to the GridPane
        gridPane.add(buttonBox, 1, 1); // Add to the center column and center row

        // Center alignment for buttons
        GridPane.setHalignment(buttonBox, HPos.CENTER);
        GridPane.setValignment(buttonBox, VPos.CENTER);

        // Render everything
        var scene = new Scene(gridPane, 1000, 600);
        scene.setFill(Color.BLACK); // 将场景背景颜色设置为白色
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
        loginbutton.setOnAction(e -> {
            loginpage loginPage = new loginpage(stage, conn);
            loginPage.initUI();
            loginPage.show();
        });

        registerbutton.setOnAction(e -> {
            registerpage registerpage = new registerpage(stage, conn);
            registerpage.initUI();
            registerpage.show();
        });       
    }


}
