package ms.inz.systemisaf.dto;
import lombok.Getter;
import lombok.Setter;
import ms.inz.systemisaf.enums.FriendRequestStatus;
@Getter
@Setter
public class FriendRequestDto {
    private Long id;
    private Long senderId;
    private String senderUsername;
    private Long receiverId;
    private String receiverUsername;
    private FriendRequestStatus status;
}
