package ms.inz.systemisaf.dto;
import lombok.*;
import ms.inz.systemisaf.enums.MuscleGroupEnum;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDto {
    private Long id;
    private String name;
    private String instruction;
    private int repetitions;
    private int sets;
    private MuscleGroupEnum muscleGroup;
}