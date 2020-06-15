package ru.kpfu.itis.group_804.project.models;

import lombok.*;
import ru.kpfu.itis.group_804.project.utils.DialogMessageID;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long message_id;

//    @Id
//    private Long dialogId;
    @ManyToOne
    @JoinColumn(name = "dialog_id", referencedColumnName = "id")
    private Dialog dialog;

    private String message_text;
    private boolean is_read;
    private LocalDateTime messageDateTime;

    @ManyToOne
    @JoinColumn(name = "user_from", referencedColumnName = "id")
    private User user_from;

    @ManyToOne
    @JoinColumn(name = "user_to", referencedColumnName = "id")
    private User user_to;
}
