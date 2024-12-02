package ms.inz.systemisaf.controller;
import ms.inz.systemisaf.config.CustomUserDetails;
import ms.inz.systemisaf.dto.CustomWorkoutPlanDto;
import ms.inz.systemisaf.dto.CustomWorkoutPlanRequestDto;
import ms.inz.systemisaf.dto.WeeklyWorkoutPlanDto;
import ms.inz.systemisaf.services.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping("/split")
    public ResponseEntity<Object> createSplitWorkout(Authentication authentication) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            WeeklyWorkoutPlanDto workoutPlanDto = workoutService.createSplitWorkout(userId);
            return new ResponseEntity<>(workoutPlanDto, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/fbw")
    public ResponseEntity<Object> createFBWWorkout(Authentication authentication) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            WeeklyWorkoutPlanDto workoutPlanDto = workoutService.createFBWWorkout(userId);
            return new ResponseEntity<>(workoutPlanDto, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/upper-lower")
    public ResponseEntity<Object> createUpperLowerWorkout(Authentication authentication) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            WeeklyWorkoutPlanDto workoutPlanDto = workoutService.createUpperLowerWorkout(userId);
            return new ResponseEntity<>(workoutPlanDto, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/set-active")
    public ResponseEntity<Object> setActiveWorkoutPlan(Authentication authentication, @RequestParam Long workoutPlanId) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            workoutService.setActiveWorkoutPlan(userId, workoutPlanId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    @PostMapping("/custom-plan")
    public ResponseEntity<CustomWorkoutPlanDto> assignWorkout(
            @RequestBody CustomWorkoutPlanRequestDto requestDto,
            Authentication authentication) {
        Long trainerId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        CustomWorkoutPlanDto dto = workoutService.assignWorkout(requestDto, trainerId);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/assigned")
    public ResponseEntity<Object> getAssignedWorkout(Authentication authentication) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            CustomWorkoutPlanDto assignedWorkout = workoutService.getAssignedWorkout(userId);
            return ResponseEntity.ok(assignedWorkout);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}