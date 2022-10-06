//package SIGNUP;
//
//import DataBase.DataBaseSubClass;
//
//import java.util.Scanner;
//
//public class StartSigningUp {
//
//
//    public static void start(){
//        DataBaseSubClass dataBase=new DataBaseSubClass();
//        Scanner input=new Scanner(System.in);
//        System.out.println("If you are an admin enter 'a' and if you are user enter 'u'.");
//        char choice=input.nextLine().charAt(0);
//        switch(choice){
//            case 'a':
//                  dataBase.insertValue(adminSignUp());
//               // System.out.println("you are successfully signed up!");
//                  break;
//            case 'u':
//                 dataBase.insertValue(userSignUp());
//               // System.out.println("you are successfully signed up!");
//                break;
//            default:
//                System.out.println("Try again later!");
//                break;
//        }
//    }
//    public static AdminSignUp adminSignUp(){
//        Scanner input4=new Scanner(System.in);
//        SignUp signUp=commonForBoth();
//        //AdminSignUp admin= (AdminSignUp) commonForBoth();
//        System.out.println("Enter your Recovery email.");
//        String email=input4.nextLine();
//        //admin.setRecoveryEmail(input4.nextLine());
//        AdminSignUp admin=new AdminSignUp(signUp.getName()
//                ,signUp.getPassWord(),signUp.getPassWordHint(),email);
//
//        return admin;
//    }
//    public static UserSignUp userSignUp(){
//        SignUp signUp=commonForBoth();
//        UserSignUp user=new UserSignUp(
//                signUp.getName(),
//                signUp.getPassWord(),
//                signUp.getPassWordHint());
//       return user;
//    }
//    public static SignUp commonForBoth(){
//        Scanner input1=new Scanner(System.in);
//        Scanner input2=new Scanner(System.in);
//        Scanner input3=new Scanner(System.in);
//        SignUp signUp=new SignUp();
//
//        System.out.println("This is sign up page!");
//        System.out.println("Enter your Name.");
//        signUp.setName(input1.nextLine());
//        System.out.println("Enter your password.");
//        signUp.setPassWord(input2.nextLine());
//        System.out.println("Enter your password Hint.");
//        signUp.setPassWordHint(input3.nextLine());
//        return signUp;
//    }
//}
