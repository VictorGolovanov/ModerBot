package org.golovanov.repository;

import lombok.extern.log4j.Log4j;
import org.golovanov.database.DbConnection;
import org.golovanov.model.MessageDb;
import java.sql.Connection;
import java.sql.SQLException;

import static org.golovanov.database.DbStrings.*;

@Log4j
public class MessageRepository {

    public boolean saveMessage(MessageDb messageDb) {
        if (messageDb == null) {
            log.error("MessageDb object is null: saving to db is failed");
            return false;
        }
        Connection connection = DbConnection.getConnection();

        String sql = SAVE_MESSAGE_START + "values (" +
                messageDb.getTgUserId() + ", " +
                "'" + messageDb.getTgUserFirstName() + "'" + ", " +
                "'" + messageDb.getTgUserLastName() + "'" + ", " +
                messageDb.getIsBot() + ", " +
//                java.sql.Date.valueOf(messageDb.getDate()) + ", " + todo !
                messageDb.getDate().toString() + ", " +
                "'" + messageDb.getText() + "'" + ");";

        try {
            int result = connection.createStatement().executeUpdate(sql);
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
