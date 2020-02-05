package trendyol.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import dto.BooksDto;
import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import config.TestConfig;

import java.util.Objects;

@Slf4j
public class APITestCase {

    public static String API_ROOT = Objects.requireNonNull(TestConfig.getProperty("api_base_url"));
    public static final Logger log = LoggerFactory.getLogger(APITestCase.class);

    public RequestSpecification requestSpecification() {
        RestAssured.baseURI = API_ROOT;
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Accept", "application/json");
        requestSpecification.header("Content-Type", "application/json");
        return requestSpecification;
    }

    public static JsonElement generateRequestBody(String title, String author) {
        BooksDto booksDto = new BooksDto();
        booksDto.setTitle(title);
        booksDto.setAuthor(author);
        Gson gson = new Gson();
        return gson.toJsonTree(booksDto);
    }

    public static JsonElement generateRequestBodyWithIdField(String title, String author, Integer id) {
        JsonElement requestJson = generateRequestBody(title, author);
        requestJson.getAsJsonObject().addProperty("id", id);
        return requestJson;
    }
}
