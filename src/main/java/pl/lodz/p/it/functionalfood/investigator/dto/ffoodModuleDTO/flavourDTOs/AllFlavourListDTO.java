package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.flavourDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
public class AllFlavourListDTO {
    private List<String> flavours;
}
