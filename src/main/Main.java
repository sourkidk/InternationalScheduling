package main;

import database.JDBC;
import database.Queries;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.sql.SQLException;

public class Main extends Application {

    public static int visitCount = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"));
        primaryStage.setTitle("International Scheduling");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        JDBC.makeConnection();
        Queries.select();
        JDBC.closeConnection();
        launch(args);
    }
}
