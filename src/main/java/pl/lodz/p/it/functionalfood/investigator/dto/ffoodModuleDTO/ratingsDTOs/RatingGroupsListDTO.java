package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.ratingsDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RatingGroupsListDTO {
    private List<RatingGroupOnRatingGroupListDTO> ratingGroups;
}
