package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.nutritionalvaluegroupDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class NutritionalValueGroupAllDTO {
    private List<NutritionalValueGroupDTO> nutritionalValueGroups;
}
