package pl.lodz.p.it.functionalfood.investigator.accountmodule.services;

import pl.lodz.p.it.functionalfood.investigator.accountmodule.repositories.AccountModuleAccountRepository;
import pl.lodz.p.it.functionalfood.investigator.accountmodule.repositories.AccountModuleLocalAuthenticationRepository;
import pl.lodz.p.it.functionalfood.investigator.config.security.JwsService;
import pl.lodz.p.it.functionalfood.investigator.config.security.JwtService;
import pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs.*;
import pl.lodz.p.it.functionalfood.investigator.exceptions.databaseException.UniqueNotFollowedException;
import pl.lodz.p.it.functionalfood.investigator.exceptions.UserNotFoundException;
import pl.lodz.p.it.functionalfood.investigator.exceptions.jwsException.IfMatchNullException;
import pl.lodz.p.it.functionalfood.investigator.exceptions.logicExceprtions.OldPasswordDoesntMatchCurrentPasswordException;
import pl.lodz.p.it.functionalfood.investigator.exceptions.logicExceprtions.PasswordMatchesException;
import pl.lodz.p.it.functionalfood.investigator.util.converters.AccountConverter;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs.*;
import pl.lodz.p.it.functionalfood.investigator.entities.*;

import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class AccountModuleService {

    private final AccountModuleAccountRepository accountRepository;
    private final JwsService jwsService;
    private final JwtService jwtService;
    private final AccountModuleLocalAuthenticationRepository localAuthenticationRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountModuleService(AccountModuleAccountRepository accountRepository, JwsService jwsService,
                                JwtService jwtService,
                                AccountModuleLocalAuthenticationRepository localAuthenticationRepository,
                                PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.jwsService = jwsService;
        this.jwtService = jwtService;
        this.localAuthenticationRepository = localAuthenticationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "accountModuleTransactionManager")
    public AccountDTO findAccountById(UUID id) throws UserNotFoundException {
        return AccountConverter.createAccountDTO(accountRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with specified id does not exist.")));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'EXPERT', 'CLIENT')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "accountModuleTransactionManager")
    public AccountDetailsDTO getMyAccountDetails() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return AccountConverter.createAccountDetails(accountRepository.findByLogin(username).orElseThrow());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "accountModuleTransactionManager")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateAccount(String ifMatch, UpdateAccountDTO updateAccountDTO) throws UserNotFoundException {
        if (ifMatch == null) {
            throw new IfMatchNullException();
        }
        Account accountToUpdate = accountRepository.findById(updateAccountDTO.getId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        AccountDTO accountToUpdateDTO = AccountConverter.createAccountDTO(accountToUpdate);
        if (!Objects.equals(jwsService.signAccountDTO(accountToUpdateDTO), ifMatch)) {
            throw new OptimisticLockException();
        }
        accountToUpdate.setActive(updateAccountDTO.isActive());
        accountRepository.save(accountToUpdate);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "accountModuleTransactionManager")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'EXPERT', 'CLIENT')")
    public void updateOwnAccount(String ifMatch, OwnAccountUpdateDTO updateAccountDTO) {
        if (ifMatch == null) {
            throw new IfMatchNullException();
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account accountToUpdate = accountRepository.findByLogin(username).orElseThrow();
        AccountDetailsDTO accountToUpdateDTO = AccountConverter.createAccountDetails(accountToUpdate);
        if (!Objects.equals(jwsService.signSignableDTO(accountToUpdateDTO), ifMatch)) {
            throw new OptimisticLockException();
        }
        accountToUpdate.setFirstName(updateAccountDTO.getFirstName());
        accountToUpdate.setLastName(updateAccountDTO.getLastName());
        System.out.println(accountToUpdate.getFirstName());
        System.out.println(accountToUpdate.getLastName());
        accountRepository.saveAndFlush(accountToUpdate);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "accountModuleTransactionManager")
    public AccountListDTO findAllAccountWithoutPagination() {
        return AccountConverter.createAccountListDTO(accountRepository.findAll(Sort.by("login")));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "accountModuleTransactionManager")
    @PreAuthorize("hasRole('ADMIN')")
    public void createEmployee(Account account, EmployeeAccessLevel employeeAccessLevel, String password) {;
        try {
            account.setAccessLevel(employeeAccessLevel);
            employeeAccessLevel.setAccount(account);
            LocalAuthentication localAuthentication = new LocalAuthentication(passwordEncoder.encode(password));
            account.setAuthenticationType(localAuthentication);
            localAuthentication.setAccount(account);
            accountRepository.saveAndFlush(account);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("login")) {
                throw new UniqueNotFollowedException("login");
            } else if (e.getMessage().contains("email")) {
                throw new UniqueNotFollowedException("email");
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "accountModuleTransactionManager")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void createExpert(Account account, ExpertAccessLevel expertAccessLevel, String password) {
        try {
            account.setAccessLevel(expertAccessLevel);
            expertAccessLevel.setAccount(account);
            LocalAuthentication localAuthentication = new LocalAuthentication(passwordEncoder.encode(password));
            account.setAuthenticationType(localAuthentication);
            localAuthentication.setAccount(account);
            accountRepository.saveAndFlush(account);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("login")) {
                throw new UniqueNotFollowedException("login");
            } else if (e.getMessage().contains("email")) {
                throw new UniqueNotFollowedException("email");
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "accountModuleTransactionManager")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void createAdmin(Account account, AdminAccessLevel adminAccessLevel, String password) {
        try {
            account.setAccessLevel(adminAccessLevel);
            adminAccessLevel.setAccount(account);
            LocalAuthentication localAuthentication = new LocalAuthentication(passwordEncoder.encode(password));
            account.setAuthenticationType(localAuthentication);
            localAuthentication.setAccount(account);
            accountRepository.saveAndFlush(account);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("login")) {
                throw new UniqueNotFollowedException("login");
            } else if (e.getMessage().contains("email")) {
                throw new UniqueNotFollowedException("email");
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "accountModuleTransactionManager")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'EXPERT', 'CLIENT')")
    public void changeMyPassword(ChangePasswordDTO changePasswordDTO) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        LocalAuthentication localAuthentication = localAuthenticationRepository.findByAccount_Login(login).orElseThrow();
        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), localAuthentication.getPassword())) {
            throw new OldPasswordDoesntMatchCurrentPasswordException("Provided password don't match current password");
        }
        if (passwordEncoder.matches(changePasswordDTO.getNewPassword(), localAuthentication.getPassword())) {
            throw new PasswordMatchesException("New password matches old password");
        }
        localAuthentication.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        localAuthenticationRepository.saveAndFlush(localAuthentication);
    }

}
