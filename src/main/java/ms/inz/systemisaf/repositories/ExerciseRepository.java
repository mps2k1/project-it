package ms.inz.systemisaf.repositories;
import ms.inz.systemisaf.enums.MuscleGroupEnum;
import ms.inz.systemisaf.model.workout.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByMuscleGroup(MuscleGroupEnum firstMuscleGroup);
}
