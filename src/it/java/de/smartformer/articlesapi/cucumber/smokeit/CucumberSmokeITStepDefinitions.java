package de.smartformer.articlesapi.cucumber.smokeit;

import de.smartformer.articlesapi.cucumber.commonsit.LogFileParserService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.awaitility.Awaitility;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CucumberSmokeITStepDefinitions {

    private final Logger log = LoggerFactory.getLogger(CucumberSmokeITStepDefinitions.class);

    @Value("${logging.file.name}")
    private String logFile;

    @Value("${test.endpoint}")
    private String endpointUrl;

    private String endpointPath;

    private String jsonBody;

    private HttpHeaders headers;

    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    private LogFileParserService logParserService;

    @Before
    public void setup() throws IOException  {
        Awaitility.setDefaultTimeout(Duration.ofMinutes(5));
        // Empty the logfile
        new FileOutputStream(this.logFile).close();
        // reset internal state of logfile parser service
        this.logParserService = new LogFileParserService(this.logFile);
    }

    @Given("Endpoint path is set to {string}")
    public void endpointPathIsSetTo(String endpointPath) {
        this.endpointPath = endpointPath;
    }

    @When("^I set a GET/DELETE request HEADER$")
    public void setGetDelRequestHeader() {
        this.headers = new HttpHeaders();
        this.headers.add("Accept", "application/json");
    }

    @When("^I set a POST/PUT request HEADER$")
    public void setPostPutRequestHeader() {
        this.headers = new HttpHeaders();
        this.headers.add("Accept", "application/json");
        this.headers.add("Content-Type", "application/json");
    }

    @And("^I set the RestTemplate$")
    public void setRestTemplate() {

        this.restTemplate = new TestRestTemplate();
    }

    @And("I send a dataless {string} HTTP request")
    public void sendDatalessRequest(String getOrDelete) throws IOException {

        HttpEntity<String> entity = new HttpEntity<String>(null, this.headers);

        final HttpMethod method;

        getOrDelete = getOrDelete.toLowerCase();

        if(getOrDelete.equals("get")){
            method = HttpMethod.GET;
        }else if(getOrDelete.equals("delete")){
            method = HttpMethod.DELETE;
        }else{
            method = HttpMethod.GET;
            log.error(String.format("[5001] sendDatalessRequest received method: '%s'; only 'get' or 'delete' allowed",  getOrDelete));
        }

        this.response = this.restTemplate.exchange(
                this.endpointUrl + this.endpointPath,
                method,
                entity,
                String.class);

        //try {
        //    Thread.sleep(1000);                 //1500 milliseconds is one second.
        //} catch (InterruptedException ex) {
        //    Thread.currentThread().interrupt();
        //}
    }

    @And("I preload jsonBody from file {string}")
    public void preloadJsonBodyFromFile(String fileName) throws IOException {
        this.jsonBody = new String(Files.readAllBytes(Paths.get("src/it/resources/" + fileName)));
    }

    @And("I preload jsonBody with article {string} {string}")
    public void preloadJsonBodyWithArticle(String title, String content) throws IOException {
        this.jsonBody = "{" +
        "    \"title\": \"" + title + "\",\n" +
        "    \"content\": \"" + content + "\"\n" +
        "}";
    }

    @And("I preload jsonBody with article {int} {string} {string}")
    public void preloadJsonBodyWithExistingArticle(Integer id, String title, String content) throws IOException {
        this.jsonBody = "{" +
        "    \"id\": \"" + id + "\",\n" +
        "    \"title\": \"" + title + "\",\n" +
        "    \"content\": \"" + content + "\"\n" +
        "}";
    }

    @And("I send a data {string} HTTP request")
    public void sendDataRequest(String postOrPut) {

        HttpEntity<String> entity = new HttpEntity<String>(this.jsonBody, headers);

        final HttpMethod method;

        postOrPut = postOrPut.toLowerCase();

        if(postOrPut.equals("post")){
            method = HttpMethod.POST;
        }else if(postOrPut.equals("put")){
            method = HttpMethod.PUT;
        }else{
            method = HttpMethod.GET;
            log.error(String.format("[5002] sendDataRequest received method: '%s'; only 'post' or 'put' allowed",  postOrPut));
        }

        this.response = this.restTemplate.exchange(
                this.endpointUrl + this.endpointPath,
                method,
                entity,
                String.class);

        //try {
        //    Thread.sleep(1000);                 //1500 milliseconds is one second.
        //} catch (InterruptedException ex) {
        //    Thread.currentThread().interrupt();
        //}
    }

    @Then("I receive http status {string}")
    public void receiveStatusCode(String httpStatus){

        final HttpStatus selectedHttpStatus = HttpStatus.valueOf(httpStatus);
        httpStatus = httpStatus.replace("\"", "");
        assertThat(this.response.getStatusCode()).isEqualTo(selectedHttpStatus);
    }

    @And("^I receive health status UP$")
    public void receiveHealthUp() throws JSONException {
        String expected = "{" +
                "    \"status\": \"UP\"\n" +
                "}";

        // https://www.baeldung.com/jsonassert <- for help/explanaition of JSONAssert
        JSONAssert.assertEquals(
                expected,
                this.response.getBody(),
                JSONCompareMode.LENIENT
        );
    }

    @And("I observe log code {int}")
    public void checkCode(Integer logCode) throws IOException {
        assertTrue(this.logParserService.checkCode(logCode));
    }
    
    @And("I observe log code {int} {string}")
    public void checkMatch(Integer logCode, String match) throws IOException {
        assertTrue(this.logParserService.checkMatch(logCode, match));
    }
    
    @And("I receive a list of {int} article(s)")
    public void receiveEmptyArticlesResponse(Integer numberOfArticles) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        assertThat(root.size()).isEqualTo(numberOfArticles);
    }
    
    @And("^I receive the first article$")
    public void receiveFirstArticle() throws IOException, JSONException {
        String expected = new String(Files.readAllBytes(Paths.get("src/it/resources/article.json")));

        JSONAssert.assertEquals(
                expected,
                this.response.getBody(),
                JSONCompareMode.LENIENT
        );

        // Fetch the Article ID
        // http://tutorials.jenkov.com/java-json/jackson-jsonnode.html
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        assertThat(root.get("id").asInt()).isEqualTo(1);
    }
    
    @And("I receive the article {int} {string} {string}")
    public void receiveArticle(Integer id, String title, String content) throws JsonProcessingException, JSONException {
        String expected = "{" +
        "    \"title\": \"" + title + "\",\n" +
        "    \"content\": \"" + content + "\"\n" +
        "}";

        JSONAssert.assertEquals(
                expected,
                this.response.getBody(),
                JSONCompareMode.LENIENT
        );

        // Fetch the Article ID
        // http://tutorials.jenkov.com/java-json/jackson-jsonnode.html
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        assertThat(root.get("id").asInt()).isEqualTo(id);
    }


    @And("I print all LogLines")
    public void printAllLogLines() throws IOException {
        this.logParserService.print();
    }

}
