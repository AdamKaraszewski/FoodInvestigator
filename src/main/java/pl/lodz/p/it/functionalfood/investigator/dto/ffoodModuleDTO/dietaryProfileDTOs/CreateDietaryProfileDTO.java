package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryConstraintDTOs.CreateDietaryConstraintDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateDietaryProfileDTO {
    private String name;
    private String description;
    private List<CreateDietaryConstraintDTO> constraints;
}
