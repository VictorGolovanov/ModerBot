package org.golovanov.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MessageDb {

    private Long id;

    private Long tgUserId;

    private String tgUserName;

    private Boolean isBot;

    private LocalDate date;

    private String text;

}
