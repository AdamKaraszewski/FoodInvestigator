package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.repositories;

import pl.lodz.p.it.functionalfood.investigator.entities.DietaryProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DietaryProfileRepository extends JpaRepository<DietaryProfile, UUID> {
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "ffoodModuleTransactionManager")
    List<DietaryProfile> findAllByArchivedIsFalseAndClient_Account_Login(String login);

    @PreAuthorize("hasRole('CLIENT')")
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "ffoodModuleTransactionManager")
    Optional<DietaryProfile> findByIdAndClient_Account_LoginAndArchivedIsFalse(UUID profileTemplate, String clientLogin);
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "ffoodModuleTransactionManager")
    DietaryProfile save(DietaryProfile dietaryProfile);
}
