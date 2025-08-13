package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.nutritionalvaluegroupDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class NutritionalValueGroupPageDTO {
    private int totalPages;
    private long totalElements;
    private boolean isLastPage;
    private List<NutritionalValueGroupDTO> nutritionalValueGroups;
}
