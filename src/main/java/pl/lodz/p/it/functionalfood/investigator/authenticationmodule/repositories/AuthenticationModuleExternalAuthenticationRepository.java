package pl.lodz.p.it.functionalfood.investigator.authenticationmodule.repositories;

import pl.lodz.p.it.functionalfood.investigator.entities.ExternalAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthenticationModuleExternalAuthenticationRepository extends JpaRepository<ExternalAuthentication, UUID> {
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "authenticationModuleTransactionManager")
    Optional<ExternalAuthentication> findByGoogleId(String googleId);
}
