package ms.inz.systemisaf.repositories;

import ms.inz.systemisaf.model.customworkout.CustomWorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomWorkoutPlanRepository extends JpaRepository<CustomWorkoutPlan,Long> {
}
