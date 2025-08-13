package pl.lodz.p.it.functionalfood.investigator.config.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.UUID;

@Aspect
@Component
public class TransactionLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(TransactionLoggingAspect.class);

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object logTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("ASPEKT");
        String transactionId = UUID.randomUUID().toString(); // Generowanie unikalnego ID transakcji
        String methodName = joinPoint.getSignature().toShortString(); // Nazwa metody (np. ClassName.methodName(..))
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String ip = "no info";
        if (authentication != null && authentication.getDetails() instanceof WebAuthenticationDetails) {
            // Rzutowanie na WebAuthenticationDetails
            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
            // Pobranie adresu IP
            ip = details.getRemoteAddress();
        }

        logger.info("Transaction started: ID={}, Method={}, User={}, AccessLevel={}, IP={}", transactionId, methodName,
                SecurityContextHolder.getContext().getAuthentication().getName(),
                SecurityContextHolder.getContext().getAuthentication().getAuthorities(), ip
        );

        Object result;
        try {
            result = joinPoint.proceed(); // Wykonanie metody
        } catch (Exception e) {
            if (TransactionSynchronizationManager.isActualTransactionActive() &&
                    TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
                logger.info("Transaction ID={} was marked as read-only. Method={}", transactionId, methodName);
            } else {
                logger.error("Transaction ID={} failed and is being rolled back. Method={}, User={}, AccessLevel={}, IP={}, Exception: {}", transactionId, methodName,
                        SecurityContextHolder.getContext().getAuthentication().getName(),
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities(),
                        ip, e.getMessage());
            }
            throw e; // Ponowne rzucenie wyjątku
        }

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            logger.info("Transaction ID={} completed successfully. Method={}, User={}, AccessLevel={}", transactionId, methodName,
                    SecurityContextHolder.getContext().getAuthentication().getName(),
                    SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                    );
        } else {
            logger.warn("Transaction ID={} ended, but no active transaction was detected. Method={}", transactionId, methodName);
        }

        return result;
    }
}