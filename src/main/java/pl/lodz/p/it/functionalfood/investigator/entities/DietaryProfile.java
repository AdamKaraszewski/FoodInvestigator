package pl.lodz.p.it.functionalfood.investigator.entities;

import pl.lodz.p.it.functionalfood.investigator.enums.DietaryConstraintWeights;
import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.logic.ConstraintCounterResults;
import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.logic.DoesProductFollowsDietaryProfile;
import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.logic.IConstraint;
import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.logic.ProductSummary;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "DIETARY_PROFILE")
@NoArgsConstructor
public class DietaryProfile extends AbstractEntity {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    @Setter
    @OneToMany(mappedBy = "dietaryProfile", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<DietaryProfileNutritionalValue> nutritionalValues = new ArrayList<>();

    @Setter
    @OneToMany(mappedBy = "dietaryProfile", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<DietaryProfilePackageType> packageTypes = new ArrayList<>();

    @Setter
    @OneToMany(mappedBy = "dietaryProfile",  cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<DietaryProfileRating> ratings = new ArrayList<>();

    public DietaryProfile(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Setter
    @Column
    private boolean archived = false;

    @ManyToOne
    @Getter @Setter
    private ClientAccessLevel client;

    public DoesProductFollowsDietaryProfile doesProductFollowProfile(Product product) {
        ConstraintCounterResults nutritionalValueResult = countConstraints(nutritionalValues);
        ConstraintCounterResults packageTypeResult = countConstraints(packageTypes);
        ConstraintCounterResults ratingResult = countConstraints(ratings);

        int bannedNotFollowed = 0;
        int requiredNotFollowed = 0;
        int niceToSeeFollowed = 0;

        List<UUID> constraintsFollowed = new ArrayList<>();
        List<UUID> constraintsNotFollowed = new ArrayList<>();
//
        List<UUID> bannedUuids = new ArrayList<>();
        bannedUuids.addAll(nutritionalValueResult.getBannedUuids());
        bannedUuids.addAll(packageTypeResult.getBannedUuids());
        bannedUuids.addAll(ratingResult.getBannedUuids());
        List<UUID> requiredUuids = new ArrayList<>();
        requiredUuids.addAll(nutritionalValueResult.getRequiredUuids());
        requiredUuids.addAll(packageTypeResult.getRequiredUuids());
        requiredUuids.addAll(ratingResult.getRequiredUuids());
        List<UUID> niceToSeeUuids = new ArrayList<>();
        niceToSeeUuids.addAll(nutritionalValueResult.getNiceToSeeUuids());
        niceToSeeUuids.addAll(packageTypeResult.getNiceToSeeUuids());
        niceToSeeUuids.addAll(ratingResult.getNiceToSeeUuids());

        System.out.println("OTO ZAKAZANE ATRYBUTY");
        for (UUID uuid : bannedUuids) {
            System.out.println(uuid);
        }

        System.out.println("OTO WYMAGANE ATRYBUTY");
        for (UUID uuid : requiredUuids) {
            System.out.println(uuid);
        }

        System.out.println("OTO MILE_WIDZIANE ATRYBUTY");
        for (UUID uuid : niceToSeeUuids) {
            System.out.println(uuid);
        }

        List<UUID> productAttributes = extractIdFromProduct(product);

        for (UUID bannedUuid : bannedUuids) {
            if (productAttributes.contains(bannedUuid)) {
                constraintsNotFollowed.add(bannedUuid);
                bannedNotFollowed++;
            } else {
                constraintsFollowed.add(bannedUuid);
            }
        }

        for (UUID requiredUuid : requiredUuids) {
            if (productAttributes.contains(requiredUuid)) {
                constraintsFollowed.add(requiredUuid);
            } else {
                constraintsNotFollowed.add(requiredUuid);
                requiredNotFollowed++;
            }
        }

        for (UUID niceToSeeUuid : niceToSeeUuids) {
            if (productAttributes.contains(niceToSeeUuid)) {
                constraintsFollowed.add(niceToSeeUuid);
                niceToSeeFollowed++;
            } else {
                constraintsNotFollowed.add(niceToSeeUuid);
            }
        }

        if (bannedNotFollowed > 0 || requiredNotFollowed > 0) {
            return new DoesProductFollowsDietaryProfile(ProductSummary.DOES_NOT_FOLLOW_REQUIRED_OR_BANNED.name(),
                    constraintsFollowed, constraintsNotFollowed);
        }

        // zgodnosc_procentowa = nice_to_see_followed / nice_to_see_uuids
        if (niceToSeeUuids.size() == 0) {
            return new DoesProductFollowsDietaryProfile(ProductSummary.DOES_FOLLOW_REQUIRED_AND_BANNED_AND_75_100_NICE_TO_SEE.name(),
                    constraintsFollowed, constraintsNotFollowed);
        }

        int niceToSeeSummary = (100 * niceToSeeFollowed) / niceToSeeUuids.size();

        if (niceToSeeSummary >= 75) {
            return new DoesProductFollowsDietaryProfile(ProductSummary.DOES_FOLLOW_REQUIRED_AND_BANNED_AND_75_100_NICE_TO_SEE.name(),
                    constraintsFollowed, constraintsNotFollowed);
        } else if (niceToSeeSummary >= 50) {
            return new DoesProductFollowsDietaryProfile(ProductSummary.DOES_FOLLOW_REQUIRED_AND_BANNED_AND_50_75_NICE_TO_SEE.name(),
                    constraintsFollowed, constraintsNotFollowed);
        } else if (niceToSeeSummary >= 25) {
            return new DoesProductFollowsDietaryProfile(ProductSummary.DOES_FOLLOW_REQUIRED_AND_BANNED_AND_25_50_NICE_TO_SEE.name(),
                    constraintsFollowed, constraintsNotFollowed);
        } else {
            return new DoesProductFollowsDietaryProfile(ProductSummary.DOES_FOLLOW_REQUIRED_AND_BANNED_AND_0_25_NICE_TO_SEE.name(),
                    constraintsFollowed, constraintsNotFollowed);
        }
    }

    private <T extends IConstraint> ConstraintCounterResults countConstraints(List<T> constraints) {
        System.out.println("TWORZE GRYPY CONSTRAINTOW ZE WZGLEDY NA WAGE");
        List<UUID> bannedUuids = new ArrayList<>();
        List<UUID> requiredUuids = new ArrayList<>();
        List<UUID> niceToSeeUuids = new ArrayList<>();
        for (T constraint : constraints) {
            if (constraint.getConstraintWeight() == DietaryConstraintWeights.BANNED) bannedUuids.add(constraint.getItemUuid());
            else if (constraint.getConstraintWeight() == DietaryConstraintWeights.REQUIRED) requiredUuids.add(constraint.getItemUuid());
            else niceToSeeUuids.add(constraint.getItemUuid());
        }
        return new ConstraintCounterResults(bannedUuids, requiredUuids, niceToSeeUuids);
    }

    private List<UUID> extractIdFromProduct(Product product) {
        List<UUID> uuids = new ArrayList<>();
        uuids.add(product.getPackageType().getId());
        for (Rating rating : product.getRatings()) {
            uuids.add(rating.getId());
        }
        for (NutritionalValue nutritionalValue : product.getNutritionalValues()) {
            uuids.add(nutritionalValue.getNutritionalValueName().getId());
        }
        return uuids;
    }

}
