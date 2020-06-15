package ru.kpfu.itis.group_804.project.dto;

import lombok.Data;

@Data
public class EmailValidationDto {
    private String email;
    private String username;
    private String domain;
    private String result;
    private String[] flags;
}
