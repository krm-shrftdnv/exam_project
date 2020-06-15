package ru.kpfu.itis.group_804.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kpfu.itis.group_804.project.models.Dialog;
import ru.kpfu.itis.group_804.project.repository.UsersRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DialogDto {
    private Long id;
    private Long member1id;
    private Long member2id;
    private String member1nickname;
    private String member2nickname;
    private LocalDateTime dialog_datetime;

    public static DialogDto from(Dialog dialog){
        return DialogDto.builder()
                .id(dialog.getId())
                .dialog_datetime(dialog.getDialog_datetime())
                .member1id(dialog.getMember1().getId())
                .member2id(dialog.getMember2().getId())
                .member1nickname(dialog.getMember1().getNickname())
                .member2nickname(dialog.getMember2().getNickname())
                .build();
    }

    public static List<DialogDto> from(List<Dialog> dialogs) {
        return dialogs.stream()
                .map(DialogDto::from)
                .collect(Collectors.toList());
    }

}
