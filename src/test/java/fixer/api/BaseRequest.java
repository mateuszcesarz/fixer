package fixer.api;

import fixer.url.FixerUrl;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class BaseRequest {

    public RequestSpecification requestSetup(Map<String, String> headers) {

        return new RequestSpecBuilder()
                .setBaseUri(FixerUrl.BASE_URL)
                .setContentType(ContentType.JSON)
                .addHeaders(headers)
                .build();
    }
}
