package pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs;

import pl.lodz.p.it.functionalfood.investigator.dto.SignableDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class AccountDetailsDTO implements SignableDTO {
    private UUID id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String accessLevel;
    private String authentication;
    private boolean active;
    private Long version;
}
