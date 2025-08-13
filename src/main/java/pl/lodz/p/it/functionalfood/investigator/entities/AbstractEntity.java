package pl.lodz.p.it.functionalfood.investigator.entities;

import pl.lodz.p.it.functionalfood.investigator.config.database.DatabaseStructures;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @Column(name = DatabaseStructures.ID, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private UUID id;

    @Version
    @Column(name = DatabaseStructures.VERSION, nullable = false)
    @Getter
    private long version;
}