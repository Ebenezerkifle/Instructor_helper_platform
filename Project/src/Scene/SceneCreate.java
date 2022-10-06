package Scene;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;

public class SceneCreate {
    public static void sceneCreate(ActionEvent event, Scene scene){

        Stage stage;
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        /*
         * Node is controller i.e it can be button label or any other but
         * NB that it is the cause for the even to occur.
         * so we are searching for the scene on which the node was and after the Stage(window).
         * getting the source Stage using the event occurred.
         */
        stage.setScene(scene);
        stage.show();
    }
    public static void sceneCreate(javafx.scene.input.MouseEvent event, Scene scene){

        Stage stage;
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        /*
         * Node is controller i.e it can be button label or any other but
         * NB that it is the cause for the even to occur.
         * so we are searching for the scene on which the node was and after the Stage(window).
         * getting the source Stage using the event occurred.
         */
        stage.setScene(scene);
        stage.show();
    }

}
