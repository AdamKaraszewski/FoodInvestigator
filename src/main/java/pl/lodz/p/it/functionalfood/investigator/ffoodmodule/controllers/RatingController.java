package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.controllers;

import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.services.FFoodModuleService;
import pl.lodz.p.it.functionalfood.investigator.util.converters.RatingConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
@Transactional(propagation = Propagation.NEVER)
public class RatingController {

    private final FFoodModuleService ffoodModuleService;

    public RatingController(FFoodModuleService ffoodModuleService) {
        this.ffoodModuleService = ffoodModuleService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('CLIENT', 'EXPERT')")
    public ResponseEntity<?> findAllRatingsWithoutPagination() {
        return ResponseEntity.status(HttpStatus.OK).body(RatingConverter.createRatingListDTO(ffoodModuleService.readAllRatingsWithoutPagination()));
    }
}
