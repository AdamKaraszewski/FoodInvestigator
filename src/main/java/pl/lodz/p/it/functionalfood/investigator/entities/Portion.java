package pl.lodz.p.it.functionalfood.investigator.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Portion extends AbstractEntity {

    private Integer portionQuantity;

    @ManyToOne
    private Unit unit;

    public Portion(Integer portionQuantity) {
        this.portionQuantity = portionQuantity;
    }
}
