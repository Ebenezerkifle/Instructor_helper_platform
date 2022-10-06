package PasswordReset;

import DataBase.PasswordResetDataBase;
import LOGIN.Login;
import LOGIN.LoginController;
import SIGNUP.AdminSignUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Scene.SceneCreate;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ForgotPassword {
    @FXML
    TextField firstNameId;
    @FXML
    TextField lastNameId;
    @FXML
    TextField userNameId;
    @FXML
    Label lebelId;

    public void onCheckButtonClick(ActionEvent actionEvent) throws Exception {
        Socket socket=new Socket(LoginController.host,LoginController.port);

        ObjectInputStream objectInput=new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream objectOutput=new ObjectOutputStream(socket.getOutputStream());


        Login login=new Login();
        login.setFirstName(String.valueOf(firstNameId.getText()));
        login.setLastName(String.valueOf(lastNameId.getText()));
        login.setUserName(String.valueOf(userNameId.getText()));

        objectOutput.writeObject("forgotPassword");
        objectOutput.writeObject(login);

        String response=(String)objectInput.readObject();

        if(response.equals("valid"))
        {
            Parent root = FXMLLoader.load(getClass().getResource("/PasswordReset/changePassword.fxml"));
            Scene scene=new Scene(root, 600, 500);
            SceneCreate.sceneCreate(actionEvent,scene);
        }
        else{
            lebelId.setText("Invalid!");
            Parent root = FXMLLoader.load(getClass().getResource("/LOGIN/LoginFxml.fxml"));
            Scene scene=new Scene(root, 600, 500);
            SceneCreate.sceneCreate(actionEvent,scene);
        }
    }

}
