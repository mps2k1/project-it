package ms.inz.systemisaf.model.workout;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.DayOfWeek;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "daily_workout_plan")
public class DailyWorkoutPlan {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Enumerated(EnumType.STRING)
        @Column(name = "day_of_week", nullable = false)
        private DayOfWeek dayOfWeek; // Dzień tygodnia

        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
        @JoinColumn(name = "daily_workout_plan_id")
        private List<WorkoutSession> sessions; // Lista sesji treningowych dla danego dnia
}