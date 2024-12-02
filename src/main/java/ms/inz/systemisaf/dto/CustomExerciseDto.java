package ms.inz.systemisaf.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import ms.inz.systemisaf.enums.MuscleGroupEnum;

@Getter
@Setter
public class CustomExerciseDto {
    private String name;
    private String instruction;
    private int repetitions;
    private int sets;
    @Enumerated(EnumType.STRING)
    private MuscleGroupEnum muscleGroup;

}