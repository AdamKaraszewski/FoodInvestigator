package pl.lodz.p.it.functionalfood.investigator.entities;

import pl.lodz.p.it.functionalfood.investigator.config.database.DatabaseStructures;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ACCOUNT_AUTHENTICATION_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public abstract class AuthenticationType extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = DatabaseStructures.ID, nullable = false)
    @Getter @Setter
    private Account account;

    @Column(name = "AUTHENTICATION_PROVIDER", length = 64, nullable = false)
    @Getter @Setter
    private String authenticationProvider;

    public AuthenticationType(String authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }
}
