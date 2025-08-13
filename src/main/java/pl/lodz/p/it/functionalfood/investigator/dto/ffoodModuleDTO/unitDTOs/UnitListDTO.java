package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.unitDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class UnitListDTO {
    private List<UnitDTO> units;
}
