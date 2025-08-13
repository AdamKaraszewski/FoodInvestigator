package pl.lodz.p.it.functionalfood.investigator.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
@Table(name = "nutritional_value")
public class NutritionalValue extends AbstractEntity {

    @ManyToOne
    private NutritionalValueName nutritionalValueName;

    @ManyToOne
    private Unit unit;

    private Double quantity;

    private Double nrv; // Referencja wartości spożycia - RWS

    public NutritionalValue(NutritionalValueName nutritionalValueName, Double quantity, Unit unit, Double nrv) {
        this.nutritionalValueName = nutritionalValueName;
        this.quantity = quantity;
        this.unit = unit;
        this.nrv = nrv;
    }
}
