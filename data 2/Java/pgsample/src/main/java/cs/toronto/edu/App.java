package cs.toronto.edu;
import java.sql.Connection;
import views.loginview;


import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public App() {
        // 无参构造函数
    }
    @Override
    public void start(Stage primaryStage) {
        Connection conn = databaseManager.getConnection();
        loginview loginView = new loginview(primaryStage, conn);
    }


}

