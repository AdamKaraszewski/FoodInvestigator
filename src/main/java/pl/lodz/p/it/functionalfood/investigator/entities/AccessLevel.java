package pl.lodz.p.it.functionalfood.investigator.entities;

import pl.lodz.p.it.functionalfood.investigator.config.database.DatabaseStructures;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = DatabaseStructures.ACCESS_LEVEL_TABLE)
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public abstract class AccessLevel extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = DatabaseStructures.ACCESS_LEVEL_TABLE_ACCOUNT_ID_COLUMN,
            referencedColumnName = DatabaseStructures.ID, nullable = false)
    @Getter @Setter
    private Account account;

    @Column(name = DatabaseStructures.ACCESS_LEVEL_TABLE_LEVEL_COLUMN, length = 64, nullable = false)
    @Getter @Setter
    private String level;

    public AccessLevel(String level) {
        this.level = level;
    }
}
