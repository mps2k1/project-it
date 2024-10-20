package ms.inz.systemisaf.repositories;

import ms.inz.systemisaf.model.workout.WeeklyWorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyWorkoutPlanRepository extends JpaRepository<WeeklyWorkoutPlan, Long> {
}
