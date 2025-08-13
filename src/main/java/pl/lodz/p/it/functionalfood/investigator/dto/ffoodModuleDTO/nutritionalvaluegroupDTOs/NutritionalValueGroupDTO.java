package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.nutritionalvaluegroupDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class NutritionalValueGroupDTO {
    private UUID id;
    private String name;
}
