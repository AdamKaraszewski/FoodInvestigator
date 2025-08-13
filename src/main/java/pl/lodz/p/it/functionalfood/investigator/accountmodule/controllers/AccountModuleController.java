package pl.lodz.p.it.functionalfood.investigator.accountmodule.controllers;

import pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.functionalfood.investigator.accountmodule.services.AccountModuleService;
import pl.lodz.p.it.functionalfood.investigator.config.security.JwsService;
import pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs.*;
import pl.lodz.p.it.functionalfood.investigator.exceptions.ApplicationException;
import pl.lodz.p.it.functionalfood.investigator.util.converters.AccountConverter;

import java.util.UUID;

@RestController
@RequestMapping("accounts")
@Transactional(propagation = Propagation.NEVER)
public class AccountModuleController {

    private final AccountModuleService accountService;
    private final JwsService jwsService;

    public AccountModuleController(AccountModuleService accountService, JwsService jwsService) {
        this.accountService = accountService;
        this.jwsService = jwsService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> findAccountById(@PathVariable UUID id) throws ApplicationException {
        AccountDTO accountDTO = accountService.findAccountById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setETag("\"" + jwsService.signAccountDTO(accountDTO) + "\"");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(accountDTO);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'EXPERT', 'CLIENT')")
    public ResponseEntity<?> getMyAccountDetails() {
        AccountDetailsDTO accountDetailsDTO = accountService.getMyAccountDetails();
        HttpHeaders headers = new HttpHeaders();
        headers.setETag("\"" + jwsService.signSignableDTO(accountDetailsDTO) + "\"");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(accountDetailsDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "accountModuleTransactionManager")
    public ResponseEntity<?> updateAccount(@RequestHeader(value = HttpHeaders.IF_MATCH, required = false) String ifMatch,
                                           @RequestBody UpdateAccountDTO updateAccountDTO) throws ApplicationException {
        accountService.updateAccount(ifMatch, updateAccountDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/my")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'EXPERT', 'CLIENT')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "accountModuleTransactionManager")
    public ResponseEntity<?> updateOwnAccount(@RequestHeader(value = HttpHeaders.IF_MATCH, required = false) String ifMatch,
                                              @RequestBody OwnAccountUpdateDTO updateAccountDTO) {
        accountService.updateOwnAccount(ifMatch, updateAccountDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountListDTO> findAllAccountsWithoutPagination() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.findAllAccountWithoutPagination());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<?> createAdminAccount(@RequestBody CreateAdminDTO adminDTO) {
        accountService.createAdmin(AccountConverter.createAccountFromCreateAdminDTO(adminDTO), AccountConverter.createAdminAccessLevel(adminDTO), adminDTO.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/employee")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "accountModuleTransactionManager")
    public ResponseEntity<?> createEmployeeAccount(@RequestBody CreateEmployeeDTO employeeDTO) {
        accountService.createEmployee(AccountConverter.createAccountFromCreateEmployeeDTO(employeeDTO),
                AccountConverter.createEmployeeAccessLevel(employeeDTO), employeeDTO.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/expert")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "accountModuleTransactionManager")
    public ResponseEntity<?> createExpertAccount(@RequestBody CreateExpertDTO expertDTO) {
        accountService.createExpert(AccountConverter.createAccountFromCreateExpertDTO(expertDTO), AccountConverter.createExpertAccessLevel(expertDTO), expertDTO.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT', 'EXPERT', 'EMPLOYEE')")
    @PostMapping("/my/change-password")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "accountModuleTransactionManager")
    public ResponseEntity<?> changeMyPassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        accountService.changeMyPassword(changePasswordDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
