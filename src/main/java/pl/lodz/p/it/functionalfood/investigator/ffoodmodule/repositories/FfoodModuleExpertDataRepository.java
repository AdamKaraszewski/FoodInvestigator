package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.repositories;

import pl.lodz.p.it.functionalfood.investigator.entities.ExpertAccessLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FfoodModuleExpertDataRepository extends JpaRepository<ExpertAccessLevel, UUID> {
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "ffoodModuleTransactionManager")
    @PreAuthorize("hasRole('EXPERT')")
    Optional<ExpertAccessLevel> findByAccount_Login(String login);
}
