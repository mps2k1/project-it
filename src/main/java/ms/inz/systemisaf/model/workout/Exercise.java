package ms.inz.systemisaf.model.workout;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ms.inz.systemisaf.enums.MuscleGroup;
@Getter
@Setter
@Entity
@Table(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name; // Nazwa ćwiczenia (np. "Przysiady", "Podciąganie")

    @Column(name = "instruction")
    private String instruction;

    @Column(name = "repetitions", nullable = false)
    private int repetitions; // Liczba powtórzeń

    @Column(name = "sets", nullable = false)
    private int sets; // Liczba serii

    @Enumerated(EnumType.STRING)
    @Column(name = "muscle_group", nullable = false)
    private MuscleGroup muscleGroup; // Grupa mięśniowa
}