package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.packagetypeDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AllPackageTypesOnListDTO {
    private List<PackageTypeDTO> packageTypes;
}
