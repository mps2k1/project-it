package ms.inz.systemisaf.mapper;

import ms.inz.systemisaf.dto.DailyMealPlanDto;
import ms.inz.systemisaf.dto.MealDto;
import ms.inz.systemisaf.dto.MealSessionDto;
import ms.inz.systemisaf.dto.WeeklyMealPlanDto;
import ms.inz.systemisaf.model.meal.DailyMealPlan;
import ms.inz.systemisaf.model.meal.Meal;
import ms.inz.systemisaf.model.meal.MealSession;
import ms.inz.systemisaf.model.meal.WeeklyMealPlan;

import java.util.List;
import java.util.stream.Collectors;

public class MealMapper {

    // Konwersja Meal na MealDto
    public static MealDto mealToDto(Meal meal) {
        if (meal == null) {
            return null;
        }
        MealDto mealDto = new MealDto();
        mealDto.setId(meal.getId());
        mealDto.setName(meal.getName());
        mealDto.setMealOfTheDay(meal.getMealOfTheDay());
        mealDto.setDietType(meal.getDietType());
        return mealDto;
    }

    // Konwersja MealDto na Meal
    public static Meal mealToEntity(MealDto mealDto) {
        if (mealDto == null) {
            return null;
        }
        Meal meal = new Meal();
        meal.setId(mealDto.getId());
        meal.setName(mealDto.getName());
        meal.setMealOfTheDay(mealDto.getMealOfTheDay());
        meal.setDietType(mealDto.getDietType());
        return meal;
    }

    // Konwersja MealSession na MealSessionDto
    public static MealSessionDto mealSessionToDto(MealSession mealSession) {
        if (mealSession == null) {
            return null;
        }
        MealSessionDto mealSessionDto = new MealSessionDto();
        mealSessionDto.setId(mealSession.getId());
        List<MealDto> mealDtos = mealSession.getMeals().stream()
                .map(MealMapper::mealToDto)
                .collect(Collectors.toList());
        mealSessionDto.setMeals(mealDtos);
        return mealSessionDto;
    }

    // Konwersja MealSessionDto na MealSession
    public static MealSession mealSessionToEntity(MealSessionDto mealSessionDto) {
        if (mealSessionDto == null) {
            return null;
        }
        MealSession mealSession = new MealSession();
        mealSession.setId(mealSessionDto.getId());
        List<Meal> meals = mealSessionDto.getMeals().stream()
                .map(MealMapper::mealToEntity)
                .collect(Collectors.toList());
        mealSession.setMeals(meals);
        return mealSession;
    }

    // Konwersja DailyMealPlan na DailyMealPlanDto
    public static DailyMealPlanDto dailyMealPlanToDto(DailyMealPlan dailyMealPlan) {
        if (dailyMealPlan == null) {
            return null;
        }
        DailyMealPlanDto dailyMealPlanDto = new DailyMealPlanDto();
        dailyMealPlanDto.setId(dailyMealPlan.getId());
        dailyMealPlanDto.setDayOfWeek(dailyMealPlan.getDayOfWeek());

        List<MealSessionDto> mealSessionDtos = dailyMealPlan.getMealSessions().stream()
                .map(MealMapper::mealSessionToDto)
                .collect(Collectors.toList());
        dailyMealPlanDto.setMealSessions(mealSessionDtos);

        return dailyMealPlanDto;
    }

    // Konwersja DailyMealPlanDto na DailyMealPlan
    public static DailyMealPlan dailyMealPlanToEntity(DailyMealPlanDto dailyMealPlanDto) {
        if (dailyMealPlanDto == null) {
            return null;
        }
        DailyMealPlan dailyMealPlan = new DailyMealPlan();
        dailyMealPlan.setId(dailyMealPlanDto.getId());
        dailyMealPlan.setDayOfWeek(dailyMealPlanDto.getDayOfWeek());

        List<MealSession> mealSessions = dailyMealPlanDto.getMealSessions().stream()
                .map(MealMapper::mealSessionToEntity)
                .collect(Collectors.toList());
        dailyMealPlan.setMealSessions(mealSessions);

        return dailyMealPlan;
    }

    // Konwersja WeeklyMealPlan na WeeklyMealPlanDto
    public static WeeklyMealPlanDto weeklyMealPlanToDto(WeeklyMealPlan weeklyMealPlan) {
        if (weeklyMealPlan == null) {
            return null;
        }
        WeeklyMealPlanDto weeklyMealPlanDto = new WeeklyMealPlanDto();
        weeklyMealPlanDto.setId(weeklyMealPlan.getId());
        weeklyMealPlanDto.setUserId(weeklyMealPlan.getUser() != null ? weeklyMealPlan.getUser().getId() : null);
        weeklyMealPlanDto.setActive(weeklyMealPlan.isActive());

        List<DailyMealPlanDto> dailyMealPlanDtos = weeklyMealPlan.getDailyMealPlans().stream()
                .map(MealMapper::dailyMealPlanToDto)
                .collect(Collectors.toList());
        weeklyMealPlanDto.setDailyMealPlans(dailyMealPlanDtos);

        return weeklyMealPlanDto;
    }

    // Konwersja WeeklyMealPlanDto na WeeklyMealPlan
    public static WeeklyMealPlan weeklyMealPlanToEntity(WeeklyMealPlanDto weeklyMealPlanDto) {
        if (weeklyMealPlanDto == null) {
            return null;
        }
        WeeklyMealPlan weeklyMealPlan = new WeeklyMealPlan();
        weeklyMealPlan.setId(weeklyMealPlanDto.getId());
        weeklyMealPlan.setActive(weeklyMealPlanDto.isActive());

        List<DailyMealPlan> dailyMealPlans = weeklyMealPlanDto.getDailyMealPlans().stream()
                .map(MealMapper::dailyMealPlanToEntity)
                .collect(Collectors.toList());
        weeklyMealPlan.setDailyMealPlans(dailyMealPlans);

        return weeklyMealPlan;
    }
}

