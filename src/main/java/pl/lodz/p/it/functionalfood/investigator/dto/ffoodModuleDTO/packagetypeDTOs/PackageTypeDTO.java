package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.packagetypeDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class PackageTypeDTO {
    private UUID id;
    private String name;
}
