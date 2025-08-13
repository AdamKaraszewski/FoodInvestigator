package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.controllers;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.producerDTOs.CreateProducerDTO;
import pl.lodz.p.it.functionalfood.investigator.entities.Producer;
import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.services.FFoodModuleService;
import pl.lodz.p.it.functionalfood.investigator.util.converters.ProducerConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("producers")
@Transactional(propagation = Propagation.NEVER)
public class ProducerController {

    private final FFoodModuleService ffoodModuleService;

    public ProducerController(FFoodModuleService fFoodModuleService) {
        this.ffoodModuleService = fFoodModuleService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> findProducerById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ProducerConverter.createProducerDTO(ffoodModuleService.readProducerById(id)));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> findAllProducersWithoutPagination() {
        return ResponseEntity.status(HttpStatus.OK).body(ffoodModuleService.readAllProducersWithoutPagination());
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> createProducer(@RequestBody CreateProducerDTO producerDTO) {
        Producer producer = ProducerConverter.createProducerFromCreateProducerDTO(producerDTO);
        ffoodModuleService.createProducer(producer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
