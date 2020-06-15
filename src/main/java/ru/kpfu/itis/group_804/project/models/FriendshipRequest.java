package ru.kpfu.itis.group_804.project.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "friendship_request")
public class FriendshipRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long request_id;

    @ManyToOne
    @JoinColumn(name = "senderUserId", referencedColumnName = "id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiverUserId", referencedColumnName = "id")
    private User receiver;
}
