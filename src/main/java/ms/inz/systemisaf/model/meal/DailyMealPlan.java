package ms.inz.systemisaf.model.meal;
import java.time.DayOfWeek;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "daily_meal_plans")
public class DailyMealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "daily_meal_plan_id")
    private List<MealSession> mealSessions;
}