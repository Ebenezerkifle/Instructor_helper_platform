//package LOGIN;
//
//import DataBase.DataBaseSubClass;
//import SIGNUP.AdminSignUp;
//import SIGNUP.SignUp;
//import SIGNUP.UserSignUp;
//import Server.Validity;
//
//import java.util.Scanner;
//
//public class StartLoggingIn {
//
//    public static void start(){
//        Scanner input=new Scanner(System.in);
//        Scanner input1=new Scanner(System.in);
//        System.out.println("The is login page");
//        System.out.println("Enter userName");
//        String userName=input.nextLine();
//        System.out.println("Enter password");
//        String password=input1.nextLine();
//        System.out.println("Enter 'u' if you are user and 'a' if you are admin!");
//        char choice=input.nextLine().charAt(0);
//
//        Login login=new Login(userName,password);
//        if(Validity.checkValidity(login,whoIsLogging(choice,login))){
//            System.out.println("you are successfully logged in!");
//        }
//        else{
//            System.out.println("Incorrect password or user Name! Try again");
//        }
//
//
//    }
//    public static SignUp whoIsLogging(char whoIsLogging,Login login){
//        DataBaseSubClass dataBase=new DataBaseSubClass();
//        SignUp signup=new SignUp();
//        if(whoIsLogging=='a'){
//            AdminSignUp admin=new AdminSignUp();
//            //which means the admin is trying to login.
//            admin= dataBase.fetchAdminInfo(login);
//            signup=admin;
//        }
//        else if(whoIsLogging=='u'){
//            // user is trying to login.
//            UserSignUp user=new UserSignUp();
//            user=dataBase.fetchUserInfo(login);
//            signup=user;
//        }
//        return signup;
//    }
//}
