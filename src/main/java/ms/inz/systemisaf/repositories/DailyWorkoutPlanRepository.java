package ms.inz.systemisaf.repositories;

import ms.inz.systemisaf.model.workout.DailyWorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyWorkoutPlanRepository extends JpaRepository<DailyWorkoutPlan,Long> {
}
