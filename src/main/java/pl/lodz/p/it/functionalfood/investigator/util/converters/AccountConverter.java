package pl.lodz.p.it.functionalfood.investigator.util.converters;

import pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs.*;
import pl.lodz.p.it.functionalfood.investigator.entities.*;
import pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs.*;
import pl.lodz.p.it.functionalfood.investigator.entities.Account;
import pl.lodz.p.it.functionalfood.investigator.entities.AdminAccessLevel;
import pl.lodz.p.it.functionalfood.investigator.entities.EmployeeAccessLevel;
import pl.lodz.p.it.functionalfood.investigator.entities.ExpertAccessLevel;

import java.util.ArrayList;
import java.util.List;

public class AccountConverter {
    //FIND ALL ACCOUNTS
    private static AccountOnListDTO createAccountOnListDTO(Account account) {
        return new AccountOnListDTO(account.getId(), account.getLogin(), account.getEmail(),
                account.getAccessLevel().getLevel(), account.isActive());
    }

    public static AccountListDTO createAccountListDTO(List<Account> accounts) {
        List<AccountOnListDTO> accountsOnDTO = new ArrayList<>();
        for (Account account : accounts) {
            accountsOnDTO.add(createAccountOnListDTO(account));
        }
        return new AccountListDTO(accountsOnDTO);
    }

    public static AccountDetailsDTO createAccountDetails(Account account) {
        return new AccountDetailsDTO(account.getId(), account.getLogin(), account.getFirstName(), account.getLastName(),
                account.getEmail(), account.getAccessLevel().getLevel(),
                account.getAuthenticationType().getAuthenticationProvider(), account.isActive(), account.getVersion());
    }

    public static AdminAccessLevel createAdminAccessLevel(CreateAdminDTO createAdminDTO) {
        return new AdminAccessLevel();
    }

    public static EmployeeAccessLevel createEmployeeAccessLevel(CreateEmployeeDTO createEmployeeDTO) {
        return new EmployeeAccessLevel();
    }

    public static ExpertAccessLevel createExpertAccessLevel(CreateExpertDTO createEmployeeDTO) {
        return new ExpertAccessLevel();
    }

    public static Account createAccountFromCreateAdminDTO(CreateAdminDTO createAdminDTO) {
        return new Account(createAdminDTO.getLogin(), createAdminDTO.getFirstName(),
                createAdminDTO.getLastName(), createAdminDTO.getEmail());
    }

    public static Account createAccountFromCreateEmployeeDTO(CreateEmployeeDTO createEmployeeDTO) {
        return new Account(createEmployeeDTO.getLogin(), createEmployeeDTO.getFirstName(),
                createEmployeeDTO.getLastName(), createEmployeeDTO.getEmail());
    }

    public static Account createAccountFromCreateExpertDTO(CreateExpertDTO createExpertDTO) {
        return new Account(createExpertDTO.getLogin(), createExpertDTO.getFirstName(),
                createExpertDTO.getLastName(), createExpertDTO.getEmail());
    }

    public static AccountDTO createAccountDTO(Account account) {
        return new AccountDTO(account.getId(), account.getLogin(), account.isActive(),
                account.getAccessLevel().getLevel(), account.getFirstName(),
                account.getLastName(), account.getEmail(),
                account.getAuthenticationType().getAuthenticationProvider(), account.getVersion());
    }
}
