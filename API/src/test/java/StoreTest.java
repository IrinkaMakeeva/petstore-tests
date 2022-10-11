import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class StoreTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }
    @Test
    public void storeTest() {
        // создаю заказ
        String requestBodyPost = """
                {
                  "id": 10,
                  "petId": 10,
                  "quantity": 10,
                  "status": "placed",
                  "complete": true
                }
                """;
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBodyPost)
                .when()
                .post("/store/order")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(10, response.jsonPath().getInt("quantity"));

        String id = response.jsonPath().getString("id");

        // получаю заказ
        response = given()
                .header("Content-type", "application/json")
                .when()
                .get("/store/order/" + id)
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(10, response.jsonPath().getInt("quantity"));

        id = response.jsonPath().getString("id");

        // удаляю заказ
        response = given()
                .header("Content-type", "application/json")
                .when()
                .delete("/store/order/" + id)
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());

        // проверяю, что заказ удален
        response = given()
                .header("Content-type", "application/json")
                .when()
                .get("/store/order/" + id)
                .then()
                .extract().response();
        Assertions.assertEquals(404, response.statusCode());

   }
}
