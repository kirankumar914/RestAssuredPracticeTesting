package rest.basic.testing;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;

public class PostRequestSample {

    @Test
    public void verifyResponse(){

        //payload in string format for POST request
        String requestBody="{"+
                "\"location\": { "+
                "\"lat\": -33.866971123445,"+
                "\"lng\": 151.1958750"+
                "},"+
                "\"accuracy\": 50,"+
                "\"name\": \"Google Shoes!\","+
                "\"phone_number\": \"(02) 9374 4000\","+
                "\"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\","+
                "\"types\": [\"shoe_store\"],"+
                "\"website\": \"http://www.google.com.au/\","+
                "\"language\": \"en-AU\""+
                "}";

        RestAssured.baseURI = "https://maps.googleapis.com";
       given().
                queryParam("key", "AIzaSyCv9bNvosrVWmIdZZ_4Vqh1Mzck1foMAIQ").
                body(requestBody).
                when().
                post("/maps/api/place/add/json").
                then().assertThat().statusCode(404).and().body("status", equalTo("OK"));
    }
}
