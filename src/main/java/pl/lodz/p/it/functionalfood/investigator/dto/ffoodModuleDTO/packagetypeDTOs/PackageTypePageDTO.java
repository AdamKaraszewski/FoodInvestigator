package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.packagetypeDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PackageTypePageDTO {
    private int totalPages;
    private long totalElements;
    private boolean isLastPage;
    private List<PackageTypeDTO> packageTypes;
}
