package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.producerDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class ProducerOnListDTO {
    private UUID id;
    private String name;
    private Integer countryCode;
}
