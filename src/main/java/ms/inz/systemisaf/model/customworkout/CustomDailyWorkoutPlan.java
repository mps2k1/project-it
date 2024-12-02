package ms.inz.systemisaf.model.customworkout;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "custom_daily_workout_plan")
public class CustomDailyWorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "custom_daily_workout_plan_id")
    private List<CustomWorkoutSession> sessions = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_workout_plan_id", nullable = false)
    private CustomWorkoutPlan customWorkoutPlan;
}