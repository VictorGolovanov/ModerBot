package org.golovanov.bot;

import lombok.extern.log4j.Log4j;
import org.golovanov.filter.Censor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Log4j
public class ModeratorBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

        Message message = getMessageFromUpdate(update);
        if (message != null) {
            // this log is for development
            log.debug("Message id: " + message.getMessageId());
            log.debug("Chat id: " + message.getChatId());
            log.debug("text: " + message.getText());

            boolean isObsceneLexicon = Censor.censor(message);

            if (isObsceneLexicon) {

                // this log is for production
//                log.debug("User id: " + message.getFrom().getId());
//                log.debug("Message id: " + message.getMessageId());
//                log.debug("Chat id: " + message.getChatId());
//                log.debug("text: " + message.getText());

                DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(message.getChatId()), message.getMessageId());

                SendMessage response = new SendMessage();
                response.setChatId(message.getChatId());
                response.setText("Сообщение удалено\nПрична: мат, обсценная лексика.\nУчитесь выражать свои мысли культурно."); // it depends
                response.setReplyToMessageId(message.getMessageId());
                try {
                    execute(response);
                    execute(deleteMessage); // it is optional
                } catch (TelegramApiException e) {
                    log.error(e);
                }
            }
        }
    }

    private Message getMessageFromUpdate(Update update) {
        if (update.hasMessage()) {
            return update.getMessage();
        }

        if (update.hasEditedMessage()) {
            return update.getEditedMessage();
        }
        log.error("There is not message or edited message");

        return null;
    }
}
