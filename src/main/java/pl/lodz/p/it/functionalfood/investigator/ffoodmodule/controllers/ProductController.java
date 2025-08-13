package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.controllers;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs.CreateProductDTO;
import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.services.FFoodModuleService;
import pl.lodz.p.it.functionalfood.investigator.util.converters.ProductConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@Transactional(propagation = Propagation.NEVER)
public class ProductController {

    private final FFoodModuleService ffoodModuleService;

    public ProductController(FFoodModuleService ffoodModuleService) {
        this.ffoodModuleService = ffoodModuleService;
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> create(@RequestBody CreateProductDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(ProductConverter.createProductDTO(ffoodModuleService.createProduct(ProductConverter.createProductFromCreateProductDTO(dto),
                dto.getProducerId(), dto.getUnitId(), dto.getPackageTypeId(), ProductConverter.createCompositionFromCreateProductDTO(dto),
                ProductConverter.createNutritionalIndexSetFromCreateProductDTO(dto), ProductConverter.createProductIndexSetFromCreateProductDTO(dto),
                ProductConverter.createLabelFromCreateProductDTO(dto), ProductConverter.createPortionFromCreateProductDTO(dto),
                dto.getUnitId(), dto.getNutritionalValues())));
    }

    @GetMapping("/summary/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> findAllProductsWithSummary(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ffoodModuleService.readAllProductsWithSummaryWithoutPagination(id));
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<?> findAllProductsWithoutPagination() {
        return ResponseEntity.status(HttpStatus.OK).body(ffoodModuleService.readAllProductsWithoutPagination());
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> findProductById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ffoodModuleService.generateProductWithoutSummary(id));
    }

    @GetMapping("/ean/{ean}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> findProductByEAN(@PathVariable String ean) {
        return ResponseEntity.status(HttpStatus.OK).body(ffoodModuleService.generateProductWithoutSummaryEAN(ean));
    }

    @GetMapping("/{id}/summary")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> getProductSummary(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ffoodModuleService.generateProductSummaryForCertainClient(id));
    }

    @GetMapping("ean/{ean}/summary")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> getProductSummary(@PathVariable String ean) {
        return ResponseEntity.status(HttpStatus.OK).body(ffoodModuleService.generateProductSummaryForCertainClientEAN(ean));
    }

}
