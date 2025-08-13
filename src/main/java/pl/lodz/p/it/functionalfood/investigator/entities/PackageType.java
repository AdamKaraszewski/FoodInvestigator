package pl.lodz.p.it.functionalfood.investigator.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@NoArgsConstructor
@ToString
@Entity
@Table(name = "package_type")
@Getter @Setter
public class PackageType extends AbstractEntity {
    @Column(name = "name")
    private String name;
}
