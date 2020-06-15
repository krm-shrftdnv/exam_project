package ru.kpfu.itis.group_804.project.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"member1", "member2", "messages"})
@EqualsAndHashCode(exclude = {"member1", "member2", "messages"})
@Entity
@Table(name = "dialog")
public class Dialog{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dialog_datetime;

    @ManyToOne
    @JoinColumn(name = "member1", referencedColumnName = "id")
    private User member1;

    @ManyToOne
    @JoinColumn(name = "member2", referencedColumnName = "id")
    private User member2;

    @OneToMany(mappedBy = "dialog")
    private Set<Message> messages;

}
