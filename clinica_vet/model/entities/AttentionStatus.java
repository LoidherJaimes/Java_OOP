package clinica_vet.model.entities;

public enum AttentionStatus {
    IN_PROGRESS("En Curso"),
    COMPLETED("Atendida"),
    NO_SHOW("No Asistió"),
    RESCHEDULED("Reprogramar");

    private final String displayName;

    AttentionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isFinalState() {
        return this == COMPLETED || this == NO_SHOW || this == RESCHEDULED;
    }

    public boolean isEditable() {
        return this == IN_PROGRESS;
    }

    @Override
    public String toString() {
        return displayName;
    }
}