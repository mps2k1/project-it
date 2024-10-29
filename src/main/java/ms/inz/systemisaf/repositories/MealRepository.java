package ms.inz.systemisaf.repositories;

import ms.inz.systemisaf.enums.DietTypeEnum;
import ms.inz.systemisaf.enums.MealOfTheDayEnum;
import ms.inz.systemisaf.model.meal.Meal;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface MealRepository extends JpaRepository<Meal,Long> {


    List<Meal> findByMealOfTheDayAndDietType(MealOfTheDayEnum mealType, DietTypeEnum dietType);
}
