package pl.lodz.p.it.functionalfood.investigator.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table
public class Addition extends AbstractEntity {
    private String name;

    public Addition(String name) {
        this.name = name;
    }
}
