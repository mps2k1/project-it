package ms.inz.systemisaf.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ms.inz.systemisaf.enums.RoleEnum;
import ms.inz.systemisaf.model.customworkout.CustomWorkoutPlan;
import ms.inz.systemisaf.model.meal.WeeklyMealPlan;
import ms.inz.systemisaf.model.workout.WeeklyWorkoutPlan;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum role;

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
    private WeeklyMealPlan activeMealPlan;

    @OneToOne
    @JoinColumn(name = "assigned_workout_plan_id")
    private CustomWorkoutPlan assignedWorkoutPlan;

    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )

    private Set<User> friends = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private User trainer;

    @OneToMany(mappedBy = "trainer")
    private Set<User> trainees = new HashSet<>();
}
