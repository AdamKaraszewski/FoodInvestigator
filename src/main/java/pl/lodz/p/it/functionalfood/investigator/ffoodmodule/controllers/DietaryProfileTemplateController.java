package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.controllers;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileTemplateDTOs.CreateDietaryProfileTemplateDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileTemplateDTOs.UpdateDietaryProfileTemplateDTO;
import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.services.FFoodModuleService;
import pl.lodz.p.it.functionalfood.investigator.util.converters.DietaryProfileTemplateConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dietary-profile-templates")
@Transactional(propagation = Propagation.NEVER)
public class DietaryProfileTemplateController {

    private final FFoodModuleService ffoodModuleService;

    public DietaryProfileTemplateController(FFoodModuleService ffoodModuleService) {
        this.ffoodModuleService = ffoodModuleService;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping
    public ResponseEntity<?> getAllDietaryProfileTemplates() {
        return ResponseEntity.status(HttpStatus.OK).body(ffoodModuleService.getAllDietaryProfileTemplatesWithoutPagination());
    }

    @PreAuthorize("hasRole('EXPERT')")
    @GetMapping("/my")
    public ResponseEntity<?> getAllDietaryProfileTemplatesExpertOwe() {
        return ResponseEntity.status(HttpStatus.OK).body(ffoodModuleService.getAllDietaryProfileTemplatesExpertOweWithoutPagination());
    }

    @PreAuthorize("hasRole('EXPERT')")
    @GetMapping("/my/{id}")
    public ResponseEntity<?> getDietaryProfileTemplateExpertOweById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ffoodModuleService.getDietaryProfileTemplateExpertOweById(id));
    }

    @PreAuthorize("hasRole('EXPERT')")
    @PostMapping("/my")
    public ResponseEntity<?> createDietaryProfileTemplate(@RequestBody CreateDietaryProfileTemplateDTO createDietaryProfileTemplateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
        ffoodModuleService.createDietaryProfileTemplate(
                DietaryProfileTemplateConverter.createDietaryProfileTemplateFromDTO(createDietaryProfileTemplateDTO),
                createDietaryProfileTemplateDTO.getConstraints())
        );
    }

    @PutMapping("/my/{id}")
    @PreAuthorize("hasRole('EXPERT')")
    public ResponseEntity<?> updateDietaryProfileTemplate(@PathVariable UUID id, @RequestBody UpdateDietaryProfileTemplateDTO updateDietaryProfiletemplateDTO) {
        ffoodModuleService.updateDietaryProfileTemplate(id, updateDietaryProfiletemplateDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('EXPERT')")
    @DeleteMapping("/my/{id}")
    public ResponseEntity<?> deleteDietaryProfileExpertOwe(@PathVariable UUID id) {
        ffoodModuleService.deleteDietaryProfileTemplateExpertOwe(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
