package trendyol.api;

import com.google.gson.JsonElement;
import endpoints.EndPoint;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestCases extends APITestCase {

    RequestSpecification requestSpecification = requestSpecification();

    @Test(priority = 1)
    public void noBooksStoredOnTheServer() {

        Response response = requestSpecification.get(EndPoint.GETBOOKS);

        int statusCode = response.getStatusCode();

        if (statusCode != 200) {
            log.error(" [ ERROR ] [ RESPONSE CODE IS NOT 200 ] [ PLEASE CHECK SERVER OR RESPONSE ] [ STATUS CODE ] : {}," +
                    "[ RESPONSE BODY ] : {}", statusCode, response.getBody().asString());
        }
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(response.getBody(), "");

    }

    @Test(priority = 2)
    public void titleAndAuthorAreRequiredFields() {

        Response response = requestSpecification.put(EndPoint.GETBOOKS);

        int statusCode = response.getStatusCode();

        if (statusCode != 400) {
            log.error(" [ ERROR ] [ RESPONSE CODE IS NOT 400 ] [ PLEASE CHECK SERVER OR RESPONSE ] [ STATUS CODE ] : {}," +
                    "[ RESPONSE BODY ] : {}", statusCode, response.getBody().asString());
        }

        String error = response.jsonPath().getString("error");
        Assert.assertEquals(statusCode, 400);
        Assert.assertEquals(error, "Field 'author' is required");
    }

    @Test(priority = 3)
    public void titleAndAuthorCannotBeEmpty() {

        JsonElement requestBody = generateRequestBody("", "");
        requestSpecification.body(requestBody);
        Response response = requestSpecification.put(EndPoint.GETBOOKS);

        int statusCode = response.getStatusCode();

        if (statusCode != 400) {
            log.error(" [ ERROR ] [ RESPONSE CODE IS NOT 400 ] [ PLEASE CHECK SERVER OR RESPONSE ] [ STATUS CODE ] : {}," +
                    "[ RESPONSE BODY ] : {}", statusCode, response.getBody().asString());
        }

        Assert.assertEquals(statusCode, 400);

        String error = response.jsonPath().getString("error");

        Assert.assertEquals(error, "Field 'author' cannot be empty.");


    }

    @Test(priority = 4)
    public void idFieldIsReadOnly() {

        JsonElement requestBody = generateRequestBodyWithIdField("Trendyol Automation", "Nursah", 34);
        requestSpecification.body(requestBody);

        Response response = requestSpecification.put(EndPoint.GETBOOKS);

        int statusCode = response.getStatusCode();

        if (statusCode != 400) {

            log.error(" [ ERROR ] [ RESPONSE CODE IS NOT 400 ] [ PLEASE CHECK SERVER OR RESPONSE ] [ STATUS CODE ] : {}," +
                    "[ RESPONSE BODY ] : {}", statusCode, response.getBody().asString());
        }

        Assert.assertEquals(statusCode, 400);

    }

    @Test(priority = 5)
    public void createANewBookAndGetCreatedBook() {

        JsonElement requestBody = generateRequestBody("Nursah's New Book", "Nursah");
        requestSpecification.body(requestBody);
        Response response = requestSpecification.put(EndPoint.GETBOOKS);

        if (response.getStatusCode() == 200) {

            log.info(" [ INFO ] [ SUCCESSFULLY CREATED A NEW BOOK ][ NEW BOOK CREATED ] [ CREATED WITH THIS REQUEST FIELD ] : {} ", requestBody.getAsJsonObject());
            Assert.assertEquals(response.getStatusCode(), 200);
        }
        Integer bookId = response.getBody().jsonPath().getInt("id");

        RequestSpecification requestSpecification = requestSpecification();
        requestSpecification.queryParam(String.valueOf(bookId));
        Response response1 = requestSpecification.get(EndPoint.GETBOOKS);

        Integer responseBookId = response.getBody().jsonPath().getInt("id");

        Assert.assertEquals(response1.getStatusCode(), 200);
        Assert.assertEquals(responseBookId, bookId);

    }

    @Test(priority = 6)
    public void cannotCreateADuplicateBook() {

        JsonElement requestBody = generateRequestBody("SAME", "GILBERT");
        requestSpecification.body(requestBody); //request bodye eklendi requestBody = requestFirst
        Response response = requestSpecification.put(EndPoint.GETBOOKS);

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        Response response1 = requestSpecification.put(EndPoint.GETBOOKS);
        int statusCode1 = response1.getStatusCode();

        if (statusCode1 != 400) {

            log.error(" [ ERROR ] [ STATUS CODE SHOULD BE 400 ] [ YOU CANNOT CREATE A DUPLICATE BOOK ]");
            Assert.assertEquals(statusCode1, 400);
        }

        String expectedErrorMessage = response.getBody().asString();
        Assert.assertEquals(expectedErrorMessage, "Another book with similar title and author already exists.");

    }


}