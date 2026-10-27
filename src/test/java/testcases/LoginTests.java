package testcases;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import payloads.Payload;
import pojo.Login;
import routes.Routes;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;


public class LoginTests  extends  BaseClass{


    @Test
    public void testInvalidUserLogin()
    {
        Login newLogin= Payload.loginPayload();

        given()
                .contentType(ContentType.JSON)
                .body(newLogin)
                .when()
                .post(Routes.AUTH_LOGIN)
                .then()
                .log().body()
                .statusCode(401)
                .body(containsString("username or password is incorrect"));
    }

    @Test
    public void testValidUserLogin()
    {
     String username=configreader.getProperty("username");
     String password=configreader.getProperty("password");
     Login newLogin=new Login(username,password);
        given()
                .contentType(ContentType.JSON)
                .body(newLogin)
                .when()
                .post(Routes.AUTH_LOGIN)
                .then()
                .log().body()
                .statusCode(201)
                .body("token", notNullValue());
    }
}
