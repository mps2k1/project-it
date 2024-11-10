package ms.inz.systemisaf.controller;
import ms.inz.systemisaf.config.CustomUserDetails;
import ms.inz.systemisaf.dto.MeasurementDto;
import ms.inz.systemisaf.dto.WeeklyMealPlanDto;
import ms.inz.systemisaf.dto.WeeklyWorkoutPlanDto;
import ms.inz.systemisaf.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/measurement")
    public ResponseEntity<Void> addMeasurement(Authentication authentication, @RequestParam Double weight, @RequestParam Double height) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            userService.addMeasurement(userId, weight, height);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/measurement/current")
    public ResponseEntity<Object> getCurrentMeasurement(Authentication authentication) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            MeasurementDto measurementDto = userService.getCurrentMeasurement(userId);
            return ResponseEntity.ok(measurementDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @GetMapping("/measurements")
    public ResponseEntity<?> getAllMeasurements(Authentication authentication) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            List<MeasurementDto> measurements = userService.getAllMeasurements(userId);
            return ResponseEntity.ok(measurements);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/workout/active")
    public ResponseEntity<Object> getActiveWorkoutPlan(Authentication authentication) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            WeeklyWorkoutPlanDto workoutPlanDto = userService.getActiveWorkoutPlan(userId);
            return ResponseEntity.ok(workoutPlanDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/meal/active")
    public ResponseEntity<Object> getActiveMealPlan(Authentication authentication) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            WeeklyMealPlanDto mealPlanDto = userService.getActiveMealPlan(userId);
            return ResponseEntity.ok(mealPlanDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}