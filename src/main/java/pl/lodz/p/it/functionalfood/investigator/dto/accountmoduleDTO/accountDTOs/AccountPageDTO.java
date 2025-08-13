package pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AccountPageDTO {
    private int totalPages;
    private long totalElements;
    private boolean isLastPage;
    private List<AccountOnListDTO> accounts;
}
