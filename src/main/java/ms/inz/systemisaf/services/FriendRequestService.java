package ms.inz.systemisaf.services;
import jakarta.transaction.Transactional;
import ms.inz.systemisaf.dto.FriendRequestDto;
import ms.inz.systemisaf.dto.UserDto;
import ms.inz.systemisaf.enums.FriendRequestStatus;
import ms.inz.systemisaf.enums.RoleEnum;
import ms.inz.systemisaf.mapper.FriendRequestMapper;
import ms.inz.systemisaf.mapper.UserMapper;
import ms.inz.systemisaf.model.FriendRequest;
import ms.inz.systemisaf.model.User;
import ms.inz.systemisaf.repositories.FriendRequestRepository;
import ms.inz.systemisaf.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository, UserRepository userRepository) {
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public void sendFriendRequest(Long senderId, String receiverUsername) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono"));

        User receiver = userRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono"));

        if (sender.getUsername().equals(receiver.getUsername())) {
            throw new IllegalArgumentException("Nie możesz wysłać zaproszenia do siebie");
        }

        if (sender.getRole() == RoleEnum.USER) {
            if (receiver.getRole() != RoleEnum.TRAINER) {
                throw new IllegalArgumentException("Możesz wysyłać prośby tylko do trenerów");
            }
            if (sender.getTrainer() != null) {
                throw new IllegalArgumentException("Masz już trenera");
            }
        }

        if (sender.getRole() == RoleEnum.TRAINER) {
            if (receiver.getRole() != RoleEnum.USER) {
                throw new IllegalArgumentException("You can only send friend requests to users");
            }
        }

        if (friendRequestRepository.existsBySenderAndReceiver(sender, receiver)) {
            throw new IllegalArgumentException("Już wysłałeś zaproszenie");
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setStatus(FriendRequestStatus.PENDING);

        friendRequestRepository.save(friendRequest);
    }

    @Transactional
    public void respondToFriendRequest(Long requestId, boolean accept, Long userId) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found"));

        if (!friendRequest.getReceiver().getId().equals(userId)) {
            throw new IllegalArgumentException("You are not authorized to respond to this request");
        }

        if (accept) {
            User sender = friendRequest.getSender();
            User receiver = friendRequest.getReceiver();

             sender.getFriends().add(receiver);
            receiver.getFriends().add(sender);

             if (receiver.getRole() == RoleEnum.USER && sender.getRole() == RoleEnum.TRAINER) {
                receiver.setTrainer(sender);
            } else if (receiver.getRole() == RoleEnum.TRAINER && sender.getRole() == RoleEnum.USER) {
                sender.setTrainer(receiver);
            }

            userRepository.save(sender);
            userRepository.save(receiver);

            friendRequest.setStatus(FriendRequestStatus.ACCEPTED);
        } else {
            friendRequest.setStatus(FriendRequestStatus.REJECTED);
        }

        friendRequestRepository.save(friendRequest);
    }

    public List<FriendRequestDto> getPendingRequests(Long userId) {
        User receiver = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<FriendRequest> pendingRequests = friendRequestRepository.findByReceiverAndStatus(receiver, FriendRequestStatus.PENDING);

        return FriendRequestMapper.friendRequestsToDtos(pendingRequests);
    }
    public List<UserDto> getFriends(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getRole() == RoleEnum.TRAINER) {
             return user.getTrainees().stream()
                    .map(UserMapper::userToDto)
                    .toList();
        } else {
             if (user.getTrainer() != null) {
                return List.of(UserMapper.userToDto(user.getTrainer()));
            }
            return List.of();
        }
    }
}