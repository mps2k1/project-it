package ms.inz.systemisaf.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String password;

    private Long activeWorkoutPlanId; // ID aktywnego planu treningowego
    private Long activeMealPlanId; // ID aktywnego planu posiłków

    private List<Long> measurementIds; // ID pomiarów użytkownika
    private List<Long> workoutPlanIds; // ID wszystkich planów treningowych użytkownika
    private List<Long> weeklyMealPlanIds; // ID wszystkich planów posiłków użytkownika
}