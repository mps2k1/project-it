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
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/measurement")
    public ResponseEntity<Void> addMeasurement(
            Authentication authentication,
            @RequestParam Double weight,
            @RequestParam Double height) {

        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        userService.addMeasurement(userId, weight, height);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/measurement/current")
    public ResponseEntity<MeasurementDto> getCurrentMeasurement(Authentication authentication) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        MeasurementDto measurementDto = userService.getCurrentMeasurement(userId);
        return ResponseEntity.ok(measurementDto);
    }

    @GetMapping("/workout/active")
    public ResponseEntity<WeeklyWorkoutPlanDto> getActiveWorkoutPlan(Authentication authentication) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        WeeklyWorkoutPlanDto workoutPlanDto = userService.getActiveWorkoutPlan(userId);
        return ResponseEntity.ok(workoutPlanDto);
    }

    @GetMapping("/meal/active")
    public ResponseEntity<WeeklyMealPlanDto> getActiveMealPlan(Authentication authentication) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        WeeklyMealPlanDto mealPlanDto = userService.getActiveMealPlan(userId);
        return ResponseEntity.ok(mealPlanDto);
    }
}