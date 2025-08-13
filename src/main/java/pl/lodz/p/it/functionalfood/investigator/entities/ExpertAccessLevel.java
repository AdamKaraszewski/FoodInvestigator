package pl.lodz.p.it.functionalfood.investigator.entities;

import pl.lodz.p.it.functionalfood.investigator.config.database.DatabaseStructures;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = DatabaseStructures.EXPERT_ACCESS_LEVEL_TABLE)
public class ExpertAccessLevel extends AccessLevel {

//    @OneToMany(mappedBy = "expert")
//    @Getter
//    @Setter
//    private List<DietaryProfileTemplate> dietaryProfileTemplates = new ArrayList<>();

    public ExpertAccessLevel() {
        super("EXPERT");
    }
}
