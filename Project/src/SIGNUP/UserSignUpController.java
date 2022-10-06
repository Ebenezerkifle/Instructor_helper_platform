package SIGNUP;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import Scene.SceneCreate;
import javafx.scene.paint.Color;

public class UserSignUpController implements Initializable {
    @FXML TextField userNameId;
    @FXML PasswordField passwordId;
    @FXML PasswordField confirmPasswordId;
    @FXML TextField idNumberId;
    @FXML Label labelId;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public void socketConnection(Socket socket, ObjectInputStream objectInputStream,
                                 ObjectOutputStream objectOutputStream) {
        this.socket = socket;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
    }


    public void signUpButtonClick(ActionEvent actionEvent) {

        String idNumber = String.valueOf(idNumberId.getText());
        String userName = String.valueOf(userNameId.getText());
        String password = String.valueOf(passwordId.getText());
        String confirmPassword = String.valueOf(confirmPasswordId.getText());
            if (idNumber != "" && userName != "") {

                if (password.equals(confirmPassword)) {
                    try {
                        objectOutputStream.writeObject("UserSignUp");
                        UserSignUp userSignUp = new UserSignUp();
                        userSignUp.setPassWord(password);
                        userSignUp.setIdNumber(idNumber);
                        userSignUp.setUserName(userName);
                        objectOutputStream.writeObject(userSignUp);
                        try {
                            String msg = (String) objectInputStream.readObject();
                            labelId.setText(msg);
                            if (msg.equals("successfully saved.")) {
                                Parent root = FXMLLoader.load(getClass().getResource("/LOGIN/LoginFxml.fxml"));
                                Scene scene = new Scene(root, 600, 500);
                                SceneCreate.sceneCreate(actionEvent, scene);
                            }
                            else{
                                labelId.setTextFill(Color.RED);
                                labelId.setText(msg+" you better contact your Instructor.");
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    labelId.setTextFill(Color.RED);
                    labelId.setText("password mismatch occurred.");
                }

            } else {
                labelId.setTextFill(Color.RED);
                labelId.setText("All the fields are required to be filled.");
            }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
