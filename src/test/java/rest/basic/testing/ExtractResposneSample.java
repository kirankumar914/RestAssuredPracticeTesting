package rest.basic.testing;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ExtractResposneSample {

    @Test
    public void googlePlaceSearch(){

        RestAssured.baseURI= "https://maps.googleapis.com";

       Response res=given().param("location","-33.8670522,151.1957362")
                .param("radius","500")
                .param("types","food")
                .param("name","harbour")
                .param("key","AIzaSyCv9bNvosrVWmIdZZ_4Vqh1Mzck1foMAIQ")
                .when()
                .get("/maps/api/place/nearbysearch/json")
                .then().assertThat().statusCode(200).and()
                .contentType(ContentType.JSON).and()
                .body("results[0].name",equalTo("Harbour Bar & Kitchen")).and()
                .header("server","scaffolding on HTTPServer2").
               extract().response();

       System.out.println(res);

       //convert the object into string
        String response=res.asString();
        System.out.println(response);
      // Convert the string into JSON Object

        JsonPath jpath=new JsonPath(response);
        String name=jpath.get("results[2].name");

        System.out.println("price level for this 2nd array is ---->"+name);


    }

}
