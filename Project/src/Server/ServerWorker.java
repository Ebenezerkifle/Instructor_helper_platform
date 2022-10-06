package Server;

import DataBase.ChatDataBase;
import DataBase.DataBaseSubClass;
import DataBase.PasswordResetDataBase;
import LOGIN.Login;
import SIGNUP.AdminSignUp;
import SIGNUP.SignUp;
import SIGNUP.UserSignUp;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class ServerWorker extends Thread {

    private Socket clientSocket;
    private String userName;
    private ServerNextToMain server;
    private ObjectOutputStream outputObject;
    private ObjectInputStream inputObject;
    private int userId;
    private String userSection;
    private String idNumber;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getUserSection() {
        return userSection;
    }

    public void setUserSection(String userSection) {
        this.userSection = userSection;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public ServerNextToMain getServer() {
        return server;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
    public Socket getClientSocket() {
        return clientSocket;
    }
    public ServerWorker(ServerNextToMain server , Socket clientSocket){
        this.clientSocket=clientSocket;
        this.server=server;
    }
    @Override
    public void run() {

            clientHandler();

    }

    public void clientHandler() {
        try {
            this.outputObject = new ObjectOutputStream(getClientSocket().getOutputStream());
            this.inputObject = new ObjectInputStream((getClientSocket().getInputStream()));

            while(clientSocket.isConnected()) {
                String requestType = (String) inputObject.readObject();
                if (requestType.equals("login")) {
                    loginHandler();
                } else if (requestType.equals("msg")) {
                    messageHandler();
                } else if (requestType.equals("UserSignUp")) {
                    userSignUpHandler();
                } else if (requestType.equals("AdminSignUp")) {
                    adminSignUpHandler();

                } else if (requestType.equals("forgotPassword")) {
                    forgotPasswordHandler();
                } else if (requestType.equals("deleteAccount")) {
                    deleteAccountHandler();
                } else if (requestType.equals("changePassword")) {
                    passwordChangeHandler();
                } else if(requestType.equals("addMembers")){
                    newMembersHandler();
                } else if(requestType.equals("addGroup")) {
                    addGroupsHandler();
                }else if(requestType.equals("getUserID")){
                    userIdHandler();
                }
                else if(requestType.equals("groups")){
                    fetchGroups();
                }else if(requestType.equals("mySection")){
                    fetchSection();
                }else if(requestType.equals("members")){
                    fetchMembers();
                }
                else if(requestType.equals("myIdNumber")){
                    fetchIdNumber();
                }
                else if(requestType.equals("getInstructorId")){
                    getInstructorId();
                }
                else if(requestType.equals("saveResult")){
                    saveResultHandler();
                }
                else if(requestType.equals("gradeReport")){
                    fetchGradeReport();
                }
                else if(requestType.equals("getGradeReport")){
                    getGradeReport();
                }
                else if(requestType.equals("sendFile")){
                    sendFile();
                }
                else if(requestType.equals("LogOut")){
                    logOutHandler();
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void logOutHandler() throws IOException {
        ArrayList<ServerWorker> worker=server.getWorkers();
        worker.remove(this);
        outputObject.close();
        inputObject.close();
        clientSocket.close();
    }

    private void getGradeReport() throws IOException, ClassNotFoundException {
        DataBaseSubClass dataBase=new DataBaseSubClass();
        ArrayList<ArrayList<String>> gradReport=dataBase.getGradeReportOfAll();
        ArrayList<String> messageType=new ArrayList<>();
        messageType.add("gradeReport");
        messageType.add("ouch");
        outputObject.writeObject(messageType);
        String ready=(String)inputObject.readObject();
        if(ready.equals("ready")){
            outputObject.writeObject(gradReport);
        }
    }

    private void fetchGradeReport() throws IOException {
        DataBaseSubClass dataBase=new DataBaseSubClass();
        ArrayList<String> grade=dataBase.getGradeReport(getUserId());
        grade.add(0,"gradeReport");
        outputObject.writeObject(grade);
    }

    private void saveResultHandler() throws IOException, ClassNotFoundException {
        DataBaseSubClass dataBase=new DataBaseSubClass();
        dataBase.saveResult((ArrayList<String>) inputObject.readObject());
    }

    private void getInstructorId() throws IOException {
        DataBaseSubClass dataBase=new DataBaseSubClass();
        outputObject.writeObject(dataBase.getInstructorUserId());
    }

    private void fetchIdNumber() {
        DataBaseSubClass dataBase=new DataBaseSubClass();
        setIdNumber(dataBase.getUserIdNumber(this.userId));
    }

    private void fetchMembers() throws IOException {
        DataBaseSubClass dataBase=new DataBaseSubClass();
        ArrayList<ArrayList<String>> members=dataBase.fetchMembers();
        outputObject.writeObject(members);
    }

    private void fetchSection() throws IOException, ClassNotFoundException {
        DataBaseSubClass dataBase=new DataBaseSubClass();
        String userName= (String) inputObject.readObject();
        String section=dataBase.getUserSection(userName);
        setUserSection(section);
        outputObject.writeObject(section);
    }

    private void fetchGroups() {
        ChatDataBase dataBase=new ChatDataBase();
        ArrayList<String> groups=new ArrayList<>();
        groups.add("groups");
        groups=dataBase.fetchGroups();
        try {
            outputObject.writeObject(groups);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void userIdHandler() {
        DataBaseSubClass dataBase=new DataBaseSubClass();
        try {
            ArrayList<String> userInfo= (ArrayList<String>) inputObject.readObject();
            int userId=dataBase.getUserId(userInfo.get(0),userInfo.get(1));
            this.userId=userId;
            outputObject.writeObject(userId);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addGroupsHandler(){
    ChatDataBase dataBase=new ChatDataBase();
        try {

            String group=(String)inputObject.readObject();
            dataBase.insertIntoGroupsTable(group);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void newMembersHandler() {
        DataBaseSubClass dataBase=new DataBaseSubClass();
        try {
            ArrayList<String> allowedMembers= (ArrayList<String>) inputObject.readObject();
            dataBase.insertIntoAllowedMembersIdTable(allowedMembers);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void adminSignUpHandler() {
        DataBaseSubClass dataBase=new DataBaseSubClass();
        AdminSignUp admin= null;
        try {
            if (!dataBase.checkForExistenceOfATable(dataBase.getAdminTableName())) {
                outputObject.writeObject("possible");
                admin = (AdminSignUp) inputObject.readObject();
                dataBase.insertValue(admin);
                outputObject.writeObject("successfully saved.");
            }
            else{
                outputObject.writeObject("you can't SignUp!");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void userSignUpHandler() {
    DataBaseSubClass dataBase=new DataBaseSubClass();
        UserSignUp user= null;
        try {
            user = (UserSignUp)inputObject.readObject();
            UserSignUp userSignUp=dataBase.fetchDataFromMembersTable(user);
            if(userSignUp.getIdNumber().equals(user.getIdNumber())){
                userSignUp.setPassWord(user.getPassWord());
                userSignUp.setUserName(user.getUserName());
                dataBase.insertValue(userSignUp);
                outputObject.writeObject("successfully saved.");
            }
            else{
                outputObject.writeObject("You are not Allowed!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void passwordChangeHandler()
            throws IOException, ClassNotFoundException {
      String newPassword=(String)inputObject.readObject();
      DataBaseSubClass dataBase=new DataBaseSubClass();
      dataBase.updateAnElement(newPassword);
      outputObject.writeObject("your new password is successfully saved.");
    }

    private void forgotPasswordHandler() throws IOException, ClassNotFoundException {
        PasswordResetDataBase dataBase=new PasswordResetDataBase();
        Login login=(Login)inputObject.readObject();
        AdminSignUp admin=dataBase.fetchAdminInfo(login);
        if(checkValidity(admin,login)){
         outputObject.writeObject("valid");
        }
        else{
            outputObject.writeObject("Invalid");
        }
    }

    private void deleteAccountHandler() {
        String tableName= null;
        try {
            tableName = (String)inputObject.readObject();
            DataBaseSubClass  dataBase=new DataBaseSubClass();
            dataBase.deleteTable(tableName);
            outputObject.writeObject("you account is deleted.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void sendFile() throws IOException, ClassNotFoundException {
        ArrayList<ServerWorker> workers = server.getWorkers();

        ArrayList<String> messageContent=(ArrayList<String>)inputObject.readObject();
        if(messageContent.get(0).equals("Group")) {
            for (ServerWorker worker : workers) {
                if(worker.getUserId()!=this.getUserId() && messageContent.get(1).equals(worker.getUserSection())){
                    messageContent.remove(messageContent.get(1));
                    messageContent.remove(messageContent.get(0));
                    messageContent.add(0,"File"); //1     2// is fileName.
                    worker.outputObject.writeObject(messageContent);
                    worker. outputObject.writeObject(inputObject.readObject());  // actual file.
                }
            }
        }
        else if(messageContent.get(0).equals("Private")){
            for(ServerWorker worker:workers) {
                if(worker.getUserId()!=this.getUserId() && Integer.parseInt(messageContent.get(1))==worker.getUserId()) {
                    messageContent.remove(messageContent.get(1));
                    messageContent.remove(messageContent.get(0));
                    messageContent.add(0, "File"); // 1st
                    messageContent.add(1, this.getIdNumber()); //2nd
                    worker.outputObject.writeObject(messageContent);
                    worker.outputObject.writeObject(inputObject.readObject());  // actual file.
                    break;
                }
            }
        }
    }

    public void messageHandler() {
            ArrayList<ServerWorker> workers = server.getWorkers();
            try {
                ArrayList<String> messageContent= (ArrayList<String>) inputObject.readObject();
                String chatType=messageContent.get(0);
                //message received from instructor and going to sent to a group.
                if(chatType.equals("Group")) {
                    String group = messageContent.get(1);
                    for (ServerWorker worker : workers) {
                        messageContent.remove(group);
                        if(worker.getUserId()!=this.getUserId() && group.equals(worker.getUserSection())){
                            worker.outputObject.writeObject(messageContent);
                        }
                    }
                }
                // message received from instructor going to sent to an individual.
                else if(chatType.equals("Private")){
                    String identification=messageContent.get(1);
                    messageContent.remove(identification);
                    for (ServerWorker worker : workers) {
                        if (worker.getUserId()!=(this.getUserId()) && worker.getIdNumber().equals(identification)) {
                            worker.outputObject.writeObject(messageContent);
                            break;
                        }
                    }
                }
                //message received from user and going to sent to an instructor.
                else if(chatType.equals("Private2")){
                    for (ServerWorker worker: workers) {
                        if(worker.getUserId()!=(this.getUserId()) &&
                                worker.getUserId()==Integer.parseInt(messageContent.get(1))){
                            // finding an instructor by using id.

                            messageContent.remove(messageContent.get(1));
                            messageContent.remove(messageContent.get(0));
                            messageContent.add(this.getIdNumber());
                            worker.outputObject.writeObject(messageContent);
                        }
                    }

                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

    }

    public void loginHandler() throws IOException, ClassNotFoundException {
        String whoIsLogging=(String)inputObject.readObject();
            Login loginInfo = (Login) inputObject.readObject();
            if (Validity.checkLoginValidity(loginInfo, whoIsLogging(loginInfo,whoIsLogging))) {
                String msg = "valid";
                outputObject.writeObject(msg);
            } else {
                String msg = "invalid attempt! Pleas try with valid one.";
                outputObject.writeObject(msg);
            }

    }

    public static SignUp whoIsLogging(Login login,String whoIsLogging){
        DataBaseSubClass dataBase=new DataBaseSubClass();
        if(whoIsLogging.equals("admin")) {
            SignUp admin = dataBase.fetchAdminInfo(login);
            return admin;
        }
        else {
            SignUp user=dataBase.fetchUserInfo(login);
            return user;
        }
    }

    public boolean checkValidity(SignUp user, Login login){
        boolean valid=false;
        if(user!=null) {
            if (user.getUserName().equals(login.getUserName()) &&
                    user.getPassWord().equals(login.getPassWord())) {
                valid = true;
            }
        }
        return valid;
    }

}