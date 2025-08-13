package pl.lodz.p.it.functionalfood.investigator.entities;

import pl.lodz.p.it.functionalfood.investigator.config.database.DatabaseStructures;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = DatabaseStructures.ACCOUNTS_TABLE)
@SecondaryTable(name = DatabaseStructures.PERSONAL_DATA_TABLE)
public class Account extends AbstractEntity {

    @Column(name = DatabaseStructures.ACCOUNTS_TABLE_LOGIN_COLUMN, updatable = false, length = 64, unique=true,
            nullable = false)
    @Getter
    private String login;

    @Column(name = DatabaseStructures.ACCOUNTS_TABLE_ACTIVE_COLUMN, nullable = false)
    @Getter @Setter
    private boolean active = true;

    @OneToOne(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @Getter @Setter
    private AccessLevel accessLevel;

    @OneToOne(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @Getter @Setter
    private AuthenticationType authenticationType;


    @Column(name = DatabaseStructures.ACCOUNTS_TABLE_CREATION_DATE, nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    @Getter
    private LocalDateTime creationDate;

    @Column(name = DatabaseStructures.PERSONAL_DATA_FIRST_NAME, table = DatabaseStructures.PERSONAL_DATA_TABLE, length = 64,
            nullable = false)
    @Getter @Setter
    private String firstName;

    @Column(name = DatabaseStructures.PERSONAL_DATA_LAST_NAME, table = DatabaseStructures.PERSONAL_DATA_TABLE, length = 64,
            nullable = false)
    @Getter @Setter
    private String lastName;

    @Column(name = DatabaseStructures.PERSONAL_DATA_EMAIL, table = DatabaseStructures.PERSONAL_DATA_TABLE, length = 64,
            unique = true, nullable = false)
    @Getter
    private String email;

    public Account(String login, String firstName, String lastName, String email) {
        this.login = login;
        this.creationDate = LocalDateTime.now();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}