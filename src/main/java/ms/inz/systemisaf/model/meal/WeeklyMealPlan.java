package ms.inz.systemisaf.model.meal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ms.inz.systemisaf.model.User;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "weekly_meal_plans")
public class WeeklyMealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "weekly_meal_plan_id")
    private List<DailyMealPlan> dailyMealPlans;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = false;
}