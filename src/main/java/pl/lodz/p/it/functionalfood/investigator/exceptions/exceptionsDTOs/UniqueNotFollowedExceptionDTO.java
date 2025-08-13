package pl.lodz.p.it.functionalfood.investigator.exceptions.exceptionsDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UniqueNotFollowedExceptionDTO {
    private String errorType;
    private String notFollowed;
}
