package pl.lodz.p.it.functionalfood.investigator.util.converters;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.nutritionalvaluenameDTOs.*;
import pl.lodz.p.it.functionalfood.investigator.entities.NutritionalValueName;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.nutritionalvaluenameDTOs.AllNutritionalValueNamesListDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.nutritionalvaluenameDTOs.NutritionalValueNameDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.nutritionalvaluenameDTOs.NutritionalValueNameOnAllNutritionalValueGroupsNutritionalValuesListDTO;

import java.util.ArrayList;
import java.util.List;

public class NutritionalValueNameConverter {
    public static NutritionalValueNameDTO createNutritionalValueNameDTO(NutritionalValueName nutritionalValueName) {
        return new NutritionalValueNameDTO(nutritionalValueName.getId(), nutritionalValueName.getName(), nutritionalValueName.getGroup().getGroupName());
    }

    private static NutritionalValueNameOnAllNutritionalValueGroupsNutritionalValuesListDTO createNutritionalValueNameOnList(NutritionalValueName nutritionalValueName) {
        return new NutritionalValueNameOnAllNutritionalValueGroupsNutritionalValuesListDTO(nutritionalValueName.getId(), nutritionalValueName.getName(), nutritionalValueName.getGroup().getGroupName());
    }

    public static AllNutritionalValueNamesListDTO createAllNutritionalValueNamesListDTO(List<NutritionalValueName> nutritionalValueNames) {
        List<NutritionalValueNameOnAllNutritionalValueGroupsNutritionalValuesListDTO> allNutritionalValueNamesListDTO = new ArrayList<>();
        for (NutritionalValueName nutritionalValueName : nutritionalValueNames) {
            allNutritionalValueNamesListDTO.add(createNutritionalValueNameOnList(nutritionalValueName));
        }
        return new AllNutritionalValueNamesListDTO(allNutritionalValueNamesListDTO);
    }

}
