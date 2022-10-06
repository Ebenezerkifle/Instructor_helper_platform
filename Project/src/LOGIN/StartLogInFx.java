package LOGIN;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StartLogInFx {

    private Button button;
    private Text signUp;
    private Text userName;
    private TextField userNameField;
    private Text password;
    private TextField passwordField;

    public StartLogInFx(){
        this.button=new Button("Done");
        this.password=new Text("password");
        this.signUp=new Text("Sign Up");
        this.userName=new Text("User Name");
    }

    public Button getButton() {
        return button;
    }

    public Text getSignUp() {
        return signUp;
    }

    public Text getUserName() {
        return userName;
    }

    public Text getPassword() {
        return password;
    }

    public Group startSignUp(){
        getSignUp().setFont(new Font(20));
        getSignUp().setLayoutX(150);
        getSignUp().setLayoutY(40);

        // Text userName=new Text("User Name.");
        getUserName().setFont(new Font(15));
        getUserName().setLayoutX(10);
        getUserName().setLayoutY(60);

        TextField userNameField =new TextField();
        userNameField.setLayoutX(15);
        userNameField.setLayoutY(80);

        Text password=new Text("password.");
        password.setFont(new Font(15));
        password.setLayoutX(10);
        password.setLayoutY(135);

        TextField passwordField =new TextField();
        passwordField.setLayoutX(15);
        passwordField.setLayoutY(155);


        Button button=new Button("Done");
        button.setLayoutY(200);
        button.setLayoutX(300);
        button.setTextFill(Color.gray(0));
        Group root=new Group(
                button,signUp,passwordField,password,userNameField,userName);
        return root;
    }
}

