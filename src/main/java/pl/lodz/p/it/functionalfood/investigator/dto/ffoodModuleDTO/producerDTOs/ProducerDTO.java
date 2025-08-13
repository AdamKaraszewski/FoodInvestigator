package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.producerDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ProducerDTO {
    private UUID id;
    private String name;
    private String address;
    private Integer countryCode;
    private String nip;
    private Integer rmsd;
    private String contact;
    //private boolean archived;
}
