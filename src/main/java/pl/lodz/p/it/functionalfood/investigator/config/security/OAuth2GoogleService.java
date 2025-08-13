package pl.lodz.p.it.functionalfood.investigator.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.lodz.p.it.functionalfood.investigator.authenticationmodule.repositories.AuthenticationModuleAccountRepository;
import pl.lodz.p.it.functionalfood.investigator.authenticationmodule.repositories.AuthenticationModuleExternalAuthenticationRepository;
import pl.lodz.p.it.functionalfood.investigator.entities.Account;
import pl.lodz.p.it.functionalfood.investigator.entities.ClientAccessLevel;
import pl.lodz.p.it.functionalfood.investigator.entities.ExternalAuthentication;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class OAuth2GoogleService {

    private AuthenticationModuleAccountRepository accountRepository;
    private AuthenticationModuleExternalAuthenticationRepository externalAuthenticationRepository;
    private final JwtService jwtService;

    public OAuth2GoogleService(AuthenticationModuleAccountRepository accountRepository,
                               AuthenticationModuleExternalAuthenticationRepository externalAuthenticationRepository, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.externalAuthenticationRepository = externalAuthenticationRepository;
        this.jwtService = jwtService;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "authenticationModuleTransactionManager")
    public String handleOauthAuthentication(String code) {
        Map oAuthToken = getClaimsFromOAuthToken(code);
        String googleId = extreactClaimFromOAuthToken("sub", oAuthToken);
        Optional<ExternalAuthentication> externalAuthentication = externalAuthenticationRepository.findByGoogleId(googleId);
        if (externalAuthentication.isEmpty()) {
            String email = (String) oAuthToken.get("email");
            String firstName = (String) oAuthToken.get("given_name");
            String lastName = (String) oAuthToken.get("family_name");

            Account newAccount = new Account(email, firstName, lastName, email);
            ClientAccessLevel clientAccessLevel = new ClientAccessLevel();
            ExternalAuthentication newExternalAuthentication = new ExternalAuthentication(googleId);
            newAccount.setAccessLevel(clientAccessLevel);
            newAccount.setAuthenticationType(newExternalAuthentication);
            clientAccessLevel.setAccount(newAccount);
            newExternalAuthentication.setAccount(newAccount);
            return jwtService.generateToken(accountRepository.save(newAccount));
        } else {
            if (!externalAuthentication.get().getAccount().isActive()) throw new RuntimeException("Konto zablokowane");
            return jwtService.generateToken(externalAuthentication.get().getAccount());
        }
    }

    private Map getClaimsFromOAuthToken(String encodedOAuthToken) {
        String payload = encodedOAuthToken.split("\\.")[1];
        String decodedPayload = new String(java.util.Base64.getDecoder().decode(payload));
        return parseJson(decodedPayload);
    }

    public static Map<String, Object> parseJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String extreactClaimFromOAuthToken(String claimName, Map token) {
        return (String) token.get(claimName);
    }


}
