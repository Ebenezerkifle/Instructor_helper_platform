package CHAT.OwnerChat;


import CHAT.UserChat.CreateLabel;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ReceiveMessage {

    private final ObjectInputStream objectInputStream;
    private final Socket socket;
    private final Text headerTextId;
    private final MessageHandler messageHandler;
    private final VBox vBoxId;
    private final VBox vBoxIdLeft;
    private final HBox hBoxDown;
    private final HBox hBoxTop;
    private final ObjectOutputStream objectOutputStream;
    private final VBox nameVBox;
    private final VBox idVBox;
    private final VBox resultVBox;
    private final VBox no_VBox;
    private final HBox gradeReportHBox;
    private final FlowPane flowPaneId;
    private HBox departmentHBox;
    private HBox sectionHBox;
    private HBox yearHBox;
    private Button doneButton;
    private Button cancelId;
    private ScrollPane gradeReportScrollPane;
    private Pane paneOverlapped;
    private VBox vBox;
    private TextField textMessage;
    private ArrayList<ArrayList<String>> gradeReport;

    public ArrayList<ArrayList<String>> getGradeReport() {
        return gradeReport;
    }

    public ArrayList<Label> getGroupChatArray() {
        return groupChatArray;
    }

    private ArrayList<Label> groupChatArray;

    public ReceiveMessage(
            Socket socket, ObjectInputStream objectInputStream ,
            VBox vBox, TextField textMessage, Text headerTextId,
            MessageHandler messageHandler, VBox vBoxId, VBox vBoxIdLeft,
            HBox hBoxDown, HBox hBoxTop, ArrayList<Label> groupChatArray,
            ObjectOutputStream objectOutputStream , VBox no_VBox ,
            VBox nameVBox , VBox idVBox , VBox resultVBox, HBox gradeReportHBox
            , HBox departmentHBox , HBox sectionHBox, HBox yearHBox,
            Button doneButton, Button cancelId, Pane paneOverlapped,
            ScrollPane gradeReportScrollPane, FlowPane flowPaneId){
        this.socket=socket;
        this.objectInputStream=objectInputStream;
        this.vBox=vBox;
        this.textMessage=textMessage;
        this.headerTextId=headerTextId;
        this.messageHandler=messageHandler;
        this.vBoxId=vBoxId;
        this.vBoxIdLeft=vBoxIdLeft;
        this.hBoxDown=hBoxDown;
        this.hBoxTop=hBoxTop;
        this.groupChatArray=groupChatArray;
        this.objectOutputStream=objectOutputStream;
        this.nameVBox=nameVBox;
        this.idVBox=idVBox;
        this.resultVBox=resultVBox;
        this.no_VBox=no_VBox;
        this.gradeReportHBox=gradeReportHBox;
        this.departmentHBox=departmentHBox;
        this.sectionHBox=sectionHBox;
        this.yearHBox=yearHBox;
        this.doneButton=doneButton;
        this.cancelId=cancelId;
        this.paneOverlapped=paneOverlapped;
        this.gradeReportScrollPane=gradeReportScrollPane;
        this.flowPaneId=flowPaneId;
    }

    public void  receiveTextMessage(){
            Thread thread=new Thread(() -> {
                while(socket.isConnected()){
                    try {
                        final ArrayList<String> messageContent= (ArrayList<String>) objectInputStream.readObject();
                        for (String msg:messageContent) {
                            System.out.println(msg);
                        }
                        if(messageContent.get(0).equals("gradeReport")){
                            handleGradeReport();

                        }
                        else if(messageContent.get(0).equals("File")){
                            fileReceiver(messageContent); // calling a method that handles coming file.
                        }
                        else {
                            Platform.runLater(() -> {
                                boolean go = true;
                                for (ArrayList<String> possibleSenders : messageHandler.getGroupHandler()) {
                                    for (String sender : possibleSenders) {
                                        if (sender.contains(messageContent.get(1))) { // searching for sender by using sent id Number.
                                            Label label = LabelCreate.setGroupMembersLabel(messageContent.get(0));
                                            // this is actual message.

                                            String[] senderName = sender.split("\\s");
                                            messageHandler.setArrayList(
                                                    LabelCreate.setTextMessageLabel(StringHandler.nameAfterIgnoringSpace(
                                                            senderName[0] + " " + senderName[1])), label);
                                            messageHandler.setArrayList(CreateLabel.setTextMessageLabel(sender), label);

                                            if (headerTextId.getText().equals(senderName[0] + " " + senderName[1])) {
                                                vBoxId.getChildren().add(label);
                                                vBox.setSpacing(20);
                                            }
                                            go = false;
                                            break;
                                        }
                                    }
                                    if (go == false) {
                                        break;
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

    private void fileReceiver(ArrayList<String> messageContent) throws IOException, ClassNotFoundException {

        FileOutputStream fileOutputStream;
        byte[] byteArray = (byte[]) objectInputStream.readObject();

        String fileName=messageContent.get(2);
        String idNumber=messageContent.get(1);
        fileOutputStream = new FileOutputStream("/Users/mac/Desktop/"+fileName);
        fileOutputStream.write(byteArray);
        fileOutputStream.flush();
        System.out.println("received.");
        FileInputStream fileInputStream1=new FileInputStream(
                "/Users/mac/Documents/JAVA/Project/src/Adobe-PDF-Document-icon.png");
        Image image=new Image(fileInputStream1,100,60,false,false);
        Label label=new Label(fileName);
        ImageView imageView=new ImageView(image);
        label.setGraphic(imageView);
        label.setTextFill(Color.WHITE);
        label.setContentDisplay(ContentDisplay.TOP);
        boolean go=true;
        for (ArrayList<String> possibleSenders:messageHandler.getGroupHandler()) {
            for (String sender:possibleSenders) {
                System.out.println(sender);
                if(sender.contains(idNumber)){
                    System.out.println(messageContent.get(2));
                    //System.out.println(sender);
                    String[] header=sender.split("\\s");
                    Platform.runLater(()-> {
                        flowPaneId.getChildren().add(label);
                        messageHandler.setArrayList(LabelCreate.setGroupLabel(header[0]+" "+header[1]+"File"),label);
                    });
                    go=false;
                    break;
                }

            }
            if(go==false){
                break;
            }
        }

    }

    public void handleGradeReport(){
        try {
            objectOutputStream.writeObject("ready");
            this.gradeReport=(ArrayList<ArrayList<String>>)objectInputStream.readObject();

                int i=1;
            Platform.runLater(()-> {
                for (ArrayList<String> grade:gradeReport) {
                   LabelCreate.gradeReport(grade,i,no_VBox ,nameVBox, idVBox,resultVBox,gradeReportHBox);
                }
            });
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    private void handleGroups(ArrayList<String> groups) {
            groupChatArray = new ArrayList<>();
        for (String group : groups) {
            Label label = LabelCreate.setGroupLabel(group);
            label.setOnMouseClicked((event -> {
                HandleScene.enable(hBoxDown);
                HandleScene.enable(hBoxTop);
                LabelActionHandler.handleGroupsEvent(
                        event, headerTextId, vBoxId, group, messageHandler);
            }));
            vBoxIdLeft.getChildren().add(label);
            this.groupChatArray.add(label);
        }
    }

    private void handleMessage() {
        Platform.runLater(()->{
            try {

                String msg=(String)objectInputStream.readObject();
                vBox.getChildren().add(new Label(msg));
                vBox.setSpacing(20);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
    }

    public void receiveImage(){

    }
    public void receivePdf(){

    }
    public void receivePPT(){

    }
}

