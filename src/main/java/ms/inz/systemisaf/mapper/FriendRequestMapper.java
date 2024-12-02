package ms.inz.systemisaf.mapper;
import ms.inz.systemisaf.dto.FriendRequestDto;
import ms.inz.systemisaf.model.FriendRequest;
import java.util.List;
import java.util.stream.Collectors;

public class FriendRequestMapper {


    public static FriendRequestDto friendRequestToDto(FriendRequest friendRequest) {
        if (friendRequest == null) {
            return null;
        }

        FriendRequestDto dto = new FriendRequestDto();
        dto.setId(friendRequest.getId());
        dto.setSenderId(friendRequest.getSender().getId());
        dto.setSenderUsername(friendRequest.getSender().getUsername());
        dto.setReceiverId(friendRequest.getReceiver().getId());
        dto.setReceiverUsername(friendRequest.getReceiver().getUsername());
        dto.setStatus(friendRequest.getStatus());
        return dto;
    }

    public static List<FriendRequestDto> friendRequestsToDtos(List<FriendRequest> friendRequests) {
        if (friendRequests == null || friendRequests.isEmpty()) {
            return List.of();
        }
        return friendRequests.stream()
                .map(FriendRequestMapper::friendRequestToDto)
                .collect(Collectors.toList());
    }
}