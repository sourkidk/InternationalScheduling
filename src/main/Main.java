package main;

import database.JDBC;
import database.Queries;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.sql.SQLException;

/**
 * The type Main.
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
        Queries.select();
        JDBC.closeConnection();
        launch(args);
    }
}
