package ms.inz.systemisaf.controller;
import ms.inz.systemisaf.config.CustomUserDetails;
import ms.inz.systemisaf.dto.WeeklyMealPlanDto;
import ms.inz.systemisaf.services.MealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping("/create/keto")
    public ResponseEntity<Object> createKetoMealPlan(Authentication authentication) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            WeeklyMealPlanDto mealPlan = mealService.createKetoMealPlan(userId);
            return new ResponseEntity<>(mealPlan, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/create/high-protein")
    public ResponseEntity<Object> createHighProteinMealPlan(Authentication authentication) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            WeeklyMealPlanDto mealPlan = mealService.createHighProteinMealPlan(userId);
            return new ResponseEntity<>(mealPlan, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/set-active")
    public ResponseEntity<Object> setActiveMealPlan(Authentication authentication, @RequestParam Long mealPlanId) {
        try {
            Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
            mealService.setActiveMealPlan(userId, mealPlanId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
