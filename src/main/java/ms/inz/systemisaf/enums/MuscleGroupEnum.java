package ms.inz.systemisaf.enums;

public enum MuscleGroupEnum {
    LEGS("nogi"),              // Nogi
    GLUTES("pośladki"),         // Pośladki
    BACK("plecy"),              // Plecy
    CHEST("klatka piersiowa"),  // Klatka piersiowa
    SHOULDERS("barki"),         // Barki
    ARMS("ramiona"),            // Ramiona (biceps, triceps)
    CORE("mięśnie brzucha"),    // Mięśnie brzucha
    FULL_BODY("całe ciało");    // Całe ciało (np. burpees)

    private final String displayName;

    MuscleGroupEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}