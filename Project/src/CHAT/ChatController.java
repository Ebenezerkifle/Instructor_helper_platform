package CHAT;

import CHAT.OwnerChat.ReceiveMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static javafx.scene.paint.Color.BLACK;


public class ChatController implements Initializable {

    @FXML private TextField textMessage;
    @FXML private Text TypeOfChatTextId;
    @FXML private AnchorPane anchorPaneDown;
    @FXML private AnchorPane anchorPaneUp;
    @FXML private VBox vBoxId;
    @FXML private VBox vBoxTwo;
    @FXML private VBox vBoxCorner;
    @FXML private Button dataBase;
    @FXML private Button backArrow;
    @FXML private Button optionId;
    @FXML private HBox hBox;


    @FXML Button A,B,C;
    private ArrayList<String> messages;
    private ArrayList<String> messagesFromB;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Socket socket;
    private String userName;
    private String forWhoToSend;
    private ArrayList<Button> buttons;
    private ArrayList<Label> optionLabels;


    public void socketConnection(Socket socket,String userName,
            ObjectInputStream objectInputStream,ObjectOutputStream objectOutputStream)  {

        this.socket=socket;
        this.userName=userName;
        this.objectOutputStream=objectOutputStream;
        this.objectInputStream=objectInputStream;
        System.out.println(socket);
//        ReceiveMessage receiveMessage=new ReceiveMessage(socket,objectInputStream ,vBoxId,textMessage);
//        receiveMessage.receiveTextMessage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messages=new ArrayList<String>();
        backArrow.setVisible(false);
        backArrow.setDisable(true);
    }
    //--module-path /Users/mac/Downloads/javafx-sdk-13.0.1/lib --add-modules javafx.controls,javafx.fxml
    public void onSendButtonClick(ActionEvent actionEvent) {
        implementSend();

    }
    public void createLabel(Color color,VBox vBoxId,Label label){
        label.setTextFill(Color.WHITE);
        label.setGraphicTextGap(80);
        label.setFont(new Font("Arial",20));
        label.setBackground(new Background(
                new BackgroundFill(color , CornerRadii.EMPTY, Insets.EMPTY)));
        vBoxId.getChildren().add(label);
        vBoxId.setSpacing(20);
        textMessage.setText("");
    }

    public void keyEvent(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            implementSend();
        }

    }

    public void implementSend(){
        System.out.println("implementing send.");
        String msg = String.valueOf(textMessage.getText());
        String text=splitText(msg);
        messages.add(text);
        Color color= BLACK;
        Label label=new Label(text);
        createLabel(color,vBoxId,label);
        try {
            objectOutputStream.writeObject("msg");
            objectOutputStream.writeObject(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dropHandler(DragEvent dragEvent){
        Dragboard dragboard=dragEvent.getDragboard();
        if (dragboard.hasFiles()) {
            String filePath = null;
            for (File file:dragboard.getFiles()) {
                filePath = file.getAbsolutePath();

                try {
                    FileInputStream fileInputStream=new FileInputStream(filePath);

                    Image image = new Image(fileInputStream,
                            100,120,false,false);

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

    public void onSectionAClicked(MouseEvent mouseEvent) {
        // should show the members and helps to add members.

    }

    public void onSectionBClick(ActionEvent actionEvent) {

        // section B notice group comes.....

        this.forWhoToSend="groupB";
        Parent root = null;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/CHAT/HomeSecBFxml.fxml"));
            root=loader.load();
            SectionBController sectionBController=loader.getController();
            sectionBController.messageFromA(messages,messagesFromB);
            Scene scene = new Scene(root, 655, 572);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onFileButtonClicked(ActionEvent actionEvent) {
        // shows the file.
    }

    public void messagesFromB(ArrayList<String> messages,
                              ArrayList<String> messagesFromB) {

        this.messages=messages;
        for (String message:messages) {
            Label label=new Label(message);
            Color color= BLACK;
            createLabel(color,vBoxId,label);
        }
        this.messagesFromB=messagesFromB;
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

    public void onOptionsButtonClicked(ActionEvent actionEvent) {

        backArrow.setVisible(true);
        backArrow.setDisable(false);
        optionId.setVisible(false);
        optionId.setDisable(true);

        Label groupChat=createLabel("Group Chat");
        Label privateChat=createLabel("Private Chat");
        Label assessment=createLabel("Assessment");
        Label addGroups=createLabel("Add Group");

        this.buttons=new ArrayList<>();
        buttons.add(A);
        buttons.add(B);
        buttons.add(C);

        vBoxTwo.getChildren().remove(A);
        vBoxTwo.getChildren().remove(B);
        vBoxTwo.getChildren().remove(C);

        anchorPaneDown.getChildren().remove(vBoxCorner);

       // anchorPaneUp.getChildren().remove(Ty)

        TypeOfChatTextId.setText("Options");
        vBoxTwo.getChildren().add(groupChat);
        vBoxTwo.getChildren().add(privateChat);
        vBoxTwo.getChildren().add(assessment);
        vBoxTwo.getChildren().add(addGroups);
        this.optionLabels=new ArrayList<>();
        optionLabels.add(groupChat);
        optionLabels.add(privateChat);
        optionLabels.add(assessment);
        optionLabels.add(addGroups);

        vBoxTwo.setBackground(new Background(
                new BackgroundFill(BLACK , CornerRadii.EMPTY, Insets.EMPTY)));
        vBoxTwo.setAlignment(Pos.TOP_CENTER);
    }
    public Label createLabel(String textFill){
        Label label=new Label(textFill);
        label.setTextFill(Paint.valueOf("#ffffff"));
        label.setFont(new Font("Arial",20));
       // label.setStyle("-fx-background-color: #005580;");
        label.setAlignment(Pos.CENTER);
        return label;
    }

    public void backButtonClick(ActionEvent actionEvent) {
        backArrow.setVisible(false);
        backArrow.setDisable(true);
        optionId.setVisible(true);
        optionId.setDisable(false);
        for (Label label: optionLabels) {
            vBoxTwo.getChildren().remove(label);
        }
        for (Button button: buttons) {
            vBoxTwo.getChildren().add(button);
        }



    }
}

