package ru.kpfu.itis.group_804.project.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "writerId", referencedColumnName = "id")
    private User writer;
    private String text;
    private LocalDateTime postDateTime;

    @ManyToMany
    @JoinTable(
            name = "user_like_post",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    Set<User>likers = new HashSet<>();

}
