package cs.toronto.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.stage.Stage;
import javafx.application.Application;
import views.loginview;

public class Main {
    public static void main(String[] args) {
        // 连接数据库
        databaseManager.connect();

        // 启动JavaFX应用
        Application.launch(App.class, args);

        // 关闭数据库连接
        databaseManager.close();
    }
}

