package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryConstraintDTOs;

import pl.lodz.p.it.functionalfood.investigator.enums.DietaryConstraintWeights;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DietaryConstraintThatDoesNotBelongToGroupDTO {
    private String category;
    private String group;
    private UUID itemId;
    private String itemName;
    private DietaryConstraintWeights weight;
}
