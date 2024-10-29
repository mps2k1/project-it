package ms.inz.systemisaf.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ms.inz.systemisaf.model.meal.WeeklyMealPlan;
import ms.inz.systemisaf.model.workout.WeeklyWorkoutPlan;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Measurement> measurements;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeeklyWorkoutPlan> workoutPlans;

    @OneToOne
    @JoinColumn(name = "active_workout_plan_id")
    private WeeklyWorkoutPlan activeWorkoutPlan;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeeklyMealPlan> weeklyMealPlans;

    @OneToOne
    @JoinColumn(name = "active_meal_plan_id")
    private WeeklyMealPlan activeMealPlan;  // Dodane aktywny plan posiłków
}