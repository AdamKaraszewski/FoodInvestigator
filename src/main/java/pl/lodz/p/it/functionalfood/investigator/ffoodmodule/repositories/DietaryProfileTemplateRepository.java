package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.repositories;

import pl.lodz.p.it.functionalfood.investigator.entities.DietaryProfileTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DietaryProfileTemplateRepository extends JpaRepository<DietaryProfileTemplate, UUID> {
    @PreAuthorize("hasRole('CLIENT')")
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "ffoodModuleTransactionManager")
    List<DietaryProfileTemplate> findAllByArchivedIsFalse();

    @PreAuthorize("hasRole('EXPERT')")
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "ffoodModuleTransactionManager")
    Optional<DietaryProfileTemplate> findByIdAndExpert_Account_LoginAndArchivedIsFalse(UUID templateId, String login);

    @PreAuthorize("hasRole('EXPERT')")
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "ffoodModuleTransactionManager")
    List<DietaryProfileTemplate> findAllByExpert_Account_LoginAndArchivedIsFalse(String login);
    @PreAuthorize("hasRole('EXPERT')")
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "ffoodModuleTransactionManager")
    DietaryProfileTemplate save(DietaryProfileTemplate dietaryProfileTemplate);

}
