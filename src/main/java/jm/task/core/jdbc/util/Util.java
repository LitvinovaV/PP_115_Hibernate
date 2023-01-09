package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root92";
    private static SessionFactory sessionFactory; // Hibernate

    // JDBC
    public static Connection getConnection() {
        Connection connection = null;
        Driver driver;
        try {
            driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Hibernate
    public static SessionFactory getConnectionHibernate() {
        if (sessionFactory == null) {
            try {
                Properties properties = new Properties();
                properties.setProperty("hibernate.connection.url", URL);
                properties.setProperty("hibernate.connection.username", USERNAME);
                properties.setProperty("hibernate.connection.password", PASSWORD);
                properties.setProperty("hibernate.driver_class", "com.mysql.cj.jdbc.Driver");
                properties.setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect");
                properties.setProperty("hibernate.show_sql", "true");
                properties.setProperty("hibernate.current_session_context_class", "thread");
                sessionFactory = new Configuration()
                        .addAnnotatedClass(User.class)
                        .addProperties(properties)
                        .buildSessionFactory();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
            return sessionFactory;

    }
        // реализуйте настройку соеденения с БД
    }
