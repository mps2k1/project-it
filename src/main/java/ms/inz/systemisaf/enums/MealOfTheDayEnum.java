package ms.inz.systemisaf.enums;

public enum MealOfTheDayEnum {
    BREAKFAST("Śniadanie"),
    LUNCH("Obiad"),
    DINNER("Kolacja");

    private final String displayName;

    MealOfTheDayEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}