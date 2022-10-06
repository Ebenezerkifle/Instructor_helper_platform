package DataBase;


import LOGIN.Login;
import MYEXCEPTION.MyException;
import SIGNUP.AdminSignUp;
import SIGNUP.UserSignUp;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseSubClass extends DataBaseConnection {


    public int getUserIdentificationNum() {
        return userIdentificationNum;
    }

    public void setUserIdentificationNum(int userIdentificationNum) {
        this.userIdentificationNum = userIdentificationNum;
    }

    private int userIdentificationNum = 100; // initialization.


    public Connection createConnection() {
        Connection connection = null;
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            // loading the driver class file into memory at runtime.
            connection = DriverManager.getConnection(
                    getDataBaseurl(), getDataBaseusername(), getDataBasePassWord());
            // creating connection.

        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return connection;
    }


    public void createNewTable(String query) {
        Connection connection = null;
        PreparedStatement preStatement = null;

        connection = createConnection();
        try {
            preStatement = connection.prepareStatement(query);

            preStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preStatement != null) {
                try {
                    preStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public void createUserIdTable() {
        createAllowedMembersIdTable();

        String query = "CREATE TABLE IF NOT EXISTS UserIdentification(" +
                "ID INT(3) AUTO_INCREMENT ,user_ID INT(5) NOT NULL, PRIMARY KEY (ID,user_ID))";
        createNewTable(query);
    }

    public void createAllowedMembersIdTable() {

        String query = "CREATE TABLE IF NOT EXISTS AllowedMembersId " +
                "(id INT(2) PRIMARY KEY AUTO_INCREMENT ," +
                "First_Name VARCHAR(20),Last_Name VARCHAR(20),IDNumber VARCHAR(20),Section VARCHAR(50))";
        createNewTable(query);
    }


    public void insertIntoUserIdTable() {
        fetchFromUserIdTable();

        Connection connection = null;
        PreparedStatement preStatement = null;

        connection = createConnection();

        try {
            preStatement = connection.prepareStatement("INSERT INTO UserIdentification (user_ID)" +
                    "VALUES (?)");
            preStatement.setInt(1, getUserIdentificationNum());
            preStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void fetchFromUserIdTable() {

        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        connection = createConnection();
        try {
            //SELECT column_name FROM table_name
            //ORDER BY column_name DESC
            //LIMIT 1;
            preStatement = connection.prepareStatement("SELECT User_Id FROM UserIdentification " +
                    "ORDER BY User_Id DESC LIMIT 1 ");
            //preStatement.executeUpdate();
            resultSet = preStatement.executeQuery();
            if (resultSet.next()) {
                setUserIdentificationNum(resultSet.getInt("User_ID") + 1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void createTable(String tableName) {

        createUserIdTable();

        String query = "CREATE TABLE IF NOT EXISTS " +
                tableName + " (id INT(1) PRIMARY KEY NOT NULL AUTO_INCREMENT, " + getColumn1Name() +
                " varchar(30) ," + getColumn2Name() + " VARCHAR(30) ," + getColumn5Name() + " VARCHAR(60),"
                + getColumn6Name() + " VARCHAR(10)," + getColumn3Name() + " VARCHAR(20), "
                + getColumn4Name() + " VARCHAR(20), " + getUserId() + " INT(5) NOT NULL)";
        createNewTable(query);
    }


    public void createUserTable(String tableName) {
        createUserIdTable();

        String query = "CREATE TABLE IF NOT EXISTS " +
                tableName + " (id INT(1) PRIMARY KEY NOT NULL AUTO_INCREMENT, "
                + getColumn1Name() + " varchar(30) ," + getColumn2Name() + " VARCHAR(30) ,"
                + getColumn7Name() + " VARCHAR(60)," + getColumn3Name() +
                " VARCHAR(10)," + getColumn4Name() + " VARCHAR(20), " +
                getUserId() + " INT(5) NOT NULL , Section VARCHAR(50))";
        createNewTable(query);
    }


    public void insertValue(UserSignUp user) {

        createUserTable(getUserTableName());

        insertIntoUserIdTable();

        Connection connection = null;
        PreparedStatement prepared = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = createConnection();
            preparedStatement = connection.prepareStatement("SELECT Section FROM AllowedMembersId WHERE " + getColumn7Name() +
                    "=?");
            preparedStatement.setString(1, user.getIdNumber());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String section = resultSet.getString("Section");
            System.out.println("yet nen  lkjh" + section);
            prepared = connection.prepareStatement("INSERT INTO  " + getUserTableName() +
                    "(" + getColumn1Name() + ", " + getColumn2Name() + ", " + getColumn7Name()
                    + " ," + getColumn3Name() + ", " + getColumn4Name() + ", " + getUserId() + ", Section)" +
                    "VALUES(?,?,?,?,?,?,?)");
            prepared.setString(1, user.getFirstName());
            prepared.setString(2, user.getLastName());
            prepared.setString(3, user.getIdNumber());
            prepared.setString(4, user.getUserName());
            prepared.setString(5, user.getPassWord());
            prepared.setInt(6, getUserIdentificationNum());
            prepared.setString(7, section);
            prepared.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (prepared != null) {
                try {
                    prepared.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertValue(AdminSignUp admin) {

        createTable(getAdminTableName());

        insertIntoUserIdTable();

        Connection connection = null;
        PreparedStatement prepared = null;

        try {
            connection = createConnection();
            prepared = connection.prepareStatement("INSERT INTO "
                    + getAdminTableName() +
                    "(" + getColumn1Name() + ", " + getColumn2Name() +
                    ", " + getColumn5Name() + ", " + getColumn6Name() + ", "
                    + getColumn3Name() + ", " + getColumn4Name() + ", " + getUserId() + ")" +
                    "VALUES(?,?,?,?,?,?,?)");
            prepared.setString(1, admin.getFirstName());
            prepared.setString(2, admin.getLastName());
            prepared.setString(3, admin.getOfficeSpecification());
            prepared.setString(4, admin.getOfficeNumber());
            prepared.setString(5, admin.getUserName());
            prepared.setString(6, admin.getPassWord());
            prepared.setInt(7, getUserIdentificationNum());
            prepared.executeUpdate();
            //  System.out.println("value inserted.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (prepared != null) {
                try {
                    prepared.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void deleteTable(String tableName) {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = createConnection();
        try {
            statement = connection.prepareStatement("DROP TABLE " + tableName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String getGroupOfUser(int userId) {
        //SELECT Section FROM sys.User WHERE UserId=109;
        String query = "SELECT Section FROM " + getUserTableName() + " WHERE UserId=?";

        String section = null;
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        connection = createConnection();
        try {
            preStatement = connection.prepareStatement(query);
            preStatement.setInt(1, userId);

            resultSet = preStatement.executeQuery();

            resultSet.next();
            section = resultSet.getString("Section");
            System.out.println(section);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return section;
    }

    public AdminSignUp fetchAdminInfo(Login login) {


        AdminSignUp admin = new AdminSignUp();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            if (checkForExistenceOfATable(getAdminTableName())) {

                connection = createConnection();
                statement = connection.prepareStatement(
                        "SELECT * FROM " + getAdminTableName() +
                                " WHERE " + getColumn3Name() + "=? AND "
                                + getColumn4Name() + "=?"
                );
                //a statement created.

                statement.setString(1, login.getUserName());
                statement.setString(2, login.getPassWord());

                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    // next() points to the values in the resultSet and if
                    // i call next() again i started to point to second value of resultSet.
                    admin.setUserName(resultSet.getString(getColumn3Name()));
                    admin.setPassWord(resultSet.getString(getColumn4Name()));
                } else {
                    admin = null;
                    throw new MyException("Incorrect user name or password!");
                }
            } else {
                admin = null;
            }
        } catch (MyException myException) {
            System.out.println(myException);
        } catch (NullPointerException nullPointer) {
            System.out.println("Incorrect user Name or password!");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }

        return admin;

    }

    public UserSignUp fetchUserInfo(Login login) {

        UserSignUp user = new UserSignUp();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            if (checkForExistenceOfATable(getUserTableName())) {

                connection = createConnection();
                statement = connection.prepareStatement(
                        "SELECT * FROM " + getUserTableName()
                                + " WHERE " + getColumn3Name() + "=? AND " +
                                getColumn4Name() + "=?");
                //a statement created.

                statement.setString(1, login.getUserName());
                statement.setString(2, login.getPassWord());
                resultSet = statement.executeQuery();


                if (resultSet.next()) {
                    user.setUserName(resultSet.getString(getColumn3Name()));
                    user.setPassWord(resultSet.getString(getColumn4Name()));
                } else {
                    user = null;
                }
            } else {
                user = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }
        return user;
    }

    public String getUserSection(String userName) {
        String section = null;
        String query = "SELECT Section FROM " + getUserTableName() + " WHERE " + getColumn3Name() + "=?";
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        connection = createConnection();
        try {
            preStatement = connection.prepareStatement(query);
            preStatement.setString(1, userName);
            resultSet = preStatement.executeQuery();
            if (resultSet.next()) {
                section = resultSet.getString("Section");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return section;
    }

    public int getMyId(String tableName, String idNumber) {
        String query = "SELECT " + getUserId() + " FROM " + tableName + " WHERE " + getColumn7Name() + "=?";
        return getId(query, idNumber);
    }

    public int getInstructorUserId() {
        int userId = 0;
        String query = "SELECT " + getUserId() + " FROM " + getAdminTableName();
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        connection = createConnection();
        try {
            preStatement = connection.prepareStatement(query);
            resultSet = preStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt(getUserId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public int getId(String query, String column) {
        int userId = 0;
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        connection = createConnection();
        try {
            preStatement = connection.prepareStatement(query);
            preStatement.setString(1, column);
            resultSet = preStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt(getUserId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public int getUserId(String tableName, String userName) {
        String query = "SELECT " + getUserId() + " FROM " + tableName + " WHERE " + getColumn3Name() + "=?";
        return getId(query, userName);
    }

    public boolean checkForExistenceOfATable(String tableName) {
        // This function checks if there is a table created or not.
        /*
         *   This is because went to have only one admin and if there is an attempt
         *   to sign up it rejects.
         *
         */
        boolean exist = false;
        Connection connection = null;
        ResultSet table = null;
        try {
            connection = createConnection();
            java.sql.DatabaseMetaData dataBase = connection.getMetaData();

            table = dataBase.getTables(
                    null, null, tableName, null);
            while (table.next()) {
                String tName = table.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    exist = true;
                    break;
                }
            }
            /*
             *  getMetaDat ?
             *
             *
             */

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if (table != null) {
                try {
                    table.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            return exist;
        }

    }

    public void updateAnElement(String newPassWord) {
        Connection connection = null;
        PreparedStatement statement = null;
        connection = createConnection();
        try {
            statement = connection.prepareStatement(
                    "UPDATE " + getAdminTableName() + " SET " + getColumn4Name() + "="
                            + newPassWord + " WHERE id=1");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void insertIntoAllowedMembersIdTable(
            ArrayList<String> allowedMembersId) {
        createAllowedMembersIdTable();
        Connection connection = null;
        PreparedStatement preStatement = null;

        connection = createConnection();

        try {
            String section = allowedMembersId.get(0);
            allowedMembersId.remove(0);
            for (String memberInfo : allowedMembersId) {
                String[] memberInfoSplit = memberInfo.split("\\s");
                preStatement = connection.prepareStatement(
                        "INSERT INTO AllowedMembersId (First_Name,Last_Name,IDNUmber,Section)" +
                                "VALUES (?,?,?,?)");
                preStatement.setString(1, memberInfoSplit[0]);
                preStatement.setString(2, memberInfoSplit[1]);
                preStatement.setString(3, memberInfoSplit[2]);
                preStatement.setString(4, section);
                preStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public UserSignUp fetchDataFromMembersTable(UserSignUp userSignUp) {

        UserSignUp user = new UserSignUp();
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        connection = createConnection();
        if (checkForExistenceOfATable("AllowedMembersId")) {
            try {
                preStatement = connection.prepareStatement(
                        "SELECT * FROM AllowedMembersId WHERE IDNumber=?");
                preStatement.setString(1, userSignUp.getIdNumber());
                resultSet = preStatement.executeQuery();
                if (resultSet.next()) {
                    user.setIdNumber(resultSet.getString("IDNumber"));
                    user.setFirstName(resultSet.getString("First_Name"));
                    user.setLastName(resultSet.getString("Last_Name"));
                    System.out.println("fetching data  " + user.getIdNumber());
                } else {
                    user.setIdNumber("null");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            user.setIdNumber("null");
        }

        return user;
    }

    public ArrayList<ArrayList<String>> fetchMembers() {

        ArrayList<ArrayList<String>> membersArray = new ArrayList<ArrayList<String>>();
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSet1 = null;
        connection = createConnection();
        try {
            if(checkForExistenceOfATable("AllowedMembersId") && checkForExistenceOfATable("Groups")) {
                preStatement = connection.prepareStatement("SELECT Name From Groups");
                resultSet1 = preStatement.executeQuery();
                while (resultSet1.next()) {
                    ArrayList<String> group1 = new ArrayList<>();
                    String section = resultSet1.getString("Name");
                    group1.add(section);
                    preStatement = connection.prepareStatement("SELECT * FROM AllowedMembersId WHERE " +
                            "Section=?");
                    preStatement.setString(1, section);
                    resultSet = preStatement.executeQuery();
                    while (resultSet.next()) {

                        group1.add(resultSet.getString("First_Name") + " "
                                + resultSet.getString("Last_Name") + " " +
                                resultSet.getString("IDNumber"));
                    }
                    membersArray.add(group1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membersArray;
    }

    public String getUserIdNumber(int userId) {
        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        connection = createConnection();
        String query = "SELECT " + getColumn7Name() + "  FROM " + getUserTableName() + " WHERE " +
                getUserId() + "=?";
        String idNumber = null;
        try {
            preStatement = connection.prepareStatement(query);
            preStatement.setInt(1, userId);
            resultSet = preStatement.executeQuery();
            resultSet.next();
            idNumber = resultSet.getString(getColumn7Name());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idNumber;
    }


    public void saveResult(ArrayList<String> results) {
        String myColumn = results.get(0) + "_" + results.get(1);
        String query = "ALTER TABLE " + getUserTableName() + " ADD COLUMN " + myColumn + " INT(3)";
        createNewTable(query);
        Connection connection = null;
        PreparedStatement preStatement = null;

        connection = createConnection();

        try {
            for (int i = 2; i < results.size(); i++) {
                String[] resultSplit = results.get(i).split("\\s");
                String query2 = "UPDATE " + getUserTableName() + "  SET  " + myColumn + "=?"
                        + "  WHERE  " + getColumn7Name() + "=?";

                preStatement = connection.prepareStatement(query2);

                preStatement.setString(1, resultSplit[0]);
                preStatement.setString(2, resultSplit[1]);
                preStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<String> getGradeReport(int userId) {
        String query = "SELECT * FROM " + getUserTableName();
        ArrayList<String> gradeReport = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preStatement = null;
        PreparedStatement preStatement2 = null;
        connection = createConnection();
        ResultSet resultSet = null;
        try {
            preStatement = connection.prepareStatement(query);
            resultSet = preStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = (ResultSetMetaData) resultSet.getMetaData();
            int col = resultSetMetaData.getColumnCount();

            for (int i = 1; i <= col; i++) {
                String col_name = resultSetMetaData.getColumnName(i);
                if (col_name.contains("_")) {
                    String query2 = "SELECT " + col_name + " FROM " + getUserTableName() + " WHERE " + getUserId() + "=?";
                    preStatement2 = connection.prepareStatement(query2);
                    preStatement2.setInt(1, userId);
                    resultSet = preStatement.executeQuery();
                    if (resultSet.next()) {
                        gradeReport.add(col_name + " " + resultSet.getInt(col_name));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gradeReport;
    }

    public ArrayList<ArrayList<String>> getGradeReportOfAll() {
        ArrayList<ArrayList<String>> gradeReport=new ArrayList<>();
        String query = "SELECT * FROM " + getUserTableName();
        Connection connection = null;
        PreparedStatement preStatement = null;
        connection = createConnection();
        ResultSet resultSet = null;
        try {
            preStatement = connection.prepareStatement(query);
            resultSet = preStatement.executeQuery();
            while(resultSet.next()){
                String name=resultSet.getString(getColumn1Name());
                String fName=resultSet.getString(getColumn2Name());
                String idNumber=resultSet.getString(getColumn7Name());
                ArrayList<String> grade=getGradeReport(resultSet.getInt(getUserId()));
                grade.add(0,idNumber);
                grade.add(0,fName);
                grade.add(0,name);
                gradeReport.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gradeReport;
    }
}
