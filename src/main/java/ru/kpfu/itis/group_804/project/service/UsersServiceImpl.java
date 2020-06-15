package ru.kpfu.itis.group_804.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.group_804.project.dto.UpdatedUserDto;
import ru.kpfu.itis.group_804.project.dto.UserDto;
import ru.kpfu.itis.group_804.project.models.enums.Role;
import ru.kpfu.itis.group_804.project.models.enums.State;
import ru.kpfu.itis.group_804.project.models.User;
import ru.kpfu.itis.group_804.project.models.enums.Status;
import ru.kpfu.itis.group_804.project.repository.UsersRepository;
import ru.kpfu.itis.group_804.project.service.interfaces.UsersService;

import java.util.List;

@Component
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return UserDto.from(usersRepository.findAll());
    }

    @Override
    public void deleteUser(Long userId) {
        usersRepository.deleteById(userId);
    }

    @Override
    public void confirmUser(Long userId) {
        User user = usersRepository.findById(userId).get();
        user.setState(State.CONFIRMED);
        usersRepository.save(user);
    }

    @Override
    public void changeUserRole(Long userId, Role role) {
        User user = usersRepository.findById(userId).get();
        user.setRole(role);
        usersRepository.save(user);
    }

    @Override
    public User getUserById(Long userId){
        return usersRepository.findById(userId).get();
    }

    @Override
    public boolean userExists(Long id){
        return usersRepository.existsById(id);
    }

    @Override
    public void updateUser(Long userId, UpdatedUserDto dto){
        User user = usersRepository.findById(userId).get();
        user.setStatus(Status.valueOf(dto.getStatus()));
        if(dto.getNickname().length() > 0) {
            user.setNickname((dto.getNickname()));
        }
        usersRepository.save(user);
    }

}
