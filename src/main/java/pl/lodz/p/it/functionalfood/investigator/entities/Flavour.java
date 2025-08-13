package pl.lodz.p.it.functionalfood.investigator.entities;
import jakarta.persistence.Entity;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Flavour extends AbstractEntity {

    //@NotNull
    private String name;

    public Flavour(String name) {
        this.name = name;
    }
}
