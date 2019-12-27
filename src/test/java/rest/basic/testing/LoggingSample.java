package rest.basic.testing;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class LoggingSample {
    @Test
    public void googlePlaceSearch(){

        RestAssured.baseURI= "https://maps.googleapis.com";

                 given().param("location","-33.8670522,151.1957362")
                .param("radius","500")
                .param("types","food")
                .param("name","harbour")
                .param("key","AIzaSyCv9bNvosrVWmIdZZ_4Vqh1Mzck1foMAIQ")
                .log().all()

                .when()
                .get("/maps/api/place/nearbysearch/json")


                .then().assertThat().statusCode(200)
                         .log().all();
    }
}
