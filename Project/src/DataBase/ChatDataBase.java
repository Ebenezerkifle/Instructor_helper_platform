package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatDataBase extends DataBaseSubClass {
    private String group="Groups";
    private String name="Name";

    public void createGroupsTable(){
        Connection connection=null;
        PreparedStatement preStatement=null;


        // assigning the return value form getConnection method to and object of Connection class.
        try {
            connection=createConnection();
            preStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " +
                    group + " (ID INT(3) PRIMARY KEY NOT NULL AUTO_INCREMENT, "+name+" VARCHAR(60), " +
                    "Time DATETIME DEFAULT CURRENT_TIMESTAMP)");
            preStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(connection!= null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(preStatement!=null){
                try {
                    preStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void insertIntoGroupsTable(String groupName){
        createGroupsTable();
        Connection connection=null;
        PreparedStatement prepared=null;

        try{
            connection=createConnection();
            prepared =connection.prepareStatement("INSERT INTO  " +group+
                    "(" +name+")" +
                    "VALUES(?)");
            prepared.setString(1,groupName);
            prepared.executeUpdate();


        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(prepared!=null){
                try {
                    prepared.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection !=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public ArrayList<String> fetchGroups(){

        ArrayList<String> groups=new ArrayList<>();

        Connection connection = null;
        PreparedStatement preStatement = null;
        ResultSet resultSet=null;
        connection =createConnection();
        if(checkForExistenceOfATable(group)) {
            try {
                preStatement = connection.prepareStatement(
                        "SELECT Name FROM Groups");
                resultSet = preStatement.executeQuery();
                while(resultSet.next()) {
                    groups.add(resultSet.getString(name));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return groups;
    }


    public void createChatTable(String chatName,String columnName){
        Connection connection=null;
        PreparedStatement preStatement=null;


        // assigning the return value form getConnection method to and object of Connection class.
        try {
            connection=createConnection();
            preStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " +
                    chatName + " (id INT(3) PRIMARY KEY NOT NULL AUTO_INCREMENT, Time DATETIME DEFAULT CURRENT_TIMESTAMP , "+columnName+" TEXT)");
            preStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(connection!= null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(preStatement!=null){
                try {
                    preStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void insertMessage(String chatName,String columnName,String valueToBeInserted){
        createChatTable(chatName,columnName);
        Connection connection=null;
        PreparedStatement prepared=null;

        try{
            connection=createConnection();
            prepared =connection.prepareStatement("INSERT INTO  " +chatName+
                    "(" +columnName+")" +
                    "VALUES(?)");
            prepared.setString(1,valueToBeInserted);
            prepared.executeUpdate();


        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(prepared!=null){
                try {
                    prepared.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection !=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}