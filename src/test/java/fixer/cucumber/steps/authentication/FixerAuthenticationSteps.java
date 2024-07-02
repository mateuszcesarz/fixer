package fixer.cucumber.steps.authentication;

import fixer.handlers.api.RequestHandler;
import fixer.handlers.fixer.FixerAuthentication;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FixerAuthenticationSteps {

    private final FixerAuthentication fixerAuthentication;
    private final RequestHandler requestHandler;

    @Given("I am authenticated to Fixer")
    public void i_am_authenticated_to_fixer() {
        requestHandler.addHeader("apikey", fixerAuthentication.getKey());
    }

    @Given("I am not authenticated to Fixer")
    public void i_am_not_authenticated_to_fixer() {
        requestHandler.addHeader("apikey", "");
    }

    @Given("I use depleted apikey")
    public void i_use_depleted_apikey() {
        requestHandler.addHeader("apikey", "7cWomY2ZlcsDIhvCg9VxEoXyxDjCluDn");
    }
}
