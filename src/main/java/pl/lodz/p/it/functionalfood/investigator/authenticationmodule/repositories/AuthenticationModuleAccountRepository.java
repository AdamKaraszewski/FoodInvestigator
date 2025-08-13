package pl.lodz.p.it.functionalfood.investigator.authenticationmodule.repositories;

import pl.lodz.p.it.functionalfood.investigator.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface AuthenticationModuleAccountRepository extends JpaRepository<Account, UUID> {
//    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "authenticationModuleTransactionManager")
    Account findByLogin(String login);
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "authenticationModuleTransactionManager")
    Account save(Account account);
}
