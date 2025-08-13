package pl.lodz.p.it.functionalfood.investigator.util.converters;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileTemplateDTOs.CreateDietaryProfileTemplateDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileTemplateDTOs.DietaryProfileTemplateDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileTemplateDTOs.DietaryProfileTemplatesListDTO;
import pl.lodz.p.it.functionalfood.investigator.entities.*;
import pl.lodz.p.it.functionalfood.investigator.entities.DietaryProfileTemplate;

import java.util.ArrayList;
import java.util.List;

public class DietaryProfileTemplateConverter {
    public static DietaryProfileTemplate createDietaryProfileTemplateFromDTO(CreateDietaryProfileTemplateDTO createDietaryProfileTemplateDTO) {
        return new DietaryProfileTemplate(createDietaryProfileTemplateDTO.getName(), createDietaryProfileTemplateDTO.getDescription());
    }

    public static DietaryProfileTemplateDTO createDietaryProfileTemplateDTO(DietaryProfileTemplate dietaryProfileTemplate) {
        return new DietaryProfileTemplateDTO(dietaryProfileTemplate.getId(), dietaryProfileTemplate.getName(), dietaryProfileTemplate.getDescription(),
                DietaryConstraintConverter.createConstraintListToDietaryProfileTemplateDTO(
                        dietaryProfileTemplate.getNutritionalValues(), dietaryProfileTemplate.getRatings(),
                        dietaryProfileTemplate.getPackageTypes()
                ));
    }

    public static DietaryProfileTemplatesListDTO createDietaryProfileTemplatesListDTO (List<DietaryProfileTemplate> dietaryProfileTemplates) {
        List<DietaryProfileTemplateDTO> dietaryProfileTemplatesListDTO = new ArrayList<>();
        for (DietaryProfileTemplate dietaryProfileTemplate : dietaryProfileTemplates) {
            dietaryProfileTemplatesListDTO.add(createDietaryProfileTemplateDTO(dietaryProfileTemplate));
        }
        return new DietaryProfileTemplatesListDTO(dietaryProfileTemplatesListDTO);
    }
}
