package pl.lodz.p.it.functionalfood.investigator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "DIETARY_PROFILE_TEMPLATES")
@NoArgsConstructor
public class DietaryProfileTemplate extends AbstractEntity {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    @Setter
    @OneToMany(mappedBy = "dietaryProfileTemplate", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<DietaryProfileTemplateNutritionalValue> nutritionalValues = new ArrayList<>();

    @Setter
    @OneToMany(mappedBy = "dietaryProfileTemplate", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<DietaryProfileTemplatePackageType> packageTypes = new ArrayList<>();

    @Setter
    @OneToMany(mappedBy = "dietaryProfileTemplate",  cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<DietaryProfileTemplateRating> ratings = new ArrayList<>();

    @Setter
    @Column
    private boolean archived = false;

    @ManyToOne
    @Getter @Setter
    private ExpertAccessLevel expert;

    public DietaryProfileTemplate(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
