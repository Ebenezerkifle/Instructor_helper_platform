package CHAT.UserChat;

import javafx.application.Platform;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.io.*;
import java.util.ArrayList;


public class ReceiveMessage  {

    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    private final UserChatHandler userChatHandler;
    private final Text headerTextId;
    private final String mySection;
    private final VBox leftVBoxDescription;
    private final VBox rightVBoxResult;
    private final HBox gradeReportHBox;
    private final Text rightResultId;
    private final Text yourResultTextId;
    private final FlowPane flowPaneId;
    private VBox vBox;
    private TextField textMessage;
    private Label group;
    private Label private1;

    public ReceiveMessage(ObjectInputStream objectInputStream,
                          VBox vBox, ObjectOutputStream objectOutputStream,
                          UserChatHandler userChatHandler, Text headerTextId, String mySection
                          , VBox leftVBoxDescription, VBox rightVBoxResult, HBox gradeReportHBox
                         , Text rightResultId, Text yourResultTextId, FlowPane flowPaneId){
        this.objectInputStream=objectInputStream;
        this.vBox=vBox;
        this.objectOutputStream=objectOutputStream;
        this.userChatHandler=userChatHandler;
        this.group=CreateLabel.setGroupMembersLabel("Group");
        this.private1=CreateLabel.setTextMessageLabel("Private");
        this.headerTextId=headerTextId;
        this.mySection=mySection;
        this.leftVBoxDescription=leftVBoxDescription;
        this.rightVBoxResult=rightVBoxResult;
        this.gradeReportHBox=gradeReportHBox;
        this.rightResultId=rightResultId;
        this.yourResultTextId=yourResultTextId;
        this.flowPaneId=flowPaneId;
    }

    public void  receiveGroupTextMessage() {

        Thread thread=new Thread(() -> {
            while(true){
                try {
                    final ArrayList<String> messageContent=(ArrayList<String>) objectInputStream.readObject();
                    if(messageContent.get(0).equals("File")){
                        receiveFile(messageContent);
                    }
                    else {
                        Platform.runLater(() -> {
                            Label label=CreateLabel.setGroupMembersLabel(messageContent.get(1));
                            if (messageContent.get(0).equals("Group")) {
                                userChatHandler.setArrayList(group, label);
                                if (headerTextId.getText().equals(mySection)) {
                                    vBox.getChildren().add(label);
                                    vBox.setSpacing(20);
                                }//Private  //Private
                            } else if (messageContent.get(0).equals("Private")) {
                                userChatHandler.setArrayList(private1, label);
                                if (headerTextId.getText().equals("Private")) {
                                    System.out.println("ouch");
                                    vBox.getChildren().add(label);
                                    vBox.setSpacing(20);
                                }
                            } else if (messageContent.get(0).equals("gradeReport")) {
                                leftVBoxDescription.getChildren().clear();
                                rightVBoxResult.getChildren().clear();
                                leftVBoxDescription.getChildren().add(yourResultTextId);
                                rightVBoxResult.getChildren().add(rightResultId);
                                for (int i = 1; i < messageContent.size(); i++) {
                                    String[] result = messageContent.get(i).split("\\s");
                                    String[] description = result[0].split("\\_");
                                    leftVBoxDescription.getChildren().add(CreateLabel.setGroupLabel(description[0]));
                                    rightVBoxResult.getChildren().add(CreateLabel.setGroupLabel(result[1] + "/" + description[1]));
                                }
                            }
                        });
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();

    }


    public void receiveFile(ArrayList<String> messageContent){
        FileOutputStream fos = null;
        try {
            // giving the name and format for the file we are receiving form the client.
            byte[] byteArray = (byte[]) objectInputStream.readObject();

            String fileName=messageContent.get(1);
            fos = new FileOutputStream("/Users/mac/Desktop/"+fileName);
            fos.write(byteArray);
            fos.flush();
            System.out.println("received.");
            FileInputStream fileInputStream1=new FileInputStream(
                    "/Users/mac/Documents/JAVA/Project/src/Adobe-PDF-Document-icon.png");
            Image image=new Image(fileInputStream1,100,60,false,false);
            Label label=new Label(fileName);
            ImageView imageView=new ImageView(image);
            label.setGraphic(imageView);
            label.setTextFill(Color.WHITE);
            label.setContentDisplay(ContentDisplay.TOP);
            Platform.runLater(()-> {
                flowPaneId.getChildren().add(label);
                System.out.println("ouch");
            });
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }





    public void receivePrivateTextMessage(){
//        try {
//            objectOutputStream.writeObject("msg");
//            objectOutputStream.writeObject("private");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Thread thread=new Thread(() -> {
            while(true){
                try {
                    final String msg = (String) objectInputStream.readObject();

                    Platform.runLater(()->{
                        Color color=Color.BLACK;
                        Label label=new Label(msg);
                        label.setFont(new Font("Arial",20));
//                        label.setBackground(new Background(
//                                new BackgroundFill(color , CornerRadii.EMPTY, Insets.EMPTY)));
                        vBox.getChildren().add(label);
                        vBox.setSpacing(20);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }
    public void receiveImage(){

    }
    public void receivePdf(){

    }
    public void receivePPT(){

    }
}



