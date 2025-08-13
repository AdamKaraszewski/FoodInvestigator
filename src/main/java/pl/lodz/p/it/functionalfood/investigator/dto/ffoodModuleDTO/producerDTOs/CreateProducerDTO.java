package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.producerDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateProducerDTO {
    private String name;
    private String address;
    private Integer countryCode;
    private String NIP;
    private Integer RMSD;
    private String contact;
}
