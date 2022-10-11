import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class PetTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }
    @Test
    public void petTest() {
        // создаю питомца
        String requestBodyPost = """
                {
                  "id": 42,
                  "name": "spot",
                  "status": "available"
                }
                """;
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBodyPost)
                .when()
                .post("/pet")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("spot", response.jsonPath().getString("name"));

        String id = response.jsonPath().getString("id");

        // получаю питомца
        response = given()
                .header("Content-type", "application/json")
                .when()
                .get("/pet/" + id)
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("spot", response.jsonPath().getString("name"));

        // переименовываю питомца
        String requestBodyPut = """
                {
                  "id": 42,
                  "name": "cat"
                }
                """;
        response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBodyPut)
                .when()
                .put("/pet")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("cat", response.jsonPath().getString("name"));

        id = response.jsonPath().getString("id");

        // получаю питомца
        response = given()
                .header("Content-type", "application/json")
                .when()
                .get("/pet/" + id)
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("cat", response.jsonPath().getString("name"));

        // удаляю питомца
        response = given()
                .header("Content-type", "application/json")
                .when()
                .delete("/pet/" + id)
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
   }
}
