package pl.lodz.p.it.functionalfood.investigator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Product extends AbstractEntity {

//    @Size(max = 13)
    private String ean;

    @ManyToOne
    private Producer producer;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_quantity")
    private Integer productQuantity;

    @ManyToOne
    private Unit unit;

    @ManyToOne
    private PackageType packageType;

    @Column(name = "country", length = Integer.MAX_VALUE)
    private String country;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Composition composition;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<NutritionalIndex> nutritionalIndexes = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<ProductIndex> productIndexes = new HashSet<>();

    @OneToOne(cascade = CascadeType.PERSIST)
    private Label label;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Portion portion;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_rating",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "ratings_id")
    )
    private Set<Rating> ratings = new HashSet<>();

   @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
   private List<NutritionalValue> nutritionalValues;

    public Product(String ean, String productName, String productDescription, Integer productQuantity, String country) {
        this.ean = ean;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
        this.country = country;
    }
}
