package ms.inz.systemisaf.services;
import jakarta.transaction.Transactional;
import ms.inz.systemisaf.dto.MeasurementDto;
import ms.inz.systemisaf.dto.WeeklyMealPlanDto;
import ms.inz.systemisaf.dto.WeeklyWorkoutPlanDto;
import ms.inz.systemisaf.mapper.MealMapper;
import ms.inz.systemisaf.mapper.UserMapper;
import ms.inz.systemisaf.mapper.WorkoutMapper;
import ms.inz.systemisaf.model.Measurement;
import ms.inz.systemisaf.model.User;
import ms.inz.systemisaf.model.meal.WeeklyMealPlan;
import ms.inz.systemisaf.model.workout.WeeklyWorkoutPlan;
import ms.inz.systemisaf.repositories.MeasurementRepository;
import ms.inz.systemisaf.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MeasurementRepository measurementRepository;

    public UserService(UserRepository userRepository, MeasurementRepository measurementRepository) {
        this.userRepository = userRepository;
        this.measurementRepository = measurementRepository;
    }

     @Transactional
    public void addMeasurement(Long userId, Double weight, Double height) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Measurement measurement = new Measurement();
        measurement.setUser(user);
        measurement.setWeight(weight);
        measurement.setHeight(height);
        measurement.setBmi(calculateBMI(weight, height));
        measurement.setCreatedAt(LocalDateTime.now());


        measurementRepository.save(measurement);
    }

     private Double calculateBMI(Double weight, Double height) {
        if (height == null || weight == null || height <= 0) {
            throw new IllegalArgumentException("Invalid height or weight");
        }
        return weight / (height * height);
    }

     @Transactional
    public MeasurementDto getCurrentMeasurement(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Measurement measurement = measurementRepository.findTopByUserOrderByCreatedAtDesc(user)
                .orElseThrow(() -> new IllegalArgumentException("No measurements found for this user"));

        return UserMapper.measurementToDto(measurement);
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

    @Transactional
    public List<MeasurementDto> getAllMeasurements(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Measurement> measurements = measurementRepository.findByUserOrderByCreatedAtDesc(user);

        if (measurements.isEmpty()) {
            throw new IllegalArgumentException("No measurements found for this user");
        }

        return measurements.stream()
                .map(UserMapper::measurementToDto)
                .collect(Collectors.toList());
    }


     @Transactional
    public WeeklyMealPlanDto getActiveMealPlan(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        WeeklyMealPlan activeMealPlan = user.getActiveMealPlan();

        if (activeMealPlan == null) {
            throw new IllegalArgumentException("No active meal plan found for this user");
        }

        return MealMapper.weeklyMealPlanToDto(activeMealPlan);
    }

     @Transactional
    public void updateMeasurement(Long userId, Double weight, Double height) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

         addMeasurement(userId, weight, height);
    }
}