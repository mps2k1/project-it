package ms.inz.systemisaf.model;
import jakarta.persistence.*;
import ms.inz.systemisaf.model.workout.WeeklyWorkoutPlan;
import java.util.List;
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username; // Unikalna nazwa użytkownika

    @Column(name = "email", nullable = false, unique = true)
    private String email; // Unikalny email

    @Column(name = "password", nullable = false)
    private String password; // Hasło użytkownika

    @Column(name = "weight")
    private Double weight; // Waga użytkownika w kilogramach

    @Column(name = "height")
    private Double height; // Wzrost użytkownika w centymetrach

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeeklyWorkoutPlan> workoutPlans; // Lista wszystkich planów treningowych użytkownika

    @OneToOne
    @JoinColumn(name = "active_workout_plan_id")
    private WeeklyWorkoutPlan activeWorkoutPlan; // Jeden aktywny plan treningowy
}