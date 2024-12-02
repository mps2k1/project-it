package ms.inz.systemisaf.model.customworkout;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "custom_workout_session")
public class CustomWorkoutSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "custom_workout_session_id")
    private List<CustomExercise> exercises = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_daily_workout_plan_id", nullable = false)
    private CustomDailyWorkoutPlan customDailyWorkoutPlan;
}