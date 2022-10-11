import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class UserTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }
    @Test
    public void UserTest() {
        // создаю пользователя
        String username = "irinka";
        String requestBodyPost = """
                {
                  "id": 10,
                  "username": "irinka",
                  "password": "1234"
                }
                """;
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBodyPost)
                .when()
                .post("/user")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());

        // получаю пользователя
        response = given()
                .header("Content-type", "application/json")
                .when()
                .get("/user/" + username)
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("1234", response.jsonPath().getString("password"));

        // переименовываю пользователя
        String requestBodyPut = """
                {
                  "id": 10,
                  "username": "irinka",
                  "password": "qwerty"
                }
                """;
        response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBodyPut)
                .when()
                .put("/user/" + username)
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());

        // получаю пользователя
        response = given()
                .header("Content-type", "application/json")
                .when()
                .get("/user/" + username)
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("qwerty", response.jsonPath().getString("password"));

        // удаляю пользователя
        response = given()
                .header("Content-type", "application/json")
                .when()
                .delete("/user/" + username)
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
    }
}

