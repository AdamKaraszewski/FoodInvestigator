package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.unitDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UnitPageDTO {
    private int totalPages;
    private long totalElements;
    private boolean isLastPage;
    private List<UnitDTO> units;
}
