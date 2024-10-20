package ms.inz.systemisaf.dto;
import lombok.Getter;
import lombok.Setter;
import ms.inz.systemisaf.enums.TypeOfWorkoutEnum;
import java.util.List;
@Getter
@Setter
public class WeeklyWorkoutPlanDto {
    private Long id;
    private List<DailyWorkoutPlanDto> dailyWorkoutPlans;
    private TypeOfWorkoutEnum typeOfWorkout;
    private Long userId;
    private boolean isActive;

}