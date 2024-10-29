package ms.inz.systemisaf.enums;

public enum MealOfTheDayEnum {
    BREAKFAST("Śniadanie"),  // Śniadanie
    LUNCH("Obiad"),          // Obiad
    DINNER("Kolacja");       // Kolacja

    private final String displayName;

    MealOfTheDayEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}