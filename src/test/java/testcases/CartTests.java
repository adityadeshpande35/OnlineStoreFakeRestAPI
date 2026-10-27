package testcases;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import payloads.Payload;
import pojo.Cart;
import routes.Routes;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.*;


public class CartTests extends BaseClass {

    @Test
    public void testGetAllCarts() {
        given()
                .when().get(Routes.GET_ALL_USERS)
                .then().statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testGetCartByID() {
        int cartId = configreader.getIntProperty("cartId");
        given()
                .pathParam("id", cartId)
                .when()
                .get(Routes.GET_CART_BY_ID)
                .then()
                .statusCode(200)
                .log().body()
                .body("id", equalTo(cartId));
    }

    @Test
    public void testGetCartsByDateRange() {
        String startDate = configreader.getProperty("startdate");
        String endDate = configreader.getProperty("enddate");

        Response response = given().pathParam("startdate", startDate)
                .pathParam("enddate", endDate)
                .when()
                .get(Routes.GET_CARTS_BY_DATE_RANGE)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .extract().response();

        List<String> cartDates = response.jsonPath().getList("date");

        assertThat(validateCartDatesWithinRange(cartDates, startDate, endDate), is(true));

    }

    @Test
    public void testGetUserCart() {
        int userId = configreader.getIntProperty("userId");
        given()
                .pathParam("userId", userId)
                .when()
                .get(Routes.GET_USER_CART)
                .then()
                .statusCode(200)
                .body("userId", everyItem(equalTo(userId)));
    }

    @Test
    public void testGetCartWithLimit() {
        int limit = configreader.getIntProperty("limit");
        given()
                .pathParam("limit", limit)
                .when()
                .get(Routes.GET_CARTS_WITH_LIMIT)
                .then()
                .statusCode(200)
                .body("size()", lessThanOrEqualTo(limit));
    }

    @Test
    public void testGetCartsSortedDesc() {
        Response response = given()
                .pathParam("order", "desc")
                .when()
                .get(Routes.GET_CARTS_SORTED)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .extract().response();

        List<Integer> cartIds = response.jsonPath().getList("id", Integer.class);
        assertThat(isSortedDescending(cartIds),is(true));
    }

    @Test

    public void testGetCartsSortedAsc() {
        Response response = given()
                .pathParam("order", "asc")
                .when()
                .get(Routes.GET_CARTS_SORTED)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .extract().response();

        List<Integer> cartIds = response.jsonPath().getList("id", Integer.class);
        assertThat(isSortedAscending(cartIds),is(true));
    }

    @Test
    public void testCreateCart()
    {
        int userId=configreader.getIntProperty("userId");
        Cart newCart= Payload.cartPayload(userId);

        given().contentType(ContentType.JSON)
                .body(newCart)
                .when()
                .post(Routes.CREATE_CART)
                .then()
                .statusCode(201)
                .log().body()
                .body("id",notNullValue())
                .body("userId",notNullValue())
                .body("products.size()",greaterThan(0));
    }

    @Test
    public void testUpdateCart()
    {
        int userId=configreader.getIntProperty("userId");
        int cartId=configreader.getIntProperty("cartId");
        Cart updaCart=Payload.cartPayload(userId);
        given()
                .pathParam("id",cartId)
                .contentType(ContentType.JSON)
                .body(updaCart)
                .when()
                .put(Routes.UPDATE_CART)
                .then()
                .statusCode(200)
                .body("id",equalTo(cartId))
                .body("userId",notNullValue())
                .body("products.size()",equalTo(1));
    }

    @Test
    public void testDeleteCart()
    {
        int cartId=configreader.getIntProperty("cartId");
        given()
                .pathParam("id",cartId)
                .when()
                .delete(Routes.DELETE_CART)
                .then()
                .statusCode(200);
    }
}