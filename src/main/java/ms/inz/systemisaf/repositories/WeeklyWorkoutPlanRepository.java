package ms.inz.systemisaf.repositories;

import ms.inz.systemisaf.model.User;
import ms.inz.systemisaf.model.workout.WeeklyWorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklyWorkoutPlanRepository extends JpaRepository<WeeklyWorkoutPlan, Long> {
    List<WeeklyWorkoutPlan> findByUser(User user);
}
