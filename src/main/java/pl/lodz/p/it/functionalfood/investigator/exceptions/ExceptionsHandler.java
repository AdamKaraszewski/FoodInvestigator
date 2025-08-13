package pl.lodz.p.it.functionalfood.investigator.exceptions;

import pl.lodz.p.it.functionalfood.investigator.exceptions.exceptionsDTOs.OptimisticLockExceptionDTO;
import pl.lodz.p.it.functionalfood.investigator.exceptions.exceptionsDTOs.IfMatchNullExceptionDTO;
import pl.lodz.p.it.functionalfood.investigator.exceptions.databaseException.UniqueNotFollowedException;
import pl.lodz.p.it.functionalfood.investigator.exceptions.exceptionsDTOs.PasswordMatchesExceptionDTO;
import pl.lodz.p.it.functionalfood.investigator.exceptions.exceptionsDTOs.UniqueNotFollowedExceptionDTO;
import pl.lodz.p.it.functionalfood.investigator.exceptions.jwsException.IfMatchNullException;
import pl.lodz.p.it.functionalfood.investigator.exceptions.logicExceprtions.OldPasswordDoesntMatchCurrentPasswordException;
import pl.lodz.p.it.functionalfood.investigator.exceptions.logicExceprtions.PasswordMatchesException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {
    //LOGIC EXCEPTION HANDLING
    @ExceptionHandler
    ResponseEntity<?> handleOldPasswordDoesntMatchCurrentPassword(OldPasswordDoesntMatchCurrentPasswordException opdmcpe) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseException("OldPasswordDoesntMatchCurrentPasswordError"));
    }
    @ExceptionHandler
    ResponseEntity<?> handlePasswordMatchesException(PasswordMatchesException pme) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PasswordMatchesExceptionDTO("PasswordMatchesError"));
    }

    //ACCOUNT MODULE EXCEPTION HANDLING
    @ExceptionHandler
    ResponseEntity<?> handleUniqueConstraintNotFollowed(UniqueNotFollowedException unfe) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UniqueNotFollowedExceptionDTO("UniqueNotFollowedError", unfe.getMessage()));
    }

    @ExceptionHandler
    ResponseEntity<?> handleIfMatchNull(IfMatchNullException imne) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IfMatchNullExceptionDTO("IfMatchNullError"));
    }

    @ExceptionHandler
    ResponseEntity<?> handleOptimisticLockException(OptimisticLockException ole) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OptimisticLockExceptionDTO("OptimisticLockError"));
    }

    @ExceptionHandler
    ResponseEntity<String> handleUserNotFoundException(UserNotFoundException iufe) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(iufe.getMessage());
    }

//    @ExceptionHandler
//    ResponseEntity<String> handleInvalidUrlParameterException(InvalidUrlParameterException iupe) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iupe.getMessage());
//    }



//    @ExceptionHandler
//    ResponseEntity<String> handleThrowable(Throwable s) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("THROWABLE");
//    }

}
