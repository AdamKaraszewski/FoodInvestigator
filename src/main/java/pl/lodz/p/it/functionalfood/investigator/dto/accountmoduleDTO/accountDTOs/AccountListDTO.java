package pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AccountListDTO {
    private List<AccountOnListDTO> accounts;
}
