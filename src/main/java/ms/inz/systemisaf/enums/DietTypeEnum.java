package ms.inz.systemisaf.enums;

public enum DietTypeEnum {
    HIGH_PROTEIN("High Protein"),  // Wysokobiałkowa
    KETO("Keto"),                  // Keto
    VEGAN("Vegan");                // Wegan

    private final String displayName;

    DietTypeEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}