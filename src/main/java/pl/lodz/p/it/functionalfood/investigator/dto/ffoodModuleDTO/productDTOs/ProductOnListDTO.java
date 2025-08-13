package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class ProductOnListDTO {
    private UUID id;
    private String productName;
    private String producerName;
    private String description;
    private String image;
}
