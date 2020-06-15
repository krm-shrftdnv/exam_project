package ru.kpfu.itis.group_804.project.service.interfaces;

import ru.kpfu.itis.group_804.project.models.FriendshipRequest;
import ru.kpfu.itis.group_804.project.models.User;

import java.util.List;

public interface FriendshipRequestService {
    boolean sendFriendshipRequest(Long sender_id, Long receiver_id);
    boolean confirmFriendshipRequest(Long senderId, Long receiverId);
    boolean breakFriendship(Long breaker, Long friend);
    List<FriendshipRequest> getAllIncomingRequests(Long id);
    List<FriendshipRequest> getAllOutgoingRequests(Long id);
}
