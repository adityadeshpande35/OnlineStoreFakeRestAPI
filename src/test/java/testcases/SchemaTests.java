package testcases;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import routes.Routes;

import static io.restassured.RestAssured.given;

public class SchemaTests extends  BaseClass {

    @Test
    public  void testProductSchema()
    {
        int productId=configreader.getIntProperty("productId");

        given()
                .pathParam("id",productId)
                .when()
                .get(Routes.GET_PRODUCT_BY_ID)
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("productSchema.json"));
    }
    @Test
    public  void testCartSchema()
    {
        int cartId=configreader.getIntProperty("cartId");

        given()
                .pathParam("id",cartId)
                .when()
                .get(Routes.GET_CART_BY_ID)
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("cartSchema.json"));
    }

    @Test
    public  void testUserSchema()
    {
        int userId=configreader.getIntProperty("userId");

        given()
                .pathParam("id",userId)
                .when()
                .get(Routes.GET_USER_BY_ID)
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("userSchema.json"));
    }
}
