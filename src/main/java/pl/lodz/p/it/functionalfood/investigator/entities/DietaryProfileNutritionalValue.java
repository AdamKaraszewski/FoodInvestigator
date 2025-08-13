package pl.lodz.p.it.functionalfood.investigator.entities;

import pl.lodz.p.it.functionalfood.investigator.enums.DietaryConstraintWeights;
import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.logic.IConstraint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DIETARY_PROFILE_NUTRITIONAL_VALUE")
public class DietaryProfileNutritionalValue extends AbstractEntity implements IConstraint {

    @Getter
    @Enumerated(EnumType.STRING)
    private DietaryConstraintWeights weight;

    @Getter
    @ManyToOne
    @JoinColumn(name = "nutritional_value_name_id", nullable = false)
    private NutritionalValueName nutritionalValueName;

    @Getter
    @ManyToOne
    @JoinColumn(name = "dietary_profile_id", nullable = false)
    private DietaryProfile dietaryProfile;

    @Override
    public DietaryConstraintWeights getConstraintWeight() {
        return weight;
    }

    @Override
    public UUID getItemUuid() {
        return nutritionalValueName.getId();
    }


}
