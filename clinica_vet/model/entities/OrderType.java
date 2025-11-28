package clinica_vet.model.entities;

public enum OrderType {
    EXAM("Examen"),
    CONTROL("Control"),
    PROCEDURE("Procedimiento");

    private final String displayName;
    OrderType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean requiresAppointment() {
        return this == CONTROL || this == PROCEDURE;
    }

    public boolean isUrgentByDefault() {
        return this == EXAM;
    }

    @Override
    public String toString() {
        return displayName;
    }
}