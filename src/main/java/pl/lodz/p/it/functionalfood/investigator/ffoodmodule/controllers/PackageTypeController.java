package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.controllers;

import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.services.FFoodModuleService;
import pl.lodz.p.it.functionalfood.investigator.util.converters.PackageTypeConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/package-types")
@Transactional(propagation = Propagation.NEVER)
public class PackageTypeController {

    private final FFoodModuleService ffoodModuleService;
    public PackageTypeController(FFoodModuleService ffoodModuleService) {
        this.ffoodModuleService = ffoodModuleService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('CLIENT', 'EXPERT', 'EMPLOYEE')")
    public ResponseEntity<?> findAllPackageTypesWithoutPagination() {
        return ResponseEntity.status(HttpStatus.OK).body(
                PackageTypeConverter.createAllPackageTypesListDTO(
                        ffoodModuleService.readAllPackageTypesWithoutPagination()
                )
        );
    }
}
