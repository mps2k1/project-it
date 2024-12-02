package ms.inz.systemisaf.enums;
public enum TypeOfWorkoutEnum {
    FBW("Full Body Workout"),
    SPLIT("Split Training"),
    UPPERLOWER("Upper Lower"),
    CUSTOM("Plan własny użytkownika");

    private final String displayName;

    TypeOfWorkoutEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
