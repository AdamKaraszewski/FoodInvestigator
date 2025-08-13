package pl.lodz.p.it.functionalfood.investigator.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Ingredient extends AbstractEntity {

    //@NotNull
    private String name;

    public Ingredient(String name) {
        this.name = name;
    }
}
