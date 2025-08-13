package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CreateProductDTO {
    private String img;
    private String EAN; //mam
    private UUID producerId; //mam
    private String productName; //mam
    private String productDescription; //mam
    private Integer productQuantity; //mam
    private UUID unitId; //mam
    private UUID packageTypeId; //mam
    private String country; //mam
    //composition
    //{
    private String flavour; //mam
    private List<String> ingredients; //mam
    private List<String> additions; //mam
    //}
    private List<CreateNutritionalIndexDTO> nutritionalIndexes; //mam
    private List<CreateProductIndexDTO> productIndexes; //mam
    private String storage; //mam
    private String durability; //mam
    private String instructionsAfterOpening;  //mam
    private String preparation; //mam
    private String allergens; //mam
    private UUID portionUnitId; //mam
    private Integer portionQuantity; //mam
    //byte[]
    private List<CreateNutritionalValueDTO> nutritionalValues;
}
