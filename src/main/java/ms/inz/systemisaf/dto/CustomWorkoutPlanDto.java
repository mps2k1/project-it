package ms.inz.systemisaf.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CustomWorkoutPlanDto {
    private Long id;
    private String planName;
    private Long userId;
    private List<CustomWorkoutDayDto> workoutDays;
}
