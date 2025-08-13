package pl.lodz.p.it.functionalfood.investigator.exceptions.databaseException;

public class UniqueNotFollowedException extends RuntimeException {
    public UniqueNotFollowedException(String message) {
        super(message);
    }
}
