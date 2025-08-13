package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.ingredientDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class IngredientsOnAllIngredientsListDTO {
    private UUID id;
    private String name;
}
