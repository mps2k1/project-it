package ms.inz.systemisaf.enums;
public enum TypeOfWorkoutEnum {
    FBW("Full Body Workout"),   // Trening całego ciała
    SPLIT("Split Training"),    // Trening dzielony
    UPPERLOWER("Upper Lower");

    private final String displayName;

    TypeOfWorkoutEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
