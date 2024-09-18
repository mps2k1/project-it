package ms.inz.systemisaf.controller;

import ms.inz.systemisaf.services.WorkoutService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkoutController {
    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

}
