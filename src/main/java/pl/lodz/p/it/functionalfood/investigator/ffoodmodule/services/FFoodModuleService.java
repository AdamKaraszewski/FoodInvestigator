package pl.lodz.p.it.functionalfood.investigator.ffoodmodule.services;

import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryConstraintDTOs.CreateDietaryConstraintDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs.DietaryProfileDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs.DietaryProfileListDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs.UpdateDietaryProfileDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileTemplateDTOs.DietaryProfileTemplateDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileTemplateDTOs.DietaryProfileTemplatesListDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileTemplateDTOs.UpdateDietaryProfileTemplateDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.producerDTOs.ProducerListDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs.*;
import pl.lodz.p.it.functionalfood.investigator.entities.*;
import pl.lodz.p.it.functionalfood.investigator.enums.DietaryConstraintWeights;
import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.repositories.*;
import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.repositories.*;
import pl.lodz.p.it.functionalfood.investigator.util.converters.DietaryProfileTemplateConverter;
import pl.lodz.p.it.functionalfood.investigator.util.converters.ProducerConverter;
import pl.lodz.p.it.functionalfood.investigator.util.converters.ProductConverter;
import pl.lodz.p.it.functionalfood.investigator.util.converters.DietaryProfileConverter;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs.CreateNutritionalValueDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs.ProductSummaryForClientDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs.ProductWithSummaryListDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs.ProductWithSummaryOnListDTO;
import pl.lodz.p.it.functionalfood.investigator.entities.*;
import pl.lodz.p.it.functionalfood.investigator.enums.DietaryConstraintTarget;

import java.util.*;

@Service
public class FFoodModuleService {

    private final ProducerRepository producerRepository;
    private final UnitRepository unitRepository;
    private final PackageTypeRepository packageTypeRepository;
    private final ProductRepository productRepository;
    private final NutritionalValueNameRepository nutritionalValueNameRepository;
    private final RatingRepository ratingRepository;
    private final DietaryProfileRepository dietaryProfileRepository;
    private final DietaryProfileTemplateRepository dietaryProfileTemplateRepository;
    private final FfoodModuleExpertDataRepository expertDataRepository;
    private final FfoodModuleClientDataRepository clientDataRepository;

    public FFoodModuleService(ProducerRepository producerRepository, UnitRepository unitRepository,
                              PackageTypeRepository packageTypeRepository, ProductRepository productRepository,
                              NutritionalValueNameRepository nutritionalValueNameRepository,
                              RatingRepository ratingRepository, DietaryProfileRepository dietaryProfileRepository,
                              DietaryProfileTemplateRepository dietaryProfileTemplateRepository,
                              FfoodModuleExpertDataRepository expertDataRepository,
                              FfoodModuleClientDataRepository clientDataRepository
                              ) {
        this.producerRepository = producerRepository;
        this.unitRepository = unitRepository;
        this.packageTypeRepository = packageTypeRepository;
        this.productRepository = productRepository;
        this.nutritionalValueNameRepository = nutritionalValueNameRepository;
        this.ratingRepository = ratingRepository;
        this.dietaryProfileRepository = dietaryProfileRepository;
        this.dietaryProfileTemplateRepository = dietaryProfileTemplateRepository;
        this.expertDataRepository = expertDataRepository;
        this.clientDataRepository = clientDataRepository;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public List<Unit> readAllUnits() {
        return unitRepository.findAll();
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public Producer readProducerById(UUID producerId) {
        return producerRepository.findById(producerId).orElseThrow();
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public ProducerListDTO readAllProducersWithoutPagination() {
        return ProducerConverter.createProducerListDTO(producerRepository.findAll());
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public void createProducer(Producer producer) {
        producerRepository.save(producer);
    }

    @PreAuthorize("hasAnyRole('CLIENT', 'EXPERT', 'EMPLOYEE')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public List<PackageType> readAllPackageTypesWithoutPagination() {
        return packageTypeRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('CLIENT', 'EXPERT')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public List<NutritionalValueName> readAllNutritionalValueNamesWithoutPagination() {
        return nutritionalValueNameRepository.findAll(Sort.by("name"));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    private List<NutritionalValue> createNutritionalValues(List<CreateNutritionalValueDTO> nutritionalValueDTOs) {
        List<NutritionalValue> nutritionalValues = new ArrayList<>();
        for (CreateNutritionalValueDTO nutritionalValueDTO : nutritionalValueDTOs) {
            nutritionalValues.add(new NutritionalValue(nutritionalValueNameRepository.findById(nutritionalValueDTO.getNutritionalValueNameId()).orElseThrow(), nutritionalValueDTO.getQuantity(), unitRepository.findById(nutritionalValueDTO.getUnitId()).orElseThrow() ,nutritionalValueDTO.getNrv()));
        }
        return nutritionalValues;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public Product createProduct(Product product, UUID producerId, UUID unitId, UUID packageTypeId, Composition composition, Set<NutritionalIndex> nutritionalIndexes, Set<ProductIndex> productIndexes, Label label, Portion portion, UUID portionUnitId, List<CreateNutritionalValueDTO> nutritionalValueDTOs) { //, UUID producerId, UUID unitId, UUID packageTypeId, Composition composition, Set<NutritionalIndex> nutritionalIndexes, Set<ProductIndex> productIndexes, Label label, Portion portion, UUID portionUnitId) {
        Producer producer = producerRepository.findById(producerId).orElseThrow();
        Unit unit = unitRepository.findById(unitId).orElseThrow();
        Unit unitPortion = unitRepository.findById(portionUnitId).orElseThrow();
        PackageType packageType = packageTypeRepository.findById(packageTypeId).orElseThrow();
        List<NutritionalValue> nutritionalValues = createNutritionalValues(nutritionalValueDTOs);
        product.setProducer(producer);
        product.setUnit(unit);
        product.setPackageType(packageType);
        product.setComposition(composition);
        product.setNutritionalIndexes(nutritionalIndexes);
        product.setProductIndexes(productIndexes);
        product.setLabel(label);
        portion.setUnit(unitPortion);
        product.setPortion(portion);
        product.setNutritionalValues(nutritionalValues);
        return productRepository.save(product);
    }

    @PreAuthorize("permitAll()")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public ProductWithSummaryListDTO readAllProductsWithoutPagination() {
        List<ProductWithSummaryOnListDTO> productsListDTO = new ArrayList<>();
        List<Product> allProducts = productRepository.findAll(Sort.by("productName"));
        for (Product product : allProducts) {
            productsListDTO.add(ProductConverter.createProductWithoutSummaryOnListDTO(product));
        }
        return new ProductWithSummaryListDTO(productsListDTO);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public ProductWithSummaryListDTO readAllProductsWithSummaryWithoutPagination(UUID id) {
        List<ProductWithSummaryOnListDTO> productWithSummaryOnList = new ArrayList<>();
        List<Product> allProducts = productRepository.findAll(Sort.by("productName"));
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        DietaryProfile dietaryProfile = dietaryProfileRepository.findByIdAndClient_Account_LoginAndArchivedIsFalse(id, login).orElseThrow();
        for (Product product : allProducts) {
            productWithSummaryOnList.add(ProductConverter.createProductWithSummaryOnListDTO(product, dietaryProfile));
        }
        return new ProductWithSummaryListDTO(productWithSummaryOnList);
    }

    @PreAuthorize("hasAnyRole('CLIENT', 'EXPERT')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public List<Rating> readAllRatingsWithoutPagination() {
        return ratingRepository.findAll(Sort.by("name"));
    }


    //----------------------------------------------PROFILE DIETETYCZNE

    @PreAuthorize("hasRole('CLIENT')")
    private DietaryProfileNutritionalValue createNutritionalValueConstraint(DietaryConstraintWeights weight, UUID nutritionalValueNameId, DietaryProfile dietaryProfile) {
        NutritionalValueName nutritionalValueName = nutritionalValueNameRepository.findById(nutritionalValueNameId).orElseThrow();
        return new DietaryProfileNutritionalValue(weight, nutritionalValueName, dietaryProfile);
    }

    @PreAuthorize("hasRole('EXPERT')")
    private DietaryProfileTemplateNutritionalValue createNutritionalValueConstraintTemplate(DietaryConstraintWeights weight, UUID nutritionalValueNameId, DietaryProfileTemplate dietaryProfileTemplate) {
        NutritionalValueName nutritionalValueName = nutritionalValueNameRepository.findById(nutritionalValueNameId).orElseThrow();
        return new DietaryProfileTemplateNutritionalValue(weight, nutritionalValueName, dietaryProfileTemplate);
    }

    @PreAuthorize("hasRole('CLIENT')")
    private DietaryProfileRating createRatingConstraint(DietaryConstraintWeights weight, UUID ratingId, DietaryProfile dietaryProfile) {
        Rating rating = ratingRepository.findById(ratingId).orElseThrow();
        return new DietaryProfileRating(weight, rating, dietaryProfile);
    }

    @PreAuthorize("hasRole('EXPERT')")
    private DietaryProfileTemplateRating createRatingConstraintTemplate(DietaryConstraintWeights weight, UUID ratingId, DietaryProfileTemplate dietaryProfileTemplate) {
        Rating rating = ratingRepository.findById(ratingId).orElseThrow();
        return new DietaryProfileTemplateRating(weight, rating, dietaryProfileTemplate);
    }

    @PreAuthorize("hasRole('CLIENT')")
    private DietaryProfilePackageType createPackageTypeConstraint(DietaryConstraintWeights weight, UUID packageTypeId, DietaryProfile dietaryProfile) {
        PackageType packageType = packageTypeRepository.findById(packageTypeId).orElseThrow();
        return new DietaryProfilePackageType(weight, packageType, dietaryProfile);
    }

    @PreAuthorize("hasRole('EXPERT')")
    private DietaryProfileTemplatePackageType createPackageTypeConstraintTemplate(DietaryConstraintWeights weight, UUID packageTypeId, DietaryProfileTemplate dietaryProfileTemplate) {
        PackageType packageType = packageTypeRepository.findById(packageTypeId).orElseThrow();
        return new DietaryProfileTemplatePackageType(weight, packageType, dietaryProfileTemplate);
    }

    @PreAuthorize("hasRole('EXPERT')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public DietaryProfileTemplateDTO createDietaryProfileTemplate(DietaryProfileTemplate dietaryProfileTemplateToCreate, List<CreateDietaryConstraintDTO> constraintDTOs) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        ExpertAccessLevel expert = expertDataRepository.findByAccount_Login(login).orElseThrow();
        dietaryProfileTemplateToCreate.setExpert(expert);
        for (CreateDietaryConstraintDTO constraintDTO : constraintDTOs) {
            switch (constraintDTO.getTarget()) {
                case DietaryConstraintTarget.PACKAGE_TYPE ->
                        dietaryProfileTemplateToCreate.getPackageTypes().add(createPackageTypeConstraintTemplate(constraintDTO.getWeight(), constraintDTO.getElementId(), dietaryProfileTemplateToCreate));
                case DietaryConstraintTarget.NUTRITIONAL_VALUE ->
                        dietaryProfileTemplateToCreate.getNutritionalValues().add(createNutritionalValueConstraintTemplate(constraintDTO.getWeight(), constraintDTO.getElementId(), dietaryProfileTemplateToCreate));
                case DietaryConstraintTarget.RATING ->
                        dietaryProfileTemplateToCreate.getRatings().add(createRatingConstraintTemplate(constraintDTO.getWeight(), constraintDTO.getElementId(), dietaryProfileTemplateToCreate));
            }
        }
        return DietaryProfileTemplateConverter.createDietaryProfileTemplateDTO(dietaryProfileTemplateRepository.save(dietaryProfileTemplateToCreate));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public DietaryProfileTemplatesListDTO getAllDietaryProfileTemplatesWithoutPagination() {
        return DietaryProfileTemplateConverter.createDietaryProfileTemplatesListDTO(dietaryProfileTemplateRepository.findAllByArchivedIsFalse());
    }

    @PreAuthorize("hasRole('EXPERT')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public DietaryProfileTemplatesListDTO getAllDietaryProfileTemplatesExpertOweWithoutPagination() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return DietaryProfileTemplateConverter.createDietaryProfileTemplatesListDTO(dietaryProfileTemplateRepository.findAllByExpert_Account_LoginAndArchivedIsFalse(username));
    }

    @PreAuthorize("hasRole('EXPERT')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public DietaryProfileTemplateDTO getDietaryProfileTemplateExpertOweById(UUID id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return DietaryProfileTemplateConverter.createDietaryProfileTemplateDTO(dietaryProfileTemplateRepository.findByIdAndExpert_Account_LoginAndArchivedIsFalse(id, username).orElseThrow());
    }

    @PreAuthorize("hasRole('EXPERT')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public void updateDietaryProfileTemplate(UUID id, UpdateDietaryProfileTemplateDTO updateDietaryProfileTemplateDTO) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        DietaryProfileTemplate dietaryProfileTemplateToUpdate = dietaryProfileTemplateRepository.findByIdAndExpert_Account_LoginAndArchivedIsFalse(id, login).orElseThrow();
        List<DietaryProfileTemplatePackageType> packageTypeConstraints = new ArrayList<>();
        List<DietaryProfileTemplateNutritionalValue> nutritionalValueConstraints = new ArrayList<>();
        List<DietaryProfileTemplateRating> ratingConstraints = new ArrayList<>();
        dietaryProfileTemplateToUpdate.setName(updateDietaryProfileTemplateDTO.getName());
        dietaryProfileTemplateToUpdate.setDescription(updateDietaryProfileTemplateDTO.getDescription());
        for (CreateDietaryConstraintDTO constraintDTO : updateDietaryProfileTemplateDTO.getConstraints()) {
            switch (constraintDTO.getTarget()) {
                case DietaryConstraintTarget.PACKAGE_TYPE ->
                        packageTypeConstraints.add(createPackageTypeConstraintTemplate(constraintDTO.getWeight(), constraintDTO.getElementId(), dietaryProfileTemplateToUpdate));
                case DietaryConstraintTarget.NUTRITIONAL_VALUE ->
                        nutritionalValueConstraints.add(createNutritionalValueConstraintTemplate(constraintDTO.getWeight(), constraintDTO.getElementId(), dietaryProfileTemplateToUpdate));
                case DietaryConstraintTarget.RATING ->
                        ratingConstraints.add(createRatingConstraintTemplate(constraintDTO.getWeight(), constraintDTO.getElementId(), dietaryProfileTemplateToUpdate));
            }
        }

        dietaryProfileTemplateToUpdate.getPackageTypes().clear();
        dietaryProfileTemplateToUpdate.getPackageTypes().addAll(packageTypeConstraints);
        dietaryProfileTemplateToUpdate.getNutritionalValues().clear();
        dietaryProfileTemplateToUpdate.getNutritionalValues().addAll(nutritionalValueConstraints);
        dietaryProfileTemplateToUpdate.getRatings().clear();
        dietaryProfileTemplateToUpdate.getRatings().addAll(ratingConstraints);
        dietaryProfileTemplateRepository.save(dietaryProfileTemplateToUpdate);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    @PreAuthorize("hasRole('EXPERT')")
    public void deleteDietaryProfileTemplateExpertOwe(UUID id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        DietaryProfileTemplate dietaryProfileTemplateToBeArchived = dietaryProfileTemplateRepository.findByIdAndExpert_Account_LoginAndArchivedIsFalse(id, username).orElseThrow();
        dietaryProfileTemplateToBeArchived.setArchived(true);
        dietaryProfileTemplateRepository.save(dietaryProfileTemplateToBeArchived);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    @PreAuthorize("hasRole('CLIENT')")
    public void updateDietaryProfile(UUID id, UpdateDietaryProfileDTO updateDietaryProfileDTO) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        DietaryProfile dietaryProfileToUpdate = dietaryProfileRepository.findByIdAndClient_Account_LoginAndArchivedIsFalse(id, login).orElseThrow();
        List<DietaryProfilePackageType> packageTypeConstraints = new ArrayList<>();
        List<DietaryProfileNutritionalValue> nutritionalValueConstraints = new ArrayList<>();
        List<DietaryProfileRating> ratingConstraints = new ArrayList<>();
        dietaryProfileToUpdate.setName(updateDietaryProfileDTO.getName());
        dietaryProfileToUpdate.setDescription(updateDietaryProfileDTO.getDescription());
        for (CreateDietaryConstraintDTO constraintDTO : updateDietaryProfileDTO.getConstraints()) {
            switch (constraintDTO.getTarget()) {
                case DietaryConstraintTarget.PACKAGE_TYPE ->
                        packageTypeConstraints.add(createPackageTypeConstraint(constraintDTO.getWeight(), constraintDTO.getElementId(), dietaryProfileToUpdate));
                case DietaryConstraintTarget.NUTRITIONAL_VALUE ->
                        nutritionalValueConstraints.add(createNutritionalValueConstraint(constraintDTO.getWeight(), constraintDTO.getElementId(), dietaryProfileToUpdate));
                case DietaryConstraintTarget.RATING ->
                        ratingConstraints.add(createRatingConstraint(constraintDTO.getWeight(), constraintDTO.getElementId(), dietaryProfileToUpdate));
            }
        }
        dietaryProfileToUpdate.getPackageTypes().clear();
        dietaryProfileToUpdate.getPackageTypes().addAll(packageTypeConstraints);
        dietaryProfileToUpdate.getNutritionalValues().clear();
        dietaryProfileToUpdate.getNutritionalValues().addAll(nutritionalValueConstraints);
        dietaryProfileToUpdate.getRatings().clear();
        dietaryProfileToUpdate.getRatings().addAll(ratingConstraints);
        dietaryProfileRepository.save(dietaryProfileToUpdate);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    @PreAuthorize("hasRole('CLIENT')")
    public DietaryProfileDTO createDietaryProfile(DietaryProfile dietaryProfileToCreate, List<CreateDietaryConstraintDTO> constraintDTOs) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        ClientAccessLevel client = clientDataRepository.findByAccount_Login(login).orElseThrow(RuntimeException::new);
        dietaryProfileToCreate.setClient(client);
        for (CreateDietaryConstraintDTO constraintDTO : constraintDTOs) {
            switch (constraintDTO.getTarget()) {
                case DietaryConstraintTarget.PACKAGE_TYPE ->
                        dietaryProfileToCreate.getPackageTypes().add(createPackageTypeConstraint(constraintDTO.getWeight(), constraintDTO.getElementId(), dietaryProfileToCreate));
                case DietaryConstraintTarget.NUTRITIONAL_VALUE ->
                        dietaryProfileToCreate.getNutritionalValues().add(createNutritionalValueConstraint(constraintDTO.getWeight(), constraintDTO.getElementId(), dietaryProfileToCreate));
                case DietaryConstraintTarget.RATING ->
                        dietaryProfileToCreate.getRatings().add(createRatingConstraint(constraintDTO.getWeight(), constraintDTO.getElementId(), dietaryProfileToCreate));
            }
        }
        return DietaryProfileConverter.createDietaryProfileDTO(dietaryProfileRepository.save(dietaryProfileToCreate));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    @PreAuthorize("hasRole('CLIENT')")
    public DietaryProfileListDTO getAllActiveDietaryProfilesClientOwe() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return DietaryProfileConverter.createDietaryProfileListDTO(dietaryProfileRepository.findAllByArchivedIsFalseAndClient_Account_Login(login));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public DietaryProfileDTO getDietaryProfileClientOweById(UUID id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return DietaryProfileConverter.createDietaryProfileDTO(dietaryProfileRepository.findByIdAndClient_Account_LoginAndArchivedIsFalse(id, login).orElseThrow());
    }


    @PreAuthorize("hasRole('CLIENT')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public void deleteDietaryProfileClientOwe(UUID id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        DietaryProfile dietaryProfileToBeArchived = dietaryProfileRepository.findByIdAndClient_Account_LoginAndArchivedIsFalse(id, login).orElseThrow();
        dietaryProfileToBeArchived.setArchived(true);
        dietaryProfileRepository.saveAndFlush(dietaryProfileToBeArchived);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    @PreAuthorize("hasRole('CLIENT')")
    public ProductSummaryForClientDTO generateProductSummaryForCertainClient(UUID productId) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Product product = productRepository.findById(productId).orElseThrow();
        List<DietaryProfile> dietaryProfilesClientOwe = dietaryProfileRepository.findAllByArchivedIsFalseAndClient_Account_Login(login);
        for (DietaryProfile profile : dietaryProfilesClientOwe) {
            System.out.println(profile.getName());
        }
        return ProductConverter.createProductSummaryForClientDTO(product, dietaryProfilesClientOwe);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    @PreAuthorize("hasRole('CLIENT')")
    public ProductSummaryForClientDTO generateProductSummaryForCertainClientEAN(String ean) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Product product = productRepository.findByEan(ean).orElseThrow();
        List<DietaryProfile> dietaryProfilesClientOwe = dietaryProfileRepository.findAllByArchivedIsFalseAndClient_Account_Login(login);
        for (DietaryProfile profile : dietaryProfilesClientOwe) {
            System.out.println(profile.getName());
        }
        return ProductConverter.createProductSummaryForClientDTO(product, dietaryProfilesClientOwe);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    @PreAuthorize("permitAll()")
    public ProductSummaryForClientDTO generateProductWithoutSummary(UUID productId) {
        return new ProductSummaryForClientDTO(ProductConverter.createProductDTO(
                productRepository.findById(productId).orElseThrow()), new ArrayList<>()
        );
    }

    @PreAuthorize("hasRole('CLIENT')")
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "ffoodModuleTransactionManager")
    public ProductSummaryForClientDTO generateProductWithoutSummaryEAN(String ean) {
        return new ProductSummaryForClientDTO(ProductConverter.createProductDTO(
                productRepository.findByEan(ean).orElseThrow()), new ArrayList<>()
        );
    }
}
