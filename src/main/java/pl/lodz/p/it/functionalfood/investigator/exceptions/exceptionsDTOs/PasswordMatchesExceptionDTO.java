package pl.lodz.p.it.functionalfood.investigator.exceptions.exceptionsDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PasswordMatchesExceptionDTO {
    private String errorType;
}
