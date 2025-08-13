package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileTemplateDTOs;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryConstraintDTOs.DietaryConstraintThatDoesNotBelongToGroupDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class DietaryProfileTemplateDTO {
    private UUID id;
    private String name;
    private String description;
    private List<DietaryConstraintThatDoesNotBelongToGroupDTO> constraints;
}
