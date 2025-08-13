package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryConstraintDTOs.DietaryConstraintThatDoesNotBelongToGroupDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DietaryProfileDTO {
    private UUID id;
    private String name;
    private String description;
    private List<DietaryConstraintThatDoesNotBelongToGroupDTO> constraints;
}
