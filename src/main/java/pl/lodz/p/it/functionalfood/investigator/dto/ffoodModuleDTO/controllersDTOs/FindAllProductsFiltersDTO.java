package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.controllersDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class FindAllProductsFiltersDTO {
    private UUID profileId;
}
