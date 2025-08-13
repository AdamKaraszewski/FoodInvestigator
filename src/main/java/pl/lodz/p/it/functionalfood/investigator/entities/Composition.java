package pl.lodz.p.it.functionalfood.investigator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Composition extends AbstractEntity {

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Ingredient> ingredients = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Addition> additions = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Flavour flavour;

    public Composition(List<Ingredient> ingredients, List<Addition> additions, Flavour flavour) {
        this.ingredients.addAll(ingredients);
        this.additions.addAll(additions);
        this.flavour = flavour;
    }
}
