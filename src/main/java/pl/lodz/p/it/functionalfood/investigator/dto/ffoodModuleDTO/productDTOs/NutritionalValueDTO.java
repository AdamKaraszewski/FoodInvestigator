package pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class NutritionalValueDTO {
    private String nutritionalValueName;
    private String nutritionalValueGroup;
    private Double quantity;
    private String unit;

    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        var other = (NutritionalValueDTO) otherObject;
        return Objects.equals(nutritionalValueName, other.getNutritionalValueName()) &&
                Objects.equals(nutritionalValueGroup, other.nutritionalValueGroup)  &&
                Objects.equals(unit, other.unit);
    }
}
