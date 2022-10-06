package CHAT.UserChat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class userChatController implements Initializable {


    @FXML private VBox vBoxId;
    @FXML private TextField textFieldId;
    @FXML private Text rightResultId;
    @FXML private Text yourResultTextId;
    @FXML private Button sendButtonId;
    @FXML private Pane paneOverVBox;
    @FXML private Text headerTextId;
    @FXML private Text noticeId;
    @FXML private HBox hBoxDownId;
    @FXML private HBox hBoxTopId;
    @FXML private VBox vBoxGradeReportId;
    @FXML private FlowPane flowPaneId;
    @FXML private HBox gradeReportHBox;
    @FXML private VBox leftVBoxDescription;
    @FXML private VBox rightVBoxResult;
    @FXML private Button fileBrowseButton;





    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String userName;
    private int userId;
    private String mySection;
    private UserChatHandler userChatHandler;
    private String idNumber;
    private int instructorId;
    private FileManager fileManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        HandleScene.disable(paneOverVBox);
        HandleScene.disable(hBoxTopId);
        HandleScene.disable(hBoxDownId);
        HandleScene.disable(gradeReportHBox);
        userChatHandler=new UserChatHandler();
    }
    public void socketConnection(Socket socket,String userName,
                                 ObjectOutputStream objectOutputStream,
                                 ObjectInputStream objectInputStream)  {
        this.socket=socket;
        this.objectInputStream=objectInputStream;
        this.objectOutputStream=objectOutputStream;
        this.userName=userName;

        try {
            objectOutputStream.writeObject("getUserID");
            ArrayList<String> userInfo=new ArrayList<>();
            userInfo.add("User");
            userInfo.add(userName);
            objectOutputStream.writeObject(userInfo);
            this.userId=(int)objectInputStream.readObject();
            loadMySection();
            loadMyIdNumber();

            loadInstructorId();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ReceiveMessage receiveMessage=new ReceiveMessage(objectInputStream ,
                vBoxId,objectOutputStream,userChatHandler,headerTextId,
                mySection,leftVBoxDescription,rightVBoxResult,gradeReportHBox
               ,rightResultId,yourResultTextId,flowPaneId);
        receiveMessage.receiveGroupTextMessage();
        this.fileManager=new FileManager(flowPaneId,headerTextId,userChatHandler);
    }

    private void loadInstructorId() throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject("getInstructorId");
        this.instructorId=(int)objectInputStream.readObject();
    }

    private void loadMyIdNumber() throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject("myIdNumber");
        //objectOutputStream.writeObject(userId);
//        this.idNumber=(String)objectInputStream.readObject();
    }

    private void loadMySection() throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject("mySection");
        objectOutputStream.writeObject(userName);
        this.mySection= (String) objectInputStream.readObject();
    }

    public void onFileButtonClicked(ActionEvent actionEvent) {
        vBoxId.getChildren().clear();
        HandleScene.enable(paneOverVBox);
        noticeId.setText("File");
        vBoxId.getChildren().add(paneOverVBox);
        fileBrowseButton.setDisable(false);
        textFieldId.setDisable(true);
        sendButtonId.setDisable(true);
       /* flowPaneId.getChildren().clear();*/
        ArrayList<Label> files=userChatHandler.getMessageList(headerTextId.getText()+"File");
        for (int i = 1; i <files.size(); i++) {
            flowPaneId.getChildren().add(files.get(i));
        }
    }

    public void onSectionAClicked(MouseEvent mouseEvent) {
    }



    public void publicChatButtonClicked(ActionEvent actionEvent) {
        HandleScene.disable(gradeReportHBox);
        HandleScene.enable(hBoxTopId);
        HandleScene.disable(hBoxDownId);
        headerTextId.setText(mySection);
        noticeId.setText("Notices");
        vBoxId.getChildren().clear();
        vBoxId.setSpacing(20);
        ArrayList<Label> message=userChatHandler.getMessageList("Group");
        for (int i = 1; i <message.size() ; i++) {
           vBoxId.getChildren().add(message.get(i));
        }
    }

    public void privateChatButtonClicked(ActionEvent actionEvent) {
        HandleScene.disable(gradeReportHBox);
        HandleScene.enable(hBoxDownId);
        HandleScene.enable(hBoxTopId);
        noticeId.setText("Talk to Instructor.");
        headerTextId.setText("Private");
        vBoxId.getChildren().clear();
        vBoxId.setSpacing(20);
        ArrayList<Label> message=userChatHandler.getMessageList("Private");
        for (int i = 1; i <message.size() ; i++) {
            vBoxId.getChildren().add(message.get(i));
        }
    }

    public void onSendButtonClicked(ActionEvent actionEvent) {

        implementSend();
    }
    public void keyEvent(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER && !hBoxDownId.isDisabled()){
            implementSend();
        }
    }

    public void implementSend(){
        ArrayList<String> messageContent=new ArrayList<>();

        String msg = String.valueOf(textFieldId.getText());
        String text=splitText(msg);
        Label label=new Label(text);
        label.setTextFill(Color.WHITE);
        label.setOnMouseClicked((event -> {
            //System.out.println("label is clicked.");
        }));
        label.setFont(new Font("Abyssinica SIL",16));
        label.setBackground(new Background(
                new BackgroundFill(null , CornerRadii.EMPTY, Insets.EMPTY)));
        userChatHandler.setArrayList(CreateLabel.setGroupMembersLabel("Private"),label);
        vBoxId.getChildren().add(label);
        vBoxId.setSpacing(20);
        textFieldId.setText("");

        try {
            messageContent.add("Private2"); //1
            messageContent.add(String.valueOf(instructorId)); //2
            messageContent.add(text); //3
            objectOutputStream.writeObject("msg");
            objectOutputStream.writeObject(messageContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String splitText(String msg){
        String[] msgSplit=msg.split(" ");
        String text="";
        int length=7;
        for(int i=0;i<msgSplit.length;i++){
            text+=msgSplit[i]+" ";
            if(i%length==0&& i!=0){
                text+="\n";
            }
        }
        return text;
    }

    public void onGradeReportButtonClick(ActionEvent actionEvent) {
        vBoxId.getChildren().clear();
        HandleScene.enable(gradeReportHBox);
        vBoxId.getChildren().add(gradeReportHBox);
        HandleScene.disable(hBoxDownId);
        HandleScene.disable(hBoxTopId);
        try {
            objectOutputStream.writeObject("gradeReport");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTextMessageWindowButtonClick(ActionEvent actionEvent) {
        ArrayList<Label> message = new ArrayList<>();
        vBoxId.getChildren().clear();
        vBoxId.setSpacing(20);
        noticeId.setText("Notice");
        sendButtonId.setDisable(false);
        textFieldId.setDisable(false);
        fileBrowseButton.setDisable(true);

        if(headerTextId.getText().equals("Private")){
            message=userChatHandler.getMessageList("Private");
        }
        else {
            message = userChatHandler.getMessageList("Group");
        }
        for (int i = 1; i <message.size() ; i++) {
            vBoxId.getChildren().add(message.get(i));
        }
    }

    public void fileBrowseButton(ActionEvent actionEvent) throws IOException {

        File file=fileManager.browseFilesToSend();
        if(file!=null) {
            FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            objectOutputStream.writeObject("sendFile"); // request type.
            ArrayList<String> messageContent=new ArrayList<>();
            messageContent.add("Private");         //0    // chat type.
            messageContent.add(String.valueOf(instructorId));    //1   receiver.
            messageContent.add(file.getName());     //2   file Name.
            objectOutputStream.writeObject(messageContent);  // msg
            objectOutputStream.writeObject(buffer);  // actual file.
/*
            objectOutputStream.flush();      // flushing the object Output Stream.
*/
            System.out.println("the file is sent.");
        }


    }
}
