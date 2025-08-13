package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.controllers;

import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.services.FFoodModuleService;
import pl.lodz.p.it.functionalfood.investigator.util.converters.NutritionalValueNameConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nutritional-value-names")
@Transactional(propagation = Propagation.NEVER)
public class NutritionalValueNameController {

    private final FFoodModuleService ffoodModuleService;

    public NutritionalValueNameController(FFoodModuleService ffoodModuleService) {
        this.ffoodModuleService = ffoodModuleService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('CLIENT', 'EXPERT')")
    public ResponseEntity<?> findAllNutritionalValueNamesWithoutPagination() {
        return ResponseEntity.status(HttpStatus.OK).body(NutritionalValueNameConverter.createAllNutritionalValueNamesListDTO(ffoodModuleService.readAllNutritionalValueNamesWithoutPagination()));
    }
}
