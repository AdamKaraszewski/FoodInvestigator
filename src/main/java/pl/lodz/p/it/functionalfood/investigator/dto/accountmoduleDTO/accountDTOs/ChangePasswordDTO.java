package pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
}
