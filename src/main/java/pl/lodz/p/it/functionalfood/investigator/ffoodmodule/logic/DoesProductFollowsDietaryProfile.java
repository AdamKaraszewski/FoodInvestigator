package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class DoesProductFollowsDietaryProfile {
    private String productSummary;
    private List<UUID> constraintsFollowed;
    private List<UUID> constraintsNotFollowed;
}
