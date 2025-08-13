package pl.lodz.p.it.functionalfood.investigator.dto.authenticationmoduleDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginDTO {
    private String login;
    private String password;
}
