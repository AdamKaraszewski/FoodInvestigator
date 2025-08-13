package pl.lodz.p.it.functionalfood.investigator.util.converters;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.unitDTOs.UnitDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.unitDTOs.UnitListDTO;
import pl.lodz.p.it.functionalfood.investigator.entities.Unit;

import java.util.ArrayList;
import java.util.List;

public class UnitConverter {
    public static UnitDTO createUnitDTO(Unit unit) {
        return new UnitDTO(unit.getId(), unit.getName());
    }

    public static UnitListDTO createUnitListDTO(List<Unit> units) {
        List<UnitDTO> unitDTOs = new ArrayList<>();
        for (Unit unit : units) {
            unitDTOs.add(createUnitDTO(unit));
        }
        return new UnitListDTO(unitDTOs);
    }
}
