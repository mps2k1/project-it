package ms.inz.systemisaf.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String password;

    private Long activeWorkoutPlanId;
    private Long activeMealPlanId;
    private List<Long> measurementIds;
    private List<Long> workoutPlanIds;
    private List<Long> weeklyMealPlanIds;
}
