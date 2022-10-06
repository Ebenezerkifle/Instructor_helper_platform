package SIGNUP;

import java.io.Serializable;

public class SignUp  implements Serializable {
    public void setUserName(String name) {
        this.userName = name;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }



    private String userName; // it is better if it is formal name.
    private String passWord;
    private String passWordHint;

    public SignUp(){
        this.userName=null;
        this.passWord=null;
    }
    public SignUp(String userName, String passWord){
        this.userName=userName;
        this.passWord=passWord;
    }

    public void setPassWordHint(String passWordHint) {
        this.passWordHint = passWordHint;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getPassWordHint() {
        return passWordHint;
    }

    public static boolean checkValidity(SignUp user1,SignUp user2){
        boolean valid=false;
        if(user1.getUserName().equals(user2.getUserName())){
            if(user1.getPassWord().equals(user2.getPassWord())){
                if(user1.getPassWordHint().equals(user2.getPassWordHint())){
                    valid=true;
                }
            }
        }

        return valid;
    }


}
