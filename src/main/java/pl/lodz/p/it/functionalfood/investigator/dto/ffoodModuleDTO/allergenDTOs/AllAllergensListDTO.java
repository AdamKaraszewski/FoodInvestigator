package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.allergenDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllAllergensListDTO {
    private List<String> allergens;
}
