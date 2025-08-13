package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.additionDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class AdditionOnAllAdditionsListDTO {
    private UUID id;
    private String name;
}
