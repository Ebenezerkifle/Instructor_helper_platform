package DataBase;

import SIGNUP.AdminSignUp;

import java.sql.Connection;

abstract class DataBaseConnection {

    private String dataBaseurl="jdbc:mysql://localhost:3306/sys";
    private String dataBaseusername="root";
    private String dataBasePassWord="167492";



    private String adminTableName="Owner";
    private String userTableName="User";
    private String column1Name="FirstName";
    private String column2Name="LastName";
    private String column3Name="UserName";
    private String column4Name="PassWord";
    private String column5Name="Office_Specification";
    private String column6Name="Office_Number";
    private String column7Name="IdNumber";
    private String userId="UserID";

    public String getUserId() {
        return userId;
    }

    public String getColumn7Name() {
        return column7Name;
    }

    public String getColumn5Name() {
        return column5Name;
    }

    public String getColumn6Name() {
        return column6Name;
    }

    public String getColumn4Name() {
        return column4Name;
    }


    public String getColumn1Name() {
        return column1Name;
    }

    public String getColumn2Name() {
        return column2Name;
    }

    public String getColumn3Name() {
        return column3Name;
    }

    public String getAdminTableName() {
        return adminTableName;
    }

    public String getUserTableName() {
        return userTableName;
    }

    public String getDataBaseurl() {
        return dataBaseurl;
    }

    public String getDataBaseusername() {
        return dataBaseusername;
    }

    public String getDataBasePassWord() {
        return dataBasePassWord;
    }


    abstract public Connection createConnection();
    abstract public void updateAnElement(String password);
    abstract public void insertValue(AdminSignUp admin);
    abstract public void deleteTable(String tableName);

}
