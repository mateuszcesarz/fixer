package fixer.handlers.api;

import io.restassured.response.Response;
import lombok.Data;

@Data
public class ResponseHandler {

    private Response response;

    public int getStatusCode() {
        return response.getStatusCode();
    }
}
