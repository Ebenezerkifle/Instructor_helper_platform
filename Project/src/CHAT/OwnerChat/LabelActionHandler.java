package CHAT.OwnerChat;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LabelActionHandler {


    private ArrayList<String> idNumberList;

    public static void handleGroupMembersEvent(MouseEvent event,Text headerText, String name,
                                               VBox chatBox, Button optionButton,
                                               MessageHandler messageHandler,
                                               HBox textWriteHBox){

        Label label =(Label)event.getSource();
        if(!headerText.getText().equals(label.getText())){
            headerText.setText(label.getText());
            chatBox.getChildren().clear();
            optionButton.setDisable(false);
            headerText.setDisable(false);

            String firstArrayListName=StringHandler.nameAfterIgnoringSpace(name);
            ArrayList<Label> arrayList = messageHandler.getMessageList(firstArrayListName);
            for(int i=1;i<arrayList.size();i++) {
                chatBox.getChildren().add(arrayList.get(i));
            }
            HandleScene.enable(textWriteHBox);
        }
    }
    public static void handleGroupsEvent(MouseEvent event ,Text headerTextId,
                                          VBox vBoxId,String groupName,
                                          MessageHandler messageHandler) {

       Label source = (Label) event.getSource();

        if(!source.getText().equals(headerTextId.getText())) {
            headerTextId.setText(groupName);
            headerTextId.setDisable(false);
            LoadTextMessages(vBoxId,groupName,messageHandler);
       }

    }
    public static void LoadTextMessages(VBox vBoxId,String groupName,MessageHandler messageHandler){
        vBoxId.getChildren().clear();
        if (vBoxId.getChildren().isEmpty()) {
            String firstArrayListName=StringHandler.nameAfterIgnoringSpace(groupName);
            ArrayList<Label> arrayList=messageHandler.getMessageList(firstArrayListName);
            for(int i=1;i<arrayList.size();i++) {
                // System.out.println(arrayList.get(i));
                vBoxId.getChildren().add(arrayList.get(i));
            }
        }
    }

    public ArrayList<String> getIdNumberList() {
        return idNumberList;
    }

    public  void handleGradeReportEvent(
            MouseEvent Mouseevent , Pane paneOverlapped, VBox vBoxId,
            MessageHandler messageHandler, String group
            , VBox no_VBox, VBox nameVBox, VBox idVBox, VBox resultVBox
           , Text no_textId, Text nameTextId, Text idNumberTextId, HBox resultHBoxId,
            Button submitButtonId, ObjectOutputStream objectOutputStream,TextField resultField){

            no_VBox.getChildren().clear();
            idVBox.getChildren().clear();
            resultVBox.getChildren().clear();
            nameVBox.getChildren().clear();

            no_VBox.getChildren().add(no_textId);
            idVBox.getChildren().add(idNumberTextId);
            resultVBox.getChildren().add(resultHBoxId);
            nameVBox.getChildren().add(nameTextId);
             ArrayList<TextField> results=new ArrayList<>();
            idNumberList=new ArrayList<>();
            for (ArrayList<String> sections : messageHandler.getGroupHandler()) {
                if (sections.get(0).equals(group)) {

                    for (int i = 1; i < sections.size(); i++) {
                        String[] studentInfo = sections.get(i).split("\\s");
                        TextField result=LabelCreate.createGradeReportVBox(
                                i, studentInfo[0] +" "+ studentInfo[1], studentInfo[2],
                        no_VBox,nameVBox,idVBox,resultVBox);
                        result.setId("result"+i);
                        results.add(result);
                        this.idNumberList.add(studentInfo[2]);
                    }
                    TextField assessmentField=new TextField();
                    assessmentField.setFont(new Font("Abyssinica SIL",15));
                    resultVBox.getChildren().add(submitButtonId);
                    nameVBox.getChildren().add(assessmentField);
                    submitButtonId.setOnMouseClicked((event -> {

                        ArrayList<String> saveInDataBase=new ArrayList<>();
                        saveInDataBase.add(assessmentField.getText());
                        saveInDataBase.add(resultField.getText());
                        for (int i = 0; i <results.size() ; i++) {
                            if(!results.get(i).equals("")) {
                                saveInDataBase.add(results.get(i).getText() + " " + idNumberList.get(i));
                            }
                        }
                        resultField.setText("");
                        assessmentField.setText("");
                        for (TextField textField:results) {
                            textField.setText("");
                        }
                        try {
                            objectOutputStream.writeObject("saveResult");
                            objectOutputStream.writeObject(saveInDataBase);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }));
                    break;
                }
        }
        vBoxId.getChildren().add(paneOverlapped);

    }
}


