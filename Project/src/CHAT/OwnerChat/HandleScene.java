package CHAT.OwnerChat;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HandleScene {

    //enables controllers.
    public static void enable(Label label){
        label.setDisable(false);
        label.setVisible(true);
    }
    public static void enable(Button button){
        button.setDisable(false);
        button.setVisible(true);
    }
    public static void enable(HBox hBox){
        hBox.setDisable(false);
        hBox.setVisible(true);
    }
    public static void enable(VBox vBox){
        vBox.setDisable(false);
        vBox.setVisible(true);
    }
    public static void enable(Pane pane){
        pane.setDisable(false);
        pane.setVisible(true);
    }
    public static void enable(ScrollPane scrollPane){
        scrollPane.setDisable(false);
        scrollPane.setVisible(true);
    }
    public static void enable(TextField  textField){
        textField.setDisable(false);
        textField.setVisible(true);
    }


    //disable controllers.
    public static void disable(Label label){
        label.setDisable(true);
        label.setVisible(false);
    }
    public static void disable(Button button){
        button.setDisable(true);
        button.setVisible(false);
    }
    public static void disable(HBox hBox){
        hBox.setDisable(true);
        hBox.setVisible(false);
    }
    public static void disable(VBox vBox){
        vBox.setDisable(true);
        vBox.setVisible(false);
    }
    public static void disable(Pane pane){
        pane.setDisable(true);
        pane.setVisible(false);
    }
    public static void disable(ScrollPane scrollPane){
        scrollPane.setDisable(true);
        scrollPane.setVisible(false);
    }
    public static void disable(TextField textField){
        textField.setDisable(true);
        textField.setVisible(false);
    }



    public static void enableAddGroupScene(
            HBox departmentHBox,HBox sectionHBox,
            HBox yearHBox,Button doneButton,
            Button cancelId
    ){
        HandleScene.enable(departmentHBox);
        HandleScene.enable(sectionHBox);
        HandleScene.enable(doneButton);
        HandleScene.enable(cancelId);
        HandleScene.enable(yearHBox);
    }
    public static void disableAddGroupScene(
            HBox departmentHBox,HBox sectionHBox,
            HBox yearHBox,Button doneButton,
            Button cancelId){

        HandleScene.disable(departmentHBox);
        HandleScene.disable(sectionHBox);
        HandleScene.disable(doneButton);
        HandleScene.disable(cancelId);
        HandleScene.disable(yearHBox);
    }
}
