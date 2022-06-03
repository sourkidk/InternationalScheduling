package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 *  This class and method were made to reduce code reuse in switching Scenes.  The original routine
 *  taught in the webinar is viewable in the MainMenuController onActionCreateAnimal event handler.
 */

public class SceneController {

    /**
     * This is the Stage variable used to change scenes.
     * */

    private static Stage stage;

    /**
     * This is the Scene variable used to change scenes.
     * */

    private static Scene scene;


    /**
     *  This method reduces the five lines of code required to switch scenes down to a single line.
     * @param event Any event that can be handled
     * @param sceneFile The url of the scene fxml file.  Can be relative or absolute.
     */
    public static void switchToScene(ActionEvent event, String sceneFile) throws IOException {
        Parent root = FXMLLoader.load(SceneController.class.getResource(sceneFile));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}