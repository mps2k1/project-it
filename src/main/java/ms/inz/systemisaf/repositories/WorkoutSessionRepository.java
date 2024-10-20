package ms.inz.systemisaf.repositories;

import ms.inz.systemisaf.model.workout.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession,Long> {
}
