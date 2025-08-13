package pl.lodz.p.it.functionalfood.investigator.authenticationmodule.controllers;

import pl.lodz.p.it.functionalfood.investigator.authenticationmodule.services.AuthenticationModuleService;
import pl.lodz.p.it.functionalfood.investigator.config.security.OAuth2GoogleService;
import pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs.CreateClientDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.authenticationmoduleDTO.LoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional(propagation = Propagation.NEVER)
public class AuthenticationModuleController {
    private final AuthenticationModuleService authenticationService;
    private final OAuth2GoogleService oAuth2GoogleService;

    public AuthenticationModuleController(AuthenticationModuleService authenticationService,
                                          OAuth2GoogleService oAuth2GoogleService) {
        this.authenticationService = authenticationService;
        this.oAuth2GoogleService = oAuth2GoogleService;
    }

    @PostMapping("/register-client-local")
    public ResponseEntity<?> registerClientLocal(@RequestBody CreateClientDTO clientDTO) {
        authenticationService.localRegistration(clientDTO.getLogin(), clientDTO.getPassword(), clientDTO.getFirstName(), clientDTO.getLastName(), clientDTO.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login/oauth2/google")
    public ResponseEntity<?> authenticateUserWithGoogle(@RequestBody String token) {
        return ResponseEntity.status(HttpStatus.OK).body(oAuth2GoogleService.handleOauthAuthentication(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateLocalUser(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.loginLocalUser(loginDTO.getLogin(),
                loginDTO.getPassword()));
    }
}
