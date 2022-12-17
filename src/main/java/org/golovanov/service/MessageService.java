package org.golovanov.service;

import org.golovanov.model.MessageDb;
import org.golovanov.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void addNewMessage(MessageDb messageDb) {
        // TODO: think
        messageRepository.saveAndFlush(messageDb);
    }
}
