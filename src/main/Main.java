package main;

import database.JDBC;
import database.NewQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"));
        primaryStage.setTitle("International Scheduling");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        JDBC.makeConnection();

//        int rowsAffected = NewQuery.update(4, "Keith Fletcher");
//
//        if (rowsAffected > 0) {
//            System.out.println("Update Successful! " + rowsAffected + " rows affected!");
//        }


//        int rowsAffected = NewQuery.insert("Keith Fletcher", "sourkidk@gmail.com");
//
//        if (rowsAffected > 0) {
//            System.out.println("Update Successful! " + rowsAffected + " rows affected!");
//        }
//        else {
//            System.out.println("Update Failed...");
//        }


//        int rowsAffected = NewQuery.delete(4);
//
//        if (rowsAffected > 0) {
//            System.out.println("Delete Successful! " + rowsAffected + " rows affected!");
//        }
//        else {
//            System.out.println("Delete Failed...");
//        }

//        NewQuery.select(3);



        JDBC.closeConnection();
        launch(args);
    }
}
