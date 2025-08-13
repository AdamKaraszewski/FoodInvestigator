package pl.lodz.p.it.functionalfood.investigator.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table
public class Unit extends AbstractEntity {
    private String name;
}
