package ms.inz.systemisaf.services;
import jakarta.transaction.Transactional;
import ms.inz.systemisaf.dto.WeeklyMealPlanDto;
import ms.inz.systemisaf.enums.DietTypeEnum;
import ms.inz.systemisaf.enums.MealOfTheDayEnum;
import ms.inz.systemisaf.mapper.MealMapper;
import ms.inz.systemisaf.model.User;
import ms.inz.systemisaf.model.meal.DailyMealPlan;
import ms.inz.systemisaf.model.meal.Meal;
import ms.inz.systemisaf.model.meal.MealSession;
import ms.inz.systemisaf.model.meal.WeeklyMealPlan;
import ms.inz.systemisaf.repositories.MealRepository;
import ms.inz.systemisaf.repositories.UserRepository;
import ms.inz.systemisaf.repositories.WeeklyMealPlanRepository;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.util.*;
@Service
public class MealService {

    private final MealRepository mealRepository;
    private final WeeklyMealPlanRepository weeklyMealPlanRepository;
    private final UserRepository userRepository;

    public MealService(MealRepository mealRepository,
                       WeeklyMealPlanRepository weeklyMealPlanRepository,
                       UserRepository userRepository) {
        this.mealRepository = mealRepository;
        this.weeklyMealPlanRepository = weeklyMealPlanRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public WeeklyMealPlanDto createKetoMealPlan(Long userId) {
        WeeklyMealPlan mealPlan = createMealPlan(userId, DietTypeEnum.KETO, 7);
        return MealMapper.weeklyMealPlanToDto(mealPlan);
    }

    @Transactional
    public WeeklyMealPlanDto createHighProteinMealPlan(Long userId) {
        WeeklyMealPlan mealPlan = createMealPlan(userId, DietTypeEnum.HIGH_PROTEIN, 7);
        return MealMapper.weeklyMealPlanToDto(mealPlan);
    }

    private WeeklyMealPlan createMealPlan(Long userId, DietTypeEnum dietType, int days) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        WeeklyMealPlan mealPlan = new WeeklyMealPlan();
        mealPlan.setUser(user);
        mealPlan.setActive(false);

        List<DailyMealPlan> dailyMealPlans = new ArrayList<>();
        Set<Meal> usedMeals = new HashSet<>();

        for (int i = 0; i < days; i++) {
            DailyMealPlan dailyPlan = new DailyMealPlan();
            dailyPlan.setDayOfWeek(DayOfWeek.of((i % 7) + 1));

            List<MealSession> mealSessions = new ArrayList<>();
            mealSessions.add(createMealSession(MealOfTheDayEnum.BREAKFAST, dietType, usedMeals));
            mealSessions.add(createMealSession(MealOfTheDayEnum.LUNCH, dietType, usedMeals));
            mealSessions.add(createMealSession(MealOfTheDayEnum.DINNER, dietType, usedMeals));

            dailyPlan.setMealSessions(mealSessions);
            dailyMealPlans.add(dailyPlan);
        }

        mealPlan.setDailyMealPlans(dailyMealPlans);
        return weeklyMealPlanRepository.save(mealPlan);
    }

    private MealSession createMealSession(MealOfTheDayEnum mealType, DietTypeEnum dietType, Set<Meal> usedMeals) {
        List<Meal> meals = mealRepository.findByMealOfTheDayAndDietType(mealType, dietType);
        meals.removeAll(usedMeals);
        if (meals.isEmpty()) {
            throw new IllegalArgumentException("No meals available for this diet type and meal type");
        }

        Collections.shuffle(meals);
        Meal selectedMeal = meals.get(0);
        usedMeals.add(selectedMeal);

        MealSession mealSession = new MealSession();
        mealSession.setMeals(Collections.singletonList(selectedMeal));

        return mealSession;
    }

    @Transactional
    public void setActiveMealPlan(Long userId, Long mealPlanId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        WeeklyMealPlan newActiveMealPlan = weeklyMealPlanRepository.findById(mealPlanId)
                .orElseThrow(() -> new IllegalArgumentException("Meal plan not found"));

        if (!newActiveMealPlan.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Meal plan does not belong to the user");
        }

        WeeklyMealPlan currentActiveMealPlan = user.getActiveMealPlan();
        if (currentActiveMealPlan != null) {
            currentActiveMealPlan.setActive(false);
            weeklyMealPlanRepository.save(currentActiveMealPlan);
        }

        newActiveMealPlan.setActive(true);
        user.setActiveMealPlan(newActiveMealPlan);

        userRepository.save(user);
        weeklyMealPlanRepository.save(newActiveMealPlan);
    }
}