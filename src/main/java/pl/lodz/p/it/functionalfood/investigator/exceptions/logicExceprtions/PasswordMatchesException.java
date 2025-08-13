package pl.lodz.p.it.functionalfood.investigator.exceptions.logicExceprtions;

public class PasswordMatchesException extends RuntimeException {
    public PasswordMatchesException(String message) {
        super(message);
    }
}
