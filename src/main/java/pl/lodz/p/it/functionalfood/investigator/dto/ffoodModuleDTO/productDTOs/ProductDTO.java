package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class ProductDTO {
    private UUID id; //mam
    private String EAN; //mam
    private String productName; //mam
    private String productDescription; //mam
    private Integer productQuantity; //mam
    private String unit; //mam
    private String packageType; //mam
    private String country; //mam
    private List<String> ingredients; //mam <-----
    private List<String> additions; //mam <-----
    private List<String> flavour; //mam <-----
    private List<NutritionalIndexDTO> nutritionalIndexes; //mam
    private List<ProductIndexDTO> productIndexes; //mam
    private String storage;
    private String durability;
    private String instructionsAfterOpening;
    private String preparation;
    private List<String> allergens; //mam <-----
    private String image;
    private PortionDTO portion; //mam
    private List<RatingDTO> ratings; //mam
    private List<NutritionalValueDTO> nutritionalValues; //mam
}
