package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.logic;

import pl.lodz.p.it.functionalfood.investigator.enums.DietaryConstraintWeights;

import java.util.UUID;

public interface IConstraint {
    DietaryConstraintWeights getConstraintWeight();
    UUID getItemUuid();
}
