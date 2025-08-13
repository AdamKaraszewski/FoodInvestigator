package pl.lodz.p.it.functionalfood.investigator.entities;

import pl.lodz.p.it.functionalfood.investigator.enums.DietaryConstraintWeights;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DIETARY_PROFILE_TEMPLATE_NUTRITIONAL_VALUE")
public class DietaryProfileTemplateNutritionalValue extends AbstractEntity {

    @Getter
    @Enumerated(EnumType.STRING)
    private DietaryConstraintWeights weight;

    @Getter
    @ManyToOne
    @JoinColumn(name = "nutritional_value_name_id", nullable = false)
    private NutritionalValueName nutritionalValueName;

    @Getter
    @ManyToOne
    @JoinColumn(name = "dietary_profile_template_id", nullable = false)
    private DietaryProfileTemplate dietaryProfileTemplate;
}
