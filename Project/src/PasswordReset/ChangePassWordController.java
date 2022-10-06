package PasswordReset;

import LOGIN.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import Scene.SceneCreate;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChangePassWordController {
    @FXML
    TextField passwordId;
    @FXML
    TextField confirmedId;
    @FXML
    Label labelId;
    public void onSaveButtonClick(ActionEvent actionEvent) throws Exception {
        // here new password is created and saved to the database.



        String password=String.valueOf(passwordId.getText());
        String confirm=String.valueOf(confirmedId.getText());
        if(password.equals(confirm)){

            Socket socket=new Socket(LoginController.host,LoginController.port);
            ObjectInputStream objectInput=new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutput=new ObjectOutputStream(socket.getOutputStream());

            objectOutput.writeObject("changePassword");
            objectOutput.writeObject(password);

            String response=(String)objectInput.readObject();
             labelId.setText(response);



             socket.close();


            Parent root = FXMLLoader.load(getClass().getResource("/LOGIN/LoginFxml.fxml"));
            Scene scene=new Scene(root, 600, 500);
            SceneCreate.sceneCreate(actionEvent,scene);
        }
        else{
            labelId.setTextFill(Color.RED);
            labelId.setText("Password mismatch occurred!");
        }

    }
}
