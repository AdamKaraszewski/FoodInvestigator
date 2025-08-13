package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileTemplateDTOs;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryConstraintDTOs.CreateDietaryConstraintDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UpdateDietaryProfileTemplateDTO {
    private String name;
    private String description;
    private List<CreateDietaryConstraintDTO> constraints;
}
