package ms.inz.systemisaf.model.workout;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ms.inz.systemisaf.enums.TypeOfWorkout;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "weekly_workout_plan")
public class WeeklyWorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "weekly_workout_plan_id")
    private List<DailyWorkoutPlan> dailyWorkoutPlans;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_workout", nullable = false)
    private TypeOfWorkout typeOfWorkout;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}