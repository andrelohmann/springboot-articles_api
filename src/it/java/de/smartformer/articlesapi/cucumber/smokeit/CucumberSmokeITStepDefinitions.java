package de.smartformer.articlesapi.cucumber.smokeit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.awaitility.Awaitility;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.awaitility.pollinterval.FibonacciPollInterval.fibonacci;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

//import com.adyen.mirakl.connector.cucumber.commonsit.ConnectorITHttpClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CucumberSmokeITStepDefinitions {

    private final Logger log = LoggerFactory.getLogger(CucumberSmokeITStepDefinitions.class);

    @Value("${test.endpoint}")
    private String endpointUrl;

    @Value("${logging.file.name}")
    private String logFile;

    private HttpHeaders headers;

    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    @Before
    public void setup() throws IOException {
        Awaitility.setDefaultTimeout(Duration.ofMinutes(5));
        new FileOutputStream(this.logFile).close();
    }

    // https://www.softwaretestinghelp.com/rest-api-testing-with-bdd-cucumber/
    @Given("I set the endpoint url")
    public void setEndpoint() {
        log.info(this.endpointUrl);
    }

    @When("^I set request HEADER$")
    public void setRequestHeader() {
        headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        //headers.add("Content-Type", "application/json");
        log.info(this.headers.toString());
    }

    @And("^I set the RestTemplate$")
    public void setAuthenticatedRestTemplate() {

        restTemplate = new TestRestTemplate();

        log.info("RestTemplate");
    }

    @And("^I send a GET HTTP request$")
    public void sendGetRequest() throws IOException {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        response = restTemplate.exchange(
                this.endpointUrl,
                HttpMethod.GET,
                entity,
                String.class);

        log.info("send a GET HTTP request");

        log.info(response.getBody());

        log.info("Pausing for 1 second now, to allow email to be send");
        try {
            Thread.sleep(1000);                 //1500 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        log.info("Wake up again");
    }

    @And("^I parsed the regarding log message$")
    public void parseLogMessage() {
        log.info("Parse the log message");
    }

    @Then("^I receive an empty articles list response$")
    public void receiveEmptyResponse() {
        log.info("ReceiveEmptyResponse");
    }

    @When("^Send a POST HTTP request$")
    public void sendPostRequest() throws IOException {

        HttpEntity<String> entity = new HttpEntity<String>(new String(Files.readAllBytes(Paths.get("src/it/resources/ACCOUNT_HOLDER_VERIFICATION_AWAITING_DATA.json"))), headers);

        response = restTemplate.exchange(
                this.endpointUrl,
                HttpMethod.POST,
                entity,
                String.class);

        log.info("send a POST HTTP request");

        log.info(response.getBody());

        log.info("Pausing for 1 second now, to allow email to be send");
        try {
            Thread.sleep(1000);                 //1500 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        log.info("Wake up again");
    }

    @When("^I receive valid response$")
    public void validPostResponse() throws JSONException {

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        assertTrue(actual.contains("/api/adyen-notifications/"));

        String expected = "{\n" +
                "    \"notificationResponse\": \"[accepted]\"\n" +
                "}";

        JSONAssert.assertEquals(expected, response.getBody(), false);

        // Log Test Message
        log.info("I receive valid response");
    }

    @Then("^I also receive Awaiting ID data Email$")
    public void receiveAwaitingEmail() throws JSONException {

        String searchUrl = "http://localhost:2020/api/v2/search?kind=containing&query=We+are+currently+awaiting+identity+verification+data.";
        String deleteUrl = "http://localhost:2020/api/v1/messages";

        await().with().pollInterval(fibonacci()).atMost(10, SECONDS).untilAsserted(() -> {

            TestRestTemplate testRestTemplate = new TestRestTemplate();

            ResponseEntity<String> response = testRestTemplate.getForEntity(searchUrl, String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            ArrayNode arrayNode = (ArrayNode) root.get("items");

            // Mail should have only one message
            assertThat(arrayNode.size()).isEqualTo(1);

            JsonNode message = arrayNode.get(0);

            JsonNode mailId = message.get("ID");

            JsonNode mailSubject = message.get("Content").get("Headers").get("Subject").get(0);

            assertThat(arrayNode.size()).isEqualTo(1);

            assertThat(mailSubject.textValue()).isEqualTo("Awaiting ID data");

            log.info("Pausing for 1 second now, to allow email to be deleted");
            try {
                Thread.sleep(1000);                 //1500 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            log.info("Wake up again");
            HttpEntity<String> entity = new HttpEntity<>("", headers);
            response = testRestTemplate.exchange(deleteUrl, HttpMethod.DELETE, entity, String.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            log.info("Awaiting ID Data Email received");

        });
    }

    @When("^I receive invalid response$")
    public void invalidPostResponse() throws JSONException {

        String expected = "{" +
                "    \"timestamp\": \"2020-09-25T23:56:44.567+00:00\",\n" +
                "    \"status\": 401,\n" +
                "    \"error\": \"Unauthorized\",\n" +
                "    \"message\": \"\",\n" +
                "    \"path\": \"/api/adyen-notifications/\"\n" +
                "}";

        log.info(response.getBody());

        JSONAssert.assertEquals(
                expected,
                response.getBody(),
                new CustomComparator(
                        JSONCompareMode.LENIENT,
                        new Customization("timestamp", (o1, o2) -> true)
                )
        );

        // Log Test Message
        log.info("I receive invalid response");
    }

}
