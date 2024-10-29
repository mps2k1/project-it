package ms.inz.systemisaf.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class WeeklyMealPlanDto {
    private Long id;
    private List<DailyMealPlanDto> dailyMealPlans;
    private Long userId;
    private boolean isActive;
}