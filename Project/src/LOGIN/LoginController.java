package LOGIN;

import CHAT.OwnerChat.HomeController;
import CHAT.UserChat.userChatController;
import DataBase.DataBaseSubClass;
import SIGNUP.SignUpController;
import SIGNUP.UserSignUpController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Scene.SceneCreate;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController extends AnchorPane implements Initializable {

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    public static  String host="localhost";
    public static int port=9090;
    private Socket socket;


    @FXML private TextField userNameId;
    @FXML private PasswordField passwordId;
    @FXML private Label labelId;
    @FXML private CheckBox checkBox;


    /* as soon as the login button is clicked the client starts to connect to the server
     *  to check the validity of the password and user name.
     */
    // The input and outputStream of the socket to help us .....


    public void onLogInButtonClick(ActionEvent event) throws Exception{

        String userName = String.valueOf(userNameId.getText());
        String password=String.valueOf(passwordId.getText());
        Login login=new Login(userName,password);

        if(!userName.isEmpty() || !password.isEmpty()) {
            objectOutputStream.writeObject("login");
            if(checkBox.isSelected()){
                objectOutputStream.writeObject("admin");
            }
            else{
                objectOutputStream.writeObject("user");
            }
            objectOutputStream.writeObject(login);
            String msg = (String) objectInputStream.readObject();

            if (msg.equals("valid")) {
                labelId.setTextFill(Color.GREEN);
                labelId.setText("you are successfully logged in.");

                if(checkBox.isSelected()) {
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/CHAT/OwnerChat/Home.fxml"));
                    Parent root=loader.load();

                    HomeController homeController=loader.getController();
                    homeController.socketConnection(socket,userName,objectInputStream,objectOutputStream);

                    Scene scene = new Scene(root, 868, 622);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    /*
                     * Node is controller i.e it can be button label or any other but
                     * NB that it is the cause for the even to occur.
                     * so we are searching for the scene on which the node was and after the Stage(window).
                     * getting the source Stage using the event occurred.
                     */
                    stage.setScene(scene);
                    stage.show();
                }
                else{
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/CHAT/UserChat/userChatFxml.fxml"));
                    Parent root=loader.load();
                    //Parent root = FXMLLoader.load(getClass().getResource("/CHAT/UserChat/userChatFxml.fxml"));
                    userChatController userchatcontroller= loader.getController();
                    userchatcontroller.socketConnection(socket,userName,objectOutputStream,objectInputStream);

                    Scene scene = new Scene(root, 655, 572);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    /*
                     * Node is controller i.e it can be button label or any other but
                     * NB that it is the cause for the even to occur.
                     * so we are searching for the scene on which the node was and after the Stage(window).
                     * getting the source Stage using the event occurred.
                     */
                    stage.setScene(scene);
                    stage.show();
                }
            }
            else{
                labelId.setTextFill(Color.RED);
                labelId.setText("Incorrect Password or user name");
            }
        }
        else if(userName.isEmpty() || password.isEmpty()){
            labelId.setTextFill(Color.RED);
            labelId.setText("The spaces are required to be filled!");
        }


    }


    public void onForgotIdClick(MouseEvent mouseEvent) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(
                "/PasswordReset/forgotpasswordFxml.fxml"));
        Scene scene=new Scene(root,600,500);
        SceneCreate.sceneCreate(mouseEvent,scene);
        // calling a method to create new scene.
    }

    public void onSignUpClick(ActionEvent actionEvent) throws Exception  {

        if(checkBox.isSelected()) {
            objectOutputStream.writeObject("AdminSignUp");
            if(objectInputStream.readObject().equals("possible")){
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/SIGNUP/SignUp.fxml"));
                Parent root=loader.load();
                SignUpController signUpController=loader.getController();
                signUpController.socketConnection(socket,objectInputStream,objectOutputStream);
                // a condition to check for existence of an admin.
                Scene scene = new Scene(root, 500, 600);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                labelId.setTextFill(Color.RED);
                labelId.setText("You are not allowed to Sign Up.");
            }
        }
        else{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/SIGNUP/UserSignUpFxml.fxml"));
            Parent root=loader.load();
            UserSignUpController userSignUpController=loader.getController();
            userSignUpController.socketConnection(socket,objectInputStream,objectOutputStream);
            Scene scene = new Scene(root, 500, 600);
            SceneCreate.sceneCreate(actionEvent,scene);
        }
    }

    public void onDeleteButtonClicked(ActionEvent actionEvent) {
        Socket socket= null;
        try {
            socket = new Socket(host,port);
            System.out.println("connected.");
            this.objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream=new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject("deleteAccount");
            DataBaseSubClass dataBase=new DataBaseSubClass();
            objectOutputStream.writeObject(dataBase.getAdminTableName());
            String response=(String)objectInputStream.readObject();
            labelId.setText(response);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Socket socket=new Socket(host,port);
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());
            socketConnection(socket,objectInputStream,objectOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void socketConnection(Socket socket, ObjectInputStream objectInputStream,
                                 ObjectOutputStream objectOutputStream) {
        this.socket = socket;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
    }
}
