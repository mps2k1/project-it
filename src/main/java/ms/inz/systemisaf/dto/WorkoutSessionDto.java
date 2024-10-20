package ms.inz.systemisaf.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class WorkoutSessionDto {
    private Long id;
    private List<ExerciseDto> exerciseDtos;
}
