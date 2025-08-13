package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateNutritionalIndexDTO {
    private String legend;
    private Integer indexValue;
}
