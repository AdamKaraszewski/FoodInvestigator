package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.ratingsDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class RatingListDTO {
    private List<RatingOnRatingListDTO> ratingList;
}
