package rest.basic.testing;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;


public class GetRequestSample {
    @Test
    public void googlePlaceSearch(){

        RestAssured.baseURI= "https://maps.googleapis.com";

        given().param("location","-33.8670522,151.1957362")
                .param("radius","500")
                .param("types","food")
                .param("name","harbour")
                .param("key","AIzaSyCv9bNvosrVWmIdZZ_4Vqh1Mzck1foMAIQ")
                .when()
                .get("/maps/api/place/nearbysearch/json")
                .then().assertThat().statusCode(200).and()
                .contentType(ContentType.JSON).and()
                .body("results[0].name",equalTo("Harbour Bar & Kitchen")).and()
                .body("results[13].name",equalTo("World Newsagency")).and()
                .header("server","scaffolding on HTTPServer2");
    }
}
