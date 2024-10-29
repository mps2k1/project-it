package ms.inz.systemisaf.model.meal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "meal_sessions")
public class MealSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "meal_session_meals",
            joinColumns = @JoinColumn(name = "meal_session_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private List<Meal> meals;
}