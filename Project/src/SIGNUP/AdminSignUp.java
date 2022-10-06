package SIGNUP;


import java.io.Serializable;

public class AdminSignUp extends SignUp implements Serializable {
    private String firstName;
    private String lastName;
    private String officeSpecification;
    private String officeNumber;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOfficeSpecification() {
        return officeSpecification;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }




     public AdminSignUp(){
     }

      public AdminSignUp(String firstName,String lastName,
                         String officeSpecification,String officeNumber
                         ,String userName ,String passWord)
      {

          super(userName,passWord );
          this.firstName=firstName;
          this.lastName=lastName;
          this.officeSpecification=officeSpecification;
          this.officeNumber=officeNumber;

      }

}
