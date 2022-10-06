package CHAT.UserChat;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CreateLabel {


    public static Label setTextMessageLabel(String text){
        Label label=new Label(text);
        label.setTextFill(Color.WHITE);
        label.setOnMouseClicked((event -> {
            System.out.println("label is clicked.");
        }));
        label.setFont(new Font("Arial",20));
        label.setBackground(new Background(
                new BackgroundFill(null , CornerRadii.EMPTY, Insets.EMPTY)));
        return label;
    }

    public static Label setGroupMembersLabel(String name){
        Label label=new Label(name);
        label.setLayoutX(303);
        label.setLayoutY(38);
        label.setFont(new Font("Abyssinica SIL",16));
        label.setTextFill(Color.WHITE);
        label.setAlignment(Pos.TOP_RIGHT);
        return label;
    }

    public static Label setGroupLabel(String groupName){

        Label label=new Label(groupName);
        label.setLayoutX(309);
        label.setLayoutY(38);
        label.setFont(new Font("Abyssinica SIL",23));
        label.setTextFill(Color.WHITE);
        label.setAlignment(Pos.CENTER);
        return label;
    }
}
