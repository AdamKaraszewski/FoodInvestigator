package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.nutritionalvaluenameDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class NutritionalValueNameOnAllNutritionalValueGroupsNutritionalValuesListDTO {
    private UUID id;
    private String name;
    private String group;
}
