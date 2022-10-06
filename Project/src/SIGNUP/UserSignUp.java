package SIGNUP;

import DataBase.DataBaseSubClass;

import java.io.Serializable;

public class UserSignUp extends SignUp  implements Serializable {
    private String firstName;
    private String lastName;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    private String idNumber;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public UserSignUp(){

    }
     public UserSignUp(String firstName,String lastName,String idNumber,
             String userName,String passWord){
         //here user Name should be formal name or id number since only
         // allowed members are needed to sign up!

      super(userName,passWord);
      this.firstName=firstName;
      this.lastName=lastName;
      this.idNumber=idNumber;
  }
    public static void saveOnDataBase(UserSignUp user,UserSignUp userDuplicate){
         DataBaseSubClass save=new DataBaseSubClass();
         if(checkValidity(user,userDuplicate)) {
             save.insertValue(user);
         }
    }


}
