package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.controllers;

import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.services.FFoodModuleService;
import pl.lodz.p.it.functionalfood.investigator.util.converters.UnitConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/units")
@Transactional(propagation = Propagation.NEVER)
public class UnitController {
    private final FFoodModuleService ffoodModuleService;

    public UnitController(FFoodModuleService ffoodModuleService) {
        this.ffoodModuleService = ffoodModuleService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> getAllUnits() {
        return ResponseEntity.status(HttpStatus.OK).body(UnitConverter.createUnitListDTO(ffoodModuleService.readAllUnits()));
    }
}
