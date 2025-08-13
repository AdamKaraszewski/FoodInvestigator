package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class ConstraintCounterResults {
    private List<UUID> bannedUuids;
    private List<UUID> requiredUuids;
    private List<UUID> niceToSeeUuids;
}
