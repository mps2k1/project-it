package ms.inz.systemisaf.services;
import jakarta.transaction.Transactional;
import ms.inz.systemisaf.dto.WeeklyWorkoutPlanDto;
import ms.inz.systemisaf.enums.MuscleGroupEnum;
import ms.inz.systemisaf.enums.TypeOfWorkoutEnum;
import ms.inz.systemisaf.mapper.WorkoutMapper;
import ms.inz.systemisaf.model.User;
import ms.inz.systemisaf.model.workout.DailyWorkoutPlan;
import ms.inz.systemisaf.model.workout.Exercise;
import ms.inz.systemisaf.model.workout.WeeklyWorkoutPlan;
import ms.inz.systemisaf.model.workout.WorkoutSession;
import ms.inz.systemisaf.repositories.ExerciseRepository;
import ms.inz.systemisaf.repositories.UserRepository;
import ms.inz.systemisaf.repositories.WeeklyWorkoutPlanRepository;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.util.*;
@Service
public class WorkoutService {
    private final ExerciseRepository exerciseRepository;
    private final WeeklyWorkoutPlanRepository weeklyWorkoutPlanRepository;
    private final UserRepository userRepository;
    public WorkoutService(ExerciseRepository exerciseRepository,
                          WeeklyWorkoutPlanRepository weeklyWorkoutPlanRepository,
                          UserRepository userRepository) {
        this.exerciseRepository = exerciseRepository;
        this.weeklyWorkoutPlanRepository = weeklyWorkoutPlanRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public WeeklyWorkoutPlanDto createSplitWorkout(Long userId) {
        WeeklyWorkoutPlan workoutPlan = createWorkoutPlan(userId, TypeOfWorkoutEnum.SPLIT, 4);
        setActiveWorkoutPlan(userId, workoutPlan.getId());
        return WorkoutMapper.weeklyWorkoutPlanToDto(workoutPlan);
    }

    @Transactional
    public WeeklyWorkoutPlanDto createFBWWorkout(Long userId) {
        WeeklyWorkoutPlan workoutPlan = createWorkoutPlan(userId, TypeOfWorkoutEnum.FBW, 5);
        setActiveWorkoutPlan(userId, workoutPlan.getId());
        return WorkoutMapper.weeklyWorkoutPlanToDto(workoutPlan);
    }

    @Transactional
    public WeeklyWorkoutPlanDto createUpperLowerWorkout(Long userId) {
        WeeklyWorkoutPlan workoutPlan = createWorkoutPlan(userId, TypeOfWorkoutEnum.UPPERLOWER, 4);
        setActiveWorkoutPlan(userId, workoutPlan.getId());
        return WorkoutMapper.weeklyWorkoutPlanToDto(workoutPlan);
    }

    private WeeklyWorkoutPlan createWorkoutPlan(Long userId, TypeOfWorkoutEnum type, int days) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        WeeklyWorkoutPlan workoutPlan = new WeeklyWorkoutPlan();
        workoutPlan.setUser(user);
        workoutPlan.setActive(false);
        workoutPlan.setTypeOfWorkout(type);

        List<DailyWorkoutPlan> dailyWorkoutPlans = new ArrayList<>();
        Set<Exercise> usedExercises = new HashSet<>();
        Set<MuscleGroupEnum> usedMuscleGroupsInWeek = new HashSet<>();

        for (int i = 0; i < days; i++) {
            DailyWorkoutPlan dailyPlan = new DailyWorkoutPlan();
            dailyPlan.setDayOfWeek(DayOfWeek.of((i % 7) + 1));

            List<WorkoutSession> workoutSessions = new ArrayList<>();
            switch (type) {
                case SPLIT:
                    workoutSessions.add(createSplitSession(usedExercises, usedMuscleGroupsInWeek));
                    break;
                case FBW:
                    workoutSessions.add(createFullBodySession(usedExercises));
                    break;
                case UPPERLOWER:
                    workoutSessions.add(createUpperLowerSession(i, usedExercises));
                    break;
            }

            dailyPlan.setSessions(workoutSessions);
            dailyWorkoutPlans.add(dailyPlan);
        }

        workoutPlan.setDailyWorkoutPlans(dailyWorkoutPlans);
        return weeklyWorkoutPlanRepository.save(workoutPlan);
    }

    private WorkoutSession createSplitSession(Set<Exercise> usedExercises, Set<MuscleGroupEnum> usedMuscleGroupsInWeek) {
        List<MuscleGroupEnum> muscleGroups = Arrays.asList(
                MuscleGroupEnum.LEGS,
                MuscleGroupEnum.GLUTES,
                MuscleGroupEnum.BACK,
                MuscleGroupEnum.CHEST,
                MuscleGroupEnum.SHOULDERS,
                MuscleGroupEnum.BICEPS,
                MuscleGroupEnum.TRICEPS
        );

        List<MuscleGroupEnum> availableGroups = new ArrayList<>();
        for (MuscleGroupEnum group : muscleGroups) {
            if (!usedMuscleGroupsInWeek.contains(group)) {
                availableGroups.add(group);
            }
        }

        if (availableGroups.size() < 2) {
            usedMuscleGroupsInWeek.clear();
            availableGroups = new ArrayList<>(muscleGroups);
        }

        Collections.shuffle(availableGroups);
        MuscleGroupEnum firstMuscleGroup = availableGroups.get(0);
        MuscleGroupEnum secondMuscleGroup = availableGroups.get(1);

        usedMuscleGroupsInWeek.add(firstMuscleGroup);
        usedMuscleGroupsInWeek.add(secondMuscleGroup);

        List<Exercise> exercisesForFirstGroup = exerciseRepository.findByMuscleGroup(firstMuscleGroup);
        List<Exercise> exercisesForSecondGroup = exerciseRepository.findByMuscleGroup(secondMuscleGroup);

        Exercise exercise1 = getRandomExercise(exercisesForFirstGroup, usedExercises);
        Exercise exercise2 = getRandomExercise(exercisesForFirstGroup, usedExercises);
        Exercise exercise3 = getRandomExercise(exercisesForFirstGroup, usedExercises);
        Exercise exercise4 = getRandomExercise(exercisesForSecondGroup, usedExercises);
        Exercise exercise5 = getRandomExercise(exercisesForSecondGroup, usedExercises);
        Exercise exercise6 = getRandomExercise(exercisesForSecondGroup, usedExercises);

        List<Exercise> selectedExercises = Arrays.asList(exercise1, exercise2, exercise3, exercise4, exercise5, exercise6);
        selectedExercises.removeIf(Objects::isNull);

        WorkoutSession workoutSession = new WorkoutSession();
        workoutSession.setExercises(selectedExercises);

        return workoutSession;
    }

    private WorkoutSession createFullBodySession(Set<Exercise> usedExercises) {
        List<MuscleGroupEnum> muscleGroups = Arrays.asList(
                MuscleGroupEnum.LEGS,
                MuscleGroupEnum.BACK,
                MuscleGroupEnum.CHEST,
                MuscleGroupEnum.SHOULDERS,
                MuscleGroupEnum.BICEPS,
                MuscleGroupEnum.TRICEPS,
                MuscleGroupEnum.CORE
        );

        WorkoutSession workoutSession = new WorkoutSession();
        List<Exercise> selectedExercises = new ArrayList<>();

        for (MuscleGroupEnum group : muscleGroups) {
            List<Exercise> exercisesForGroup = exerciseRepository.findByMuscleGroup(group);
            Exercise exercise = getRandomExercise(exercisesForGroup, usedExercises);
            if (exercise != null) {
                selectedExercises.add(exercise);
            }
        }

        workoutSession.setExercises(selectedExercises);
        return workoutSession;
    }

    private WorkoutSession createUpperLowerSession(int dayIndex, Set<Exercise> usedExercises) {
        List<MuscleGroupEnum> upperBodyGroups = Arrays.asList(
                MuscleGroupEnum.CHEST, MuscleGroupEnum.BACK,
                MuscleGroupEnum.SHOULDERS, MuscleGroupEnum.BICEPS, MuscleGroupEnum.TRICEPS
        );

        List<MuscleGroupEnum> lowerBodyGroups = Arrays.asList(
                MuscleGroupEnum.LEGS, MuscleGroupEnum.GLUTES,
                MuscleGroupEnum.CORE
        );

        List<MuscleGroupEnum> targetGroups = (dayIndex % 2 == 0) ? upperBodyGroups : lowerBodyGroups;

        List<Exercise> exercisesForGroup = new ArrayList<>();
        for (MuscleGroupEnum group : targetGroups) {
            exercisesForGroup.addAll(exerciseRepository.findByMuscleGroup(group));
        }

        WorkoutSession workoutSession = new WorkoutSession();
        List<Exercise> selectedExercises = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Exercise exercise = getRandomExercise(exercisesForGroup, usedExercises);
            if (exercise != null) {
                selectedExercises.add(exercise);
            }
        }

        workoutSession.setExercises(selectedExercises);
        return workoutSession;
    }

    private Exercise getRandomExercise(List<Exercise> exercises, Set<Exercise> usedExercises) {
        List<Exercise> availableExercises = new ArrayList<>();
        for (Exercise exercise : exercises) {
            if (!usedExercises.contains(exercise)) {
                availableExercises.add(exercise);
            }
        }
        if (availableExercises.isEmpty()) {
            return null;
        }
        Collections.shuffle(availableExercises);
        Exercise selectedExercise = availableExercises.get(0);
        usedExercises.add(selectedExercise);
        return selectedExercise;
    }

    @Transactional
    public void setActiveWorkoutPlan(Long userId, Long workoutPlanId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        WeeklyWorkoutPlan newActiveWorkoutPlan = weeklyWorkoutPlanRepository.findById(workoutPlanId)
                .orElseThrow(() -> new IllegalArgumentException("Workout plan not found"));

        if (!newActiveWorkoutPlan.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Workout plan does not belong to the user");
        }

        WeeklyWorkoutPlan currentActivePlan = user.getActiveWorkoutPlan();
        if (currentActivePlan != null) {
            currentActivePlan.setActive(false);
            weeklyWorkoutPlanRepository.save(currentActivePlan);
        }

        newActiveWorkoutPlan.setActive(true);
        user.setActiveWorkoutPlan(newActiveWorkoutPlan);

        userRepository.save(user);
        weeklyWorkoutPlanRepository.save(newActiveWorkoutPlan);
    }
    @Transactional
    public WeeklyWorkoutPlanDto getActiveWorkoutPlan(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        WeeklyWorkoutPlan activePlan = user.getActiveWorkoutPlan();

        if (activePlan == null) {
            throw new IllegalArgumentException("User has no active workout plan.");
        }

        return WorkoutMapper.weeklyWorkoutPlanToDto(activePlan);
    }
}