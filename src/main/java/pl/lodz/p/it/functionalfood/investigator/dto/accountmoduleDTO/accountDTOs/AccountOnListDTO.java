package pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class AccountOnListDTO {
    private UUID id;
    private String login;
    private String email;
    private String accessLevel;
    private boolean active;
}
