package ms.inz.systemisaf;


import ms.inz.systemisaf.enums.RoleEnum;
import ms.inz.systemisaf.model.FriendRequest;
import ms.inz.systemisaf.model.User;
import ms.inz.systemisaf.repositories.FriendRequestRepository;
import ms.inz.systemisaf.repositories.UserRepository;
import ms.inz.systemisaf.services.FriendRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FriendRequestServiceTest {

    @Mock
    private FriendRequestRepository friendRequestRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FriendRequestService friendRequestService;

    private User sender;
    private User receiver;

    @BeforeEach
    void setUp() {
        sender = new User();
        sender.setId(1L);
        sender.setUsername("sender");
        sender.setRole(RoleEnum.USER);

        receiver = new User();
        receiver.setId(2L);
        receiver.setUsername("receiver");
        receiver.setRole(RoleEnum.TRAINER);
    }

    @Test
    void sendFriendRequest_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findByUsername("receiver")).thenReturn(Optional.of(receiver));
        when(friendRequestRepository.existsBySenderAndReceiver(sender, receiver)).thenReturn(false);

        friendRequestService.sendFriendRequest(1L, "receiver");

        verify(friendRequestRepository, times(1)).save(any(FriendRequest.class));
    }

    @Test
    void sendFriendRequest_senderNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> friendRequestService.sendFriendRequest(1L, "receiver"));
        verify(friendRequestRepository, never()).save(any(FriendRequest.class));
    }

    @Test
    void sendFriendRequest_receiverNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findByUsername("receiver")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> friendRequestService.sendFriendRequest(1L, "receiver"));
        verify(friendRequestRepository, never()).save(any(FriendRequest.class));
    }

    @Test
    void sendFriendRequest_alreadySent() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findByUsername("receiver")).thenReturn(Optional.of(receiver));
        when(friendRequestRepository.existsBySenderAndReceiver(sender, receiver)).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> friendRequestService.sendFriendRequest(1L, "receiver"));
        verify(friendRequestRepository, never()).save(any(FriendRequest.class));
    }
}