package ms.inz.systemisaf.controller;

import ms.inz.systemisaf.config.CustomUserDetails;
import ms.inz.systemisaf.dto.FriendRequestDto;
import ms.inz.systemisaf.dto.UserDto;
import ms.inz.systemisaf.services.FriendRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @PostMapping("/send")
    public ResponseEntity<Object> sendFriendRequest(Authentication authentication, @RequestParam String receiverUsername) {
        try {
            Long senderId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            friendRequestService.sendFriendRequest(senderId, receiverUsername);
            return ResponseEntity.ok("Friend request sent");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/respond")
    public ResponseEntity<Object> respondToFriendRequest(
            @RequestParam Long requestId,
            @RequestParam boolean accept,
            Authentication authentication) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            friendRequestService.respondToFriendRequest(requestId, accept, userId);
            return ResponseEntity.ok(accept ? "Friend request accepted" : "Friend request rejected");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<Object> getPendingRequests(Authentication authentication) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            List<FriendRequestDto> pendingRequests = friendRequestService.getPendingRequests(userId);
            return ResponseEntity.ok(pendingRequests);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getFriends(Authentication authentication) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            List<UserDto> friends = friendRequestService.getFriends(userId);
            return ResponseEntity.ok(friends);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}