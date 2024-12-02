package ms.inz.systemisaf.repositories;
import ms.inz.systemisaf.enums.FriendRequestStatus;
import ms.inz.systemisaf.model.FriendRequest;
import ms.inz.systemisaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByReceiverAndStatus(User receiver, FriendRequestStatus friendRequestStatus);

    boolean existsBySenderAndReceiver(User sender, User receiver);
}
