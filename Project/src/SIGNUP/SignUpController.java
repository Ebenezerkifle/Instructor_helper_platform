package SIGNUP;


import LOGIN.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML TextField firstNameId;
    @FXML TextField lastNameId;
    @FXML TextField userNameId;
    @FXML TextField  officeSpecificationId;
    @FXML TextField  officeNumberId;
    @FXML PasswordField passwordid;
    @FXML PasswordField confirmPasswordId;
    @FXML Label lebelId;

    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public void socketConnection(Socket socket, ObjectInputStream objectInputStream,
                                 ObjectOutputStream objectOutputStream) {
        this.socket = socket;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
    }

    public void onDoneButtonClick(ActionEvent actionEvent) throws Exception {
        String firstName=String.valueOf(firstNameId.getText());
        String lastName=String.valueOf(lastNameId.getText());
        String officeSpecification=String.valueOf(officeSpecificationId.getText());
        String officeNumber=String.valueOf(officeNumberId.getText());
        String userName=String.valueOf(userNameId.getText());
        String password=String.valueOf(passwordid.getText());
        String confirmPassword=String.valueOf(confirmPasswordId.getText());

         if(passwordConfirmed(password,confirmPassword)){
             AdminSignUp admin=new AdminSignUp(
                     firstName,lastName,officeSpecification,
                     officeNumber,userName,password);

             objectOutputStream.writeObject(admin);

             if(objectInputStream.readObject().equals("successfully saved.")) {

                 FXMLLoader loader=new FXMLLoader(getClass().getResource("/LOGIN/LoginFxml.fxml"));
                 Parent root=loader.load();
                 LoginController loginController=loader.getController();
                 loginController.socketConnection(socket,objectInputStream,objectOutputStream);

                 Scene scene = new Scene(root, 600, 500);
                 Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                 stage.setScene(scene);
                 stage.show();
             }
             else{
                 lebelId.setTextFill(Color.RED);
                 lebelId.setText("Unable to SignUp.");
             }
         }
    }
    public boolean passwordConfirmed(String password,String confirmPassword){
        boolean confirmed;
        if(password.equals(confirmPassword)){
            confirmed=true;
        }
        else{
            confirmed=false;
        }
        return confirmed;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
