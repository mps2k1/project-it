package ms.inz.systemisaf.dto;

import lombok.*;
import ms.inz.systemisaf.enums.DietTypeEnum;
import ms.inz.systemisaf.enums.MealOfTheDayEnum;

@Getter
@Setter
public class MealDto {
    private Long id;
    private String name;
    private MealOfTheDayEnum mealOfTheDay;
    private DietTypeEnum dietType;
}