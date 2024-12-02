package ms.inz.systemisaf.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomWorkoutPlanRequestDto {
    private String username;
    private String planName;
    private List<CustomWorkoutDayDto> workoutDays;
}