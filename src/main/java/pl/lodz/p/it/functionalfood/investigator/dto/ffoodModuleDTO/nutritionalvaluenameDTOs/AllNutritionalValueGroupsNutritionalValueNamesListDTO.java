package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.nutritionalvaluenameDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AllNutritionalValueGroupsNutritionalValueNamesListDTO {
    private List<NutritionalValueNameOnAllNutritionalValueGroupsNutritionalValuesListDTO> nutritionalValues;
}
