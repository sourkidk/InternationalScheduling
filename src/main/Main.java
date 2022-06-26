package main;

import database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.sql.SQLException;

/**
 * The Main Class.  Initializes the application and prepares Stage and Scene for display.
 * @author Keith Fletcher
 * @version  Scheduling Application 1.0.1
 *
 */
public class Main extends Application {

    /**
     * The constant visitCount.
     */
    public static int visitCount = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"));
        primaryStage.setTitle("International Scheduling");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws SQLException the sql exception
     */
    public static void main(String[] args) throws SQLException {
        JDBC.makeConnection();
        launch(args);
    }
}
