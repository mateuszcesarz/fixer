package fixer.api;

import fixer.handlers.api.RequestHandler;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import static io.restassured.RestAssured.given;

@RequiredArgsConstructor
public class ReadRequest {

    private final BaseRequest baseRequest;

    public Response read(RequestHandler requestHandler) {

        return given()
                .spec(baseRequest.requestSetup(requestHandler.getHeaders()))
                .queryParams(requestHandler.getQueryParams())
                .when()
                .get(requestHandler.getEndpoint())
                .then()
                .log().all()
                .extract()
                .response();
    }
}
