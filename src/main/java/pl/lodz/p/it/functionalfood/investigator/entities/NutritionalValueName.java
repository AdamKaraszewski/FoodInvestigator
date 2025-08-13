package pl.lodz.p.it.functionalfood.investigator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "nutritional_value_name")
public class NutritionalValueName extends AbstractEntity {

    @ManyToOne
    private NutritionalValueGroup group;

    private String name;
}
