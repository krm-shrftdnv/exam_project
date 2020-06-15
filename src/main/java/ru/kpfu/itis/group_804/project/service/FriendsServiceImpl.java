package ru.kpfu.itis.group_804.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.group_804.project.dto.UserDto;
import ru.kpfu.itis.group_804.project.models.FriendshipRequest;
import ru.kpfu.itis.group_804.project.models.User;
import ru.kpfu.itis.group_804.project.repository.FriendshipRequestRepository;
import ru.kpfu.itis.group_804.project.repository.UsersRepository;
import ru.kpfu.itis.group_804.project.service.interfaces.FriendsService;
import ru.kpfu.itis.group_804.project.service.interfaces.FriendshipRequestService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    FriendshipRequestService friendshipRequestService;

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    FriendshipRequestRepository friendshipRequestRepository;

    @Override
    public boolean addToFriends(Long sender, Long receiver) {
        return friendshipRequestService.sendFriendshipRequest(sender, receiver);
    }

    @Override
    public boolean confirmFriend(Long sender, Long receiver) {
        return friendshipRequestService.confirmFriendshipRequest(sender, receiver);
    }

    @Transactional
    @Override
    public List<UserDto> getAllFriends(Long id) {
        User user = usersRepository.findById(id).get();
        if (user.getFriends() != null) {
            List<User> friends = new ArrayList<>(user.getFriends());
            return UserDto.from(friends);
        } else return new ArrayList<>();
    }

    @Transactional
    @Override
    public List<UserDto> getIncomingRequests(Long id) {
        List<FriendshipRequest> requests = friendshipRequestService.getAllIncomingRequests(id);
        List<User> users = new ArrayList<>();
        for (FriendshipRequest request : requests) {
            users.add(request.getSender());
        }
        return UserDto.from(users);
    }

    @Transactional
    @Override
    public List<UserDto> getOutgoingRequests(Long id) {
        List<FriendshipRequest> requests = friendshipRequestService.getAllOutgoingRequests(id);
        List<User> users = new ArrayList<>();
        for (FriendshipRequest request : requests) {
            users.add(request.getReceiver());
        }
        return UserDto.from(users);
    }

    @Transactional
    @Override
    public boolean breakFriendship(Long breakerId, Long friendId) {
        if (friendshipRequestService.breakFriendship(breakerId, friendId)) return true;
        else {
            User breaker = usersRepository.findById(breakerId).get();
            User friend = usersRepository.findById(friendId).get();
            breaker.getFriends().remove(friend);
            usersRepository.save(breaker);
            friendshipRequestService.sendFriendshipRequest(friend.getId(), breaker.getId());
            return true;
        }
    }

    @Transactional
    @Override
    public List<UserDto> getNonFriends(Long id) {
        User current = usersRepository.findById(id).get();
        List<User> users = usersRepository.findAll();
        users.remove(current);
        List<FriendshipRequest> outgoingRequests = friendshipRequestRepository.findAllBySender_id(id);
        List<FriendshipRequest> incomingRequests = friendshipRequestRepository.findAllByReceiver_id(id);
        for(FriendshipRequest request : outgoingRequests){
            users.remove(request.getReceiver());
        }
        if (!current.getFriends().isEmpty()) {
            List<User> friends = new ArrayList<>(current.getFriends());
            List<User> nonFriends = new ArrayList<>();
            for (User user : users) {
                if (!friends.contains(user)) nonFriends.add(user);
            }
            return UserDto.from(nonFriends);
        } else return UserDto.from(users);
    }

    @Transactional
    @Override
    public boolean areFriends(Long user1Id, Long user2Id){
        User user1 = usersRepository.findById(user1Id).get();
        User user2 = usersRepository.findById(user2Id).get();
        return user1.getFriends().contains(user2);
    }

//    public List<UserDto> getJointFriends(Long id1, Long id2) {
//        List<UserDto> friendsOf1 = getAllFriends(id1);
//        List<UserDto> friendsOf2 = getAllFriends(id2);
//
//    }
}
