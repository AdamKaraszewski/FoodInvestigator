package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class CreateNutritionalValueDTO {
    private UUID nutritionalValueNameId;
    private Double quantity;
    private UUID unitId;
    private Double nrv;
}
