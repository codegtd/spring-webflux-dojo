package academy.devdojo.webflux.utils;

import io.restassured.http.Header;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;

import static java.nio.charset.StandardCharsets.UTF_8;

@Getter
@RequiredArgsConstructor
public class RoleUsersHeaders {

    public static Header role_admin_header_plain = new Header(
            "Authorization",
            "Basic paulo:devdojo");

    public static Header role_admin_header = new Header(
            "Authorization",
            "Basic " + Base64Utils
                    .encodeToString(("paulo" + ":" + "devdojo").getBytes(UTF_8)));

    public static Header role_user_header = new Header(
            "Authorization",
            "Basic " + Base64Utils
                    .encodeToString(("demetria" + ":" + "devdojo").getBytes(UTF_8)));

    public static Header role_invalid_header = new Header(
            "Authorization",
            "Basic " + Base64Utils
                    .encodeToString(("x" + ":" + "devdojo").getBytes(UTF_8)));

}
