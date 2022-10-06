package DataBase;

import LOGIN.Login;
import MYEXCEPTION.MyException;
import SIGNUP.AdminSignUp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordResetDataBase extends DataBaseSubClass {

    public AdminSignUp fetchAdminInfo(Login login){
        AdminSignUp admin = new AdminSignUp();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = createConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM " + getAdminTableName() +
                            " WHERE " + getColumn1Name() + "=? AND " + getColumn2Name() + "=?"
            );
            //a statement created.

            statement.setString(1, login.getFirstNameName());
            statement.setString(2, login.getLastName());

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // next() points to the values in the resultSet and if
                // i call next() again i started to point to second value of resultSet.
                admin.setFirstName(resultSet.getString(getColumn1Name()));
                admin.setLastName(resultSet.getString(getColumn2Name()));
                admin.setUserName(resultSet.getString(getColumn3Name()));
            } else {
                admin = null;
                throw new MyException("Incorrect user name or password!");
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
}
