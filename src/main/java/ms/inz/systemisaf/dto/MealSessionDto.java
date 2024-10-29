package ms.inz.systemisaf.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class MealSessionDto {

    private Long id;
    private List<MealDto> meals;
}