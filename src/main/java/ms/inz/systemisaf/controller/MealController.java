package ms.inz.systemisaf.controller;
import ms.inz.systemisaf.dto.WeeklyMealPlanDto;
import ms.inz.systemisaf.services.MealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    // Endpoint do wygenerowania planu Keto
    @PostMapping("/create/keto")
    public ResponseEntity<WeeklyMealPlanDto> createKetoMealPlan(@RequestParam Long userId) {
        WeeklyMealPlanDto mealPlan = mealService.createKetoMealPlan(userId);
        return new ResponseEntity<>(mealPlan, HttpStatus.CREATED);
    }

    // Endpoint do wygenerowania planu wysokobiałkowego
    @PostMapping("/create/high-protein")
    public ResponseEntity<WeeklyMealPlanDto> createHighProteinMealPlan(@RequestParam Long userId) {
        WeeklyMealPlanDto mealPlan = mealService.createHighProteinMealPlan(userId);
        return new ResponseEntity<>(mealPlan, HttpStatus.CREATED);
    }

    // Endpoint do ustawiania planu jako aktywnego
    @PutMapping("/set-active")
    public ResponseEntity<Void> setActiveMealPlan(@RequestParam Long userId, @RequestParam Long mealPlanId) {
        mealService.setActiveMealPlan(userId, mealPlanId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}