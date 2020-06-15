package ru.kpfu.itis.group_804.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kpfu.itis.group_804.project.models.Post;
import ru.kpfu.itis.group_804.project.repository.UsersRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostDto {
    private Long id;
    private String text;
    private Long writerId;
    private String writerNickname;
    private LocalDate postDate;
    private int hour;
    private int minute;
    private int likersCount;
    private boolean isLiked;

    public static PostDto from(Post post){
        return PostDto.builder()
                .id(post.getPostId())
                .text(post.getText())
                .writerId(post.getWriter().getId())
                .writerNickname(post.getWriter().getNickname())
                .postDate(post.getPostDateTime().toLocalDate())
                .hour(post.getPostDateTime().getHour())
                .minute(post.getPostDateTime().getMinute())
                .build();
    }

    public static List<PostDto> from(List<Post>posts){
        return posts.stream()
                .map(PostDto::from)
                .collect(Collectors.toList());
    }

}
