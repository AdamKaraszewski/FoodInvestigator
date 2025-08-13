package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.repositories;

import pl.lodz.p.it.functionalfood.investigator.entities.PackageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface PackageTypeRepository extends JpaRepository<PackageType, UUID> {
    @PreAuthorize("hasAnyRole('CLIENT', 'EXPERT', 'EMPLOYEE')")
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "ffoodModuleTransactionManager")
    List<PackageType> findAll();
}
