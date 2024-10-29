package ms.inz.systemisaf.dto;

import lombok.*;

import java.time.DayOfWeek;
import java.util.List;

@Getter
@Setter
public class DailyMealPlanDto {
    private Long id;
    private DayOfWeek dayOfWeek;
    private List<MealSessionDto> mealSessions;
}