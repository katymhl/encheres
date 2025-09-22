package fr.eni.encheres.bo.enumeration;

public enum StatutEnchere {
    NON_COMMENCEE(0),
    EN_COURS(1),
    CLOTUREE(2),
    LIVREE(3),
    ANNULE(100);

    private final int value;

    StatutEnchere(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // Méthode pour récupérer l'enum à partir de l'int
    public static StatutEnchere fromInt(int value) {
        for (StatutEnchere s : StatutEnchere.values()) {
            if (s.getValue() == value) {
                return s;
            }
        }
        throw new IllegalArgumentException("Statut inconnu: " + value);
    }
}
