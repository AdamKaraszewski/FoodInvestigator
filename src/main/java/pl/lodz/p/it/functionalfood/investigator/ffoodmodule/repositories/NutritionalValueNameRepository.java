package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.repositories;

import pl.lodz.p.it.functionalfood.investigator.entities.NutritionalValueName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface NutritionalValueNameRepository extends JpaRepository<NutritionalValueName, UUID> {
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "ffoodModuleTransactionManager")
    @PreAuthorize("hasAnyRole('CLIENT', 'EXPERT')")
    List<NutritionalValueName> findAll();
}
