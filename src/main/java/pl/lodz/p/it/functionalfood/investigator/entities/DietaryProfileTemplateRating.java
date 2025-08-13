package pl.lodz.p.it.functionalfood.investigator.entities;


import pl.lodz.p.it.functionalfood.investigator.enums.DietaryConstraintWeights;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DIETARY_PROFILE_TEMPLATE_RATING")
public class DietaryProfileTemplateRating extends AbstractEntity {
    @Getter
    @Enumerated(EnumType.STRING)
    private DietaryConstraintWeights weight;

    @Getter
    @ManyToOne
    @JoinColumn(name = "rating_id", nullable = false)
    private Rating rating;

    @Getter
    @ManyToOne
    @JoinColumn(name = "dietary_profile_template_id", nullable = false)
    private DietaryProfileTemplate dietaryProfileTemplate;
}
