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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import cs.toronto.edu.User;
import cs.toronto.edu.Portfolio;
import cs.toronto.edu.StockHolding;
import cs.toronto.edu.User;

public class personinfo {

    Stage stage; //stage on which all is rendered
    Button portfolios, stocklist, frinds, message; //buttons
    Boolean helpToggle = false; //is help on display?

    Connection conn;
    User user;
    public personinfo(Stage stage, Connection conn, User user) {
        this.stage = stage;
        this.conn = conn;
        this.user = user;
    }

    public void initUI() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.BLACK,
                new CornerRadii(0),
                new Insets(0)
        )));


        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(500);
        ColumnConstraints column3 = new ColumnConstraints(400);
        column3.setHgrow(Priority.SOMETIMES);
        column1.setHgrow(Priority.SOMETIMES);

        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow(Priority.SOMETIMES);
        row3.setVgrow(Priority.SOMETIMES);
          
        Label titleLabel = new Label("WELCOME, " + user.getUsername() + "!");
        titleLabel.setFont(new Font("Arial", 60));    
        titleLabel.setTextFill(Color.WHITE); // 设置标题的字体颜色
        titleLabel.setAlignment(Pos.CENTER);
        gridPane.add(titleLabel,1,0,4,1);

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

        portfolios = new Button("portfolios");
        portfolios.setId("portfolios");
        portfolios.setMinWidth(300); // 设置按钮的最小宽度
        portfolios.setMinHeight(70); // 设置按钮的最小高度
        portfolios.setFont(new Font("Arial", 20)); // 设置按钮的字体大小
        portfolios.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10; -fx-border-color: #388E3C; -fx-border-width: 2px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");
        portfolios.setOnAction(
                e -> {
                    portfolieView f = new portfolieView(stage, conn, user);
                    f.show();
                }
        );


        stocklist = new Button("stocklist");
        stocklist.setId("stocklist");
        stocklist.setMinWidth(300); // 设置按钮的最小宽度
        stocklist.setMinHeight(70); // 设置按钮的最小高度
        stocklist.setFont(new Font("Arial", 20)); // 设置按钮的字体大小
        stocklist.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10; -fx-border-color: #388E3C; -fx-border-width: 2px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");
        stocklist.setOnAction(
                e -> {
                    stockView f = new stockView(stage, conn, user);
                    f.show();
                }
        );


        frinds= new Button("frinds");
        frinds.setId("frinds");
        frinds.setMinWidth(300); // 设置按钮的最小宽度
        frinds.setMinHeight(70); // 设置按钮的最小高度
        frinds.setFont(new Font("Arial", 20)); // 设置按钮的字体大小
        frinds.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10; -fx-border-color: #388E3C; -fx-border-width: 2px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");
        frinds.setOnAction(
                e -> {
                    friendsView f = new friendsView(stage, conn, user);
                    f.show();
                }
        );

        message = new Button("analysis");
        message.setId("analysis");
        message.setMinWidth(300); // 设置按钮的最小宽度
        message.setMinHeight(70); // 设置按钮的最小高度
        message.setFont(new Font("Arial", 20)); // 设置按钮的字体大小
        message.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10; -fx-border-color: #388E3C; -fx-border-width: 2px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");
        message.setOnAction(
                e -> {
                    analysisView f = new analysisView(stage, conn, user);
                    f.show();
                }
        );


        gridPane.add(portfolios,1,1);
        gridPane.add(stocklist,2,1);
        gridPane.add(frinds,1,2);
        gridPane.add(message,2,2);

        Scene scene = new Scene(gridPane, 1000, 600);
        scene.setFill(Color.WHITE);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
    }

    public void show() {
        this.stage.show();
    }

}
