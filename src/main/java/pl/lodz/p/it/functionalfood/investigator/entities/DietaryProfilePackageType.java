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
@Table(name = "DIETARY_PROFILE_PACKAGE_TYPE")
public class DietaryProfilePackageType extends AbstractEntity implements IConstraint {

    @Getter
    @Enumerated(EnumType.STRING)
    private DietaryConstraintWeights weight;

    @Getter
    @ManyToOne
    @JoinColumn(name = "package_type_id", nullable = false)
    private PackageType packageType;

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
        return packageType.getId();
    }
}
