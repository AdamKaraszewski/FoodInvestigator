package pl.lodz.p.it.functionalfood.investigator.util.converters;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.ratingsDTOs.RatingListDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.ratingsDTOs.RatingOnRatingListDTO;
import pl.lodz.p.it.functionalfood.investigator.entities.Rating;

import java.util.ArrayList;
import java.util.List;

public class RatingConverter {
    public static RatingListDTO createRatingListDTO(List<Rating> ratings) {
        List<RatingOnRatingListDTO> ratingsListOnDTO = new ArrayList<>();
        for (Rating rating : ratings) {
            ratingsListOnDTO.add(createRatingOnListDTO(rating));
        }
        return new RatingListDTO(ratingsListOnDTO);
    }

    private static RatingOnRatingListDTO createRatingOnListDTO(Rating ratings) {
        return new RatingOnRatingListDTO(ratings.getId(), ratings.getName(), ratings.getGroupName());
    }
}
