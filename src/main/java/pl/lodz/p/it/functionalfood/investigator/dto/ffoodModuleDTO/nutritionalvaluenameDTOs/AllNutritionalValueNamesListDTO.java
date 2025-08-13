package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.nutritionalvaluenameDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AllNutritionalValueNamesListDTO {
    private List<NutritionalValueNameOnAllNutritionalValueGroupsNutritionalValuesListDTO> nutritionalValues;
}
