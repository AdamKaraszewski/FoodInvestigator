package pl.lodz.p.it.functionalfood.investigator.util.converters;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.packagetypeDTOs.AllPackageTypesOnListDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.packagetypeDTOs.PackageTypeDTO;
import pl.lodz.p.it.functionalfood.investigator.entities.PackageType;

import java.util.ArrayList;
import java.util.List;

public class PackageTypeConverter {
    public static PackageTypeDTO createPackageTypeDTO(PackageType packageType) {
        return new PackageTypeDTO(packageType.getId(), packageType.getName());
    }

    public static AllPackageTypesOnListDTO createAllPackageTypesListDTO(List<PackageType> list) {
        List<PackageTypeDTO> packageTypesDTOs = new ArrayList<>();
        for (PackageType packageType : list) {
            packageTypesDTOs.add(createPackageTypeDTO(packageType));
        }
        return new AllPackageTypesOnListDTO(packageTypesDTOs);
    }
}
