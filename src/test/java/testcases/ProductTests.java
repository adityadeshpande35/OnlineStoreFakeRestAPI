package testcases;

import pojo.Product;
import routes.Routes;
import  utils.ConfigReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Payload;

import org.testng.ITestContext;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;






public class ProductTests extends  BaseClass{

   // 1) Test to Retrive all the products
    @Test
    public void testGetAllProducts()
    {
        given().
                when()
                .get(Routes.GET_ALL_PRODUCTS)
                .then()
                .statusCode(200)
                .body("size()",greaterThan(0))
                 .log().body();
    }

    //2) Test to Retrieve a single product by id
    @Test

    public void testGetSingleProductById()
    {
        int productId=configreader.getIntProperty("productId");
        given()
                .pathParam("id",productId)
                .when().get(Routes.GET_PRODUCT_BY_ID)
                .then().statusCode(200).log().body();
    }


    //3) Test to retrive a limited no of products
    @Test
    public void testGetLimitedProducts()
    {
        int limit=configreader.getIntProperty("limit");
        given().pathParam("limit",limit)
                .when().get(Routes.GET_PRODUCTS_WITH_LIMIT)
                .then().statusCode(200)
                .log().body()
                .body("size()",equalTo(limit));
    }

    // 4) Test to retrieve products sorted in descending order
    @Test
    public void testGetSortedProductsDesc()
    {
        Response response=given().pathParam("order","desc").when()
                .get(Routes.GET_PRODUCTS_SORTED)
                .then().log().all().statusCode(200)
                .extract().response();

        List<Integer> productIds=response.jsonPath().getList("id", Integer.class);
        assertThat(isSortedDescending(productIds),is(true));

    }



    // 5) Test to retrieve products sorted in asccending order
    @Test
    public void testGetSortedProductsAsc()
    {
        Response response=given().log().all().pathParam("order","Asc").when()
                .get(Routes.GET_PRODUCTS_SORTED)
                .then().log().all().statusCode(200)
                .extract().response();

        List<Integer> productIds=response.jsonPath().getList("id", Integer.class);
        assertThat(isSortedAscending(productIds),is(true));

    }



//6)Test to get all product Categories
@Test
    public void testGetAllCategories()
    {
        given().log().all().
                when()
                .get(Routes.GET_ALL_CATEGORIES)
                .then().log().all().
                statusCode(200)
                .body("size()",greaterThan(0));
    }

    // 7)Test to get products by category
    @Test
    public void testGetProductsByCategory()
    {
        given().log().all()
                .pathParam("category","electronics")
                .when()
                .get(Routes.GET_PRODUCTS_BY_CATEGORY)
                .then()
                .statusCode(200)
                .body("size()",greaterThan(0))
                 .body("category",everyItem(notNullValue()))
                .body("category",everyItem(equalTo("electronics")))
            .log().body();
    }

    // 8)Test to add a new product
    @Test
    public void testAddNewProduct()
    {
        Product newProduct=Payload.productPayload();
        int productId=given()
                .contentType(ContentType.JSON)
                .body(newProduct)
                .when()
                .post(Routes.CREATE_PRODUCT)
                .then()
                .log().body()
                .statusCode(201)
                .body("id",notNullValue())
                .body("title",equalTo(newProduct.getTitle()))
                .extract().jsonPath().getInt("id");

        System.out.println(productId);

    }

    //9) Test to update an exisiting product
    @Test
    public void testUpdateProduct()
    {
        int productId=configreader.getIntProperty("productId");

        Product updatedPayload=Payload.productPayload();
        given().contentType(ContentType.JSON)
                .body(updatedPayload)
                .pathParam("id",productId)
                .when()
                .put(Routes.UPDATE_PRODUCT)
                .then()
                .log().body()
                .statusCode(200)
                .body("title",equalTo(updatedPayload.getTitle()));
    }


    //10)Test to delete a product
    @Test
    public void testDeleteProduct()
    {
        int productId=configreader.getIntProperty("productId");
        given().
                pathParam("id",productId)
                .when().delete(Routes.DELETE_PRODUCT)
                .then().statusCode(200);

    }

}
