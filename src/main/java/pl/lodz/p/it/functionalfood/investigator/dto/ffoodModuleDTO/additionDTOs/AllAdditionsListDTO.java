package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.additionDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllAdditionsListDTO {
    private List<AdditionOnAllAdditionsListDTO> additions;
}
