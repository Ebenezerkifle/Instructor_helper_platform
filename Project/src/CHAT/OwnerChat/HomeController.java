package CHAT.OwnerChat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class HomeController implements Initializable {

    @FXML private VBox vBoxId;
    @FXML private VBox vBoxOptions;
    @FXML private VBox vBoxIdLeft;
    @FXML private VBox membersVBoxId;
    @FXML private VBox no_VBox;
    @FXML private VBox nameVBox;  //
    @FXML private VBox idVBox;
    @FXML private VBox resultVBox;



    @FXML private TextField textMessage;
    @FXML private TextField departmentId;
    @FXML private TextField sectionId;
    @FXML private TextField yearId;
    @FXML private TextField resultField;

    @FXML private Text optionId;
    @FXML private Text headerTextId;
    @FXML private Text no_textId;
    @FXML private Text nameTextId;
    @FXML private Text idNumberTextId;



    @FXML private HBox resultHBoxId;
    @FXML private HBox hBoxIdTop;
    @FXML private HBox hBoxIdDown;
    @FXML private HBox addMembersHBox;
    @FXML private HBox departmentHBox;
    @FXML private HBox sectionHBox;
    @FXML private HBox yearHBox;
    @FXML private HBox gradeReportHBox;


    @FXML private Button doneButton;
    @FXML private Button cancelId;
    @FXML private Button submitButtonId;
    @FXML private Button fileBrowseButton;
    @FXML private Button sendButtonId;



    @FXML private Label groupChatId;
    @FXML private Label privateChatId;
    @FXML private Label gradeReportId;
    @FXML private Label addGroupsId;

    @FXML private Button optionButtonId;
    @FXML private Button browseButtonId;

    @FXML private Pane paneOverlapped;
    @FXML private Pane paneInfo;
    @FXML private Pane filePaneId;
    @FXML private ScrollPane gradeReportScrollPane;
    @FXML private FlowPane flowPaneId;



    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private ArrayList<Label> options;
    private ArrayList<Label> groupChatArray;
    private ArrayList<Label> textMessageLabelArray;
    private MessageHandler messageHandler;
    private ArrayList<Label> membersList;
    private int userId;
    private String userName;
    private ReceiveMessage receiveMessage;
    private String idNumber;
    private ArrayList<TextField> resultsArray;
    private ArrayList<String> idNumberList;
    private FileManager fileManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textMessageLabelArray=new ArrayList<>();
        groupChatArray=new ArrayList<>();
        options=new ArrayList<>();
        membersList= new ArrayList<>();
        resultsArray=new ArrayList<>();
        idNumberList=new ArrayList<>();
        HandleScene.disable(hBoxIdDown);
        HandleScene.disable(hBoxIdTop);
        HandleScene.disable(addGroupsId);
        HandleScene.disable(paneOverlapped);
        HandleScene.disable(paneInfo);
        HandleScene.disable(browseButtonId);
        HandleScene.disable(filePaneId);
        HandleScene.disable(gradeReportScrollPane);
        optionButtonId.setDisable(true);

    }

    public void socketConnection(Socket socket, String userName,
          ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream)  {
        this.socket=socket;
        this.objectOutputStream=objectOutputStream;
        this.objectInputStream=objectInputStream;
        this.userName=userName;
        this.messageHandler=new MessageHandler();


        try {
            objectOutputStream.writeObject("getUserID");
            ArrayList<String> userInfo=new ArrayList<>();
            userInfo.add("Owner");
            userInfo.add(userName);
            objectOutputStream.writeObject(userInfo);
            this.userId=(int)objectInputStream.readObject();
            loadGroups();
            loadMembersListStartUp();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.receiveMessage=new ReceiveMessage(socket,objectInputStream ,
                vBoxId, textMessage,headerTextId,messageHandler,
                vBoxId, vBoxIdLeft,hBoxIdDown,hBoxIdTop,groupChatArray,objectOutputStream
                ,no_VBox ,nameVBox, idVBox,resultVBox,gradeReportHBox
                ,departmentHBox ,sectionHBox,yearHBox,doneButton,cancelId,paneOverlapped,gradeReportScrollPane,flowPaneId);
        receiveMessage.receiveTextMessage();

        this.fileManager=new FileManager(
                membersVBoxId,headerTextId,hBoxIdDown,
                vBoxId,textMessage,textMessageLabelArray,optionButtonId,
                messageHandler,objectOutputStream,groupChatArray,flowPaneId);
    }


    public void keyEvent(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER && !hBoxIdDown.isDisabled()) {
            implementSend();
        }
    }

    public void onSendButtonClick(ActionEvent actionEvent) {
        implementSend();
    }


    public void onOptionButtonClick(ActionEvent actionEvent) {
        headerTextId.setText("Select chat to start");

        addGroupsId.setDisable(true);
        optionButtonId.setDisable(true);

        HandleScene.disable(hBoxIdTop);
        HandleScene.disable(hBoxIdDown);

        vBoxIdLeft.getChildren().clear();
        vBoxIdLeft.getChildren().removeAll();
        vBoxId.getChildren().clear();
        vBoxIdLeft.getChildren().add(vBoxOptions);

    }

    public void implementSend(){

        String msg = String.valueOf(textMessage.getText());
        String text=StringHandler.splitText(msg);

        Label label=LabelCreate.setTextMessageLabel(text);
        String group=headerTextId.getText();
        messageHandler.setArrayList(
                LabelCreate.setTextMessageLabel(StringHandler.nameAfterIgnoringSpace(
                        headerTextId.getText())),label);

        vBoxId.getChildren().add(label);
        vBoxId.setSpacing(20);
        textMessage.setText("");

        ArrayList<String> messageContent=new ArrayList<>();
        for (Label label1:groupChatArray) {
            if(headerTextId.getText().equals(label1.getText())){
                messageContent.add("Group");  //1
                messageContent.add(group);//2  group.
                break;
            }
            else{
                if(label1.equals(groupChatArray.get(groupChatArray.size()-1))) {
                    for (String content:messageContent) {
                        messageContent.remove(content);
                    }
                   // messageContent=null;
                    messageContent.add("Private");//1
                    String sender = Integer.toString(userId);
                    messageContent.add(idNumber);//2  //receiver.
                   // messageContent.add(sender);//3    // sender.
                }
            }
        }
        try {
            messageContent.add(text);// 4
            objectOutputStream.writeObject("msg");
            objectOutputStream.writeObject(messageContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enable(){
        HandleScene.enable(hBoxIdDown);
        HandleScene.enable(hBoxIdTop);
    }

    public void onGroupChatClicked(MouseEvent mouseEvent) {

        optionButtonId.setDisable(false);
        fileBrowseButton.setDisable(true);
        HandleScene.enable(addGroupsId);
        options.add(groupChatId);
        options.add(privateChatId);
        options.add(gradeReportId);

        vBoxIdLeft.getChildren().clear();

            for (Label label : groupChatArray) {
                vBoxIdLeft.getChildren().add(label);
            }

        optionId.setText("Group Chat");
        headerTextId.setText("Select A Group!");
    }

    public void onPrivateChatClick(MouseEvent mouseEvent) {
        vBoxIdLeft.getChildren().clear();
        optionButtonId.setDisable(false);
        optionId.setText("Private Chat");
        headerTextId.setText("Select Chat");
        for (ArrayList<String> groups:messageHandler.getGroupHandler()) {

            loadMembers(vBoxIdLeft,groups);
        }
    }

    public void onAddGroupClick(MouseEvent mouseEvent) {
        addGroupsId.setDisable(true);
        vBoxId.getChildren().clear();
        HandleScene.enableAddGroupScene(departmentHBox,
                sectionHBox,yearHBox,doneButton,cancelId);
        HandleScene.disable(gradeReportScrollPane);
        headerTextId.setText("Create Group");
        HandleScene.enable(paneOverlapped);
        vBoxId.getChildren().add(paneOverlapped);
    }

    public void onDoneButtonClickGroupAdd(ActionEvent actionEvent){
        String department =departmentId.getText();
        departmentId.setText("");
        String section=sectionId.getText();
        sectionId.setText("");
        String year=yearId.getText();
        yearId.setText("");

        HandleScene.disable(paneOverlapped);

        addGroupsId.setDisable(false);
        optionButtonId.setDisable(false);
        String groupName=" "+department+" "+year+section;
        messageHandler.createGroupArrayList(groupName);
        Label label=LabelCreate.setGroupLabel(groupName);
        label.setOnMouseClicked((event -> {
            enable();
            LabelActionHandler.handleGroupsEvent(
                   event, headerTextId,vBoxId,groupName,messageHandler);
        }));



        groupChatArray.add(label);
        vBoxIdLeft.getChildren().add(label);
        try {
            objectOutputStream.writeObject("addGroup");
            objectOutputStream.writeObject(groupName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onCancelButtonClicked(ActionEvent actionEvent) {
        HandleScene.disable(paneOverlapped);
        HandleScene.enable(addGroupsId);
        if(vBoxId.getChildren().isEmpty()) {
            for (Label label : groupChatArray) {
                    vBoxIdLeft.getChildren().add(label);
            }
        }
    }
    public void disable(Label label){
        label.setVisible(false);
        label.setDisable(true);
    }
    public void enable(Label label){
        label.setVisible(true);
        label.setDisable(false);
    }

    public void handleHeaderTextClick(MouseEvent mouseEvent){
       if(!paneInfo.isDisabled()){
           implementSaveButtonClick();
       }
       else {
           for (Label label : groupChatArray) {
               if ((headerTextId.getText()).equals(label.getText())) {
                   optionButtonId.setDisable(true);
                   vBoxId.getChildren().clear();
                   hBoxIdDown.setDisable(true);
                   hBoxIdTop.setDisable(true);
                   HandleScene.enable(paneInfo);
                   vBoxId.getChildren().add(paneInfo);
                   headerTextId.setDisable(true);
                   membersVBoxId.getChildren().removeAll();
                   HandleScene.enable(addMembersHBox);
                   for (ArrayList<String> group:messageHandler.getGroupHandler()) {
                       if(headerTextId.getText().equals(group.get(0))){
                          loadMembers(membersVBoxId,group);
                       }
                   }
                   break;
               }
           }
       }
    }

    public void onSaveButtonClick(ActionEvent actionEvent) {
      implementSaveButtonClick();
    }
    public void implementSaveButtonClick(){
        optionButtonId.setDisable(false);
        HandleScene.disable(paneInfo);
        hBoxIdDown.setDisable(false);
        hBoxIdTop.setDisable(false);
        HandleScene.disable(browseButtonId);
        vBoxId.getChildren().clear();
        headerTextId.setDisable(false);
        for (Label label:groupChatArray){
            if(headerTextId.getText().equals(label.getText())){
                if (vBoxId.getChildren().isEmpty()) {
                    String firstArrayListName=StringHandler.nameAfterIgnoringSpace(headerTextId.getText());
                    ArrayList<Label> arrayList=messageHandler.getMessageList(firstArrayListName);
                    for(int i=1;i<arrayList.size();i++) {
                        vBoxId.getChildren().add(arrayList.get(i)); }
                }
            }

        }
    }

    public void onAddMembersButtonClick(MouseEvent mouseEvent) {
        HandleScene.enable(browseButtonId);
    }
    public void onAddMembersBrowseClick(ActionEvent actionEvent) {
       // memberAddOptionId.setDisable(true);
        HandleScene.disable(browseButtonId);
        this.fileManager.browseFile();
        this.idNumber=fileManager.getIdNumber();
        this.membersList=fileManager.getMembersList();
    }

    public void loadGroups() throws IOException, ClassNotFoundException {
            ArrayList<String> groups=new ArrayList<>();
            objectOutputStream.writeObject("groups");
            groups= (ArrayList<String>) objectInputStream.readObject();
            groups.remove(null);  // this is because the function is also fetching null value.
            for (String group : groups) {
                Label label = LabelCreate.setGroupLabel(group);
                label.setOnMouseClicked((event -> {
                    messageHandler.createGroupArrayList(group);
                    HandleScene.enable(hBoxIdDown);
                    HandleScene.enable(hBoxIdTop);
                    LabelActionHandler.handleGroupsEvent(
                            event, headerTextId, vBoxId, group, messageHandler);
                }));
                this.groupChatArray.add(label);
            }

    }
    public void loadMembersListStartUp() throws IOException, ClassNotFoundException {

        objectOutputStream.writeObject("members");
        this.messageHandler.setGroupHandler((ArrayList<ArrayList<String>>) objectInputStream.readObject());
        this.messageHandler.getGroupHandler().remove(null);

    }

    public void loadMembers(VBox membersVBox,ArrayList<String> group){
        for (int i=1;i<group.size();i++) {
            String[] name=group.get(i).split("\\s");
            Label label1=LabelCreate.setGroupMembersLabel(name[0]+" "+name[1]);
            //String member=group.get(i);
            label1.setOnMouseClicked((event -> {
                this.idNumber=name[2];
                textMessage.setDisable(false);
                sendButtonId.setDisable(false);
                fileBrowseButton.setDisable(true);
                HandleScene.enable(hBoxIdTop);
                LabelActionHandler.handleGroupMembersEvent(event,
                        headerTextId,name[0]+" "+name[1],
                        vBoxId,optionButtonId,messageHandler,hBoxIdDown);

            }));
            membersVBox.getChildren().add(label1);
        }
    }

    public void onGradeReportClick(MouseEvent mouseEvent) {
        addGroupsId.setDisable(true);
        HandleScene.enable(hBoxIdTop);
        optionButtonId.setDisable(false);
        vBoxId.getChildren().clear();

        headerTextId.setText("Grade Report");
        HandleScene.disableAddGroupScene(departmentHBox,
                sectionHBox,yearHBox,doneButton,cancelId);
        vBoxIdLeft.getChildren().clear();
        HandleScene.enable(paneOverlapped);
        HandleScene.enable(gradeReportScrollPane);

        for (Label label : groupChatArray) {
            Label label1=LabelCreate.setGroupLabel(label.getText());
            LabelActionHandler labelActionHandler=new LabelActionHandler();
            label1.setOnMouseClicked((event -> {
                vBoxId.getChildren().clear();
                labelActionHandler.handleGradeReportEvent(event,paneOverlapped,
                        vBoxId,messageHandler
                        ,label1.getText(),no_VBox,nameVBox,idVBox,resultVBox,
                        no_textId,nameTextId,idNumberTextId,resultHBoxId,submitButtonId,objectOutputStream,resultField);
            }));

            vBoxIdLeft.getChildren().add(label1);
            this.idNumberList=labelActionHandler.getIdNumberList();
        }
    }

    public void mouseExited(MouseEvent mouseEvent) {

    }

    public void onSeeButtonClick(ActionEvent actionEvent) throws IOException {
        vBoxId.getChildren().clear();
        //resultField.setText("Result");
        HandleScene.enable(gradeReportHBox);
        HandleScene.disableAddGroupScene(departmentHBox,
                sectionHBox,yearHBox,doneButton,cancelId);
        HandleScene.enable(paneOverlapped);
        vBoxId.getChildren().add(paneOverlapped);
        no_VBox.getChildren().clear();
        idVBox.getChildren().clear();
        resultVBox.getChildren().clear();
        nameVBox.getChildren().clear();

        no_VBox.getChildren().add(no_textId);
        idVBox.getChildren().add(idNumberTextId);
        resultVBox.getChildren().add(resultHBoxId);
        nameVBox.getChildren().add(nameTextId);


        objectOutputStream.writeObject("getGradeReport");
    }

    public void dragDropHandler(DragEvent dragEvent){
        Dragboard dragboard=dragEvent.getDragboard();
        if (dragboard.hasFiles()) {
            String filePath = null;
            for (File file:dragboard.getFiles()) {
                filePath = file.getAbsolutePath();
                try {
                    FileInputStream fileInputStream=new FileInputStream(filePath);

                    Image image = new Image(fileInputStream,100,120,
                            false,false);

                    Label label=new Label("image");
                    ImageView imageView=new ImageView(image);
                    label.setGraphic(imageView);
                    vBoxId.getChildren().add(label);

                    byte[] buffer = new byte[fileInputStream.available()];
                    fileInputStream.read(buffer);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void onFileBrowseButtonClick(ActionEvent actionEvent) throws IOException {
        //HandleScene.disable(hBoxIdDown);
        File file=fileManager.browseFilesToSend();
        if(file!=null) {
            FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            objectOutputStream.writeObject("sendFile");  // request type.
            ArrayList<String> messageContent=new ArrayList<>();
            messageContent.add("Group");      //1          // chat type.
            messageContent.add(headerTextId.getText());  //2   // specification of a group to receive.
            messageContent.add(file.getName());       // 3    // file Name.
            objectOutputStream.writeObject(messageContent);  // sending all the information.
            objectOutputStream.writeObject(buffer);        // the actual file
/*
            objectOutputStream.flush();               // flushing the objectOutputStream.
*/
        }
    }

    public void onFileButtonClick(ActionEvent actionEvent) {
        vBoxId.getChildren().clear();
        textMessage.setDisable(true);
        sendButtonId.setDisable(true);
        HandleScene.enable(filePaneId);
        HandleScene.enable(fileBrowseButton);
        filePaneId.getChildren().clear();
        vBoxId.getChildren().add(filePaneId);
        ArrayList<Label> files=messageHandler.getMessageList(headerTextId.getText()+"File");
        for (int i = 1; i <files.size(); i++) {
            filePaneId.getChildren().add(files.get(i));
        }
    }

    public void onTextWindowButtonClick(ActionEvent actionEvent) {
        HandleScene.disable(fileBrowseButton);
        HandleScene.enable(textMessage);
        HandleScene.enable(sendButtonId);
        HandleScene.disable(filePaneId);
        LabelActionHandler.LoadTextMessages(
                vBoxId,headerTextId.getText(),messageHandler);
    }

    public void onPowerButtonClick(ActionEvent actionEvent) throws IOException {
        objectOutputStream.writeObject("LogOut");
        objectInputStream.close();
        objectOutputStream.close();
        socket.close();
    }
}
