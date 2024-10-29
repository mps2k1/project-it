package ms.inz.systemisaf.controller;
import ms.inz.systemisaf.dto.WeeklyWorkoutPlanDto;
import ms.inz.systemisaf.services.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping("/split/{userId}")
    public ResponseEntity<WeeklyWorkoutPlanDto> createSplitWorkout(@PathVariable Long userId) {
        WeeklyWorkoutPlanDto workoutPlanDto = workoutService.createSplitWorkout(userId);
        return new ResponseEntity<>(workoutPlanDto, HttpStatus.CREATED);
    }

    @PostMapping("/fbw/{userId}")
    public ResponseEntity<WeeklyWorkoutPlanDto> createFBWWorkout(@PathVariable Long userId) {
        WeeklyWorkoutPlanDto workoutPlanDto = workoutService.createFBWWorkout(userId);
        return new ResponseEntity<>(workoutPlanDto, HttpStatus.CREATED);
    }

    @PostMapping("/upper-lower/{userId}")
    public ResponseEntity<WeeklyWorkoutPlanDto> createUpperLowerWorkout(@PathVariable Long userId) {
        WeeklyWorkoutPlanDto workoutPlanDto = workoutService.createUpperLowerWorkout(userId);
        return new ResponseEntity<>(workoutPlanDto, HttpStatus.CREATED);
    }

    @PutMapping("/set-active")
    public ResponseEntity<Void> setActiveWorkoutPlan(@RequestParam Long userId, @RequestParam Long workoutPlanId) {
        workoutService.setActiveWorkoutPlan(userId, workoutPlanId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}