package pl.lodz.p.it.functionalfood.investigator.authenticationmodule.services;

import pl.lodz.p.it.functionalfood.investigator.authenticationmodule.repositories.AuthenticationModuleAccountRepository;
import pl.lodz.p.it.functionalfood.investigator.config.security.JwtService;
import pl.lodz.p.it.functionalfood.investigator.entities.Account;
import pl.lodz.p.it.functionalfood.investigator.entities.ClientAccessLevel;
import pl.lodz.p.it.functionalfood.investigator.entities.LocalAuthentication;
import pl.lodz.p.it.functionalfood.investigator.exceptions.databaseException.UniqueNotFollowedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationModuleService {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationModuleAccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationModuleService(
                                       AuthenticationModuleAccountRepository accountRepository,
                                       PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "authenticationModuleTransactionManager")
    public void localRegistration(String username, String password, String firstName, String lastName, String email) {
        try {
            ClientAccessLevel clientAccessLevel = new ClientAccessLevel();
            LocalAuthentication localAuthentication = new LocalAuthentication(passwordEncoder.encode(password));
            Account clientAccount = new Account(username, firstName, lastName, email);
            clientAccount.setAccessLevel(clientAccessLevel);
            clientAccount.setAuthenticationType(localAuthentication);
            clientAccessLevel.setAccount(clientAccount);
            localAuthentication.setAccount(clientAccount);
            accountRepository.save(clientAccount);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("login")) {
                throw new UniqueNotFollowedException("login");
            } else if (e.getMessage().contains("email")) {
                throw new UniqueNotFollowedException("email");
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "authenticationModuleTransactionManager")
    public String loginLocalUser(String login, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        return jwtService.generateToken(accountRepository.findByLogin(login));
    }
}
