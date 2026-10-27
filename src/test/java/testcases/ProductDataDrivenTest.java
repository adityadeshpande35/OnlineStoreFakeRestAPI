package testcases;

import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.Test;
import pojo.Product;
import routes.Routes;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ProductDataDrivenTest  extends  BaseClass{

    @Test(dataProvider = "jsonDataProvider",dataProviderClass = utils.DataProviders.class)
    public void testAddNewProduct(Map<String,String> data)

    {
        String title=data.get("title");
        double price=Double.parseDouble(data.get("price"));
        String category=data.get("category");
        String description=data.get("description");
        String image=data.get("image");

        //String title,double price,String description,String image,String category

        Product newProduct=new Product(title,price,description,image,category);

        int productId=given()
                .contentType(ContentType.JSON)
                .body(newProduct)
                .when()
                .post(Routes.CREATE_PRODUCT)
                .then()
                .log().body()
                .statusCode(201)
                .body("id", CoreMatchers.notNullValue())
                .body("title",equalTo(newProduct.getTitle()))
                .extract().jsonPath().getInt("id");

        System.out.println("product Id is"+ productId);

        //Delete product
        given()
                .pathParam("id",productId)
                .when()
                .delete(Routes.DELETE_PRODUCT)
                .then()
                .statusCode(200);

        System.out.println("deleted product Id is"+ productId);


    }



}
