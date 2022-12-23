package org.golovanov.repository;

import lombok.extern.log4j.Log4j;
import org.golovanov.database.DbConnection;
import org.golovanov.model.MessageDb;
import java.sql.Connection;
import java.sql.SQLException;


@Log4j
public class MessageRepository {

    private static final String SAVE_MESSAGE_START = "insert into messages(user_id, `user_name`, is_bot, message_date, `text`) ";

    public boolean saveMessage(MessageDb messageDb) {
        if (messageDb == null) {
            log.error("MessageDb object is null: saving to db is failed");
            return false;
        }
        Connection connection = DbConnection.getConnection();

        String sql = SAVE_MESSAGE_START + "values (" +
                messageDb.getTgUserId() + ", " +
                "'" + messageDb.getTgUserName() + "'" + ", " +
                messageDb.getIsBot() + ", " +
                "'" + messageDb.getDate() + "', " +
                "'" + messageDb.getText() + "'" + ");";

        try {
            connection.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            log.error("Something went wrong with insertion to database", e);
        }
        return false;
    }
}
