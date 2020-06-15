package ru.kpfu.itis.group_804.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.group_804.project.models.FriendshipRequest;
import ru.kpfu.itis.group_804.project.models.User;
import ru.kpfu.itis.group_804.project.repository.FriendshipRequestRepository;
import ru.kpfu.itis.group_804.project.repository.UsersRepository;
import ru.kpfu.itis.group_804.project.service.interfaces.FriendshipRequestService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FriendshipRequestServiceImpl implements FriendshipRequestService {

    @Autowired
    FriendshipRequestRepository friendshipRequestRepository;
    @Autowired
    UsersRepository usersRepository;

    @Transactional
    @Override
    public boolean sendFriendshipRequest(Long sender_id, Long receiver_id) {
        User sender = usersRepository.findById(sender_id).get();
        User receiver = usersRepository.findById(receiver_id).get();
        if (friendshipRequestRepository.existsBySenderAndReceiver(receiver, sender)) {
            confirmFriendshipRequest(sender_id, receiver_id);
            return true;
        } else {
            if (friendshipRequestRepository.existsBySenderAndReceiver(sender, receiver)) {
                return false;
            } else {
                FriendshipRequest friendshipRequest = FriendshipRequest.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .build();
                friendshipRequestRepository.save(friendshipRequest);
                return true;
            }
        }
    }

    @Transactional
    @Override
    public boolean confirmFriendshipRequest(Long senderId, Long receiverId) {
        User sender = usersRepository.findById(senderId).get();
        User receiver = usersRepository.findById(receiverId).get();
        FriendshipRequest friendshipRequest = friendshipRequestRepository.findBySender_idAndReceiver_id(receiver.getId(), sender.getId());
        friendshipRequestRepository.delete(friendshipRequest);
        sender.getFriends().add(receiver);
        receiver.getFriends().add(sender);
        usersRepository.save(sender);
        usersRepository.save(receiver);
        return true;
    }

    @Override
    public List<FriendshipRequest> getAllIncomingRequests(Long id) {
        return friendshipRequestRepository.findAllByReceiver_id(id);
    }

    @Override
    public List<FriendshipRequest> getAllOutgoingRequests(Long id) {
        return friendshipRequestRepository.findAllBySender_id(id);
    }

    @Transactional
    @Override
    public boolean breakFriendship(Long breakerId, Long friendId) {
        User breaker = usersRepository.findById(breakerId).get();
        User friend = usersRepository.findById(friendId).get();
        // проверка, есть ли идентичный запрос дружбы(отмена подписки)
        if (friendshipRequestRepository.existsBySenderAndReceiver(breaker, friend)) {
            this.deleteFriendshipRequest(breakerId, friendId);
            return true;
        } else {
            //проверка, есть ли обратный запрос (отказ дружбы)
            if (friendshipRequestRepository.existsBySenderAndReceiver(friend, breaker)) {
                this.deleteFriendshipRequest(friendId, breakerId);
                return true;
            } else return false;
        }
    }

    private void deleteFriendshipRequest(Long deleter, Long breaker) {
        FriendshipRequest friendshipRequest = friendshipRequestRepository.findBySender_idAndReceiver_id(deleter, breaker);
        friendshipRequestRepository.delete(friendshipRequest);
    }


}
