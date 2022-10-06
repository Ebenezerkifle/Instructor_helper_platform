package CHAT;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SectionBController implements Initializable {

    @FXML
    TextField textMessage;

    @FXML
    VBox vBoxId;

    private ArrayList<String> messageFromA;
    private ArrayList<String> messages;

    public void onFileButtonClicked(ActionEvent actionEvent) {
    }

    public void onSectionBClicked(MouseEvent mouseEvent) {
        // members should be shown....
    }

    public void onSectionAClicked(ActionEvent actionEvent) {

        Parent root = null;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/CHAT/HomeFxml.fxml"));
            root=loader.load();
            ChatController sectionA=loader.getController();
            sectionA.messagesFromB(messageFromA,messages);
            Scene scene = new Scene(root, 655, 572);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void keyEvent(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            implementSend();
        }
    }

    private void implementSend() {
        String msg = String.valueOf(textMessage.getText());
        messages.add(msg);
        Color color=Color.BLACK;
        createLabel(msg,color,vBoxId);
        //objectOutputStream.writeObject(msg);

    }
    public void createLabel(String msg ,Color color,VBox vBoxId){

        Label label=new Label(msg);
        label.setTextFill(Color.WHITE);
        label.setGraphicTextGap(80);
        label.setFont(new Font("Arial",20));
        label.setBackground(new Background(
                new BackgroundFill(color , CornerRadii.EMPTY, Insets.EMPTY)));
        vBoxId.getChildren().add(label);
        vBoxId.setSpacing(20);
        textMessage.setText("");
    }

    public void onSendButtonClick(ActionEvent actionEvent) {
        implementSend();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messages=new ArrayList<String>();
    }

    public void messageFromA(ArrayList<String> messageFromA,ArrayList<String> messages) {
        this.messages=messages;
        if(messages!=null) {
            for (String message : messages) {
                //  Label label=new Label(message);
                Color color = Color.BLACK;
                createLabel(message, color, vBoxId);
            }
        }
        this.messageFromA=messageFromA;
    }
}
