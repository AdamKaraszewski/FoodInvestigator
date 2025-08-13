package pl.lodz.p.it.functionalfood.investigator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs.*;
import pl.lodz.p.it.functionalfood.investigator.dto.authenticationmoduleDTO.LoginDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryConstraintDTOs.CreateDietaryConstraintDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs.CreateDietaryProfileDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs.DietaryProfileDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs.DietaryProfileListDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileDTOs.UpdateDietaryProfileDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileTemplateDTOs.CreateDietaryProfileTemplateDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileTemplateDTOs.DietaryProfileTemplateDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.dietaryProfileTemplateDTOs.DietaryProfileTemplatesListDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.nutritionalvaluenameDTOs.AllNutritionalValueNamesListDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.packagetypeDTOs.AllPackageTypesOnListDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.producerDTOs.CreateProducerDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.producerDTOs.ProducerDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.producerDTOs.ProducerListDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.productDTOs.ProductWithSummaryListDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.ratingsDTOs.RatingListDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.ffoodModuleDTO.unitDTOs.UnitListDTO;
import pl.lodz.p.it.functionalfood.investigator.entities.Product;
import pl.lodz.p.it.functionalfood.investigator.enums.DietaryConstraintTarget;
import pl.lodz.p.it.functionalfood.investigator.enums.DietaryConstraintWeights;
import pl.lodz.p.it.functionalfood.investigator.exceptions.BaseException;
import pl.lodz.p.it.functionalfood.investigator.exceptions.exceptionsDTOs.IfMatchNullExceptionDTO;
import pl.lodz.p.it.functionalfood.investigator.exceptions.exceptionsDTOs.OptimisticLockExceptionDTO;
import pl.lodz.p.it.functionalfood.investigator.exceptions.exceptionsDTOs.PasswordMatchesExceptionDTO;
import pl.lodz.p.it.functionalfood.investigator.exceptions.exceptionsDTOs.UniqueNotFollowedExceptionDTO;
import pl.lodz.p.it.functionalfood.investigator.ffoodmodule.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class IntegrationTests {

    private String employeeJWT;
    private String adminJWT;
    private String expertJWT;
    private String clientJWT;

    @Autowired
    ProductRepository productRepository;

    private String obtainJWT(String username, String password) {
        LoginDTO loginDTO = new LoginDTO(username, password);
        return RestAssured
                .given()
                .contentType("application/json")
                .body(loginDTO)
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract()
                .asString();
    }

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("functionalfood")
            .withUsername("admin")
            .withPassword("admin")
            .withExposedPorts(5432) // Dodaj to
            .waitingFor(Wait.forListeningPort());

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.functional.food.database.url", () -> "jdbc:postgresql://localhost:5432/functionalfood");

        registry.add("spring.admin.datasource.url", () -> "jdbc:postgresql://localhost:5432/functionalfood");

        registry.add("spring.accountmodule.datasource.url", () -> "jdbc:postgresql://localhost:5432/functionalfood");

        registry.add("spring.ffoodmodule.datasource.url", () -> "jdbc:postgresql://localhost:5432/functionalfood");
    }

    @AfterAll
    static void afterAll() {
        postgres.stop(); // Zatrzymaj kontener po testach
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + postgres.getFirstMappedPort();
        employeeJWT = "Bearer " + obtainJWT("EMPLOYEE1", "password");
        adminJWT = "Bearer " + obtainJWT("ADMIN1", "password");
        expertJWT = "Bearer " + obtainJWT("EXPERT1", "password");
        clientJWT = "Bearer " + obtainJWT("CLIENT1", "password");
        List<Product> products = productRepository.findAllByEan("new_ean");
        productRepository.deleteAll(products);
    }

    @Test
    void shouldReturnAllAccounts() {
        assertTrue(true);
    }

    @Test
    void authenticationSuccessIntegrationTest() {
        LoginDTO loginDTO = new LoginDTO("EMPLOYEE1", "password");
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginDTO)
                .when()
                .post("/api/login")
                .then()
                .statusCode(200);
    }

    @Test
    void registerAccountTestSuccessIntegrationTest() {
        CreateClientDTO createClientDTO = new CreateClientDTO("NEW_ACCOUNT", "password", "firstName",
                "lastName", "testemail@example.com");
        LoginDTO loginDTO = new LoginDTO("NEW_ACCOUNT", "password");
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createClientDTO)
                .when()
                .post("/api/register-client-local")
                .then()
                .statusCode(201);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginDTO)
                .when()
                .post("/api/login")
                .then()
                .statusCode(200);
    }

    //.header("Authorization", "Bearer " + adminJWT)
    @Test
    void findAccountByIdSuccessIntegrationTest() {
        AccountDTO accountDTO = RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/accounts/00000000-0000-0000-0000-000000000009")
                .then()
                .statusCode(200)
                .extract()
                .as(AccountDTO.class);

        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000009"), accountDTO.getId());
        assertEquals("CLIENT6", accountDTO.getLogin());
        assertEquals("firstName1", accountDTO.getFirstName());
        assertEquals("lastName1", accountDTO.getLastName());
        assertEquals("i242415@edu.p.lodz.pl", accountDTO.getEmail());
        assertEquals("CLIENT", accountDTO.getAccessLevel());
    }

    @Test
    void findAccountByIdUnauthorizedIntegrationTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/accounts/00000000-0000-0000-0000-000000000009")
                .then()
                .statusCode(403);
    }

    @Test
    void findAccountByIdWhichDoesNotExistIntegrationTest() {
        RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/accounts/00000000-0000-0000-0000-000000000019")
                .then()
                .statusCode(404);
    }

    @Test
    void findMyAccountSuccessIntegrationTest() {
        AccountDetailsDTO accountDTO = RestAssured.given()
                .header("Authorization", expertJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/accounts/my")
                .then()
                .statusCode(200)
                .extract()
                .as(AccountDetailsDTO.class);
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000002"), accountDTO.getId());
        assertEquals("EXPERT1", accountDTO.getLogin());
        assertEquals("firstName1", accountDTO.getFirstName());
        assertEquals("lastName1", accountDTO.getLastName());
        assertEquals("b242415@edu.p.lodz.pl", accountDTO.getEmail());
        assertEquals("EXPERT", accountDTO.getAccessLevel());
    }

    @Test
    void findMyAccountNotAuthorizedTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/accounts/my")
                .then()
                .statusCode(403);
    }

    @Test
    void blockAccountSuccessIntegrationTest() {
        String ETAG = RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/accounts/00000000-0000-0000-0000-000000000010")
                .then()
                .statusCode(200)
                .extract()
                .header("ETag")
                .replaceAll("^\"|\"$", "");

        UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO(UUID.fromString("00000000-0000-0000-0000-000000000010"), false);
        RestAssured.given()
                .header("Authorization", adminJWT)
                .header("If-match", ETAG)
                .contentType(ContentType.JSON)
                .body(updateAccountDTO)
                .when()
                .put("/api/accounts")
                .then()
                .statusCode(200);
    }

    @Test
    void blockAccountNotAuthorizedTest() {
        String ETAG = RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/accounts/00000000-0000-0000-0000-000000000010")
                .then()
                .statusCode(200)
                .extract()
                .header("ETag")
                .replaceAll("^\"|\"$", "");

        UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO(UUID.fromString("00000000-0000-0000-0000-000000000010"), false);
        RestAssured.given()
                .header("Authorization", clientJWT)
                .header("If-match", ETAG)
                .contentType(ContentType.JSON)
                .body(updateAccountDTO)
                .when()
                .put("/api/accounts")
                .then()
                .statusCode(403);
    }

    @Test
    void blockAccountWithoutIfMatchFieldThrowsIfMatchNullException() {
        UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO(UUID.fromString("00000000-0000-0000-0000-000000000010"), false);
        IfMatchNullExceptionDTO ifneDTO = RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .body(updateAccountDTO)
                .when()
                .put("/api/accounts")
                .then()
                .statusCode(400)
                .extract()
                .as(IfMatchNullExceptionDTO.class);

        assertEquals("IfMatchNullError", ifneDTO.getErrorType());
    }

    @Test
    void blockAccountWhichDoesNotExistThrowsUserNotFoundException() {
        String ETAG = RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/accounts/00000000-0000-0000-0000-000000000010")
                .then()
                .statusCode(200)
                .extract()
                .header("ETag")
                .replaceAll("^\"|\"$", "");

        UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO(UUID.fromString("00000000-0000-0000-0000-000000000018"), false);
        String userNotFoundExceptionMessage = RestAssured.given()
                .header("Authorization", adminJWT)
                .header("If-match", ETAG)
                .contentType(ContentType.JSON)
                .body(updateAccountDTO)
                .when()
                .put("/api/accounts")
                .then()
                .statusCode(404)
                .extract()
                .asString();
        assertEquals("user not found", userNotFoundExceptionMessage);
    }

    @Test
    void blockAccountVersionIntegrityNotFollowedIntegrationTest() {
        UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO(UUID.fromString("00000000-0000-0000-0000-000000000010"), false);
        OptimisticLockExceptionDTO ole = RestAssured.given()
                .header("Authorization", adminJWT)
                .header("If-match", "invalidIfMatch")
                .contentType(ContentType.JSON)
                .body(updateAccountDTO)
                .when()
                .put("/api/accounts")
                .then()
                .statusCode(400)
                .extract()
                .as(OptimisticLockExceptionDTO.class);
        assertEquals("OptimisticLockError", ole.getErrorType());
    }

    @Test
    void changeMyAccountInfoTestSuccessIntegrationTest() {
        OwnAccountUpdateDTO accountUpdateDTO = new OwnAccountUpdateDTO("NewFirstName", "NewFirstName");
        String ETAG = RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/accounts/my")
                .then()
                .statusCode(200)
                .extract()
                .header("ETag")
                .replaceAll("^\"|\"$", "");

        RestAssured.given()
                .header("Authorization", clientJWT)
                .header("If-match", ETAG)
                .contentType(ContentType.JSON)
                .body(accountUpdateDTO)
                .when()
                .put("/api/accounts/my")
                .then()
                .statusCode(200);

        AccountDetailsDTO accountDTO = RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/accounts/my")
                .then()
                .statusCode(200)
                .extract()
                .as(AccountDetailsDTO.class);
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000001"), accountDTO.getId());
        assertEquals("CLIENT1", accountDTO.getLogin());
        assertEquals("NewFirstName", accountDTO.getFirstName());
        assertEquals("NewFirstName", accountDTO.getLastName());
        assertEquals("a242415@edu.p.lodz.pl", accountDTO.getEmail());
        assertEquals("CLIENT", accountDTO.getAccessLevel());
    }

    @Test
    void changeMyAccountInfoWithoutJWTTest() {
        OwnAccountUpdateDTO accountUpdateDTO = new OwnAccountUpdateDTO("NewFirstName", "NewFirstName");
        String ETAG = RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/accounts/my")
                .then()
                .statusCode(200)
                .extract()
                .header("ETag")
                .replaceAll("^\"|\"$", "");

        RestAssured.given()
                .header("If-match", ETAG)
                .contentType(ContentType.JSON)
                .body(accountUpdateDTO)
                .when()
                .put("/api/accounts/my")
                .then()
                .statusCode(403);
    }

    @Test
    void changeMyAccountInfoIfMatchNullException() {
        OwnAccountUpdateDTO accountUpdateDTO = new OwnAccountUpdateDTO("NewFirstName", "NewFirstName");
        IfMatchNullExceptionDTO ifne = RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .body(accountUpdateDTO)
                .when()
                .put("/api/accounts/my")
                .then()
                .statusCode(400)
                .extract()
                .as(IfMatchNullExceptionDTO.class);
        assertEquals("IfMatchNullError", ifne.getErrorType());
    }

    @Test
    void changeMyAccountInfoVersionIntegrationConstraintNotFollowed() {
        OwnAccountUpdateDTO accountUpdateDTO = new OwnAccountUpdateDTO("NewFirstName", "NewFirstName");
        OptimisticLockExceptionDTO ole = RestAssured.given()
                .header("Authorization", clientJWT)
                .header("If-match", "invalidIfMatch")
                .contentType(ContentType.JSON)
                .body(accountUpdateDTO)
                .when()
                .put("/api/accounts/my")
                .then()
                .statusCode(400)
                .extract()
                .as(OptimisticLockExceptionDTO.class);
        assertEquals("OptimisticLockError", ole.getErrorType());
    }

    @Test
    void findAllAccountsTestSuccessIntegrationTest() {
        AccountListDTO accountListDTO = RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/accounts")
                .then()
                .statusCode(200)
                .extract()
                .as(AccountListDTO.class);

        assertEquals(11, accountListDTO.getAccounts().size());
    }

    @Test
    void findAllAccountsUnauthorizedTest() {
        RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/accounts")
                .then()
                .statusCode(403);
    }

    @Test
    void createAdminAccountSuccessIntegrationTest() {
        CreateAdminDTO adminDTO = new CreateAdminDTO("NEW_ADMIN_ACCOUNT", "password",
                "newFirstName", "newLastName", "new_admin_account@wxample.com");
        RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .body(adminDTO)
                .when()
                .post("/api/accounts/admin")
                .then()
                .statusCode(201);

        String jwt = obtainJWT("NEW_ADMIN_ACCOUNT", "password");
        assertNotNull(jwt);
    }

    @Test
    void createAdminAccountUnauthorizedTest() {
        CreateAdminDTO adminDTO = new CreateAdminDTO("NEW_ADMIN_ACCOUNT", "password",
                "newFirstName", "newLastName", "new_admin_account@wxample.com");
        RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .body(adminDTO)
                .when()
                .post("/api/accounts/admin")
                .then()
                .statusCode(403);
    }

    @Test
    void createEmployeeAccountSuccessIntegrationTest() {
        CreateEmployeeDTO employeeDTO = new CreateEmployeeDTO("NEW_EMPLOYEE_ACCOUNT", "password",
                "newFirstName", "newLastName", "new_employee_account@wxample.com");
        RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .body(employeeDTO)
                .when()
                .post("/api/accounts/employee")
                .then()
                .statusCode(201);

        String jwt = obtainJWT("NEW_EMPLOYEE_ACCOUNT", "password");
        assertNotNull(jwt);
    }

    @Test
    void createEmployeeAccountUnauthorizedIntegrationTest() {
        CreateEmployeeDTO employeeDTO = new CreateEmployeeDTO("NEW_EMPLOYEE_ACCOUNT", "password",
                "newFirstName", "newLastName", "new_employee_account@wxample.com");
        RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .body(employeeDTO)
                .when()
                .post("/api/accounts/employee")
                .then()
                .statusCode(403);
    }

    @Test
    void createExpertAccountSuccessIntegrationTest() {
        CreateExpertDTO expertDTO = new CreateExpertDTO("NEW_EXPERT_ACCOUNT", "password",
                "newFirstName", "newLastName", "new_expert_account@wxample.com");
        RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .body(expertDTO)
                .when()
                .post("/api/accounts/expert")
                .then()
                .statusCode(201);

        String jwt = obtainJWT("NEW_EXPERT_ACCOUNT", "password");
        assertNotNull(jwt);
    }

    @Test
    void createExpertAccountUnauthorizedIntegrationTest() {
        CreateExpertDTO expertDTO = new CreateExpertDTO("NEW_EXPERT_ACCOUNT", "password",
                "newFirstName", "newLastName", "new_expert_account@wxample.com");
        RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .body(expertDTO)
                .when()
                .post("/api/accounts/expert")
                .then()
                .statusCode(403);
    }

    @Test
    void changePasswordSuccessIntegrationTest() {
        String jwt = "Bearer " + obtainJWT("CLIENT6", "password");
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("password", "newPassword");
        RestAssured.given()
                .header("Authorization", jwt)
                .contentType(ContentType.JSON)
                .body(changePasswordDTO)
                .when()
                .post("/api/accounts/my/change-password")
                .then()
                .statusCode(204);

        String jwtAfterChangePassword = obtainJWT("CLIENT6", "newPassword");
        assertNotNull(jwtAfterChangePassword);
    }

    @Test
    void createAdminUniqueLoginNotFollowedException() {
        CreateAdminDTO adminDTO = new CreateAdminDTO("ADMIN1", "password",
                "newFirstName", "newLastName", "new_admin_account@wxample.com");
        UniqueNotFollowedExceptionDTO unfe = RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .body(adminDTO)
                .when()
                .post("/api/accounts/admin")
                .then()
                .statusCode(400)
                .extract()
                .as(UniqueNotFollowedExceptionDTO.class);
        assertEquals("UniqueNotFollowedError", unfe.getErrorType());
        assertEquals("login", unfe.getNotFollowed());
    }

    @Test
    void createAdminUniqueEmailNotFollowedException() {
        CreateAdminDTO adminDTO = new CreateAdminDTO("NEW_ADMIN_ACCOUNT", "password",
                "newFirstName", "newLastName", "a242415@edu.p.lodz.pl");
        UniqueNotFollowedExceptionDTO unfe = RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .body(adminDTO)
                .when()
                .post("/api/accounts/admin")
                .then()
                .statusCode(400)
                .extract()
                .as(UniqueNotFollowedExceptionDTO.class);
        assertEquals("UniqueNotFollowedError", unfe.getErrorType());
        assertEquals("email", unfe.getNotFollowed());
    }

    @Test
    void createEmployeeUniqueLoginNotFollowedException() {
        CreateEmployeeDTO employeeDTO = new CreateEmployeeDTO("EMPLOYEE1", "password",
                "newFirstName", "newLastName", "new_employee_account@wxample.com");
        UniqueNotFollowedExceptionDTO unfe = RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .body(employeeDTO)
                .when()
                .post("/api/accounts/employee")
                .then()
                .statusCode(400)
                .extract()
                .as(UniqueNotFollowedExceptionDTO.class);
        assertEquals("UniqueNotFollowedError", unfe.getErrorType());
        assertEquals("login", unfe.getNotFollowed());
    }

    @Test
    void createEmployeeUniqueEmailNotFollowedException() {
        CreateEmployeeDTO employeeDTO = new CreateEmployeeDTO("NEW_EMPLOYEE_ACCOUNT", "password",
                "newFirstName", "newLastName", "a242415@edu.p.lodz.pl");
        UniqueNotFollowedExceptionDTO unfe = RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .body(employeeDTO)
                .when()
                .post("/api/accounts/employee")
                .then()
                .statusCode(400)
                .extract()
                .as(UniqueNotFollowedExceptionDTO.class);
        assertEquals("UniqueNotFollowedError", unfe.getErrorType());
        assertEquals("email", unfe.getNotFollowed());
    }

    @Test
    void createExpertUniqueLoginNotFollowedException() {
        CreateExpertDTO expertDTO = new CreateExpertDTO("EXPERT1", "password",
                "newFirstName", "newLastName", "new_expert_account@wxample.com");
        UniqueNotFollowedExceptionDTO unfe = RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .body(expertDTO)
                .when()
                .post("/api/accounts/expert")
                .then()
                .statusCode(400)
                .extract()
                .as(UniqueNotFollowedExceptionDTO.class);
        assertEquals("UniqueNotFollowedError", unfe.getErrorType());
        assertEquals("login", unfe.getNotFollowed());
    }

    @Test
    void createExpertUniqueEmailNotFollowedException() {
        CreateExpertDTO expertDTO = new CreateExpertDTO("NEW_EMPLOYEE_ACCOUNT", "password",
                "newFirstName", "newLastName", "a242415@edu.p.lodz.pl");
        UniqueNotFollowedExceptionDTO unfe = RestAssured.given()
                .header("Authorization", adminJWT)
                .contentType(ContentType.JSON)
                .body(expertDTO)
                .when()
                .post("/api/accounts/expert")
                .then()
                .statusCode(400)
                .extract()
                .as(UniqueNotFollowedExceptionDTO.class);
        assertEquals("UniqueNotFollowedError", unfe.getErrorType());
        assertEquals("email", unfe.getNotFollowed());
    }

    @Test
    void changePasswordDontMatchPasswordInDb() {
        String jwt = "Bearer " + obtainJWT("CLIENT6", "password");
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("xyz", "newPassword");
        BaseException pmex = RestAssured.given()
                .header("Authorization", jwt)
                .contentType(ContentType.JSON)
                .body(changePasswordDTO)
                .when()
                .post("/api/accounts/my/change-password")
                .then()
                .statusCode(400)
                .extract()
                .as(BaseException.class);
        assertEquals("OldPasswordDoesntMatchCurrentPasswordError", pmex.getErrorType());
    }

    @Test
    void changePasswordMachCurrentPasswordException() {
        String jwt = "Bearer " + obtainJWT("CLIENT6", "password");
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("password", "password");
        PasswordMatchesExceptionDTO pme = RestAssured.given()
                .header("Authorization", jwt)
                .contentType(ContentType.JSON)
                .body(changePasswordDTO)
                .when()
                .post("/api/accounts/my/change-password")
                .then()
                .statusCode(400)
                .extract()
                .as(PasswordMatchesExceptionDTO.class);
        assertEquals("PasswordMatchesError", pme.getErrorType());
    }

    @Test
    void changePasswordUnauthorizedIntegrationTest() {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("password", "password");
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(changePasswordDTO)
                .when()
                .post("/api/accounts/my/change-password")
                .then()
                .statusCode(403);
    }

    @Test
    void getAllUnitsSuccesIntegrationTest() {
        UnitListDTO unitListDTO = RestAssured.given()
                .header("Authorization", employeeJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/units/all")
                .then()
                .statusCode(200)
                .extract()
                .as(UnitListDTO.class);
        assertEquals(9, unitListDTO.getUnits().size());
    }

    @Test
    void getAllUnitUnauthorizedTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/units/all")
                .then()
                .statusCode(403);
    }

    @Test
    void getAllRatingIntegrationTest() {
        RatingListDTO ratingListDTO = RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/ratings/all")
                .then()
                .statusCode(200)
                .extract()
                .as(RatingListDTO.class);
        assertEquals(88, ratingListDTO.getRatingList().size());
    }

    @Test
    void getAllRatingUnauthorizedIntegrationTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/ratings/all")
                .then()
                .statusCode(403);
    }

    @Test
    void getAllNutritionalValueName() {
        AllNutritionalValueNamesListDTO nutritionalValueNamesListDTO = RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/nutritional-value-names/all")
                .then()
                .statusCode(200)
                .extract()
                .as(AllNutritionalValueNamesListDTO.class);
        assertEquals(75, nutritionalValueNamesListDTO.getNutritionalValues().size());
    }

    @Test
    void getAllNutritionalValueNameUnauthorizedTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/nutritional-value-names/all")
                .then()
                .statusCode(403);
    }

    @Test
    void getAllPackageTypesIntegrationTest() {
        AllPackageTypesOnListDTO packageTypesOnListDTO = RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/package-types/all")
                .then()
                .statusCode(200)
                .extract()
                .as(AllPackageTypesOnListDTO.class);
        assertEquals(38, packageTypesOnListDTO.getPackageTypes().size());
    }

    @Test
    void getAllPackageTypesUnauthorizedIntegrationTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/package-types/all")
                .then()
                .statusCode(403);
    }

    @Test
    void getAllProducersIntegrationTest() {
        ProducerListDTO producerListDTO = RestAssured.given()
                .header("Authorization", employeeJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/producers/all")
                .then()
                .statusCode(200)
                .extract()
                .as(ProducerListDTO.class);
        assertEquals(38, producerListDTO.getProducers().size());
    }

    @Test
    void getAllProducersUnauthorizedIntegrationTest() {
        RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/producers/all")
                .then()
                .statusCode(403);
    }

    @Test
    void getProducerIntegrationTest() {
        ProducerDTO producerDTO = RestAssured.given()
                .header("Authorization", employeeJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/producers/f7f17438-0098-452f-b7b6-317871a4707f")
                .then()
                .statusCode(200)
                .extract()
                .as(ProducerDTO.class);
        assertEquals(UUID.fromString("f7f17438-0098-452f-b7b6-317871a4707f"), producerDTO.getId());
        assertEquals("7331132897", producerDTO.getNip());
        assertEquals(1, producerDTO.getRmsd());
        assertEquals("95-061 Dmosin, Kałęczew 14", producerDTO.getAddress());
        assertEquals("soki@wiatrowysad.pl", producerDTO.getContact());
        assertEquals(590, producerDTO.getCountryCode());
        assertEquals("Wiatrowy Sad", producerDTO.getName());
    }

    @Test
    void getProducerUnauthorizedIntegrationTest() {
        RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/producers/f7f17438-0098-452f-b7b6-317871a4707f")
                .then()
                .statusCode(403);
    }

    @Test
    void getProducetWhichDoesNotExistIntegrationTest() {
        RestAssured.given()
                .header("Authorization", employeeJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/producers/f7f17438-0098-452f-b7b6-317871a47100")
                .then()
                .statusCode(403);
    }

    @Test
    void createProducerSuccessIntegrationTest() {
        CreateProducerDTO createProducerDTO = new CreateProducerDTO("test_name", "test_adres",
                60, "test_nip", 40, "test_producer_contact@example.com");
        RestAssured.given()
                .header("Authorization", employeeJWT)
                .contentType(ContentType.JSON)
                .body(createProducerDTO)
                .when()
                .post("/api/producers")
                .then()
                .statusCode(201);

        ProducerListDTO producerListDTO = RestAssured.given()
                .header("Authorization", employeeJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/producers/all")
                .then()
                .statusCode(200)
                .extract()
                .as(ProducerListDTO.class);
        assertEquals(39, producerListDTO.getProducers().size());
    }

    @Test
    void createProducerUnauthorizedIntegrationTest() {
        CreateProducerDTO createProducerDTO = new CreateProducerDTO("test_name", "test_adres",
                60, "test_nip", 40, "test_producer_contact@example.com");
        RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .body(createProducerDTO)
                .when()
                .post("/api/producers")
                .then()
                .statusCode(403);
    }

    @Test
    void createDietaryProfileUnauthorizedIntegrationTest() {
        List<CreateDietaryConstraintDTO> constraints = new ArrayList<>();
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("de04106d-7743-4495-b088-76a5dea450b0"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("f20ec95c-ed9c-4def-b527-78e3538415ee"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("4b2f29d1-09cc-467f-8196-bb97b177bbca"), DietaryConstraintTarget.NUTRITIONAL_VALUE));

        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("cb9c710c-e710-4b0f-8fc7-316b47b1fed5"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("3c765643-6e3d-4b1e-9f4d-d63f9dd9448a"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("de384ef6-6731-45d1-8ff1-e9db38555a7e"), DietaryConstraintTarget.PACKAGE_TYPE));

        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("33141901-31c9-4a68-9954-60ecc311f25b"), DietaryConstraintTarget.RATING));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("c3c735e0-0fed-4495-aa83-0c02e8a5c5d7"), DietaryConstraintTarget.RATING));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("e0dd1c33-a73e-4b41-bc22-19ed9fbbba0a"), DietaryConstraintTarget.RATING));
        CreateDietaryProfileDTO createDietaryProfileDTO = new CreateDietaryProfileDTO("test_name", "test_description", constraints);
        RestAssured.given()
                .header("Authorization", employeeJWT)
                .contentType(ContentType.JSON)
                .body(createDietaryProfileDTO)
                .when()
                .post("/api/dietary-profiles")
                .then()
                .statusCode(403);
    }

    @Test
    void getMyDietaryProfileUnauthorizedIntegrationTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/dietary-profiles/my")
                .then()
                .statusCode(403);
    }

    @Test
    void deleteMyDietaryProfileUnauthorized() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/dietary-profiles/my/" + UUID.fromString("4b2f29d1-09cc-467f-8196-bb97b177bbca"))
                .then()
                .statusCode(403);
    }

    @Test
    void updateDietaryProfileUnauthorizedTest() {
        List<CreateDietaryConstraintDTO> constraintsToUpdate = new ArrayList<>();
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("4b2f29d1-09cc-467f-8196-bb97b177bbca"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("6a4f9c69-e1b6-40e6-8739-18131620f70d"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("de04106d-7743-4495-b088-76a5dea450b0"), DietaryConstraintTarget.NUTRITIONAL_VALUE));

        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("de384ef6-6731-45d1-8ff1-e9db38555a7e"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("c2a6f544-28aa-4daf-a824-266c6fce9bf0"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("cb9c710c-e710-4b0f-8fc7-316b47b1fed5"), DietaryConstraintTarget.PACKAGE_TYPE));

        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("e0dd1c33-a73e-4b41-bc22-19ed9fbbba0a"), DietaryConstraintTarget.RATING));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("2c13c003-509c-4080-85ee-6256ed89d2db"), DietaryConstraintTarget.RATING));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("33141901-31c9-4a68-9954-60ecc311f25b"), DietaryConstraintTarget.RATING));
        UpdateDietaryProfileDTO updateDietaryProfileDTO = new UpdateDietaryProfileDTO("new_name", "new_description", constraintsToUpdate);
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateDietaryProfileDTO)
                .when()
                .put("/api/dietary-profiles/my/" + UUID.fromString("de04106d-7743-4495-b088-76a5dea450b0"))
                .then()
                .statusCode(403);
    }

    @Test
    void dietaryProfileIntegrationTest() throws JsonProcessingException {
        List<CreateDietaryConstraintDTO> constraints = new ArrayList<>();
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("de04106d-7743-4495-b088-76a5dea450b0"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("f20ec95c-ed9c-4def-b527-78e3538415ee"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("4b2f29d1-09cc-467f-8196-bb97b177bbca"), DietaryConstraintTarget.NUTRITIONAL_VALUE));

        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("cb9c710c-e710-4b0f-8fc7-316b47b1fed5"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("3c765643-6e3d-4b1e-9f4d-d63f9dd9448a"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("de384ef6-6731-45d1-8ff1-e9db38555a7e"), DietaryConstraintTarget.PACKAGE_TYPE));

        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("33141901-31c9-4a68-9954-60ecc311f25b"), DietaryConstraintTarget.RATING));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("c3c735e0-0fed-4495-aa83-0c02e8a5c5d7"), DietaryConstraintTarget.RATING));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("e0dd1c33-a73e-4b41-bc22-19ed9fbbba0a"), DietaryConstraintTarget.RATING));
        CreateDietaryProfileDTO createDietaryProfileDTO = new CreateDietaryProfileDTO("test_name", "test_description", constraints);
        RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .body(createDietaryProfileDTO)
                .when()
                .post("/api/dietary-profiles")
                .then()
                .statusCode(201);

        Response response = RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/dietary-profiles/my")
                .then()
                .statusCode(200)
                .extract()
                .response();


        ObjectMapper objectMapper = new ObjectMapper();
        DietaryProfileListDTO dietaryProfileList = objectMapper.readValue(response.getBody().asString(), DietaryProfileListDTO.class);
        assertEquals(1, dietaryProfileList.getDietaryProfiles().size());
        DietaryProfileDTO dietaryProfile = dietaryProfileList.getDietaryProfiles().get(0);
        assertEquals(9, dietaryProfile.getConstraints().size());
        assertEquals("test_name", dietaryProfile.getName());
        assertEquals("test_description", dietaryProfile.getDescription());

        List<CreateDietaryConstraintDTO> constraintsToUpdate = new ArrayList<>();
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("4b2f29d1-09cc-467f-8196-bb97b177bbca"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("6a4f9c69-e1b6-40e6-8739-18131620f70d"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("de04106d-7743-4495-b088-76a5dea450b0"), DietaryConstraintTarget.NUTRITIONAL_VALUE));

        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("de384ef6-6731-45d1-8ff1-e9db38555a7e"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("c2a6f544-28aa-4daf-a824-266c6fce9bf0"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("cb9c710c-e710-4b0f-8fc7-316b47b1fed5"), DietaryConstraintTarget.PACKAGE_TYPE));

        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("e0dd1c33-a73e-4b41-bc22-19ed9fbbba0a"), DietaryConstraintTarget.RATING));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("2c13c003-509c-4080-85ee-6256ed89d2db"), DietaryConstraintTarget.RATING));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("33141901-31c9-4a68-9954-60ecc311f25b"), DietaryConstraintTarget.RATING));
        UpdateDietaryProfileDTO updateDietaryProfileDTO = new UpdateDietaryProfileDTO("new_name", "new_description", constraintsToUpdate);
        RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .body(updateDietaryProfileDTO)
                .when()
                .put("/api/dietary-profiles/my/" + dietaryProfile.getId())
                .then()
                .statusCode(200);

        DietaryProfileDTO dietaryProfileAfterUpdate = RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/dietary-profiles/my/" + dietaryProfile.getId())
                .then()
                .statusCode(200)
                .extract()
                .as(DietaryProfileDTO.class);
        assertEquals(9, dietaryProfileAfterUpdate.getConstraints().size());
        assertEquals("new_name", dietaryProfileAfterUpdate.getName());
        assertEquals("new_description", dietaryProfileAfterUpdate.getDescription());

        RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/dietary-profiles/my/" + dietaryProfile.getId())
                .then()
                .statusCode(204);

        DietaryProfileListDTO listAfterDelete = RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/dietary-profiles/my")
                .then()
                .statusCode(200)
                .extract()
                .as(DietaryProfileListDTO.class);
        assertEquals(0, listAfterDelete.getDietaryProfiles().size());
    }

    @Test
    void createDietaryProfileTemplateUnauthorizedIntegrationTest() {
        List<CreateDietaryConstraintDTO> constraints = new ArrayList<>();
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("de04106d-7743-4495-b088-76a5dea450b0"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("f20ec95c-ed9c-4def-b527-78e3538415ee"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("4b2f29d1-09cc-467f-8196-bb97b177bbca"), DietaryConstraintTarget.NUTRITIONAL_VALUE));

        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("cb9c710c-e710-4b0f-8fc7-316b47b1fed5"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("3c765643-6e3d-4b1e-9f4d-d63f9dd9448a"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("de384ef6-6731-45d1-8ff1-e9db38555a7e"), DietaryConstraintTarget.PACKAGE_TYPE));

        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("33141901-31c9-4a68-9954-60ecc311f25b"), DietaryConstraintTarget.RATING));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("c3c735e0-0fed-4495-aa83-0c02e8a5c5d7"), DietaryConstraintTarget.RATING));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("e0dd1c33-a73e-4b41-bc22-19ed9fbbba0a"), DietaryConstraintTarget.RATING));
        CreateDietaryProfileTemplateDTO createDietaryProfileTemplateDTO = new CreateDietaryProfileTemplateDTO("test_name", "test_description", constraints);
        RestAssured.given()
                .header("Authorization", expertJWT)
                .contentType(ContentType.JSON)
                .body(createDietaryProfileTemplateDTO)
                .when()
                .post("/api/dietary-profile-templates/my")
                .then()
                .statusCode(201);
    }

    @Test
    void getAllMyDietaryProfileTemplateUnauthorizedIntegrationTest() {
        RestAssured.given()
                .header("Authorization", employeeJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/dietary-profile-templates/my")
                .then()
                .statusCode(403);
    }

    @Test
    void updateDietaryProfileUnauthorizedIntegrationTest() {
        List<CreateDietaryConstraintDTO> constraintsToUpdate = new ArrayList<>();
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("4b2f29d1-09cc-467f-8196-bb97b177bbca"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("6a4f9c69-e1b6-40e6-8739-18131620f70d"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("de04106d-7743-4495-b088-76a5dea450b0"), DietaryConstraintTarget.NUTRITIONAL_VALUE));

        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("de384ef6-6731-45d1-8ff1-e9db38555a7e"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("c2a6f544-28aa-4daf-a824-266c6fce9bf0"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("cb9c710c-e710-4b0f-8fc7-316b47b1fed5"), DietaryConstraintTarget.PACKAGE_TYPE));

        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("e0dd1c33-a73e-4b41-bc22-19ed9fbbba0a"), DietaryConstraintTarget.RATING));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("2c13c003-509c-4080-85ee-6256ed89d2db"), DietaryConstraintTarget.RATING));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("33141901-31c9-4a68-9954-60ecc311f25b"), DietaryConstraintTarget.RATING));
        UpdateDietaryProfileDTO updateDietaryProfileDTO = new UpdateDietaryProfileDTO("new_name", "new_description", constraintsToUpdate);
        RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .body(updateDietaryProfileDTO)
                .when()
                .put("/api/dietary-profile-templates/my/" + UUID.fromString("4b2f29d1-09cc-467f-8196-bb97b177bbca"))
                .then()
                .statusCode(403);
    }

    @Test
    void deleteDietaryProfileTemplateUnauthorizedIntegrationTest() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/dietary-profile-templates/my/" + UUID.fromString("e0dd1c33-a73e-4b41-bc22-19ed9fbbba0a"))
                .then()
                .statusCode(403);
    }

    @Test
    void dietaryProfileTemplateIntegrationTest() throws JsonProcessingException {
        List<CreateDietaryConstraintDTO> constraints = new ArrayList<>();
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("de04106d-7743-4495-b088-76a5dea450b0"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("f20ec95c-ed9c-4def-b527-78e3538415ee"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("4b2f29d1-09cc-467f-8196-bb97b177bbca"), DietaryConstraintTarget.NUTRITIONAL_VALUE));

        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("cb9c710c-e710-4b0f-8fc7-316b47b1fed5"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("3c765643-6e3d-4b1e-9f4d-d63f9dd9448a"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("de384ef6-6731-45d1-8ff1-e9db38555a7e"), DietaryConstraintTarget.PACKAGE_TYPE));

        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("33141901-31c9-4a68-9954-60ecc311f25b"), DietaryConstraintTarget.RATING));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("c3c735e0-0fed-4495-aa83-0c02e8a5c5d7"), DietaryConstraintTarget.RATING));
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("e0dd1c33-a73e-4b41-bc22-19ed9fbbba0a"), DietaryConstraintTarget.RATING));
        CreateDietaryProfileTemplateDTO createDietaryProfileTemplateDTO = new CreateDietaryProfileTemplateDTO("test_name", "test_description", constraints);
        RestAssured.given()
                .header("Authorization", expertJWT)
                .contentType(ContentType.JSON)
                .body(createDietaryProfileTemplateDTO)
                .when()
                .post("/api/dietary-profile-templates/my")
                .then()
                .statusCode(201);

        Response response = RestAssured.given()
                .header("Authorization", expertJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/dietary-profile-templates/my")
                .then()
                .statusCode(200)
                .extract()
                .response();


        ObjectMapper objectMapper = new ObjectMapper();
        DietaryProfileTemplatesListDTO dietaryProfileTemplateList = objectMapper.readValue(response.getBody().asString(), DietaryProfileTemplatesListDTO.class);
        assertEquals(1, dietaryProfileTemplateList.getDietaryProfiles().size());
        DietaryProfileTemplateDTO dietaryProfileTemplate = dietaryProfileTemplateList.getDietaryProfiles().get(0);
        assertEquals(9, dietaryProfileTemplate.getConstraints().size());
        assertEquals("test_name", dietaryProfileTemplate.getName());
        assertEquals("test_description", dietaryProfileTemplate.getDescription());

        List<CreateDietaryConstraintDTO> constraintsToUpdate = new ArrayList<>();
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("4b2f29d1-09cc-467f-8196-bb97b177bbca"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("6a4f9c69-e1b6-40e6-8739-18131620f70d"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("de04106d-7743-4495-b088-76a5dea450b0"), DietaryConstraintTarget.NUTRITIONAL_VALUE));

        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("de384ef6-6731-45d1-8ff1-e9db38555a7e"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("c2a6f544-28aa-4daf-a824-266c6fce9bf0"), DietaryConstraintTarget.PACKAGE_TYPE));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("cb9c710c-e710-4b0f-8fc7-316b47b1fed5"), DietaryConstraintTarget.PACKAGE_TYPE));

        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("e0dd1c33-a73e-4b41-bc22-19ed9fbbba0a"), DietaryConstraintTarget.RATING));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.NICE_TO_SEE, UUID.fromString("2c13c003-509c-4080-85ee-6256ed89d2db"), DietaryConstraintTarget.RATING));
        constraintsToUpdate.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.REQUIRED, UUID.fromString("33141901-31c9-4a68-9954-60ecc311f25b"), DietaryConstraintTarget.RATING));
        UpdateDietaryProfileDTO updateDietaryProfileDTO = new UpdateDietaryProfileDTO("new_name", "new_description", constraintsToUpdate);
        RestAssured.given()
                .header("Authorization", expertJWT)
                .contentType(ContentType.JSON)
                .body(updateDietaryProfileDTO)
                .when()
                .put("/api/dietary-profile-templates/my/" + dietaryProfileTemplate.getId())
                .then()
                .statusCode(200);

        DietaryProfileDTO dietaryProfileTemplateAfterUpdate = RestAssured.given()
                .header("Authorization", expertJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/dietary-profile-templates/my/" + dietaryProfileTemplate.getId())
                .then()
                .statusCode(200)
                .extract()
                .as(DietaryProfileDTO.class);
        assertEquals(9, dietaryProfileTemplateAfterUpdate.getConstraints().size());
        assertEquals("new_name", dietaryProfileTemplateAfterUpdate.getName());
        assertEquals("new_description", dietaryProfileTemplateAfterUpdate.getDescription());

        DietaryProfileTemplatesListDTO clientDietaryProfileTemplatesList = RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/dietary-profile-templates")
                .then()
                .statusCode(200)
                .extract()
                .as(DietaryProfileTemplatesListDTO.class);
        assertEquals(1, clientDietaryProfileTemplatesList.getDietaryProfiles().size());
        assertEquals(9, clientDietaryProfileTemplatesList.getDietaryProfiles().get(0).getConstraints().size());
        assertEquals("new_name", clientDietaryProfileTemplatesList.getDietaryProfiles().get(0).getName());
        assertEquals("new_description", clientDietaryProfileTemplatesList.getDietaryProfiles().get(0).getDescription());

        RestAssured.given()
                .header("Authorization", expertJWT)
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/dietary-profile-templates/my/" + dietaryProfileTemplate.getId())
                .then()
                .statusCode(204);

        DietaryProfileListDTO listAfterDelete = RestAssured.given()
                .header("Authorization", expertJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/dietary-profile-templates/my")
                .then()
                .statusCode(200)
                .extract()
                .as(DietaryProfileListDTO.class);
        assertEquals(0, listAfterDelete.getDietaryProfiles().size());
    }

    @Test
    void getAllProductWithoutPaginationController() {
        ProductWithSummaryListDTO productList = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductWithSummaryListDTO.class);
        assertEquals(135, productList.getProducts().size());
    }

    @Test
    void getAllProductsWithSummaryUnauthorizedIntegrationTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/products/summary/" + UUID.randomUUID())
                .then()
                .statusCode(403);
    }

    @Test
    void getAllProductsWithSummary() {
        List<CreateDietaryConstraintDTO> constraints = new ArrayList<>();
        constraints.add(new CreateDietaryConstraintDTO(DietaryConstraintWeights.BANNED, UUID.fromString("de04106d-7743-4495-b088-76a5dea450b0"), DietaryConstraintTarget.NUTRITIONAL_VALUE));
        CreateDietaryProfileDTO createDietaryProfileDTO = new CreateDietaryProfileDTO("test_name", "test_description", constraints);
        RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .body(createDietaryProfileDTO)
                .when()
                .post("/api/dietary-profiles")
                .then()
                .statusCode(201);

        UUID profileId = RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/dietary-profiles/my")
                .then()
                .statusCode(200)
                .extract()
                .as(DietaryProfileListDTO.class)
                .getDietaryProfiles()
                .get(0)
                .getId();

        ProductWithSummaryListDTO summaryListDTO = RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/products/summary/" + profileId)
                .then()
                .statusCode(200)
                .extract()
                .as(ProductWithSummaryListDTO.class);

        assertEquals(135, summaryListDTO.getProducts().size());
    }

    @Test
    void creatProductTest() {
        String requestBody = """
                {
                  "EAN": "new_ean",
                  "additions": ["new_addition1", "new_addition2"],
                  "allergens": "new_alergen, new_alergen_2",
                  "country": "new_country",
                  "durability": "new_duravility",
                  "flavour": "ne_aroma_1, new_aroma_2",
                  "img": null,
                  "ingredients": ["new_ingredient1", "new_ingredient2"],
                  "instructionsAfterOpening": "new_after_opening",
                  "nutritionalIndexes": [
                    { "legend": "new_index", "indexValue": 1 }
                  ],
                  "nutritionalValues": [],
                  "packageTypeId": "cb9c710c-e710-4b0f-8fc7-316b47b1fed5",
                  "portionQuantity": 1,
                  "portionUnitId": "54a850b7-ad94-4ffa-848e-49dd90789b8f",
                  "preparation": "new_preparation",
                  "producerId": "1b0f1a5b-76ed-41f2-8eac-b0a8b2b84d4c",
                  "productDescription": "new_description",
                  "productIndexes": [
                    { "indexValue": 1, "indexName": "A" }
                  ],
                  "productName": "new_name",
                  "productQuantity": 100,
                  "storage": "new_storage",
                  "unitId": "54a850b7-ad94-4ffa-848e-49dd90789b8f"
                }
                """;

        RestAssured.given()
                .header("Authorization", employeeJWT)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/products")
                .then()
                .statusCode(200);
    }

    @Test
    void createProductUnauthorizedIntegrationTest() {
        String requestBody = """
                {
                  "EAN": "new_ean",
                  "additions": ["new_addition1", "new_addition2"],
                  "allergens": "new_alergen, new_alergen_2",
                  "country": "new_country",
                  "durability": "new_duravility",
                  "flavour": "ne_aroma_1, new_aroma_2",
                  "img": null,
                  "ingredients": ["new_ingredient1", "new_ingredient2"],
                  "instructionsAfterOpening": "new_after_opening",
                  "nutritionalIndexes": [
                    { "legend": "new_index", "indexValue": 1 }
                  ],
                  "nutritionalValues": [],
                  "packageTypeId": "cb9c710c-e710-4b0f-8fc7-316b47b1fed5",
                  "portionQuantity": 1,
                  "portionUnitId": "54a850b7-ad94-4ffa-848e-49dd90789b8f",
                  "preparation": "new_preparation",
                  "producerId": "1b0f1a5b-76ed-41f2-8eac-b0a8b2b84d4c",
                  "productDescription": "new_description",
                  "productIndexes": [
                    { "indexValue": 1, "indexName": "A" }
                  ],
                  "productName": "new_name",
                  "productQuantity": 100,
                  "storage": "new_storage",
                  "unitId": "54a850b7-ad94-4ffa-848e-49dd90789b8f"
                }
                """;

        RestAssured.given()
                .header("Authorization", clientJWT)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/products")
                .then()
                .statusCode(403);
    }
}
