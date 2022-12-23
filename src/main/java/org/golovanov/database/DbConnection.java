package org.golovanov.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class DbConnection {

    private static Connection connection;

    private static String DB_URL_STATIC;

    private static String DB_NAME_STATIC;

    private static String DB_USERNAME_STATIC;

    private static String DB_PASS_STATIC;


    @Value("${db.url}")
    public void setDbUrlStatic(String dbUrlStatic) {
        DB_URL_STATIC = dbUrlStatic;
    }

    @Value("${db.name}")
    public void setDbNameStatic(String dbNameStatic) {
        DB_NAME_STATIC = dbNameStatic;
    }

    @Value("${db.username}")
    public void setDbUsernameStatic(String dbUsernameStatic) {
        DB_USERNAME_STATIC = dbUsernameStatic;
    }

    @Value("${db.password}")
    public void setDbPassStatic(String dbPassStatic) {
        DB_PASS_STATIC = dbPassStatic;
    }


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
                        "message_date DATE NOT NULL, " +
                        "`text` TEXT NOT NULL, " +
                        "PRIMARY KEY(id)) DEFAULT CHARSET=utf8mb4");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }


}
