package ms.inz.systemisaf.repositories;

import ms.inz.systemisaf.model.User;
import ms.inz.systemisaf.model.meal.WeeklyMealPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklyMealPlanRepository extends JpaRepository<WeeklyMealPlan,Long> {
    List<WeeklyMealPlan> findByUser(User user);
}
