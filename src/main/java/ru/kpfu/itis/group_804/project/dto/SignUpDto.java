package ru.kpfu.itis.group_804.project.dto;

import lombok.Data;
import ru.kpfu.itis.group_804.project.models.enums.Status;

import java.sql.Date;

@Data
public class SignUpDto {
    private String nickname;
    private String email;
    private String password;
    private String repeated;
    private Date birthday;
    private Status status;
}
