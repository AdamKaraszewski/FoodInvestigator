package pl.lodz.p.it.functionalfood.investigator.util.converters;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs.CreateDietaryProfileDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs.DietaryProfileDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs.DietaryProfileListDTO;
import pl.lodz.p.it.functionalfood.investigator.entities.DietaryProfile;

import java.util.ArrayList;
import java.util.List;

public class  DietaryProfileConverter {
    public static DietaryProfile createDietaryProfileFromDTO(CreateDietaryProfileDTO createDietaryProfileDTO) {
        return new DietaryProfile(createDietaryProfileDTO.getName(), createDietaryProfileDTO.getDescription());
    }

    public static DietaryProfileDTO createDietaryProfileDTO (DietaryProfile dietaryProfile) {
        return new DietaryProfileDTO(dietaryProfile.getId(), dietaryProfile.getName(), dietaryProfile.getDescription(),
                DietaryConstraintConverter.createConstraintListToDietaryProfileDTO(
                        dietaryProfile.getNutritionalValues(), dietaryProfile.getRatings(),
                        dietaryProfile.getPackageTypes()
                ));
    }

    public static DietaryProfileListDTO createDietaryProfileListDTO (List<DietaryProfile> dietaryProfiles) {
        List<DietaryProfileDTO> dietaryProfileListDTO = new ArrayList<>();
        for (DietaryProfile dietaryProfile : dietaryProfiles) {
            dietaryProfileListDTO.add(createDietaryProfileDTO(dietaryProfile));
        }
        return new DietaryProfileListDTO(dietaryProfileListDTO);
    }
}
