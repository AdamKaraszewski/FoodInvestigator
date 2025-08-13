package pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateEmployeeDTO {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
//    private String language;
}
