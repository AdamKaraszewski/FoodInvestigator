package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.ingredientDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AllIngredientsListDTO {
    private List<IngredientsOnAllIngredientsListDTO> ingredients;
}
