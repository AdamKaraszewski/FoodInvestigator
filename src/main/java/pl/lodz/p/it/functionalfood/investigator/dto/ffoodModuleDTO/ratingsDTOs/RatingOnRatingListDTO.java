package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.ratingsDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class RatingOnRatingListDTO {
    private UUID id;
    private String name;
    private String group;
}
