package SIGNUP;

import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StartSignUpFx {
    private Button button;
    private Text signUp;
    private Text userName;
    private TextField userNameField;
    private Text password;
    private PasswordField passwordField;
    private Label label;
    private ChoiceBox choiceBox;
    private MenuButton menuButton;
    private Text choose;

    public Label getLabel() {
        return label;
    }

    public TextField getUserNameField() {
        return userNameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public MenuButton getMenuButton() {
        return menuButton;
    }

    public Text getChoose() {
        return choose;
    }

    public StartSignUpFx(){
        this.button=new Button("Done");
        this.password=new Text("password");
        this.signUp=new Text("Sign Up");
        this.userName=new Text("User Name");
        this.passwordField=new PasswordField();
        this.userNameField=new TextField();
        this.label=new Label();
        this.menuButton=new MenuButton("choose");
        //this.choiceBox=new ChoiceBox();
        this.choose=new Text("Who are you?");
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

    public ChoiceBox getChoiceBox() {
        return choiceBox;
    }

    public Group startSignUp(){

        //SignUp text.
        getSignUp().setFont(new Font(20));
        getSignUp().setLayoutX(150);
        getSignUp().setLayoutY(40);

        // userName text.
        getUserName().setFont(new Font(15));
        getUserName().setLayoutX(10);
        getUserName().setLayoutY(60);

        // userName field.
        getUserNameField().setLayoutX(15);
        getUserNameField().setLayoutY(80);

       //password text.
        getPassword().setFont(new Font(15));
        getPassword().setLayoutX(10);
        getPassword().setLayoutY(135);

        //password field.
        getPasswordField().setLayoutX(15);
        getPasswordField().setLayoutY(155);

        // Button.
        getButton().setLayoutY(300);
        getButton().setLayoutX(300);
        getButton().setTextFill(Color.gray(0));

        //message for invalid entry.
        getLabel().setFont(new Font(15));
        getLabel().setTextFill(Color.RED);
        getLabel().setLayoutX(30);
        getLabel().setLayoutY(300);

        // choose form admin and user.
        getMenuButton().getItems().addAll(new MenuItem("Admin"),
                new MenuItem("User"));
        getMenuButton().setLayoutX(30);
        getMenuButton().setLayoutY(240);

        // text field to help to choose from admin and user.
        getChoose().setLayoutX(10);
        getChoose().setLayoutY(220);


        Group root=new Group(
                getSignUp(),getUserNameField(),getPasswordField(),getPassword(),
                getUserName(),getButton(),getMenuButton(),getChoose());
        return root;
    }
}
