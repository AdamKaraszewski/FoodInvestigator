package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.repositories;

import pl.lodz.p.it.functionalfood.investigator.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

@PreAuthorize("hasRole('EMPLOYEE')")
public interface UnitRepository extends JpaRepository<Unit, UUID> {
}
