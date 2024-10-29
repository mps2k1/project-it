package ms.inz.systemisaf.repositories;

import ms.inz.systemisaf.model.meal.DailyMealPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyMealPlanRepository extends JpaRepository<DailyMealPlan,Long> {
}
