package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryConstraintDTOs;

import pl.lodz.p.it.functionalfood.investigator.enums.DietaryConstraintWeights;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class DietaryConstraintDTO {
    private String category;
    private UUID ItemId;
    private String ItemName;
    private DietaryConstraintWeights weight;
}
