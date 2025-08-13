package pl.lodz.p.it.functionalfood.investigator.accountmodule.repositories;

import pl.lodz.p.it.functionalfood.investigator.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountModuleAccountRepository extends JpaRepository<Account, UUID> {
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'EXPERT', 'CLIENT')")
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "accountModuleTransactionManager")
    Optional<Account> findByLogin(String login);
    @PreAuthorize("permitAll()")
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "accountModuleTransactionManager")
    Account save(Account account);
}
