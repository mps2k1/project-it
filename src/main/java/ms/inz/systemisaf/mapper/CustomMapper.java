package ms.inz.systemisaf.mapper;

import ms.inz.systemisaf.dto.CustomExerciseDto;
import ms.inz.systemisaf.dto.CustomWorkoutDayDto;
import ms.inz.systemisaf.dto.CustomWorkoutPlanDto;
import ms.inz.systemisaf.enums.MuscleGroupEnum;
import ms.inz.systemisaf.model.customworkout.CustomWorkoutPlan;

import java.util.List;

public class CustomMapper {

    public static CustomWorkoutPlanDto toDto(CustomWorkoutPlan plan) {
        CustomWorkoutPlanDto dto = new CustomWorkoutPlanDto();
        dto.setId(plan.getId());
        dto.setPlanName(plan.getPlanName());
        dto.setUserId(plan.getUser().getId());

        List<CustomWorkoutDayDto> days = plan.getDailyWorkoutPlans().stream()
                .map(day -> {
                    CustomWorkoutDayDto dayDto = new CustomWorkoutDayDto();
                    dayDto.setDayOfWeek(day.getDayOfWeek());
                    dayDto.setExercises(day.getSessions().stream()
                            .flatMap(session -> session.getExercises().stream())
                            .map(exercise -> {
                                CustomExerciseDto exerciseDto = new CustomExerciseDto();
                                exerciseDto.setName(exercise.getName());
                                exerciseDto.setInstruction(exercise.getInstruction());
                                exerciseDto.setRepetitions(exercise.getRepetitions());
                                exerciseDto.setSets(exercise.getSets());
                                exerciseDto.setMuscleGroup(MuscleGroupEnum.valueOf(exercise.getMuscleGroup()));
                                return exerciseDto;
                            }).toList());
                    return dayDto;
                }).toList();

        dto.setWorkoutDays(days);
        return dto;
    }
}