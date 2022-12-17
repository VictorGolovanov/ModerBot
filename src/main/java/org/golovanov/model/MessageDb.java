package org.golovanov.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name = "commentator")
public class MessageDb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private Long tgUserId;

    @Column(name = "user_name")
    private String tgUserFirstName;

    @Column(name = "user_last_name")
    private String tgUserLastName;

    @Column(name = "is_bot")
    private Boolean isBot;

    @Basic
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "text")
    private String text;

}
