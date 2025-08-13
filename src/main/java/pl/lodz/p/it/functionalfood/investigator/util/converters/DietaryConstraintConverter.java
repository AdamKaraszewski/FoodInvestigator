package pl.lodz.p.it.functionalfood.investigator.util.converters;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryConstraintDTOs.DietaryConstraintThatDoesNotBelongToGroupDTO;
import pl.lodz.p.it.functionalfood.investigator.entities.*;
import pl.lodz.p.it.functionalfood.investigator.entities.*;

import java.util.ArrayList;
import java.util.List;

public class DietaryConstraintConverter {
    private static DietaryConstraintThatDoesNotBelongToGroupDTO template_createNutritionalValueConstraintDTO (
            DietaryProfileTemplateNutritionalValue nvConstraint) {
        return new DietaryConstraintThatDoesNotBelongToGroupDTO("NUTRITIONAL_VALUE",
                nvConstraint.getNutritionalValueName().getGroup().getGroupName(), nvConstraint.getNutritionalValueName().getId(),
                nvConstraint.getNutritionalValueName().getName(), nvConstraint.getWeight());
    }

    private static DietaryConstraintThatDoesNotBelongToGroupDTO profile_createNutritionalValueConstraintDTO (
            DietaryProfileNutritionalValue nvConstraint) {
        return new DietaryConstraintThatDoesNotBelongToGroupDTO("NUTRITIONAL_VALUE",
                nvConstraint.getNutritionalValueName().getGroup().getGroupName(), nvConstraint.getNutritionalValueName().getId(),
                nvConstraint.getNutritionalValueName().getName(), nvConstraint.getWeight());
    }

    private static DietaryConstraintThatDoesNotBelongToGroupDTO template_createRatingConstraintDTO (
            DietaryProfileTemplateRating ratingConstraint) {
        return new DietaryConstraintThatDoesNotBelongToGroupDTO("RATING",
                ratingConstraint.getRating().getGroupName(), ratingConstraint.getRating().getId(),
                ratingConstraint.getRating().getName(), ratingConstraint.getWeight());
    }

    private static DietaryConstraintThatDoesNotBelongToGroupDTO profile_createRatingConstraintDTO (
            DietaryProfileRating ratingConstraint) {
        return new DietaryConstraintThatDoesNotBelongToGroupDTO("RATING",
                ratingConstraint.getRating().getGroupName(), ratingConstraint.getRating().getId(),
                ratingConstraint.getRating().getName(), ratingConstraint.getWeight());
    }

    private static DietaryConstraintThatDoesNotBelongToGroupDTO template_createPackageTypeConstraintDTO (
            DietaryProfileTemplatePackageType packageTypeConstraint) {
        return new DietaryConstraintThatDoesNotBelongToGroupDTO("PACKAGE_TYPE",
                "Opakowanie produktu", packageTypeConstraint.getPackageType().getId(),
                packageTypeConstraint.getPackageType().getName(), packageTypeConstraint.getWeight());
    }

    private static DietaryConstraintThatDoesNotBelongToGroupDTO profile_createPackageTypeConstraintDTO (
            DietaryProfilePackageType packageTypeConstraint) {
        return new DietaryConstraintThatDoesNotBelongToGroupDTO("PACKAGE_TYPE",
                "Opakowanie produktu", packageTypeConstraint.getPackageType().getId(),
                packageTypeConstraint.getPackageType().getName(), packageTypeConstraint.getWeight());
    }

    public static List<DietaryConstraintThatDoesNotBelongToGroupDTO> createConstraintListToDietaryProfileTemplateDTO (
            List<DietaryProfileTemplateNutritionalValue> nutritionalValueConstraints,
            List<DietaryProfileTemplateRating> ratingsConstraints,
            List<DietaryProfileTemplatePackageType> packageTypesConstraints ) {

        List<DietaryConstraintThatDoesNotBelongToGroupDTO> constraintListDTO = new ArrayList<>();
        for (DietaryProfileTemplateNutritionalValue nutritionalValueConstraint : nutritionalValueConstraints) {
            constraintListDTO.add(template_createNutritionalValueConstraintDTO(nutritionalValueConstraint));
        }
        for (DietaryProfileTemplatePackageType packageTypeConstraint : packageTypesConstraints) {
            constraintListDTO.add(template_createPackageTypeConstraintDTO(packageTypeConstraint));
        }
        for (DietaryProfileTemplateRating ratingConstraint : ratingsConstraints) {
            constraintListDTO.add(template_createRatingConstraintDTO(ratingConstraint));
        }

        return constraintListDTO;
    }

    public static List<DietaryConstraintThatDoesNotBelongToGroupDTO> createConstraintListToDietaryProfileDTO (
            List<DietaryProfileNutritionalValue> nutritionalValueConstraints,
            List<DietaryProfileRating> ratingConstraints,
            List<DietaryProfilePackageType> packageTypeConstraints ) {

        List<DietaryConstraintThatDoesNotBelongToGroupDTO> constraintListDTO = new ArrayList<>();
        for (DietaryProfileNutritionalValue nutritionalValueConstraint : nutritionalValueConstraints) {
            constraintListDTO.add(profile_createNutritionalValueConstraintDTO(nutritionalValueConstraint));
        }
        for (DietaryProfilePackageType packageTypeConstraint : packageTypeConstraints) {
            constraintListDTO.add(profile_createPackageTypeConstraintDTO(packageTypeConstraint));
        }
        for (DietaryProfileRating ratingConstraint : ratingConstraints) {
            constraintListDTO.add(profile_createRatingConstraintDTO(ratingConstraint));
        }

        return constraintListDTO;
    }

}
