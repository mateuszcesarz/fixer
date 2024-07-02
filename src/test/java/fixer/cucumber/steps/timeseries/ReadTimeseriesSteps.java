package fixer.cucumber.steps.timeseries;

import fixer.api.ReadRequest;
import fixer.handlers.api.RequestHandler;
import fixer.handlers.api.ResponseHandler;
import fixer.url.FixerUrl;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class ReadTimeseriesSteps {

    private final RequestHandler requestHandler;
    private final ReadRequest readRequest;
    private final ResponseHandler responseHandler;

    @When("I request historical exchange rates data between two periods of time")
    public void i_request_historical_exchange_rates_data_between_two_dates() {
        LocalDate fiveDays = LocalDate.now().minusDays(5);
        String fiveDaysAgo = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(fiveDays);
        LocalDate twoDays = LocalDate.now().minusDays(2);
        String twoDaysAgo = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(twoDays);
        responseHandler.setResponse(readTimeseries(fiveDaysAgo, twoDaysAgo));
    }

    @When("I request historical exchange rates data for a non-existing symbol {string}")
    public void iRequestHistoricalExchangeRatesDataForANonExistingSymbol(String symbol) {
        LocalDate fiveDays = LocalDate.now().minusDays(5);
        String fiveDaysAgo = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(fiveDays);
        LocalDate twoDays = LocalDate.now().minusDays(2);
        String twoDaysAgo = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(twoDays);
        responseHandler.setResponse(readTimeseries(fiveDaysAgo, twoDaysAgo, symbol));
    }

    @When("I request historical exchange rates data for a non-existing base {string}")
    public void iRequestHistoricalExchangeRatesDataForANonExistingBase(String base) {
        responseHandler.setResponse(readTimeseries(base));
    }

    @When("I request daily historical rates between {string} and {string}")
    public void i_request_daily_historical_rates(String startDate, String endDate) {
        responseHandler.setResponse(readTimeseries(startDate, endDate));
    }

    @Then("the API should respond with status code {int}")
    public void the_api_should_respond_with_status_code(Integer expectedStatusCode) {
        Assertions.assertThat(responseHandler.getStatusCode()).isEqualTo(expectedStatusCode);
    }

    @Then("the API should respond with status {string}")
    public void the_api_should_respond_with_status(String isSuccess) {
        JsonPath jsonPath = responseHandler.getResponse().getBody().jsonPath();
        Assertions.assertThat(jsonPath.getBoolean("success")).isEqualTo(Boolean.valueOf(isSuccess));
    }

    @Then("the API should respond with an error code {int}")
    public void theAPIShouldRespondWithAnErrorCodeInt(Integer errorCode) {
        JsonPath jsonPath = responseHandler.getResponse().getBody().jsonPath();
        Assertions.assertThat(jsonPath.getBoolean("success")).isEqualTo(false);
        Assertions.assertThat(Integer.parseInt(jsonPath.getString("error.code"))).isEqualTo(errorCode);
    }

    @Then("the API should validate against invalid symbols")
    public void theAPIShouldValidateAgainstInvalidSymbols() {
        JsonPath jsonPath = responseHandler.getResponse().getBody().jsonPath();
        Assertions.assertThat(Integer.parseInt(jsonPath.getString("error.code"))).isEqualTo(HttpStatus.SC_ACCEPTED);
        Assertions.assertThat(jsonPath.getString("error.type")).contains("invalid_currency_codes");
        Assertions.assertThat(jsonPath.getString("error.info")).contains("You have provided one or more invalid Currency Codes");
    }

    @Then("the API should validate against invalid base")
    public void theAPIShouldValidateAgainstInvalidBase() {
        JsonPath jsonPath = responseHandler.getResponse().getBody().jsonPath();
        Assertions.assertThat(Integer.parseInt(jsonPath.getString("error.code"))).isEqualTo(HttpStatus.SC_CREATED);
        Assertions.assertThat(jsonPath.getString("error.type")).contains("invalid_base_currency");
    }

    @Then("the response should contain exchange rates data for the specified period")
    public void the_response_should_contain_exchange_rates_data_for_the_specified_period() {
        JsonPath jsonPath = responseHandler.getResponse().getBody().jsonPath();

        Assertions.assertThat(jsonPath
                        .getMap("rates"))
                        .containsKeys(requestHandler.getQueryParams().get("start_date"));

        Assertions.assertThat(jsonPath
                        .getMap("rates." + requestHandler.getQueryParams().get("start_date")))
                        .containsKeys("EUR", "USD", "PLN");

        Assertions.assertThat(jsonPath
                        .getMap("rates"))
                        .containsKeys(requestHandler.getQueryParams().get("start_date"));

        Assertions.assertThat(jsonPath
                        .getMap("rates." + requestHandler.getQueryParams().get("end_date")))
                        .containsKeys("EUR", "USD", "PLN");
    }

    @When("I request historical exchange rate s data without providing start_date")
    public void iRequestHistoricalExchangeRateSDataWithoutProvidingStart_date() {
        LocalDate twoDays = LocalDate.now().minusDays(2);
        String twoDaysAgo = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(twoDays);
        responseHandler.setResponse(readTimeseries("", twoDaysAgo));
    }

    @When("I request historical exchange rate s data without providing end_date")
    public void iRequestHistoricalExchangeRateSDataWithoutProvidingEnd_date() {
        LocalDate twoDays = LocalDate.now().minusDays(2);
        String twoDaysAgo = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(twoDays);
        responseHandler.setResponse(readTimeseries(twoDaysAgo, ""));
    }

    private Response readTimeseries(String startDate, String endDate) {
        requestHandler.setEndpoint(FixerUrl.TIMESERIES);
        requestHandler.addQueryParam("start_date", startDate);
        requestHandler.addQueryParam("end_date", endDate);
        requestHandler.addQueryParam("symbols", "usd,eur,pln");
        return readRequest.read(requestHandler);
    }

    private Response readTimeseries(String startDate, String endDate, String symbol) {
        requestHandler.setEndpoint(FixerUrl.TIMESERIES);
        requestHandler.addQueryParam("start_date", startDate);
        requestHandler.addQueryParam("end_date", endDate);
        requestHandler.addQueryParam("symbols", symbol);
        return readRequest.read(requestHandler);
    }

    private Response readTimeseries(String base) {
        LocalDate fiveDays = LocalDate.now().minusDays(5);
        String fiveDaysAgo = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(fiveDays);
        LocalDate twoDays = LocalDate.now().minusDays(2);
        String twoDaysAgo = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(twoDays);
        requestHandler.setEndpoint(FixerUrl.TIMESERIES);
        requestHandler.addQueryParam("start_date", fiveDaysAgo);
        requestHandler.addQueryParam("end_date", twoDaysAgo);
        requestHandler.addQueryParam("base", base);
        return readRequest.read(requestHandler);
    }
}
