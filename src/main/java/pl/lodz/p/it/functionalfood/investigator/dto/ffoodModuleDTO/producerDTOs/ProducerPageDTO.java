package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.producerDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProducerPageDTO {
    private int totalPages;
    private long totalElements;
    private boolean isLastPage;
    private List<ProducerOnListDTO> producers;
}
