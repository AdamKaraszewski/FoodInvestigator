package pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateAccountDTO {
    private UUID id;
    private boolean active;
}
