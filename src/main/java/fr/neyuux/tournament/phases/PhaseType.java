package fr.neyuux.tournament.phases;

import fr.neyuux.tournament.phases.classes.SingleElimination;
import fr.neyuux.tournament.phases.classes.Swiss;
import lombok.Getter;

@Getter
public enum PhaseType {
    GROUP_STAGE(null),
    SWISS_SYSTEM(Swiss.class),
    SINGLE_ELIMINATION(SingleElimination.class),
    DOUBLE_ELIMINATION(null),
    ROUND_ROBIN(null);

    private final Class<? extends Phase> clazz;

    PhaseType(Class<? extends Phase> clazz) {
        this.clazz = clazz;
    }
}
