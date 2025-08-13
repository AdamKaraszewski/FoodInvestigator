package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs;

import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.logic.DoesProductFollowsDietaryProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DietaryProfileSummary {
    public DietaryProfileDTO profile;
    public DoesProductFollowsDietaryProfile summary;
}
