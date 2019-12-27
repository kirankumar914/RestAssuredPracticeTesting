package rest.basic.testing;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JsonParserSample {

    @Test
    public void googlePlaceSearch(){

        RestAssured.baseURI= "https://maps.googleapis.com";

       Response res= given().param("location","-33.8670522,151.1957362")
                .param("radius","500")
                .param("types","food")
                .param("name","harbour")
                .param("key","AIzaSyCv9bNvosrVWmIdZZ_4Vqh1Mzck1foMAIQ")

                .when()
                .get("/maps/api/place/nearbysearch/json")


                .then().assertThat().statusCode(200)
                .extract().response();
       String response=res.asString();
       System.out.println(response);

        JsonPath jsonresponse=new JsonPath(response);
        int arraysize=jsonresponse.getInt("results.size()");

        System.out.println(arraysize);

        for(int i=0;i<arraysize;i++)
        {
            System.out.println(jsonresponse.getString("results["+i+"].name"));
        }
        System.out.println(System.getProperty("user.dir"));
    }
}
