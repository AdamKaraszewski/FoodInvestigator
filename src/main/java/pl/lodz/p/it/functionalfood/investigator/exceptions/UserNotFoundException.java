package pl.lodz.p.it.functionalfood.investigator.exceptions;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
