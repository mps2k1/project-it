package ms.inz.systemisaf.mapper;
import ms.inz.systemisaf.dto.MeasurementDto;
import ms.inz.systemisaf.dto.UserDto;
import ms.inz.systemisaf.model.Measurement;
import ms.inz.systemisaf.model.User;
import java.util.List;
import java.util.stream.Collectors;
public class UserMapper {

     public static MeasurementDto measurementToDto(Measurement measurement) {
        if (measurement == null) {
            return null;
        }

        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setId(measurement.getId());
        measurementDto.setUserId(measurement.getUser().getId());
        measurementDto.setWeight(measurement.getWeight());
        measurementDto.setHeight(measurement.getHeight());
        measurementDto.setBmi(measurement.getBmi());
        measurementDto.setCreatedAt(measurement.getCreatedAt());

        return measurementDto;
    }


    public static Measurement measurementToEntity(MeasurementDto measurementDto, User user) {
        if (measurementDto == null) {
            return null;
        }

        Measurement measurement = new Measurement();
        measurement.setId(measurementDto.getId());
        measurement.setUser(user);
        measurement.setWeight(measurementDto.getWeight());
        measurement.setHeight(measurementDto.getHeight());
        measurement.setBmi(measurementDto.getBmi());
        measurement.setCreatedAt(measurementDto.getCreatedAt());

        return measurement;
    }
     public static UserDto userToDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        if (user.getActiveWorkoutPlan() != null) {
            userDto.setActiveWorkoutPlanId(user.getActiveWorkoutPlan().getId());
        }

        if (user.getActiveMealPlan() != null) {
            userDto.setActiveMealPlanId(user.getActiveMealPlan().getId());
        }

         List<Long> measurementIds = user.getMeasurements().stream()
                .map(Measurement::getId)
                .collect(Collectors.toList());
        userDto.setMeasurementIds(measurementIds);

         List<Long> workoutPlanIds = user.getWorkoutPlans().stream()
                .map(weeklyWorkoutPlan -> weeklyWorkoutPlan.getId())
                .collect(Collectors.toList());
        userDto.setWorkoutPlanIds(workoutPlanIds);

        List<Long> weeklyMealPlanIds = user.getWeeklyMealPlans().stream()
                .map(weeklyMealPlan -> weeklyMealPlan.getId())
                .collect(Collectors.toList());
        userDto.setWeeklyMealPlanIds(weeklyMealPlanIds);

        return userDto;
    }

     public static User userToEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());


        return user;
    }
}