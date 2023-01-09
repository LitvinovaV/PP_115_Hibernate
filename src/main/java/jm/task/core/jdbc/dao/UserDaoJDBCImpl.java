package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connect = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (PreparedStatement preparedStatement =
                     connect.prepareStatement("CREATE TABLE IF NOT EXISTS User " +
                             "(id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                             " name VARCHAR(255) NOT NULL," +
                             " lastName VARCHAR(255) NOT NULL," +
                             " age TINYINT NOT NULL)")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sqlDrop = "DROP TABLE IF EXISTS User";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sqlDrop)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlSaveUser = "INSERT INTO User (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sqlSaveUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User " + name + " " + lastName + " сохранен");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sqlDelete = "DELETE FROM User WHERE id = ?";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sqlDelete)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM User";
        try (ResultSet resultSet = connect.createStatement().executeQuery(query)) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                list.add(user);
            }
            for (User user : list) {
                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String sqlClean = "TRUNCATE TABLE User";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sqlClean)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}