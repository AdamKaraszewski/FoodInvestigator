package pl.lodz.p.it.functionalfood.investigator.entities;

import pl.lodz.p.it.functionalfood.investigator.config.database.DatabaseStructures;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = DatabaseStructures.EMPLOYEE_ACCESS_LEVEL_TABLE)
public class EmployeeAccessLevel extends AccessLevel {
    public EmployeeAccessLevel() {
        super("EMPLOYEE");
    }
}
