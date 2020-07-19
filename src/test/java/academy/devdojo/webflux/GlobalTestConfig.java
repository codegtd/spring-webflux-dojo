package academy.devdojo.webflux;

//REST-ASSURE: MODULOS COMUNS
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

//ELEMENTOS REATIVOS EM GERAL
import io.restassured.module.webtestclient.RestAssuredWebTestClient.*;
import io.restassured.module.webtestclient.matcher.RestAssuredWebTestClientMatchers.*;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import reactor.blockhound.BlockHound;

//LOMBOK
import lombok.extern.slf4j.Slf4j;

//JUNIT: MODULOS
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;

//SPRING: MODULOS
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import static org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

//------CONFLITO---------------------------
//--CONFLITANTES C/ @RUNWITH-----------
@Sql(value = "/data-mass-load.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/data-mass-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WebFluxTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//--PIVOT-CONFLITO --------------------
//@RunWith(SpringRunner.class)
//------DB --------------------
//--R2DBC----------------------
@DataR2dbcTest
//--JPA-CONFLITO GERAL---------------------
//@DataJpaTest
//--DBCONFIG-CONFLITO GERA----------------
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//-------------------------
@Slf4j
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Ignore
@ActiveProfiles("test")
public class GlobalTestConfig {

    private static String baseUri = "http://restapi.wcaquino.me:80";

    private static Long MAX_TIMEOUT = 15000L;

    private static ContentType API_CONTENT_TYPE = ContentType.JSON;

    //    @LocalServerPort
    private static int port = 8080;

    @BeforeClass
    public static void setUp() {

        log.info("\n ENTROU - GLOBAL \n");

        RestAssured.baseURI = baseUri;
        RestAssured.port = port;

        //substitue os ".log().And()." em todos os REstAssureTestes
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        //DEFINE CONFIG-GLOBAL PARA OS REQUESTS DOS TESTES
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setContentType(API_CONTENT_TYPE);
        RestAssured.requestSpecification = reqBuilder.build();

        //DEFINE CONFIG-GLOBAL PARA OS RESPONSE DOS TESTES
        ResponseSpecBuilder respBuilder = new ResponseSpecBuilder();
        respBuilder.expectResponseTime(Matchers.lessThanOrEqualTo(MAX_TIMEOUT));
        RestAssured.responseSpecification = respBuilder.build();

        log.info("BlockHound.install() - OK");
        BlockHound.install();

    }

    @AfterClass
    public static void tearDown() {

        log.info("\n SAIU - GLOBAL \n");

//        DELETE AO TOKEN AFTER ALL TESTS
//        FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
//        req.removeHeader("Autorization");

        RestAssured.reset();
    }

}




