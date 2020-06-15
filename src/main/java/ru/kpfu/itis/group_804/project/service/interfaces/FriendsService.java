package ru.kpfu.itis.group_804.project.service.interfaces;

import ru.kpfu.itis.group_804.project.dto.UserDto;

import java.util.List;

public interface FriendsService {
    boolean addToFriends(Long sender, Long receiver);
    boolean confirmFriend(Long sender, Long receiver);
    List<UserDto> getAllFriends(Long id);
    boolean breakFriendship(Long sender, Long receiver);
    List<UserDto> getNonFriends(Long id);
    List<UserDto> getIncomingRequests(Long id);
    List<UserDto> getOutgoingRequests(Long id);
    boolean areFriends(Long user1Id, Long user2Id);
}
