package ms.inz.systemisaf.model.meal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ms.inz.systemisaf.enums.DietTypeEnum;
import ms.inz.systemisaf.enums.MealOfTheDayEnum;
@Getter
@Setter
@Entity
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_of_the_day", nullable = false)
    private MealOfTheDayEnum mealOfTheDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "diet_type", nullable = false)
    private DietTypeEnum dietType;
}