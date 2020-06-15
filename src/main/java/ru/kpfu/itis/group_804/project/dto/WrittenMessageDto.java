package ru.kpfu.itis.group_804.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WrittenMessageDto {
    private String text;
    private Long writerId;
    private String writerNickname;
    private Long dialogId;
    private String date;
}
