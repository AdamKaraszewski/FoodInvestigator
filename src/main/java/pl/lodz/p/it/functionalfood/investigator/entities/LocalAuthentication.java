package pl.lodz.p.it.functionalfood.investigator.entities;

import pl.lodz.p.it.functionalfood.investigator.config.database.DatabaseStructures;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LOCAL_AUTHENTICATION")
@NoArgsConstructor
public class LocalAuthentication extends AuthenticationType {

    @Column(name = DatabaseStructures.ACCOUNTS_TABLE_PASSWORD_COLUMN, length = 64, nullable = false)
    @Getter @Setter
    private String password;

    public LocalAuthentication(String password) {
        super("LOCAL");
        this.password = password;
    }
}
