package pl.lodz.p.it.functionalfood.investigator.exceptions.logicExceprtions;

public class OldPasswordDoesntMatchCurrentPasswordException extends RuntimeException {
    public OldPasswordDoesntMatchCurrentPasswordException(String message) {
        super(message);
    }
}
