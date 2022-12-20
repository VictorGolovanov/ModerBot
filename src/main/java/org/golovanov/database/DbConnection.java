package org.golovanov.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

    // тут надо как-то переделать, чтобы брать данные из файла application.properties или может брать из System.getProperty()

    private static Connection connection;


    private static final String DB_URL_STATIC = "jdbc:mysql://localhost:3306/";

    private static final String DB_NAME_STATIC = "moderbot";

    private static final String DB_USERNAME_STATIC = "username";

    private static final String DB_PASS_STATIC = "password";


    public static Connection getConnection() {
        if (connection == null) {
            Properties properties = new Properties();
            try {
                connection = DriverManager
                        .getConnection(DB_URL_STATIC + DB_NAME_STATIC + "?user=" + DB_USERNAME_STATIC + "&password=" + DB_PASS_STATIC);
                connection.createStatement().execute("CREATE TABLE if not exists messages(" +
                        "id BIGINT NOT NULL AUTO_INCREMENT, " +
                        "user_id BIGINT NOT NULL, " +
                        "user_name TEXT NOT NULL, " +
                        "is_bot TINYINT NOT NULL, " +
                        "`date` TEXT NOT NULL, " + // TODO: 20.12.2022 переделать в нормальную дату
                        "`text` TEXT NOT NULL, " +
                        "PRIMARY KEY(id)) DEFAULT CHARSET=utf8mb4");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }


}
