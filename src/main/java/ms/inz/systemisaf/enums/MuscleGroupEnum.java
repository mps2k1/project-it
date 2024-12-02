package ms.inz.systemisaf.enums;
public enum MuscleGroupEnum {
    LEGS("nogi"),
    BACK("plecy"),
    GLUTES("pośladki"),
    CHEST("klatka piersiowa"),
    SHOULDERS("barki"),
    BICEPS("biceps"),
    TRICEPS("triceps"),
    CORE("mięśnie brzucha"),
    FULL_BODY("całe ciało");

    private final String displayName;

    MuscleGroupEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}