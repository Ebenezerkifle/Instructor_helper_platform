package CHAT.OwnerChat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;


public class LabelCreate {


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
    public static TextField createGradeReportVBox(int no, String name, String idNumber
    , VBox no_VBox, VBox nameVBox, VBox idVBox, VBox resultVBox){

        Label no_=new Label(String.valueOf(no)+",");
        no_.setFont(new Font("Abyssinica SIL",22));
        no_.setLayoutX(0);
        no_.setTextFill(Color.WHITE);
        no_VBox.getChildren().add(no_);


        Label nameField=new Label(name);
        nameField.setFont(new Font("Abyssinica SIL",22));
        nameField.setLayoutX(1);
        nameField.setTextFill(Color.WHITE);
        nameVBox.getChildren().add(nameField);


        Label idNumberField=new Label(idNumber);
        idNumberField.setFont(new Font("Abyssinica SIL",22));
        idNumberField.setTextFill(Color.WHITE);
        idVBox.getChildren().add(idNumberField);

        TextField result= new TextField();
        result.setFont(new Font("Abyssinica SIL",15));
        resultVBox.getChildren().add(result);
       return result;
    }

   public static void gradeReport(ArrayList<String> grade,int index,
                                  VBox no_VBox ,VBox nameVBox,VBox idVBox,VBox resultVBox
                                   ,HBox gradeReportHBox){

       Label no_=new Label(String.valueOf(index)+",");
       no_.setFont(new Font("Abyssinica SIL",22));
       no_.setLayoutX(0);
       no_.setTextFill(Color.WHITE);
       no_VBox.getChildren().add(no_);


       Label nameField=new Label(grade.get(0)+" "+grade.get(1));
       nameField.setFont(new Font("Abyssinica SIL",22));
       nameField.setLayoutX(1);
       nameField.setTextFill(Color.WHITE);
       nameVBox.getChildren().add(nameField);


       Label idNumberField=new Label(grade.get(2));
       idNumberField.setFont(new Font("Abyssinica SIL",22));
       idNumberField.setTextFill(Color.WHITE);
       idVBox.getChildren().add(idNumberField);

       HBox hBox=new HBox();
       for (int i = 3; i < grade.size(); i++) {
           String[] result3=grade.get(i).split("\\s");
           Label result1=new Label(result3[1]+" ");
           result1.setFont(new Font("Abyssinica SIL",22));
           result1.setLayoutX(1);
           result1.setTextFill(Color.WHITE);
           hBox.getChildren().add(result1);
       }
       resultVBox.getChildren().add(hBox);
   }
}
