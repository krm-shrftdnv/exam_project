package ru.kpfu.itis.group_804.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.group_804.project.models.FriendshipRequest;
import ru.kpfu.itis.group_804.project.models.User;

import java.util.List;

public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long> {
    FriendshipRequest findBySender_idAndReceiver_id(Long sender_id, Long receiver_id);
    List<FriendshipRequest>findAllBySender_id(Long id);
    List<FriendshipRequest>findAllByReceiver_id(Long id);
    boolean existsBySenderAndReceiver(User sender, User receiver);
}
