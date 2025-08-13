package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.controllers;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs.CreateDietaryProfileDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs.UpdateDietaryProfileDTO;
import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.services.FFoodModuleService;
import pl.lodz.p.it.functionalfood.investigator.util.converters.DietaryProfileConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dietary-profiles")
@Transactional(propagation = Propagation.NEVER)
public class DietaryProfileController {

    private final FFoodModuleService ffoodModuleService;

    public DietaryProfileController(FFoodModuleService ffoodModuleService) {
        this.ffoodModuleService = ffoodModuleService;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<?> createDietaryProfile(@RequestBody CreateDietaryProfileDTO createDietaryProfileDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ffoodModuleService.createDietaryProfile(
                        DietaryProfileConverter.createDietaryProfileFromDTO(createDietaryProfileDTO),
                        createDietaryProfileDTO.getConstraints()
                ));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/my")
    public ResponseEntity<?> getAllActiveDietaryProfilesClientOwe() {
        return ResponseEntity.status(HttpStatus.OK).body(ffoodModuleService.getAllActiveDietaryProfilesClientOwe());
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/my/{id}")
    public ResponseEntity<?> getDietaryProfileClientOweById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ffoodModuleService.getDietaryProfileClientOweById(id));
    }

    @PutMapping("/my/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> updateDietaryProfileClientOwe(@PathVariable UUID id, @RequestBody UpdateDietaryProfileDTO updateDietaryProfileDTO) {
        ffoodModuleService.updateDietaryProfile(id, updateDietaryProfileDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/my/{id}")
    public ResponseEntity<?> deleteDietaryProfileClientOwe(@PathVariable UUID id) {
        ffoodModuleService.deleteDietaryProfileClientOwe(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
