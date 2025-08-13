package pl.lodz.p.it.functionalfood.investigator.util.converters;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs.DietaryProfileSummary;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs.*;
import pl.lodz.p.it.functionalfood.investigator.entities.*;
import pl.lodz.p.it.functionalfood.investigator.entities.*;
import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.logic.DoesProductFollowsDietaryProfile;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs.*;

import java.util.*;

public class ProductConverter {

    public static ProductDTO createProductDTO(Product product) {
        List<String> ingredients = createIngredientsList(product);
        List<String> additions = createAdditionsList(product);
        List<String> flavours = createStringListFromFlavour(product.getComposition().getFlavour());
        List<String> allergens = createStringListFromString(product.getLabel().getAllergens());
        List<NutritionalIndexDTO> nutritionalIndexes = createNutritionalIndexDTOList(product);
        List<ProductIndexDTO> productIndexes = createProductIndexDTOList(product);
        List<RatingDTO> ratings = createRatingDTOList(product);
        List<NutritionalValueDTO> nutritionalValues = createNutritionalValueDTOList(product);
        return new ProductDTO(product.getId(), product.getEan(), product.getProductName(), product.getProductDescription(),
                product.getProductQuantity(), product.getUnit().getName(), product.getPackageType().getName(),
                product.getCountry(), ingredients, additions, flavours, nutritionalIndexes, productIndexes,
                product.getLabel().getStorage(), product.getLabel().getDurability(), product.getLabel().getInstructionsAfterOpening(),
                product.getLabel().getPreparation(), allergens, "aaaa",
                new PortionDTO(product.getPortion().getUnit().getName(), product.getPortion().getPortionQuantity()), ratings, nutritionalValues);
    }

    private static List<ProductIndexDTO> createProductIndexDTOList(Product product) {
        List<ProductIndexDTO> productIndexDTOs = new ArrayList<>();
        List<ProductIndex> productIndexes = product.getProductIndexes().stream().toList();
        for (ProductIndex productIndex : productIndexes) {
            productIndexDTOs.add(new ProductIndexDTO(productIndex.getIndexName(), productIndex.getIndexValue()));
        }
        return productIndexDTOs;
    }

    private static List<NutritionalIndexDTO> createNutritionalIndexDTOList(Product product) {
        List<NutritionalIndexDTO> nutritionalIndexDTOs = new ArrayList<>();
        List<NutritionalIndex> nutritionalIndexes = product.getNutritionalIndexes().stream().toList();
        for (NutritionalIndex nutritionalIndex : nutritionalIndexes) {
            nutritionalIndexDTOs.add(new NutritionalIndexDTO(nutritionalIndex.getLegend(), nutritionalIndex.getIndexValue()));
        }
        return nutritionalIndexDTOs;
    }

    private static List<RatingDTO> createRatingDTOList(Product product) {
        List<RatingDTO> ratingDTOs = new ArrayList<>();
        List<Rating> ratings = product.getRatings().stream().toList();
        for (Rating rating : ratings) {
            ratingDTOs.add(new RatingDTO(rating.getGroupName(), rating.getName()));
        }
        return ratingDTOs;
    }

    private static List<NutritionalValueDTO> createNutritionalValueDTOList(Product product) {
        List<NutritionalValueDTO> nutritionalValueDTOs = new ArrayList<>();
        List<NutritionalValue> nutritionalValues = product.getNutritionalValues().stream().toList();
        for (NutritionalValue nutritionalValue : nutritionalValues) {
            nutritionalValueDTOs.add(new NutritionalValueDTO(nutritionalValue.getNutritionalValueName().getName(), nutritionalValue.getNutritionalValueName().getGroup().getGroupName(), nutritionalValue.getQuantity(), nutritionalValue.getUnit().getName()));
        }
        return nutritionalValueDTOs;
    }

    private static List<String> createIngredientsList(Product product) {
        List<String> ingredientsString = new ArrayList<>();
        List<Ingredient> ingredients = product.getComposition().getIngredients();
        for (Ingredient ingredient : ingredients) {
            ingredientsString.add(ingredient.getName());
        }
        return ingredientsString;
    }

    private static List<String> createAdditionsList(Product product) {
        List<String> additionsString = new ArrayList<>();
        List<Addition> additions = product.getComposition().getAdditions();
        for (Addition addition : additions) {
            additionsString.add(addition.getName());
        }
        return additionsString;
    }

    private static List<Ingredient> createIngredientListFromCreateProductDTO(CreateProductDTO productDTO) {
        List<Ingredient> ingredients = new ArrayList<>();
        List<String> ingredientsFromDTO = productDTO.getIngredients();
        for (String s : ingredientsFromDTO) {
            ingredients.add(new Ingredient(s));
        }
        return ingredients;
    }

    private static List<Addition> createAdditionListFromCreateAdditionDTO(CreateProductDTO productDTO) {
        List<Addition> additions = new ArrayList<>();
        List<String> additionsFromDTO = productDTO.getAdditions();
        for (String s : additionsFromDTO) {
            additions.add(new Addition(s));
        }
        return additions;
    }

    public static Composition createCompositionFromCreateProductDTO(CreateProductDTO productDTO) {
        return new Composition(createIngredientListFromCreateProductDTO(productDTO), createAdditionListFromCreateAdditionDTO(productDTO), new Flavour(productDTO.getFlavour()));
    }

    public static Set<NutritionalIndex> createNutritionalIndexSetFromCreateProductDTO(CreateProductDTO productDTO) {
        Set<NutritionalIndex> nutritionalIndexes = new HashSet<>();
        List<CreateNutritionalIndexDTO> nutritionalIndexesFromDTO = productDTO.getNutritionalIndexes();
        for (CreateNutritionalIndexDTO createNutritionalIndexDTO : nutritionalIndexesFromDTO) {
            nutritionalIndexes.add(new NutritionalIndex(createNutritionalIndexDTO.getIndexValue(), createNutritionalIndexDTO.getLegend()));
        }
        return nutritionalIndexes;
    }

    public static Set<ProductIndex> createProductIndexSetFromCreateProductDTO(CreateProductDTO createProductDTO) {
        Set<ProductIndex> productIndexes = new HashSet<>();
        List<CreateProductIndexDTO> productIndexesFromDTO = createProductDTO.getProductIndexes();
        for (CreateProductIndexDTO createProductIndexDTO : productIndexesFromDTO) {
            productIndexes.add(new ProductIndex(createProductIndexDTO.getIndexName(), createProductIndexDTO.getIndexValue()));
        }
        return productIndexes;
    }

    public static Label createLabelFromCreateProductDTO(CreateProductDTO productDTO) {
//        byte[] bytes = {};
        return new Label(productDTO.getStorage(), productDTO.getDurability(), productDTO.getInstructionsAfterOpening(),
                productDTO.getPreparation(), productDTO.getAllergens(), ConverterToBase64.convert(productDTO.getImg()));
    }

    public static Portion createPortionFromCreateProductDTO(CreateProductDTO productDTO) {
        return new Portion(productDTO.getProductQuantity());
    }

    public static Product createProductFromCreateProductDTO(CreateProductDTO productDTO) {
        return new Product(productDTO.getEAN(), productDTO.getProductName(), productDTO.getProductDescription(), productDTO.getProductQuantity(), productDTO.getCountry());
    }

    private static List<String> createStringListFromString(String string) {
        if (string == null) {
            return new ArrayList<>();
        }
        return Arrays.stream(string.split(",")).map(String::trim).toList();
    }

    private static List<String> createStringListFromFlavour(Flavour flavour) {
        if (flavour == null || Objects.equals(flavour.getName(), "")) {
            return new ArrayList<>();
        }
        return Arrays.stream(flavour.getName().split(",")).map(String::trim).toList();
    }

    public static ProductSummaryForClientDTO createProductSummaryForClientDTO(Product product, List<DietaryProfile> dietaryProfiles) {
        List<DietaryProfileSummary> summaryForEachProfile = new ArrayList<>();
        for (DietaryProfile dietaryProfile : dietaryProfiles) {
            DoesProductFollowsDietaryProfile doesProductFollowsDietaryProfile = dietaryProfile.doesProductFollowProfile(product);
            summaryForEachProfile.add(new DietaryProfileSummary(
                    DietaryProfileConverter.createDietaryProfileDTO(dietaryProfile),
                    doesProductFollowsDietaryProfile));
        }
        return new ProductSummaryForClientDTO(ProductConverter.createProductDTO(product), summaryForEachProfile);
    }

    public static ProductWithSummaryOnListDTO createProductWithSummaryOnListDTO(Product product, DietaryProfile dietaryProfile) {
        DoesProductFollowsDietaryProfile summaryForProfile = dietaryProfile.doesProductFollowProfile(product);
        return new ProductWithSummaryOnListDTO(product.getId(), product.getProductName(),
                product.getProductDescription(), ConverterToBase64.convert(product.getLabel().getImage()),
                summaryForProfile.getProductSummary());
    }

    public static ProductWithSummaryOnListDTO createProductWithoutSummaryOnListDTO(Product product) {
        return new ProductWithSummaryOnListDTO(product.getId(), product.getProductName(),
                product.getProductDescription(), ConverterToBase64.convert(product.getLabel().getImage()), null);
    }
}
