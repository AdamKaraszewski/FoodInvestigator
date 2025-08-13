package pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class AccountDTO {

    private UUID id; //

    private String login; //


    private boolean active;

    private String accessLevel;

    private String firstName; //

    private String lastName; //

    private String email; //


    private String authenticationProvider;
    private Long version;
}
