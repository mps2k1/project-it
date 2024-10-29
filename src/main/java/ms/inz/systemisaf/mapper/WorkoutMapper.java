package ms.inz.systemisaf.mapper;
import ms.inz.systemisaf.dto.DailyWorkoutPlanDto;
import ms.inz.systemisaf.dto.ExerciseDto;
import ms.inz.systemisaf.dto.WeeklyWorkoutPlanDto;
import ms.inz.systemisaf.dto.WorkoutSessionDto;
import ms.inz.systemisaf.model.workout.DailyWorkoutPlan;
import ms.inz.systemisaf.model.workout.Exercise;
import ms.inz.systemisaf.model.workout.WeeklyWorkoutPlan;
import ms.inz.systemisaf.model.workout.WorkoutSession;
import java.util.List;
import java.util.stream.Collectors;
public class WorkoutMapper {
    public static ExerciseDto exerciseToDto(Exercise exercise) {
        if (exercise == null) {
            return null;
        }
        ExerciseDto exerciseDto = new ExerciseDto();
        exerciseDto.setId(exercise.getId());
        exerciseDto.setName(exercise.getName());
        exerciseDto.setInstruction(exercise.getInstruction());
        exerciseDto.setRepetitions(exercise.getRepetitions());
        exerciseDto.setSets(exercise.getSets());
        exerciseDto.setMuscleGroup(exercise.getMuscleGroup());
        return exerciseDto;
    }
    public static Exercise exerciseToEntity(ExerciseDto exerciseDto) {
        if (exerciseDto == null) {
            return null;
        }
        Exercise exercise = new Exercise();
        exercise.setId(exerciseDto.getId());
        exercise.setName(exerciseDto.getName());
        exercise.setInstruction(exerciseDto.getInstruction());
        exercise.setRepetitions(exerciseDto.getRepetitions());
        exercise.setSets(exerciseDto.getSets());
        exercise.setMuscleGroup(exerciseDto.getMuscleGroup());
        return exercise;
    }
    public static WorkoutSessionDto workoutSessionToDto(WorkoutSession workoutSession) {
        if (workoutSession == null) {
            return null;
        }
        WorkoutSessionDto workoutSessionDto = new WorkoutSessionDto();
        workoutSessionDto.setId(workoutSession.getId());
        List<ExerciseDto> exerciseDtos = workoutSession.getExercises().stream()
                .map(WorkoutMapper::exerciseToDto)
                .collect(Collectors.toList());
        workoutSessionDto.setExerciseDtos(exerciseDtos);
        return workoutSessionDto;
    }
    public static WorkoutSession workoutSessionToEntity(WorkoutSessionDto workoutSessionDto) {
        if (workoutSessionDto == null) {
            return null;
        }
        WorkoutSession workoutSession = new WorkoutSession();
        workoutSession.setId(workoutSessionDto.getId());
        List<Exercise> exercises = workoutSessionDto.getExerciseDtos().stream()
                .map(WorkoutMapper::exerciseToEntity)
                .collect(Collectors.toList());
        workoutSession.setExercises(exercises);
        return workoutSession;
    }
    public static DailyWorkoutPlanDto dailyWorkoutPlanToDto(DailyWorkoutPlan dailyWorkoutPlan) {
        if (dailyWorkoutPlan == null) {
            return null;
        }

        DailyWorkoutPlanDto dailyWorkoutPlanDto = new DailyWorkoutPlanDto();
        dailyWorkoutPlanDto.setId(dailyWorkoutPlan.getId());
        dailyWorkoutPlanDto.setDayOfWeek(dailyWorkoutPlan.getDayOfWeek());

        List<WorkoutSessionDto> sessionDtos = dailyWorkoutPlan.getSessions().stream()
                .map(WorkoutMapper::workoutSessionToDto) // Use WorkoutMapper to map WorkoutSession to WorkoutSessionDto
                .collect(Collectors.toList());

        dailyWorkoutPlanDto.setSessions(sessionDtos);

        return dailyWorkoutPlanDto;
    }
    public static DailyWorkoutPlan dailyWorkoutPlanToEntity(DailyWorkoutPlanDto dailyWorkoutPlanDto) {
        if (dailyWorkoutPlanDto == null) {
            return null;
        }

        DailyWorkoutPlan dailyWorkoutPlan = new DailyWorkoutPlan();
        dailyWorkoutPlan.setId(dailyWorkoutPlanDto.getId());
        dailyWorkoutPlan.setDayOfWeek(dailyWorkoutPlanDto.getDayOfWeek());

        List<WorkoutSession> sessions = dailyWorkoutPlanDto.getSessions().stream()
                .map(WorkoutMapper::workoutSessionToEntity)
                .collect(Collectors.toList());

        dailyWorkoutPlan.setSessions(sessions);

        return dailyWorkoutPlan;
    }

    public static WeeklyWorkoutPlanDto weeklyWorkoutPlanToDto(WeeklyWorkoutPlan weeklyWorkoutPlan) {
        if (weeklyWorkoutPlan == null) {
            return null;
        }

        WeeklyWorkoutPlanDto weeklyWorkoutPlanDto = new WeeklyWorkoutPlanDto();
        weeklyWorkoutPlanDto.setId(weeklyWorkoutPlan.getId());
        weeklyWorkoutPlanDto.setTypeOfWorkout(weeklyWorkoutPlan.getTypeOfWorkout());
        weeklyWorkoutPlanDto.setActive(weeklyWorkoutPlan.isActive());

        if (weeklyWorkoutPlan.getUser() != null) {
            weeklyWorkoutPlanDto.setUserId(weeklyWorkoutPlan.getUser().getId());
        }

        List<DailyWorkoutPlanDto> dailyWorkoutPlanDtos = weeklyWorkoutPlan.getDailyWorkoutPlans().stream()
                .map(WorkoutMapper::dailyWorkoutPlanToDto)
                .collect(Collectors.toList());
        weeklyWorkoutPlanDto.setDailyWorkoutPlans(dailyWorkoutPlanDtos);

        return weeklyWorkoutPlanDto;
    }

    public static WeeklyWorkoutPlan weeklyWorkoutPlanToEntity(WeeklyWorkoutPlanDto weeklyWorkoutPlanDto) {
        if (weeklyWorkoutPlanDto == null) {
            return null;
        }

        WeeklyWorkoutPlan weeklyWorkoutPlan = new WeeklyWorkoutPlan();
        weeklyWorkoutPlan.setId(weeklyWorkoutPlanDto.getId());
        weeklyWorkoutPlan.setTypeOfWorkout(weeklyWorkoutPlanDto.getTypeOfWorkout());
        weeklyWorkoutPlan.setActive(weeklyWorkoutPlanDto.isActive());

        // Convert the list of DailyWorkoutPlanDtos to DailyWorkoutPlans
        List<DailyWorkoutPlan> dailyWorkoutPlans = weeklyWorkoutPlanDto.getDailyWorkoutPlans().stream()
                .map(WorkoutMapper::dailyWorkoutPlanToEntity)
                .collect(Collectors.toList());
        weeklyWorkoutPlan.setDailyWorkoutPlans(dailyWorkoutPlans);

        return weeklyWorkoutPlan;
    }
}

