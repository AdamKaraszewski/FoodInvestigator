package pl.lodz.p.it.functionalfood.investigator.entities;

import jakarta.persistence.Column;
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
public class Label extends AbstractEntity {

    private String storage;

    private String durability;

    @Column(name = "instructions_after_opening")
    private String instructionsAfterOpening;

    private String preparation;

    private String allergens;

    private byte[] image;

    public Label(String storage, String durability, String instructionsAfterOpening, String preparation, String allergens, byte[] image) {
        this.storage = storage;
        this.durability = durability;
        this.instructionsAfterOpening = instructionsAfterOpening;
        this.preparation = preparation;
        this.allergens = allergens;
        this.image = image;
    }
}
