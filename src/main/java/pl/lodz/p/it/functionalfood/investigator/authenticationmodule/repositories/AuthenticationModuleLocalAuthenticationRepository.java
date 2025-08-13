package pl.lodz.p.it.functionalfood.investigator.authenticationmodule.repositories;

import pl.lodz.p.it.functionalfood.investigator.entities.LocalAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthenticationModuleLocalAuthenticationRepository extends JpaRepository<LocalAuthentication, UUID> {
    Optional<LocalAuthentication> findByAccount_Login(String login);
}
