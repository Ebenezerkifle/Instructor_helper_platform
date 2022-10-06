package LOGIN;


import java.io.Serializable;

public class Login  implements Serializable {

    private String firstName;
    private String passWord;
    private String lastName;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Login() {
        this.userName = null;
        this.passWord = null;
    }
    public Login(String userName, String passWord){
        this.userName=userName;
        this.passWord=passWord;
    }

    public void setFirstName(String name) {
        firstName = name;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFirstNameName() {
        return firstName;
    }

    public String getPassWord() {
        return passWord;
    }




}
