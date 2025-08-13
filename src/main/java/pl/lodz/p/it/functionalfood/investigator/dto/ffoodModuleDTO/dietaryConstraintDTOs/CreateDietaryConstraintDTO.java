package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryConstraintDTOs;

import pl.lodz.p.it.functionalfood.investigator.enums.DietaryConstraintTarget;
import pl.lodz.p.it.functionalfood.investigator.enums.DietaryConstraintWeights;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateDietaryConstraintDTO {
    private DietaryConstraintWeights weight;
    private UUID elementId;
    private DietaryConstraintTarget target;
}
