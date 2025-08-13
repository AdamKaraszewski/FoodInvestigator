package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProductPageDTO {
    private int totalPages;
    private long totalElements;
    private boolean isLastPage;
    private List<ProductOnListDTO> products;
}
