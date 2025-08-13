package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.nutritionalvaluenameDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class NutritionalValueNamePageDTO {
    private int totalPages;
    private long totalElements;
    private boolean isLastPage;
    private List<NutritionalValueNameDTO> nutritionalValueNames;
}
