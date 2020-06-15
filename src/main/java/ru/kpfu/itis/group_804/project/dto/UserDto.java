package ru.kpfu.itis.group_804.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.group_804.project.models.enums.Role;
import ru.kpfu.itis.group_804.project.models.enums.State;
import ru.kpfu.itis.group_804.project.models.enums.Status;
import ru.kpfu.itis.group_804.project.models.User;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {
    private Long id;
    private String nickname;
    private String email;
    private Date birthday;
    private Status status;
    private State state;
    private Role role;

    public static UserDto from(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .id(user.getId())
                .birthday(user.getBirthday())
                .state(user.getState())
                .status(user.getStatus())
                .role(user.getRole())
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}
