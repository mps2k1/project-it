package ms.inz.systemisaf.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.List;
@Getter
@Setter
public class DailyWorkoutPlanDto {
    private Long id;
    private DayOfWeek dayOfWeek;
    private List<WorkoutSessionDto> sessions;
}