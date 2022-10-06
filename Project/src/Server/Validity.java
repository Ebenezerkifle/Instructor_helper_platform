package Server;

import LOGIN.Login;
import SIGNUP.SignUp;

public class Validity {

    public static boolean checkLoginValidity(Login loginObj, SignUp signUpObj){
        boolean valid=false;

        if(signUpObj!=null) {

            if (signUpObj.getUserName().equals(loginObj.getUserName()) &&
                    signUpObj.getPassWord().equals(loginObj.getPassWord())) {
                    valid = true;

            }
        }
        return valid;
    }
}
