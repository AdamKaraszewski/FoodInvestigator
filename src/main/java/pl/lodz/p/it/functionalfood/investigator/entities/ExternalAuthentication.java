package pl.lodz.p.it.functionalfood.investigator.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EXTERNAL_AUTHENTICATION")
@NoArgsConstructor
public class ExternalAuthentication extends AuthenticationType {

    @Column(name = "GOOGLE_ID", nullable = false, unique = true)
    @Getter
    private String googleId;

    public ExternalAuthentication(String googleId) {
        super("EXTERNAL");
        this.googleId = googleId;
    }

}
