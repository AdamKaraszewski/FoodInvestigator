package pl.lodz.p.it.functionalfood.investigator.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class BaseException {
    private String errorType;
}
