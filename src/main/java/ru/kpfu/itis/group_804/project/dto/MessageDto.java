package ru.kpfu.itis.group_804.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kpfu.itis.group_804.project.models.Message;
import ru.kpfu.itis.group_804.project.repository.UsersRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageDto {
    private Long messageId;
    private String text;
    private Long writerId;
    private String writerNickname;
    private boolean is_read;
    private LocalDate date;
    private int hour;
    private int minute;
    private int second;

    public static MessageDto from(Message message){
        return MessageDto.builder()
                .messageId(message.getMessage_id())
                .text(message.getMessage_text())
                .writerId(message.getUser_from().getId())
                .writerNickname(message.getUser_from().getNickname())
                .is_read(message.is_read())
                .date(message.getMessageDateTime().toLocalDate())
                .hour(message.getMessageDateTime().getHour())
                .minute(message.getMessageDateTime().getMinute())
                .second(message.getMessageDateTime().getSecond())
                .build();
    }

    public static List<MessageDto> from(List<Message> messages) {
        return messages.stream()
                .map(MessageDto::from)
                .collect(Collectors.toList());
    }

}
