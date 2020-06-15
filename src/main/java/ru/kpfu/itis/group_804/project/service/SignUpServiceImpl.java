package ru.kpfu.itis.group_804.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.group_804.project.dto.EmailValidationDto;
import ru.kpfu.itis.group_804.project.dto.SignUpDto;
import ru.kpfu.itis.group_804.project.models.UserEmailValidity;
import ru.kpfu.itis.group_804.project.models.enums.Role;
import ru.kpfu.itis.group_804.project.models.enums.State;
import ru.kpfu.itis.group_804.project.models.User;
import ru.kpfu.itis.group_804.project.repository.UsersRepository;
import ru.kpfu.itis.group_804.project.service.exceptions.DuplicateEntryException;
import ru.kpfu.itis.group_804.project.service.exceptions.IncorrectEntryException;
import ru.kpfu.itis.group_804.project.service.interfaces.SignUpService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpDto form) throws IncorrectEntryException, DuplicateEntryException {
        if(usersRepository.findUserByEmail(form.getEmail()).isPresent()) throw new DuplicateEntryException("User with such email already exists");
        if(form.getPassword().equals(form.getRepeated())) {
            User user = User.builder()
                    .email(form.getEmail())
                    .password(passwordEncoder.encode(form.getPassword()))
                    .nickname(form.getNickname())
                    .status(form.getStatus())
                    .state(State.NOT_CONFIRMED)
                    .role(Role.USER)
                    .birthday(form.getBirthday())
                    .build();
            usersRepository.save(user);

            setEmailValidity(user);

        } else throw new IncorrectEntryException("Passwords should be same");
    }

    private void setEmailValidity(User user){
        User userFromDB = usersRepository.findUserByEmail(user.getEmail()).get();
        UserEmailValidity userEmailValidity = UserEmailValidity.builder()
                .emailStatus(validateEmail(userFromDB.getEmail()))
                .user(userFromDB)
                .build();
        userFromDB.setUserEmailValidity(userEmailValidity);
        usersRepository.save(userFromDB);
    }

    private String validateEmail(String email){
        try {
            String encodedEmail = encodeValue(email);
            HttpResponse<String> response = Unirest.get("https://email8.p.rapidapi.com/verify/"+encodedEmail)
                    .header("x-rapidapi-host", "email8.p.rapidapi.com")
                    .header("x-rapidapi-key", "2422d0f568mshe5aa31682e906b8p13f0f5jsn47eb2ef597b7")
                    .asString();
            EmailValidationDto dto = objectMapper.readValue(response.getBody(), EmailValidationDto.class);
            return dto.getResult();
        } catch (UnirestException | UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
            return "fatal";
        }
    }

    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    @Override
    public boolean checkEmailAvailability(String email) {
        return !usersRepository.findUserByEmail(email).isPresent();
    }

}
