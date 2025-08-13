package pl.lodz.p.it.functionalfood.investigator.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table
public class Producer extends AbstractEntity {

    //@NotNull
    private String name;

    private String address;

    @Column(name = "country_code", nullable = false)
    private Integer countryCode;

    private String NIP;

    private Integer RMSD;

    private String contact;

    //private boolean archived = false;

    public Producer(String name, String address, Integer countryCode, String NIP, Integer RMSD, String contact) {
        this.name = name;
        this.address = address;
        this.countryCode = countryCode;
        this.NIP = NIP;
        this.RMSD = RMSD;
        this.contact = contact;
    }
}
