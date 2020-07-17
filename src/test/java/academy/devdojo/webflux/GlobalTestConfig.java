package academy.devdojo.webflux;

//import io.restassured.RestAssured;
//import io.restassured.builder.ResponseSpecBuilder;
//import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
//import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import reactor.blockhound.BlockHound;

import static org.springframework.test.annotation.DirtiesContext.ClassMode;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(value = "/data-mass-load.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/data-mass-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//@TestPropertySource("classpath:config-test.properties")
@TestPropertySource("classpath:application-prod.properties")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Ignore
@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@RunWith(SpringRunner.class)
//@DataJpaTest
@WebFluxTest
public class GlobalTestConfig {

    private static String baseUri = "http://restapi.wcaquino.me:80";

    private static Long MAX_TIMEOUT = 15000L;

//    private static ContentType API_CONTENT_TYPE = ContentType.JSON;

//    @LocalServerPort
    private static int port = 8080;

    @BeforeClass
    public static void beforeClass() {

        log.info("\n ENTROU - GLOBAL \n");

//        RestAssured.baseURI = baseUri;
//        RestAssured.port = port;

        //substitue os ".log().And()." em todos os REstAssureTestes
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//
//        //DEFINE CONFIG-GLOBAL PARA OS REQUESTS DOS TESTES
//        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
//        reqBuilder.setContentType(API_CONTENT_TYPE);
//        RestAssured.requestSpecification = reqBuilder.build();

//        //DEFINE CONFIG-GLOBAL PARA OS RESPONSE DOS TESTES
//        ResponseSpecBuilder respBuilder = new ResponseSpecBuilder();
//        respBuilder.expectResponseTime(Matchers.lessThanOrEqualTo(MAX_TIMEOUT));
//        RestAssured.responseSpecification = respBuilder.build();

        log.info("BlockHound.install() - OK");
        BlockHound.install();

    }

    @AfterClass
    public static void afterClass() {

        log.info("\n SAIU - GLOBAL \n");

//        DELETE AO TOKEN AFTER ALL TESTS
//        FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
//        req.removeHeader("Autorization");

//        RestAssured.reset();
    }

}
