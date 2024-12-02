package ms.inz.systemisaf.model.customworkout;

import jakarta.persistence.*;
import lombok.*;
import ms.inz.systemisaf.model.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "custom_workout_plan")
public class CustomWorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "custom_workout_plan_id")
    private List<CustomDailyWorkoutPlan> dailyWorkoutPlans = new ArrayList<>();
}