package ms.inz.systemisaf.model.customworkout;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "custom_exercise")
public class CustomExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String instruction;

    @Column(nullable = false)
    private int repetitions;

    @Column(nullable = false)
    private int sets;

    @Column(nullable = false)
    private String muscleGroup;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_workout_session_id", nullable = false)
    private CustomWorkoutSession customWorkoutSession;

}